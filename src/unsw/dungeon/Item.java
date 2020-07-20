package unsw.dungeon;

public interface Item {
    void use(Entity e);
    boolean checkCanUse();
    boolean isWeapon();
    boolean isUnique();
    boolean checkItemType(Item i);
    void pickup(Entity e);
}