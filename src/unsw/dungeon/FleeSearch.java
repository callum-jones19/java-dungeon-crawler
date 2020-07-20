package unsw.dungeon;

public class FleeSearch implements SearchStyle {
    public Coordinates pathSearch(int targetX, int targetY, int currentX, int currentY) {
        int diffX = targetX - currentX;
        int diffY = targetY - currentY;

        Coordinates c = null;

        if(Math.abs(diffX) > Math.abs(diffY)) {
            if (diffX > 0) {
                c = new Coordinates(currentX - 1, currentY);
            } else if (diffX < 0) {
                c = new Coordinates(currentX + 1, currentY);
            }
        } else {
            if (diffY > 0) {
                c = new Coordinates(currentX, currentY - 1);
            } else if (diffY < 0) {
                c = new Coordinates(currentX, currentY + 1);
            }
        }

        return c;
    }
}