package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public abstract class BoulderSwitchMediator implements Mediator {

    List<Boulder> boulders = new ArrayList<Boulder>();
    List<FloorSwitch> switches = new ArrayList<FloorSwitch>();

    public void addBoulder(Boulder b) {
        this.boulders.add(b);
    }

    public void addSwitch(FloorSwitch f) {
        this.switches.add(f);
    }

    public void deactivateSwitch(FloorSwitch f) {
        f.setActive(false);
    }

    public void notify(Entity e) {
        // TODO Auto-generated method stub

    }
    
}