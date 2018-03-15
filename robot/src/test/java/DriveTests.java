import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;
import org.junit.Test;

public class DriveTests {
  
  // Slew
  @Test public void slewSlowImmedietly() {
    double currentSpeed = .9;
    double targetSpeed = -.1;
    assert DriveSubsystem.slewLimit(targetSpeed, currentSpeed, .01) == -.1;
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
    assert DriveSubsystem.slewLimit(-.6, -.4, .2) == -.6; // Negatives
    assert DriveSubsystem.slewLimit(-.2, -.4, .2) == -.2; // Negatives becoming more positive
    assert DriveSubsystem.slewLimit(0, -.4, .01) == 0; // Negatives slowing
  }
}
