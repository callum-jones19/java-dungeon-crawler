package unsw.dungeon;

public class Boulder extends Entity implements IMoveable {
    
    public Boulder(int x, int y) {
        super(x,y);
    }

    public void move(int x, int y) {
        // TODO
        setPos(x, y);
    }

}