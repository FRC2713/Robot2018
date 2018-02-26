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
  private final NetworkTable t = nti.getTable("VisionProcessing");
  
  
  private double travelled = 0;
  private int rotation = 0;
  private boolean turningRight = true;
  
  private double lastDistance = -1;
  private double lastAngle = -1;
  private int lastHeartbeatValue = -1;
  
  private int turnAngle = 0;
  private double distanceFromTarget = -1;
  
  private boolean hasReachedSwitch = false;
  
  NetworkTableEntry degrees = t.getEntry("degrees"); // TODO make 'degrees' more explicit (is it degrees off, degrees to change?)
  NetworkTableEntry heartbeat = t.getEntry("heartbeat"); // TODO this should be updated whenever the vision loops to ensure that our values are recent
  NetworkTableEntry distance = t.getEntry("distance"); // TODO this should be updated whenever the vision loops to ensure that our values are recent
  NetworkTableEntry x = t.getEntry("x");
  NetworkTableEntry y = t.getEntry("y");
  NetworkTableEntry inches_per_pixel = t.getEntry("ipp");
  NetworkTableEntry screen_width = t.getEntry("screen_width");
  
  public VisionAuto(DriveSubsystem driveSubsystem, ArmSubsystem armSubsystem) {
    requires(driveSubsystem);
    this.driveSubsystem = driveSubsystem;
    this.armSubsystem = armSubsystem;
  }
  
  @Override
  protected void initialize() {
    RobotMap.frontUltrasonic.setAutomaticMode(true);
    
    degrees.setDouble(-1);
    distance.setDouble(-1);
    x.setDouble(-1);
    y.setDouble(-1);
    inches_per_pixel.setDouble(-1);
    lastHeartbeatValue = heartbeat.getNumber(0).intValue();
  }
  
  @Override
  protected void execute() {
    
    if (!hasReachedSwitch) {
      System.out.println(lastHeartbeatValue);
      System.out.println(heartbeat.getDouble(-1));
      /*if (heartbeat.getNumber(-2).intValue() == lastHeartbeatValue) {
        //frame has not updated
        
      } else {*/
      //System.out.println(distance.getDouble(-1));
      if (lastDistance == -1) {
        if (travelled > 5) {
          if (turningRight) {
            if (rotation < 90) {
              if(rotation > 80){
                driveSubsystem.setDriveSpeed(0, 0);
                return;
              }else {
                rotation += 3;
                driveSubsystem.setDriveSpeed(0.3, -0.3);
                System.out.println("turning right");
              }
            } else {
              turningRight = false;
            }
            
          } else {
            if (rotation > -90) {
              if(rotation < -80){
                driveSubsystem.setDriveSpeed(0, 0);

                return;
              }else {
                rotation -= 3;
                driveSubsystem.setDriveSpeed(-0.3, 0.3);
                System.out.println("turning left");
              }
            } else {
              turningRight = true;
            }
          }
      
        } else {
          driveSubsystem.setDriveSpeed(0.2);
          //System.out.println(travelled);
          travelled += 0.2;
        }
    
    
      } else {
    
        if (lastDistance != distance.getDouble(-2.0)) {
          System.out.println("found target");

          lastDistance = distance.getDouble(-2.0);
          lastAngle = degrees.getDouble(-2.0);
          //calculate or recalculate trajectory
          int xoffset = (int) x.getDouble(-2) - (int) (screen_width.getDouble(-2.0) / 2);
          turnAngle = (int) Math.asin(inches_per_pixel.getDouble(-2.0) * xoffset / lastDistance);
          distanceFromTarget = lastDistance;
        } else {
          //continue trajectory path
          if (turnAngle > 0) {
            turnAngle -= 5;
            driveSubsystem.setDriveSpeed(0.3, -0.3);
            System.out.println("aiming");

          } else if (turnAngle < 0) {
            turnAngle += 5;
            driveSubsystem.setDriveSpeed(-0.3, 0.3);
            System.out.println("aiming");

          } else {
            System.out.println(RobotMap.frontUltrasonic.getRangeInches());
            if (RobotMap.frontUltrasonic.getRangeInches() < 5) {
              driveSubsystem.setDriveSpeed(0, 0);
              System.out.println("here");

              new ArmCommand(armSubsystem, ArmSubsystem.ArmPosition.SWITCH_DELIVER).start();
            } else {
              if (distanceFromTarget < 5.0) {
                driveSubsystem.setDriveSpeed(0.3, 0.3);
                System.out.println("almost there");

                distanceFromTarget -= 0.01;
              } else {
                driveSubsystem.setDriveSpeed(0.8, 0.8);
                System.out.println("approaching from afar");

                distanceFromTarget -= 0.01;
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
