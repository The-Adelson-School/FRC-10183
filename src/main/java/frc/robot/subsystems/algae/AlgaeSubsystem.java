package frc.robot.subsystems.algae;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utility.LazyCANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.*;
import frc.robot.Constants.AlgaeConstants;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class AlgaeSubsystem extends SubsystemBase {
    LazyCANSparkMax armMotor;
    LazyCANSparkMax intakeMotor;

    public AlgaeSubsystem(int armID, int intakeID){
    armMotor = new LazyCANSparkMax(armID, MotorType.kBrushless);
    intakeMotor = new LazyCANSparkMax(intakeID, MotorType.kBrushless);
    }

    public void defaultCommand(){
        armMotor.set(0);
        intakeMotor.set(0);
    }
    
    public void raiseArm(){
        if(armMotor.getEncoder().getPosition() > AlgaeConstants.MAX_HEIGHT){
            armMotor.set(-0.8);
        }else{
            armMotor.set(0);
        }
    }

    public void lowerArm(){
        if(armMotor.getEncoder().getPosition() < AlgaeConstants.MIN_HEIGHT){
            armMotor.set(0.8);
        }else{
            armMotor.set(0);
        }
    
    }


    public void setIntakeSpeed(double speed){
        intakeMotor.set(speed);
    }

}