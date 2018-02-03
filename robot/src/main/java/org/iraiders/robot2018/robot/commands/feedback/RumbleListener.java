package org.iraiders.robot2018.robot.commands.feedback;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;

import javax.measure.Measure;
import javax.measure.quantity.Length;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;

public class RumbleListener extends Command {
  private static final Measure<Double, Length> sonicRumbleRange = Measure.valueOf(1.5d, NonSI.FOOT);
  private static final GenericHID.RumbleType rumbleType = GenericHID.RumbleType.kRightRumble;
  
  private boolean resetToNothing = false;
  
  @Override
  protected void initialize() {
    RobotMap.frontUltrasonic.setAutomaticMode(true);
  }
  
  @Override
  protected void execute() {
    double ultrasonicDistance = RobotMap.frontUltrasonic.getRangeMM();
    double sonicThreshold = sonicRumbleRange.to(SI.MILLIMETER).getValue();
    SmartDashboard.putNumber("frontUltrasonicDistance", ultrasonicDistance);
    if (ultrasonicDistance < sonicThreshold) {
      resetToNothing = false;
      double rumbleValue = 1-(ultrasonicDistance / sonicThreshold); // https://www.desmos.com/calculator/jbbvxxk1xb
      OI.rumbleController(OI.getXBoxController(), rumbleValue, 0, rumbleType);
    } else if (!resetToNothing) {
      OI.rumbleController(OI.getXBoxController(), 0, 0, rumbleType);
      resetToNothing = true;
    }
  }
  
  @Override
  protected void end() {
    OI.rumbleController(OI.getXBoxController(), 0, 0, rumbleType);
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
}
