package org.iraiders.robot2018.robot.commands;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A simple command to report encoder values to SmartDashboard
 * Useful for debugging / stats
 */
public class EncoderReporter extends Command {
  private WPI_TalonSRX talons[];
  
  public EncoderReporter(WPI_TalonSRX... talons) {
    System.arraycopy(talons, 0, this.talons, 0, talons.length);
  }
  
  @Override
  protected void execute() {
    // Not sure if this is an intensive / damaging operation, look into it sometime
    for (WPI_TalonSRX talon : talons) {
      talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
      SmartDashboard.putNumber(talon.getDescription(), talon.getSelectedSensorPosition(0));
    }
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
}
