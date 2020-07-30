package unsw.dungeon;

public class Key extends Entity implements Item {

    private CollectCollision c = new CollectCollision(this);
    private Door door;
    
    public Key(int x, int y) {
        super(x, y, ZLayer.ITEM);
        super.setCollisionBehaviour(c);
    }

    public boolean checkItemType(Item i) {
        return (i instanceof Key);
    }

    public boolean canUseAgain() {
        return false;
    }

    public void activate() {
        door.markAvailable();
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

    @Override
    public void pickup(Player p) {
        p.addToInventory(this);
        activate();
        if (p.isHoldingInstance(this)) {
            destroy();
        }

    }

}