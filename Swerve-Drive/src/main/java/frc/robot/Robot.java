// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends TimedRobot {

    private RobotContainer robotContainer;
    private XboxController controller;
    private ElevatorSubsystem elevator;

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        controller = new XboxController(0); // Initialize the controller
        elevator = robotContainer.getElevatorSubsystem(); //get elevator subsystem.
    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();

  
        if (controller.getXButtonPressed()) {
            elevator.setStage(elevator.getStage1Height());
        } else if (controller.getYButtonPressed()) {
            elevator.setStage(elevator.getStage2Height());
        } else if (controller.getAButtonPressed()) {
            elevator.setStage(elevator.getStage3Height());
        }
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }
}
