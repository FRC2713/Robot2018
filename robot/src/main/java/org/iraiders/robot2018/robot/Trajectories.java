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
    new Waypoint(0, 1.524, 0),
    new Waypoint(3.048, 1.524, 0),
    new Waypoint(4.2672, 2.1336, Pathfinder.d2r(90))
  };

  public static final Waypoint[] leftStartToSwitchSameSide = new Waypoint[]{
    new Waypoint(0, 6.7056, 0),
    new Waypoint(3.048, 6.7056, 0),
    new Waypoint(4.2672, 6.096, Pathfinder.d2r(270))
  };

  public static final Waypoint[] rightStartToScaleSameSide = new Waypoint[]{
    new Waypoint(0, 1.524, 0),
    new Waypoint(7.62, 1.524, 0),
    new Waypoint(8.2296, 2.1336, Pathfinder.d2r(90))
  };

  public static final Waypoint[] leftStartToScaleSameSide = new Waypoint[]{
    new Waypoint(0, 6.7056, 0),
    new Waypoint(7.62, 6.7056, 0),
    new Waypoint(8.2296, 6.096, Pathfinder.d2r(270))
  };

  public static final Waypoint[] leftStartToScaleRightSide = new Waypoint[]{
    new Waypoint(0, 6.7056, 0),
    new Waypoint(6.7056, 5.4864, 0),
    new Waypoint(6.096, 6.096 , Pathfinder.d2r(270)),
    new Waypoint(4.8768, 2.7432 , Pathfinder.d2r(180))
  };

  public static final Waypoint[] rightStartToSwitchLeftSide = new Waypoint[]{
    new Waypoint(0, 1.524, 0),
    new Waypoint(5.4864, 1.524, 0),
    new Waypoint(6.096, 2.4384, Pathfinder.d2r(90)),
    new Waypoint(6.096, 4.8768, Pathfinder.d2r(90)),
    new Waypoint(4.8768, 5.4864, Pathfinder.d2r(180))
  };

  public static final Waypoint[] middleStartToSwitchRightSide = new Waypoint[]{
    new Waypoint(0, 3.9624, 0),
    new Waypoint(0.6096, 3.9624, 0),
    new Waypoint(1.2192, 3.048, Pathfinder.d2r(270)),
    new Waypoint(1.8288, 2.7432, Pathfinder.d2r(180)),
    new Waypoint(3.6576, 2.7432, Pathfinder.d2r(180))
  };

  public static final Waypoint[] middleStartToSwitchLeftSide = new Waypoint[]{
    new Waypoint(0, 3.9624, 0),
    new Waypoint(1.2192, 3.9624, 0),
    new Waypoint(1.524, 4.572, Pathfinder.d2r(90)),
    new Waypoint(1.524, 5.1816, Pathfinder.d2r(90)),
    new Waypoint(2.1336, 5.4864, Pathfinder.d2r(180)),
    new Waypoint(3.6576, 5.4864, Pathfinder.d2r(180))
  };

  public static final Waypoint[] middleStartToScaleRightSide = new Waypoint[]{
    new Waypoint(0, 3.9624, 0),
    new Waypoint(0.6096, 3.9624, 0),
    new Waypoint(1.2192, 3.048, Pathfinder.d2r(270)),
    new Waypoint(1.2192, 1.524, Pathfinder.d2r(270)),
    new Waypoint(1.8288, 1.2192, Pathfinder.d2r(180)),
    new Waypoint(7.62, 1.2192, Pathfinder.d2r(180)),
    new Waypoint(8.2296, 1.8288, Pathfinder.d2r(90))
  };

  public static final Waypoint[] middleStartToScaleLeftSide = new Waypoint[]{
    new Waypoint(0, 3.9624, 0),
    new Waypoint(1.2192, 3.9624, 0),
    new Waypoint(1.524, 4.572, Pathfinder.d2r(90)),
    new Waypoint(1.524, 6.7056, Pathfinder.d2r(90)),
    new Waypoint(2.1336, 7.0104, Pathfinder.d2r(180)),
    new Waypoint(7.3152, 7.0104, Pathfinder.d2r(180)),
    new Waypoint(8.2296, 6.4008, Pathfinder.d2r(270))
  };

  public static final Waypoint[] middleStartToPcZoneLeftSide = new Waypoint[]{
    new Waypoint(0, 3.9624, 0),
    new Waypoint(1.2192, 3.9624, 0),
    new Waypoint(1.524, 4.572, Pathfinder.d2r(90)),
    new Waypoint(1.524, 6.7056, Pathfinder.d2r(90)),
    new Waypoint(2.1336, 7.0104, Pathfinder.d2r(180)),
    new Waypoint(4.2672, 7.0104, Pathfinder.d2r(180))
  };

  public static final Waypoint[] middleStartToPcZoneRightSide = new Waypoint[]{
    new Waypoint(0, 3.9624, 0),
    new Waypoint(0.6096, 3.9624, 0),
    new Waypoint(1.2192, 3.048, Pathfinder.d2r(270)),
    new Waypoint(1.2192, 1.524, Pathfinder.d2r(270)),
    new Waypoint(1.8288, 1.2192, Pathfinder.d2r(180)),
    new Waypoint(4.2672, 1.2192, Pathfinder.d2r(180))
  };

  public static final Waypoint[] rightStartToPcZoneSameSide = new Waypoint[]{
    new Waypoint(0, 1.524, 0),
    new Waypoint(0.6096, 1.524, 0),
    new Waypoint(0.9144, 0.9144, Pathfinder.d2r(270)),
    new Waypoint(1.524, 0.6096, Pathfinder.d2r(180)),
    new Waypoint(4.2672, 0.6096, Pathfinder.d2r(180))
  };

  public static final Waypoint[] leftStartToPcZoneSameSide = new Waypoint[]{
    new Waypoint(0, 6.7056, 0),
    new Waypoint(0.6096, 6.7056, 0),
    new Waypoint(1.2192, 7.3152, Pathfinder.d2r(90)),
    new Waypoint(1.8288, 7.62, Pathfinder.d2r(180)),
    new Waypoint(4.2672, 7.62, Pathfinder.d2r(180))
  };


  // End Waypoints

  public static TankModifier getTankModifierOfPoints(Waypoint[] points) {
    Trajectory t = loadPointsFromCache(points);
    return new TankModifier(t).modify(0.6096); // ~24 inches left to right from centers of wheel, converted to meters
  }

  private static Trajectory loadPointsFromCache(Waypoint[] points) {
    File waypointsPath = new File(System.getProperty("user.home") + "/robot2018/cache/paths/");
    Trajectory trajectory;

    String hashedPoints = Arrays.toString(points).hashCode() + "_" + Objects.hash(config); // Hash of path and config put together

    waypointsPath.mkdirs();

    File f = new File(waypointsPath.getPath() + "/" + hashedPoints + ".csv");
    if (f.isFile()) {
      DriverStation.reportWarning("Using points from " + f.getName(), false);
      trajectory = Pathfinder.readFromCSV(f);
    } else {
      DriverStation.reportWarning("Generating new trajectory: " + f.getName(), false);
      trajectory = Pathfinder.generate(points, config);
      Pathfinder.writeToCSV(f, trajectory);
      DriverStation.reportWarning("Generation finished", false);
    }
    return trajectory;
  }
}
