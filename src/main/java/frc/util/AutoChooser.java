package frc.util;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.autos.OneBallAuto;

import frc.autos.OnemAuto;

public class AutoChooser {
    private final SequentialCommandGroup onemAuto = new OnemAuto();
    private final SequentialCommandGroup oneBallAuto = new OneBallAuto();
    SendableChooser<SequentialCommandGroup> autoChooser = new SendableChooser<>();

    public AutoChooser() {
        autoChooser.setDefaultOption("one ball auto", oneBallAuto);
        autoChooser.addOption("onem auto", onemAuto);

        SmartDashboard.putData(autoChooser);
    }

    public SequentialCommandGroup getAutoCommand() {
        return autoChooser.getSelected();
    }
}
