package unsw.dungeon;

public class Key extends Entity implements Item, PickupActivateItem {

    CollectCollision c = new CollectCollision(this);
    Door door;
    
    public Key(int x, int y) {
        super(x, y);
        super.setCollisionBehaviour(c);
    }

    public boolean checkItemType(Item i) {
        return (i instanceof Key);
    }

    public void activate() {
        door.markAvailable();
    }

    public boolean checkCanUse() {
        return true;
    }

    public void activate(Entity e) {
        // FIX
    }

    public void use(Entity e) {
        
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Key s = (Key) o;
        return (door.equals(s.door));
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public boolean isUnique() {
        return true;
    }

    public boolean isWeapon() {
        return false;
    }

}