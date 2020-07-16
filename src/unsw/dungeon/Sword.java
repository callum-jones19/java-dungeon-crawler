package unsw.dungeon;

public class Sword extends Entity implements Item {
    
    private CollectCollision c = new CollectCollision(this);
    private int uses;

    public Sword(int x, int y) {
        super(x, y);
        super.setCollisionBehaviour(c);
        this.uses = 5;
    }

    public void swing() {
        this.uses--;
    }
}