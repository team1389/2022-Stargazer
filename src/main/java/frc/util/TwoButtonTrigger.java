package frc.util;

import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class TwoButtonTrigger extends Trigger {
    
    private Button first;
    private Button second;

    public TwoButtonTrigger(Button first, Button second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Overrides get method returns true if both button returns true
     */
    @Override
    public boolean get() {
        return this.first.get() && this.second.get();
    }

}
