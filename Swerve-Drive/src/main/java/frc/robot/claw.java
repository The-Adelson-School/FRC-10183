import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;

public class ResistanceMotor extends TimedRobot {

    private CANSparkMax motor;
    private XboxController controller;

    private final double MAX_SPEED = 1.0; // max speed change if u want
    private final double RESISTANCE_THRESHOLD = 15.0; resistence threshold
    private final int GEAR_RATIO = 27;

    private boolean forwardDirection = true;

    @Override
    public void robotInit() {
        motor = new CANSparkMax(7, MotorType.kBrushless); 
        controller = new XboxController(0); // replace with controller port
    }

    @Override
    public void teleopPeriodic() {
        if (controller.getLeftBumperPressed()) {
            forwardDirection = !forwardDirection; 
        }

        double current = Math.abs(motor.getOutputCurrent()); 
        double speed = forwardDirection ? MAX_SPEED : -MAX_SPEED;

        if (current > RESISTANCE_THRESHOLD) {
            motor.set(0.0); //
        } else {
            motor.set(speed); //
        }
    }
}
