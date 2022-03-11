package unsw.dungeon;

public interface Weapon {
    public void use(Entity target);
    public int getUsesLeft();
}
