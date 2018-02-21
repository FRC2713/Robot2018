package org.iraiders.robot2018.robot.commands.auto;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.commands.arm.ArmCommand;
import org.iraiders.robot2018.robot.subsystems.ArmSubsystem;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;

public class VisionAuto extends Command {
  private final DriveSubsystem driveSubsystem;
  private final ArmSubsystem armSubsystem;
  private final NetworkTableInstance nti = NetworkTableInstance.getDefault();
  private final NetworkTable t = nti.getTable("VisionProcessor");
  
  
  private int travelled = 0;
  private int rotation = 0;
  private boolean turningRight = true;
  
  private double lastDistance = -1;
  private double lastAngle = -1;
  private int lastHeartbeatValue = -1;
  
  private int turnAngle = 0;
  private double distanceFromTarget = -1;
  
  private boolean hasReachedSwitch = false;
  public VisionAuto(DriveSubsystem driveSubsystem, ArmSubsystem armSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.armSubsystem = armSubsystem;
  }
  
  @Override
  protected void initialize() {
  
  }
  
  @Override
  protected void execute() {
    NetworkTableEntry degrees = t.getEntry("degrees"); // TODO make 'degrees' more explicit (is it degrees off, degrees to change?)
    NetworkTableEntry heartbeat = t.getEntry("heartbeat"); // TODO this should be updated whenever the vision loops to ensure that our values are recent
    NetworkTableEntry distance = t.getEntry("distance"); // TODO this should be updated whenever the vision loops to ensure that our values are recent
    NetworkTableEntry x = t.getEntry("x");
    NetworkTableEntry y = t.getEntry("y");
    NetworkTableEntry inches_per_pixel = t.getEntry("ipp");
    NetworkTableEntry screen_width = t.getEntry("screen_width");
    if (!hasReachedSwitch) {
      if (heartbeat.getNumber(-2).intValue() == lastHeartbeatValue) {
        //frame has not updated
        return;
      } else {
        if (lastDistance == -1 && distance.getNumber(-1).intValue() == -1) {
          if (travelled > 20) {
            if (turningRight) {
              if (rotation < 90) {
                rotation += 1;
                driveSubsystem.setDriveSpeed(0.2, -0.2);
              } else {
                turningRight = false;
              }
            } else {
              if (rotation > -90) {
                rotation -= 1;
                driveSubsystem.setDriveSpeed(-0.2, 0.2);
              } else {
                turningRight = true;
              }
            }
        
          } else {
            driveSubsystem.setDriveSpeed(0.2);
            travelled += 0.2;
          }
      
      
        } else {
      
          if (lastDistance != distance.getDouble(-2.0)) {
            lastDistance = distance.getDouble(-2.0);
            lastAngle = degrees.getDouble(-2.0);
            //calculate or recalculate trajectory
            int xoffset = (int) x.getDouble(-2) - (int) (screen_width.getDouble(-2.0) / 2);
            turnAngle = (int) Math.asin(inches_per_pixel.getDouble(-2.0) * xoffset / lastDistance);
            distanceFromTarget = lastDistance;
          } else {
            //continue trajectory path
            if (turnAngle > 0) {
              turnAngle -= 1;
              driveSubsystem.setDriveSpeed(0.2, -0.2);
          
            } else if (turnAngle < 0) {
              turnAngle += 1;
              driveSubsystem.setDriveSpeed(-0.2, 0.2);
            } else {
              if (RobotMap.frontUltrasonic.getRangeInches() < 3) {
                driveSubsystem.setDriveSpeed(0, 0);
                new ArmCommand(armSubsystem, ArmSubsystem.ArmPosition.SWITCH_DELIVER).start();
              } else {
                if (distanceFromTarget < 3.0) {
                  driveSubsystem.setDriveSpeed(0.3, 0.3);
                  distanceFromTarget -= 0.01;
                } else {
                  driveSubsystem.setDriveSpeed(0.7, 0.7);
                  distanceFromTarget -= 0.01;
                }
              }
          
            }
          }
        }
      }
      lastHeartbeatValue = heartbeat.getNumber(lastHeartbeatValue).intValue();
    }
  }
  
  @Override
  protected boolean isFinished() {
    return hasReachedSwitch;
  }
}
