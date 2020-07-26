package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class bundles up coordinates into a single package.
 * 
 * Necessary for elements such as pathfinding, which must return a set of 
 * coordinates, not just an x or y.
 */
public class Coordinates {
    private IntegerProperty x, y;

    public Coordinates(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getX() {
        return this.x.get();
    }

    public int getY() {
        return this.y.get();
    }


}