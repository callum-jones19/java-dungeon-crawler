package unsw.dungeon;

public class Key extends Entity implements Item, UniqueItem, PickupActivateItem {

    CollectCollision c = new CollectCollision(this);
    Door door;
    
    public Key(int x, int y, Door d) {
        super(x, y);
        super.setCollisionBehaviour(c);
        this.door = d;
    }

    public boolean checkSameItem(Entity e) {
        return (e instanceof Key);
    }

    public void activate() {
        door.markAvailable();
    }

}