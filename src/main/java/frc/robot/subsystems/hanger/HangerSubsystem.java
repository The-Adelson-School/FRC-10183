package frc.robot.subsystems.hanger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.utility.LazyCANSparkMax;
import frc.robot.Constants.HangerConstants;
import edu.wpi.first.math.controller.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command; 
import static edu.wpi.first.units.Units.Seconds;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class HangerSubsystem extends SubsystemBase {
    
    LazyCANSparkMax hangerMotor;
    PIDController hangerController = new PIDController(0.00003, 0, 0);

    int stage = 0;
    boolean active = false;
    
    public HangerSubsystem(int hangerID){
        hangerMotor = new LazyCANSparkMax(hangerID, MotorType.kBrushless);
        SmartDashboard.putBoolean("Hanger Active", active);
    }

    private void goToPosition(int hangerSetpoint){
        hangerMotor.set(MathUtil.clamp(hangerController.calculate(hangerMotor.getEncoder().getPosition()*HangerConstants.DEGREES_PER_ROTATION, hangerSetpoint), -1.0, 1.0));
    }


    public void increaseStage(){
        if(stage < 2){
            stage++;
        }
    }

    public void decreaseStage(){
        if(stage > 0){
            stage--;
        }
    }

    public void setStage(int newStage){
        if(newStage >= 0 && newStage <= 2){
            stage = newStage;
        }
        engageStage();
    }

    private void engageStage(){
        if(stage == 0){
            goToPosition(HangerConstants.STAGE_ZERO);
            active = false;
        }else if(stage == 1){
            goToPosition(HangerConstants.STAGE_ONE);
            active = true;
        }else if(stage == 2){
            goToPosition(HangerConstants.STAGE_TWO);
           active = true;
        }
        SmartDashboard.putBoolean("Hanger Active", active);
    }

    private void defaultCommand(){
        engageStage();
    }
}
