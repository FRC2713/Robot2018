package org.iraiders.robot2018.robot;

import edu.wpi.first.wpilibj.DriverStation;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class Trajectories {
  private static final Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.05, 1.7, 2.0, 60.0);
  
  // All waypoints should be static & final for cache / hash
  public static final Waypoint[] rightStartToSwitchSameSide = new Waypoint[]{
    new Waypoint(0, 5, 0),
    new Waypoint(10, 5, 0),
    new Waypoint(14, 7, Pathfinder.d2r(90))
  };
  
  // End Waypoints
  
  public static TankModifier getTankModifierOfPoints(Waypoint[] points) {
    Trajectory t = loadPointsFromCache(points);
    return new TankModifier(t).modify(0.6096); // ~24 inches left to right from centers of wheel, converted to meters
  }
  
  private static Trajectory loadPointsFromCache(Waypoint[] points) {
    File waypointsPath = new File(System.getProperty("user.home") + "/robot2018/cache/paths/");
    Trajectory trajectory;
    
    String hashedPoints = Arrays.hashCode(points) + "_" + Objects.hash(config); // Hash of path and config put together
    
    waypointsPath.mkdirs();
    
    File f = new File(waypointsPath.getPath() + "/" + hashedPoints + ".csv");
    if (f.isFile()) {
      trajectory = Pathfinder.readFromCSV(f);
    } else {
      DriverStation.reportWarning("Generating new trajectory: " + f.getName(), false);
      trajectory = Pathfinder.generate(points, config);
      Pathfinder.writeToCSV(f, trajectory);
    }
    return trajectory;
  }
}
