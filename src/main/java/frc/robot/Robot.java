// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final WPI_TalonFX frontLeft = new WPI_TalonFX(1);
  private final WPI_TalonFX rearLeft = new WPI_TalonFX(2);
  private final WPI_TalonFX frontRight = new WPI_TalonFX(3);
  private final WPI_TalonFX rearRight = new WPI_TalonFX(4);
  private final TalonSRX intake = new TalonSRX(15);
  private final TalonSRX upanddown = new TalonSRX(9);
  private final TalonSRX conveyor = new TalonSRX(12);
  private final WPI_TalonFX flyright = new WPI_TalonFX(6);
  private final WPI_TalonFX flyleft = new WPI_TalonFX(5);
  private final TalonSRX driveleft = new TalonSRX(16);
  private final TalonSRX driveright = new TalonSRX(10);
  private final TalonSRX panel = new TalonSRX(11);
  private final Joystick stick0 = new Joystick(0);
  private final Joystick stick1 = new Joystick(1);
  
  //limelight
  private final NetworkTable shooter = NetworkTableInstance.getDefault().getTable("shooter_limelight");
  NetworkTableEntry Tapetx = shooter.getEntry("Tapetx");
  NetworkTableEntry Tapety = shooter.getEntry("Tapety");
  NetworkTableEntry Tapeta = shooter.getEntry("Tapeta");
  NetworkTableEntry Tapetv = shooter.getEntry("Tapetv");

  double x = Tapetx.getDouble(0.0);
  double y = Tapety.getDouble(0.0);
  double area = Tapeta.getDouble(0.0);
  double value = Tapetv.getDouble(0.0);
  double turn = Constants.Chassis.kp_aim * x;
  
  static UsbCamera camera;
  Timer timer = new Timer();
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    camera = CameraServer.startAutomaticCapture(0);
    camera.setResolution(160, 120);

    frontRight.setInverted(true);
    rearRight.setInverted(true);
    frontLeft.setNeutralMode(NeutralMode.Brake);
    rearLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.setNeutralMode(NeutralMode.Brake);
    rearRight.setNeutralMode(NeutralMode.Brake);

    intake.setInverted(false);
    upanddown.setInverted(false);
    upanddown.setNeutralMode(NeutralMode.Brake);

    flyleft.setInverted(false);
    flyright.setInverted(true);
    driveleft.setInverted(true);
    driveright.setInverted(false);
    panel.setInverted(true);
    conveyor.setInverted(true);
    flyleft.setNeutralMode(NeutralMode.Brake);
    flyright.setNeutralMode(NeutralMode.Brake);
    driveleft.setNeutralMode(NeutralMode.Brake);
    driveright.setNeutralMode(NeutralMode.Brake);
    panel.setNeutralMode(NeutralMode.Brake);
    conveyor.setNeutralMode(NeutralMode.Brake);

    frontLeft.configFactoryDefault();
    rearLeft.configFactoryDefault();
    frontRight.configFactoryDefault();
    rearRight.configFactoryDefault();
    flyleft.configFactoryDefault();
    flyright.configFactoryDefault();

    frontLeft.configNeutralDeadband(0.001);
    rearLeft.configNeutralDeadband(0.001);
    frontRight.configNeutralDeadband(0.001);
    rearRight.configNeutralDeadband(0.001);
    flyleft.configNeutralDeadband(0.001);
    flyright.configNeutralDeadband(0.001);

    frontLeft.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                           Constants.kPIDLoopIdx, 
                                           Constants.kTimeoutMs);
    rearLeft.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                           Constants.kPIDLoopIdx, 
                                           Constants.kTimeoutMs);
    frontRight.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                           Constants.kPIDLoopIdx, 
                                           Constants.kTimeoutMs);
    rearRight.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                           Constants.kPIDLoopIdx, 
                                           Constants.kTimeoutMs);
    flyleft.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                         Constants.kPIDLoopIdx, 
                                         Constants.kTimeoutMs);
    flyright.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                         Constants.kPIDLoopIdx, 
                                         Constants.kTimeoutMs);
    
                                      

    frontLeft.configNominalOutputForward(0, Constants.kTimeoutMs);
		frontLeft.configNominalOutputReverse(0, Constants.kTimeoutMs);
		frontLeft.configPeakOutputForward(1, Constants.kTimeoutMs);
		frontLeft.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    rearLeft.configNominalOutputForward(0, Constants.kTimeoutMs);
		rearLeft.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rearLeft.configPeakOutputForward(1, Constants.kTimeoutMs);
		rearLeft.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    frontRight.configNominalOutputForward(0, Constants.kTimeoutMs);
		frontRight.configNominalOutputReverse(0, Constants.kTimeoutMs);
		frontRight.configPeakOutputForward(1, Constants.kTimeoutMs);
		frontRight.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    rearRight.configNominalOutputForward(0, Constants.kTimeoutMs);
		rearRight.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rearRight.configPeakOutputForward(1, Constants.kTimeoutMs);
		rearRight.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    flyleft.configNominalOutputForward(0, Constants.kTimeoutMs);
		flyleft.configNominalOutputReverse(0, Constants.kTimeoutMs);
		flyleft.configPeakOutputForward(1, Constants.kTimeoutMs);
		flyleft.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    flyright.configNominalOutputForward(0, Constants.kTimeoutMs);
		flyright.configNominalOutputReverse(0, Constants.kTimeoutMs);
		flyright.configPeakOutputForward(1, Constants.kTimeoutMs);
		flyright.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    frontLeft.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
		frontLeft.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
		frontLeft.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
		frontLeft.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);

    rearLeft.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
		rearLeft.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
		rearLeft.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
		rearLeft.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);

    frontRight.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
		frontRight.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
		frontRight.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
		frontRight.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);

    rearRight.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
		rearRight.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
		rearRight.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
		rearRight.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);

    flyleft.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
		flyleft.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
		flyleft.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
		flyleft.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);

    flyright.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
		flyright.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
		flyright.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
	  flyright.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    flyleft.set(ControlMode.PercentOutput, 0.325);
    flyright.set(ControlMode.PercentOutput, 0.325);
    driveleft.set(ControlMode.PercentOutput, 1);
    driveright.set(ControlMode.PercentOutput, 1);
    Timer.delay(3);
    conveyor.set(ControlMode.PercentOutput, 1);
    Timer.delay(2);
    conveyor.set(ControlMode.PercentOutput, 0);
    flyleft.set(ControlMode.PercentOutput, 0);
    flyright.set(ControlMode.PercentOutput, 0);
    driveleft.set(ControlMode.PercentOutput, 0);
    driveright.set(ControlMode.PercentOutput, 0);
    frontLeft.set(ControlMode.Velocity, -0.4 * 2000.0 * 2048.0 / 600.0);
    rearLeft.set(ControlMode.Velocity, -0.4 * 2000.0 * 2048.0 / 600.0);
    frontRight.set(ControlMode.Velocity, -0.4 * 2000.0 * 2048.0 / 600.0);
    rearRight.set(ControlMode.Velocity, -0.4 * 2000.0 * 2048.0 / 600.0);
    Timer.delay(3);
    frontLeft.set(ControlMode.PercentOutput, 0);
    rearLeft.set(ControlMode.PercentOutput, 0);
    frontRight.set(ControlMode.PercentOutput, 0);
    rearRight.set(ControlMode.PercentOutput, 0);
    }
    
  

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
    
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    ///Chassis

    double vertical = -stick0.getRawAxis(1) * 2000.0 * 2048.0 / 600.0;
    double horizontal = -stick0.getRawAxis(0) * 2000.0 * 2048.0 / 600.0;
    double pivot = stick0.getRawAxis(4) * 2000.0 * 2048.0 / 600.0;

    frontRight.set(ControlMode.Velocity, ((-pivot + (vertical + horizontal))*Constants.Chassis.frStraightspeed));
    rearRight.set(ControlMode.Velocity, ((-pivot + (vertical - horizontal))*Constants.Chassis.rrStraightspeed));
    frontLeft.set(ControlMode.Velocity, ((pivot +(vertical - horizontal))*Constants.Chassis.flStraightspeed));
    rearLeft.set(ControlMode.Velocity, ((pivot + (vertical + horizontal))*Constants.Chassis.rlStraightspeed));

    if(stick0.getRawButton(6)){
      frontRight.set(ControlMode.Velocity, ((-pivot + (vertical + horizontal))*Constants.Chassis.frnotStraightspeed));
      rearRight.set(ControlMode.Velocity, ((-pivot + (vertical - horizontal))*Constants.Chassis.rrnotStraightspeed));
      frontLeft.set(ControlMode.Velocity, ((pivot +(vertical - horizontal))*Constants.Chassis.flnotStraightspeed));
      rearLeft.set(ControlMode.Velocity, ((pivot + (vertical + horizontal))*Constants.Chassis.rlnotStraightspeed));
    }

    else if(stick0.getRawButton(5)){
      frontRight.set(ControlMode.PercentOutput, 0);
      rearRight.set(ControlMode.PercentOutput, 0);
      frontLeft.set(ControlMode.PercentOutput, 0);
      rearLeft.set(ControlMode.PercentOutput, 0);
    }

    //limelight
    else if(stick1.getRawButton(6)){
      if(turn > Constants.Shooter.maxspeed){
        frontRight.set(ControlMode.PercentOutput, -Constants.Shooter.maxspeed);
        rearRight.set(ControlMode.PercentOutput, -Constants.Shooter.maxspeed);
        frontLeft.set(ControlMode.PercentOutput, Constants.Shooter.maxspeed);
        rearLeft.set(ControlMode.PercentOutput, Constants.Shooter.maxspeed);
      }
  
      else if(turn < -Constants.Shooter.maxspeed){
        frontRight.set(ControlMode.PercentOutput, Constants.Shooter.maxspeed);
        rearRight.set(ControlMode.PercentOutput, Constants.Shooter.maxspeed);
        frontLeft.set(ControlMode.PercentOutput, -Constants.Shooter.maxspeed);
        rearLeft.set(ControlMode.PercentOutput, -Constants.Shooter.maxspeed);
      }
  
      else{
        frontRight.set(ControlMode.PercentOutput ,-turn);
        rearRight.set(ControlMode.PercentOutput ,-turn);
        frontLeft.set(ControlMode.PercentOutput ,turn);
        rearLeft.set(ControlMode.PercentOutput ,turn);
      }
  
    }
 

    ///Intake 
    
    if(stick0.getRawButton(3))
      intake.set(ControlMode.PercentOutput, 0.6);
    else if(stick0.getRawButton(2)){
      intake.set(ControlMode.PercentOutput, 0);
      upanddown.set(ControlMode.PercentOutput, 0);
    }
    else if(stick0.getRawButton(1))
      upanddown.set(ControlMode.PercentOutput, -0.4);
    else if(stick0.getRawButton(4))
      upanddown.set(ControlMode.PercentOutput, 0.4);
    

    //Shooter Test
    if(stick1.getRawButton(1)){ //LOW
      conveyor.set(ControlMode.PercentOutput, -0.1);
      Timer.delay(0.5);
      conveyor.set(ControlMode.PercentOutput, 0);
      Timer.delay(0.5);
      flyleft.set(ControlMode.PercentOutput, 0.5);
      flyright.set(ControlMode.PercentOutput, 0.5);
      driveleft.set(ControlMode.PercentOutput, 1);
      driveright.set(ControlMode.PercentOutput, 1);
      Timer.delay(3);
      conveyor.set(ControlMode.PercentOutput, 1);
    }
    else if(stick1.getRawButton(5)){  
      flyleft.set(ControlMode.PercentOutput, 0);
      flyright.set(ControlMode.PercentOutput, 0);
      driveleft.set(ControlMode.PercentOutput, 0);
      driveright.set(ControlMode.PercentOutput, 0);
      conveyor.set(ControlMode.PercentOutput, 0);
      panel.set(ControlMode.PercentOutput, 0);
    }
    else if(stick1.getRawButton(3)){
        panel.set(ControlMode.PercentOutput, 0.2);
      
        
    }
    else if(stick1.getRawButton(2)){
      
        panel.set(ControlMode.PercentOutput, -0.2);
      
        
    }
    else if(stick1.getRawButton(4)){  //upper
      conveyor.set(ControlMode.PercentOutput, -0.1);
      Timer.delay(0.5);
      conveyor.set(ControlMode.PercentOutput, 0);
      Timer.delay(0.5);
      flyleft.set(ControlMode.PercentOutput, 1);
      flyright.set(ControlMode.PercentOutput, 1);
      driveleft.set(ControlMode.PercentOutput, 1);
      driveright.set(ControlMode.PercentOutput, 1);
      Timer.delay(3);
      conveyor.set(ControlMode.PercentOutput, 1);
    }
    
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
