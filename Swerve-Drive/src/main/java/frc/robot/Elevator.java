
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.SparkMax;
import com.revrobotics.SparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;

public class ElevatorSubsystem extends SubsystemBase {

    private SparkMax elevatorMotor;
    private Timer timer;

    private final double STAGE_1_HEIGHT = 0.3; // feet
    private final double STAGE_2_HEIGHT = 0.66; // feet
    private final double STAGE_3_HEIGHT = 1.0; // feet
    private final double TOTAL_CLIMB_TIME = 2.0; // seconds
    private final double GEAR_RATIO = 9.0; // 9:1 cartridge
    private final double FEET_PER_ROTATION = 0.116; //Replace with your actual value

    private double targetHeight = 0.0; // feet
    private double currentHeight = 0.0; // feet
    private double startTime = 0.0;

    public ElevatorSubsystem(int motorCANID) {
        elevatorMotor = new SparkMax(motorCANID, MotorType.kBrushless);
        timer = new Timer();
        elevatorMotor.getEncoder().setPosition(0); // Reset Encoder at start
    }

    public void setStage(double stageHeight) {
        targetHeight = stageHeight;
        startTime = timer.getFPGATimestamp();
    }

    @Override
    public void periodic() {
        double elapsedTime = timer.getFPGATimestamp() - startTime;
        if (elapsedTime <= TOTAL_CLIMB_TIME) {
            currentHeight = calculateCurrentHeight(elapsedTime);
            double motorRotations = feetToRotations(currentHeight);
            elevatorMotor.getEncoder().setPosition(motorRotations);
            double speed = calculateSpeed(currentHeight, targetHeight, elapsedTime);
            elevatorMotor.set(speed);
        } else {
            double motorRotations = feetToRotations(targetHeight);
            elevatorMotor.getEncoder().setPosition(motorRotations);
            elevatorMotor.set(calculateSpeed(targetHeight, targetHeight, TOTAL_CLIMB_TIME));
        }
    }

    private double calculateCurrentHeight(double elapsedTime) {
        if (elapsedTime > TOTAL_CLIMB_TIME)
            return targetHeight;
        return targetHeight * (elapsedTime / TOTAL_CLIMB_TIME);
    }

    private double feetToRotations(double feet) {
        return (feet / FEET_PER_ROTATION) * GEAR_RATIO;
    }

    private double calculateSpeed(double currentHeight, double targetHeight, double elapsedTime) {
        double distanceToTarget = targetHeight - currentHeight;
        if (elapsedTime <= 0) {
            return 0;
        }

        if (elapsedTime >= TOTAL_CLIMB_TIME) {
            return 0;
        }
        return distanceToTarget / (TOTAL_CLIMB_TIME);
    }

    public double getStage1Height() {
        return STAGE_1_HEIGHT;
    }

    public double getStage2Height() {
        return STAGE_2_HEIGHT;
    }

    public double getStage3Height() {
        return STAGE_3_HEIGHT;
    }
}

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.ElevatorSubsystem;

public class RobotContainer {

    private final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem(1); // Replace 1 with your CAN ID
    private final XboxController controller = new XboxController(0); // Replace 0 with your controller port

    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        controller.getXButtonPressed(() -> elevatorSubsystem.setStage(elevatorSubsystem.getStage1Height()));
        controller.getYButtonPressed(() -> elevatorSubsystem.setStage(elevatorSubsystem.getStage2Height()));
        controller.getAButtonPressed(() -> elevatorSubsystem.setStage(elevatorSubsystem.getStage3Height()));
    }

    public ElevatorSubsystem getElevatorSubsystem() {
        return elevatorSubsystem;
    }
}
