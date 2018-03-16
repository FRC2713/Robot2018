package org.iraiders.robot2018.robot.commands.auto;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import openrio.powerup.MatchData;
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
  private boolean hasReachedSwitch = false;
  private NetworkTableEntry degrees = t.getEntry("degrees"); // TODO make 'degrees' more explicit (is it degrees off, degrees to change?)
  private NetworkTableEntry heartbeat = t.getEntry("heartbeat"); // TODO this should be updated whenever the vision loops to ensure that our values are recent
  private NetworkTableEntry distance = t.getEntry("distance"); // TODO this should be updated whenever the vision loops to ensure that our values are recent
  private NetworkTableEntry x = t.getEntry("x");
  private NetworkTableEntry y = t.getEntry("y");
  private NetworkTableEntry inches_per_pixel = t.getEntry("ipp");
  private NetworkTableEntry screen_width = t.getEntry("screen_width");
  
  public VisionAuto(DriveSubsystem driveSubsystem, ArmSubsystem armSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.armSubsystem = armSubsystem;
  }
  
  @Override
  protected void initialize() {
  
  }
  private void update(){
    lastDistance = distance.getDouble(-1);
    lastAngle = degrees.getDouble(-1);
  }
  
  @Override
  protected void execute() {
  
    PathfindingAuto.MatchStartPosition pos = PathfindingAuto.MatchStartPosition.get(DriverStation.getInstance().getLocation());
    MatchData.OwnedSide ownedSide = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
    if(pos == PathfindingAuto.MatchStartPosition.LEFT ){
      if(ownedSide == MatchData.OwnedSide.LEFT){
        //target is in front
      }else if(ownedSide == MatchData.OwnedSide.RIGHT){
        //target is on other side (right)
      }
    }else if(pos == PathfindingAuto.MatchStartPosition.MIDDLE){
      if(ownedSide == MatchData.OwnedSide.LEFT){
        //target is left
      }else if(ownedSide == MatchData.OwnedSide.RIGHT){
        //target is right
      }
    }else if(pos == PathfindingAuto.MatchStartPosition.RIGHT){
      if(ownedSide == MatchData.OwnedSide.LEFT){
        //target is on other side (left)
      }else if(ownedSide == MatchData.OwnedSide.RIGHT){
        //target is in front
      }
    }
    
    
    
    if (lastDistance == -1) {
      if (travelled > 100) {//move forward at start
        update();
        double turnSpeedScan = 0.4;
        if (turningRight) {
          if (rotation < 90) {
            rotation += 1;
            if (rotation < 80){
              driveSubsystem.setDriveSpeed(turnSpeedScan, -turnSpeedScan);
            }else{
              driveSubsystem.setDriveSpeed(0, 0);
            }
          } else {
            turningRight = false;
          }
        } else {
          if (rotation > -90) {
            rotation -= 1;
            if (rotation > -80) {
              driveSubsystem.setDriveSpeed(-turnSpeedScan, turnSpeedScan);
            }else{
              driveSubsystem.setDriveSpeed(0, 0);
            }
          } else {
            turningRight = true;
          }
        }
        
      } else {
        driveSubsystem.setDriveSpeed(0.2);
        travelled += 1;
      }
      
      
    } else {
      //calculate or recalculate trajectory
      if (lastDistance != distance.getDouble(-1) && distance.getDouble(-1) != -1) {
        update();
        int xoffset = (int) x.getDouble(-2) - (int) (screen_width.getDouble(-1) / 2);
        turnAngle = (int) Math.asin(inches_per_pixel.getDouble(-1) * xoffset / lastDistance);
        
      }
      //continue trajectory path
      double turnSpeedAdjust = 0.4;
      if (turnAngle > 0) {
        turnAngle -= 1;
        driveSubsystem.setDriveSpeed(turnSpeedAdjust, -turnSpeedAdjust);
      } else if (turnAngle < 0) {
        turnAngle += 1;
        driveSubsystem.setDriveSpeed(-turnSpeedAdjust, turnSpeedAdjust);
      } else {
        if (RobotMap.frontUltrasonic.getRangeInches() < 3) {
          driveSubsystem.setDriveSpeed(0, 0);
          new ArmCommand(armSubsystem, ArmSubsystem.ArmPosition.SWITCH_DELIVER).start();
          hasReachedSwitch = true;
        } else if (RobotMap.frontUltrasonic.getRangeInches() < 24) {
          driveSubsystem.setDriveSpeed(0.3, 0.3);
        } else{
          driveSubsystem.setDriveSpeed(0.7, 0.7);
        }
      }
    }
  }
  @Override
  protected boolean isFinished() {
    return hasReachedSwitch;
  }
}
