package unsw.dungeon;

public class Exit extends Entity implements Triggerable{
    
    private TriggerCollision trigStrat;
    private boolean isActive;


    public Exit(int x, int y) {
        super(x, y);
        trigStrat = new TriggerCollision(this);
        isActive = false;
    }

    public void trigger() {
        isActive = true;
    }


}