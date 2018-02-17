package org.iraiders.robot2018.robot.commands.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.modifiers.TankModifier;
import openrio.powerup.MatchData;
import org.iraiders.robot2018.robot.RobotMap;
import org.iraiders.robot2018.robot.Trajectories;
import org.iraiders.robot2018.robot.commands.arm.ArmCommand;
import org.iraiders.robot2018.robot.commands.grabber.ControlGrabber;
import org.iraiders.robot2018.robot.subsystems.ArmSubsystem;
import org.iraiders.robot2018.robot.subsystems.DriveSubsystem;
import org.iraiders.robot2018.robot.subsystems.GrabberSubsystem;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class AutonomousCommand extends CommandGroup {
  private final DriveSubsystem driveSubsystem;
  private final ArmSubsystem armSubsystem;
  private final GrabberSubsystem grabberSubsystem;
  
  public AutonomousCommand(DriveSubsystem driveSubsystem, ArmSubsystem armSubsystem, GrabberSubsystem grabberSubsystem){
    this.driveSubsystem = driveSubsystem;
    this.armSubsystem = armSubsystem;
    this.grabberSubsystem = grabberSubsystem;
    requires(driveSubsystem);
    requires(armSubsystem);
    requires(grabberSubsystem);
  }
  
  @Override
  protected void initialize() {
    RobotMap.imu.reset();
    if (!RobotMap.USE_MINIMUM_VIABLE_AUTO) {
      doAuto(RobotMap.startPosition.getSelected(), MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR));
    }
  }
  
  private void doAuto(MatchStartPosition robotLocation, MatchData.OwnedSide ownedSwitchSide) {
    if (robotLocation == MatchStartPosition.GUESS) robotLocation = MatchStartPosition.get(DriverStation.getInstance().getLocation());
    switch (robotLocation) {
      case LEFT:
        // Left Starting Point
        if (ownedSwitchSide == MatchData.OwnedSide.LEFT) {
          TankModifier tankModifier = Trajectories.getTankModifierOfPoints(Trajectories.leftStartToSwitchSameSide);
          addParallel(new MotionProfileFollowCommand(driveSubsystem.getFrontLeftTalon(), driveSubsystem.getFrontRightTalon(), tankModifier));
          addParallel(new ArmCommand(armSubsystem, ArmSubsystem.ArmPosition.SWITCH_DELIVER));
          addSequential(new ControlGrabber(grabberSubsystem, GrabberSubsystem.GrabberPosition.OPEN));
        } else {
          // Scale on other side
        }
        break;
      
      case MIDDLE:
        // Middle Starting Point
        if (ownedSwitchSide == MatchData.OwnedSide.LEFT) {
          // Scale on same side as us
        } else {
          // Scale on other side
        }
        break;
      
      case RIGHT:
        // Right Starting Point
        if (ownedSwitchSide == MatchData.OwnedSide.RIGHT) {
          // Scale on same side as us
          //driveSubsystem.getFrontLeftTalon().selectProfileSlot(0,0);
          TankModifier trajectory = Trajectories.getTankModifierOfPoints(Trajectories.rightStartToSwitchSameSide);
          addParallel(new MotionProfileFollowCommand(driveSubsystem.getFrontLeftTalon(), driveSubsystem.getFrontRightTalon(), trajectory));
        } else {
          // Scale on other side
        }
        break;
    }
  }
  
  public enum MatchStartPosition {
    // This is messy code from https://stackoverflow.com/a/11550162/1709894, improve it someday
    GUESS(0), LEFT(1), MIDDLE(2), RIGHT(3);
  
    private static final Map<Integer,MatchStartPosition> lookup = new HashMap<>();
  
    static {
      for(MatchStartPosition w : EnumSet.allOf(MatchStartPosition.class)) lookup.put(w.getCode(), w);
    }
  
    private int code;
  
    MatchStartPosition(int code) {
      this.code = code;
    }
  
    public int getCode() { return code; }
  
    public static MatchStartPosition get(int code) {
      return lookup.get(code);
    }
  }
}
