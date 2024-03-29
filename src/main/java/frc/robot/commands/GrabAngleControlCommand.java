package frc.robot.commands;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GrabSubsystem;

import java.util.function.BooleanSupplier;

import static frc.robot.Constants.*;

public class GrabAngleControlCommand extends CommandBase {
    private final GrabSubsystem grabSubsystem;
    private final BooleanSupplier grabPlusSup;
    private final BooleanSupplier grabMinusSup;
    private final Timer timer = new Timer();
    private double grabAngle = START_GRAB_ANGLE;

    public GrabAngleControlCommand(
            GrabSubsystem grabSubsystem,
            BooleanSupplier grabPlus, BooleanSupplier grabMinus) {
        this.grabSubsystem = grabSubsystem;
        this.grabPlusSup = grabPlus;
        this.grabMinusSup = grabMinus;

        addRequirements(grabSubsystem);
    }

    @Override
    public void execute() {
        if (grabPlusSup.getAsBoolean()) {
            if(grabAngle <= GRAB_ANGLE_LIMIT){grabAngle+=GRAB_ANGLECONTROL_NUM;}
            timer.start();
            if (timer.get() >= GRAB_ANGLECONTROL_WAITTIME && grabAngle <= GRAB_ANGLE_LIMIT) {
                grabAngle += GRAB_ANGLECONTROL_NUM;
                timer.restart();
            }
        } else if (grabMinusSup.getAsBoolean()) {
            if(grabAngle >= 0){grabAngle-=GRAB_ANGLECONTROL_NUM;}
            timer.start();
            if (timer.get() >= GRAB_ANGLECONTROL_WAITTIME && grabAngle <= 0) {
                grabAngle -= GRAB_ANGLECONTROL_NUM;
                timer.restart();
            }
        }
        if(DriverStation.isTeleopEnabled()){grabSubsystem.setGrabAngle(grabAngle);}
        SmartDashboard.putNumber("Grab_Angle", grabAngle);
    }
}

