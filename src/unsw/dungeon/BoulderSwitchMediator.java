package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class BoulderSwitchMediator {

    List<Boulder> boulders = new ArrayList<Boulder>();
    List<FloorSwitch> switches = new ArrayList<FloorSwitch>();

    public BoulderSwitchMediator() {
        super();
    }

    public void addBoulder(Boulder b) {
        this.boulders.add(b);
        b.setMediator(this);
    }

    public void addSwitch(FloorSwitch f) {
        this.switches.add(f);
        f.setMediator(this);
    }

    public void deactivateSwitch(FloorSwitch f) {
        f.setActive(false);
    }

    public void notifyBoulderPosChange(int x, int y) {

        for (FloorSwitch f: switches) {
            if (f.getX() == x && f.getY() == y) {
                updateSwitchState(f);
            }
        } 
    }



    public void updateSwitchState(FloorSwitch f) {



    }

    public boolean checkBouldersAtPos(int x, int y) {
        for (Boulder b: boulders) {
            if (b.getX() == x && b.getY() == y) return true;
        }
        return false;
    }

    public void activateSwitch(FloorSwitch f) {
        f.trigger();
    }
    
}