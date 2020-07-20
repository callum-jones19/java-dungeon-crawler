package unsw.dungeon;

public interface CollisionBehaviour {
    boolean isEnterable();
    void setEnterability(Boolean enterability);
    void onCollide(Entity e);
}