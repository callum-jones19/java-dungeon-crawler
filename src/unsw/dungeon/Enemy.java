package unsw.dungeon;

public class Enemy extends Entity implements IMoveable, IDamagable{
    
    private Dungeon dungeon;
    
    private VulnerableCollision vulnState;
    private DamageCollision attackState;

    public Enemy (int x, int y, Dungeon d) {
        super(x,y);
        this.dungeon = d;

        vulnState = new VulnerableCollision(this);
        attackState = new DamageCollision();

        setCollisionBehaviour(attackState);
    }

    public void makeVulnerable() {
        setCollisionBehaviour(vulnState);
    }

    public void makeHarmful() {
        setCollisionBehaviour(attackState);
    }

    public void move(int x, int y) {
        // TODO
        // Implement pathfinding.
        // i.e. when this is called it moves one tile closer towards the player
    } 

    public void die() {
        destroy();
    }

}