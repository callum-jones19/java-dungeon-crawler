package unsw.dungeon;

public class Enemy extends Entity implements IMoveable, IDamagable{
    
    Dungeon d;
    
    public Enemy (int x, int y, Dungeon d) {
        super(x,y);
        this.d = d;
    }

    public void move(int x, int y) {
        // TODO
        // Implement pathfinding.
        // i.e. when this is called it moves one tile closer towards the player
    }

    public void die() {
        // TODO
    }

}