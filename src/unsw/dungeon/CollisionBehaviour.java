package unsw.dungeon;

public interface CollisionBehaviour {
    boolean isEnterable();
    void onCollide(Entity e);
}