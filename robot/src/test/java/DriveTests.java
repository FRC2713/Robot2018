import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;
import org.junit.Test;

public class DriveTests {
  
  // Slew
  @Test public void slewSlowImmedietly() {
    double currentSpeed = .9;
    double targetSpeed = -.1;
    assert DriveSubsystem.slewLimit(targetSpeed, currentSpeed, .2) == -.1;
  }
  
  @Test public void slewWillNotSurpassTarget() {
    double currentSpeed = .4;
    double targetSpeed = .6;
    for (int i = 0; i < 4; i++) {
      currentSpeed += .1;
      assert DriveSubsystem.slewLimit(targetSpeed, currentSpeed, .1) == targetSpeed;
    }
  }
  
  @Test public void slewUsesNegativesCorrectly() {
    assert DriveSubsystem.slewLimit(-.6, -.4, .2) == -.6;
  }
}
