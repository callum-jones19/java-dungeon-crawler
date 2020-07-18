package unsw.dungeon;

public interface Item {
    void use(Entity e);
    boolean checkCanUse();
    boolean isWeapon();
}