package org.iraiders.robot2018.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.iraiders.robot2018.robot.OI;
import org.iraiders.robot2018.robot.RobotMap;

import javax.measure.Measure;
import javax.measure.quantity.Length;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;

public class RumbleListener extends Command {
  private static final Measure<Double, Length> sonicRumbleRange = Measure.valueOf(2d, NonSI.FOOT);
  private static final GenericHID.RumbleType rumbleType = GenericHID.RumbleType.kRightRumble;
  
  @Override
  protected void execute() {
    double ultrasonicDistance = RobotMap.frontUltrasonic.getRangeMM();
    double sonicThreshold = sonicRumbleRange.to(SI.MILLIMETER).getValue();
    if (ultrasonicDistance < sonicThreshold) {
      double rumbleValue = 1-(ultrasonicDistance / sonicThreshold); // https://www.desmos.com/calculator/jbbvxxk1xb
      OI.rumbleController(OI.getXBoxController(), rumbleValue, 0, rumbleType);
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
