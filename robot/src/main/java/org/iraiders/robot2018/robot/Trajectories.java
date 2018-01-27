package org.iraiders.robot2018.robot;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import lombok.SneakyThrows;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Trajectories {
  // Waypoint positions *always* in feet
  private static final Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.05, 1.7, 2.0, 60.0);
  
  // All waypoints should be static & final for cache / hash
  public static final Waypoint[] rightStartToSwitchSameSide = new Waypoint[]{
    new Waypoint(0, 5, 0),
    new Waypoint(10, 5, 0),
    new Waypoint(14, 7, Pathfinder.d2r(90)),
    new Waypoint(0, 0, 0) // Stop
  };
  
  // End Waypoints
  
  public static TankModifier getTankModifierOfPoints(Waypoint[] points) {
    Trajectory t = loadPointsFromCache(points);
    return new TankModifier(t).modify(0.6096); // ~24 inches left to right from centers of wheel, converted to meters
  }
  
  public static List<TrajectoryPoint> trajectoryToTalonPoints(Trajectory t) {
    // TODO CHECK THIS ALL!!!
    List<TrajectoryPoint> tps = new ArrayList<>();
    for (int i = 0; i < t.segments.length - 1; i++) {
      TrajectoryPoint tp = new TrajectoryPoint(); // The CTRE version
      Trajectory.Segment segment = t.segments[i]; // The Pathfinder version
      
      tp.zeroPos = (i == 0); // Clear the encoder values before we start to use things
      tp.isLastPoint = (i == t.segments.length + 1);
      tp.profileSlotSelect0 = 0; // TODO ???
      tp.profileSlotSelect1 = 0; // This probably doesn't do anything
      tp.timeDur = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_50ms; // TODO get this from Pathfinder (dt variable?)
      tp.position = segment.position * 1000;
      tp.velocity = segment.velocity * 1000;
      tp.headingDeg = segment.heading;
      
      tps.add(tp);
    }
    return tps;
  }
  
  public static void loadPointsToTalon(WPI_TalonSRX talon, List<TrajectoryPoint> tps) {
    talon.clearMotionProfileTrajectories();
    talon.configMotionProfileTrajectoryPeriod(5, 50);
    for (TrajectoryPoint tp : tps) {
      talon.processMotionProfileBuffer();
      System.out.println("Loading " + tps + " to " + talon.getBaseID());
      talon.pushMotionProfileTrajectory(tp);
    }
  }
  
  @SneakyThrows(NoSuchAlgorithmException.class)
  private static Trajectory loadPointsFromCache(Waypoint[] points) {
    File waypointsPath = new File(System.getProperty("user.home") + "/robot2018/cache/paths/");
    Trajectory trajectory;
    
    String hashedPoints = Arrays.hashCode(points) + "_" + Objects.hash(config); // Hash of path and config put together
    
    waypointsPath.mkdirs();
    
    File f = new File(waypointsPath.getPath() + "/" + hashedPoints + ".csv");
    if (f.isFile()) {
      trajectory = Pathfinder.readFromCSV(f);
    } else {
      trajectory = Pathfinder.generate(points, config);
      Pathfinder.writeToCSV(f, trajectory);
    }
    return trajectory;
  }
}
