// Copyright 2019 FRC Team 3476 Code Orange

package frc.utility;

import com.revrobotics.*;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel;

/**
 * Sends only new commands to the Neo to reduce CAN usage.
 */
public class LazyCANSparkMax extends SparkMax {
	
	private double prevValue = 0;
	private ControlType prevControlMode = ControlType.kVoltage;
	

	public LazyCANSparkMax(int deviceNumber, SparkLowLevel.MotorType type) {
		super(deviceNumber, type);
		//enableVoltageCompensation(true);
		//configVoltageCompSaturation(12, 10);
	
	}

	@Override
	public void set(double outputValue) {
		//return;
			
		if (outputValue != prevValue) {
			super.set(outputValue);
			prevValue = outputValue;
			
		}
	
	}

	public double getSetpoint() {
		return prevValue;
	}
}