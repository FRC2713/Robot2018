package org.iraiders.robot2018.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import lombok.Getter;

public class OI {
  @Getter private static XboxController xBoxController;
  @Getter private static Joystick arcadeController;
  
  public OI() {
    initControllers();
  }
  
  /**
   * Scans all (7) controller ports and assigns them via known names
   */
  private void initControllers() {
    // TODO Use generics
    for (int i = 0; i < 6; i++) {
      Joystick test = new Joystick(i);
      if (test.getName().equals(RobotMap.XBOX_NAME)) {
        xBoxController = new XboxController(i);
      } else if (test.getName().equals(RobotMap.ARCADE_NAME)) {
        arcadeController = new Joystick(i);
      }
    }
    if (xBoxController == null) {
      xBoxController = new XboxController(RobotMap.BACKUP_XBOX_PORT);
    }
    if (arcadeController == null) {
      arcadeController = new Joystick(RobotMap.BACKUP_ARCADE_PORT);
    }
  }
  
  /**
   * Rumbles a given controller for a specified time
   * @param stick Controller to rumble
   * @param ms Time in Milliseconds
   */
  public static void rumbleController(Joystick stick, int ms) {
    rumbleController(stick, ms, GenericHID.RumbleType.kLeftRumble);
  }
  
  /**
   * Rumbles a given controller for a specified time
   * Left rumble is like an earthquake, right rumble is like a vibrating toothbrush
   * @param stick Controller to rumble
   * @param ms Time in Milliseconds
   * @param rumbleType Type of rumble to use
   */
  public static void rumbleController(Joystick stick, int ms, GenericHID.RumbleType rumbleType) {
    new Thread(() -> {
      stick.setRumble(rumbleType, 1);
      try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
      stick.setRumble(rumbleType, 0);
    }).start();
  }
}
