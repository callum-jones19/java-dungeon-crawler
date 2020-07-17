package unsw.dungeon;

public class Key extends Entity implements Item {

    CollectCollision c = new CollectCollision(this);
    
    public Key(int x, int y) {
        super(x, y);
        super.setCollisionBehaviour(c);
    }

}