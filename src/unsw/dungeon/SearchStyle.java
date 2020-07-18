package unsw.dungeon;

public interface SearchStyle {
    public Coordinates pathSearch(int targetX, int targetY, int currentX, int currentY);
}