// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int kSlotIdx = 0;

	/**
	 * Talon FX supports multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/**
	 * Set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
    public static final int kTimeoutMs = 30;

	/**
	 * PID Gains may have to be adjusted based on the responsiveness of control loop.
     * kF: 1023 represents output value to Talon at 100%, 20660 represents Velocity units at 100% output
     * 
	 * 	                                    			  kP   	 kI    kD      kF          Iz    PeakOut */
    public final static Gains kGains_Velocit  = new Gains( 0.1, 0.001, 5, 1023.0/20660.0,  300,  1.00);

    public final static int kStickPort1 = 1;
    public final static int kStickPort2 = 0;

    public final static int stop = 0;

    public static final class Autonomous{
        public static final double AutoSpeed = -0.4 * 2000.0 * 2048.0 / 600.0;
        public static final int AutoTime = 2;
        public static final double autofly = 1;
        public static final double autodrive = 1;
        public static final double autoconvey = 1;
    }

    public static final class Chassis{
        public static final int kFrontLeftChannel = 1;
        public static final int kRearLeftChannel = 2;
        public static final int kFrontRightChannel = 3;
        public static final int kRearRightChannel = 4;

        public static final double frStraightspeed = 0.65;
        public static final double rrStraightspeed = 0.65;
        public static final double flStraightspeed = 0.62;
        public static final double rlStraightspeed = 0.54;

        public static final double frnotStraightspeed = 0.62;
        public static final double rrnotStraightspeed = 0.53;
        public static final double flnotStraightspeed = 0.67;
        public static final double rlnotStraightspeed = 0.46; 

        public static final double kstopspeed = 0;

        public static final int kStickLeftXPort = 0;
        public static final int kStickLeftYPort = 1;
        public static final int kStickRightXPort = 4;
        public static final int kNotStraightButton = 6;
        public static final int kStopButton = 5;

        public static final double kp_aim = 0.05;
    }

    public static final class Intake{
        public static final int kball_inPort = 15;
        public static final int kupanddownPort = 9;

        public static final int ball_in_btn = 3;
        public static final int up_btn = 4;
        public static final int down_btn = 1;
        public static final int stop_btn = 2;

        public static final double upspeed = -0.4;
        public static final double downspeed = 0.4;
        public static final double intakespeed = 0.3;
    
    } 

    public static final class Conveyor{
        public static final int conveyPort = 7;
        public static final double conveySpeed = 1;
    }
    
    public static final class Shooter{
        public static final int driveleft = 16;
        public static final int driveright = 11;
        public static final int panel = 10;
        public static final int flyleft = 5;
        public static final int flyright = 6;
        public static final int uplimit = 1;
        public static final int downlimit = 0;

        public static final double panelup = 0.3;
        public static final double paneldown = -0.3;
        public static final double conveyspeed = 1;
        public static final double drivespeed = 1;
        public static final double lowflyspeed = 1;
        public static final double highflyspeed = 0.5;

        public static final int stopBtn = 5;
        public static final int upBtn = 3;
        public static final int downBtn = 2;
        public static final int aimBtn = 6;
        public static final int highshootbtn = 4;
        public static final int lowshootbtn = 1;

        public static final double maxspeed = 0.3;
        
    }
}
