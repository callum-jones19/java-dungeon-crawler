package unsw.dungeon;

public class DirectSearch implements SearchStyle {
    
    // Given a list of entities to work around.
    public Coordinates pathSearch(int targetX, int targetY, int currentX, int currentY) {
        // Graph of walls/any other non-passable entity.
        // False means it is an empty tile.

        // boolean impassibleGraph[][] = new boolean[dungeonHeight][dungeonWidth];
        // for (Entity e : entities) {
        //     if(!e.isEnterable()) {
        //         impassibleGraph[e.getY()][e.getX()] = true;
        //     }
        // }

        // boolean visited[][] = new boolean[dungeonHeight][dungeonWidth];
        // visited[startY][startX] = true;

        // tmp
        int diffX = targetX - currentX;
        int diffY = targetY - currentY;

        Coordinates c = null;

        if(Math.abs(diffX) > Math.abs(diffY)) {
            if (diffX > 0) {
                c = new Coordinates(currentX + 1, currentY);
            } else if (diffX < 0) {
                c = new Coordinates(currentX - 1, currentY);
            }
        } else {
            if (diffY > 0) {
                c = new Coordinates(currentX, currentY + 1);
            } else if (diffY < 0) {
                c = new Coordinates(currentX, currentY - 1);
            }
        }


        return c;
    }
}