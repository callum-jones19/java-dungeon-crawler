package unsw.dungeon;

public class Treasure extends Entity implements Item {

    private CollisionBehaviour collectionStrategy;

    public Treasure(int x, int y) {
        super(x, y);
        this.collectionStrategy = new CollectCollision(this);
        super.setCollisionBehaviour(collectionStrategy);
    }

    public void use(Entity e) {
        // FIXME
    }


    public boolean checkCanUse() {
        return false;
    }


    public boolean isWeapon() {
        return false;
    }


    public boolean isUnique() {
        return false;
    }


    public boolean checkItemType(Item i) {
        return (i instanceof Treasure);
    }
    
}