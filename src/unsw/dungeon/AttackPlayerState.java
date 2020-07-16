package unsw.dungeon;

public class AttackPlayerState implements PlayerState {

    Player player;

    public AttackPlayerState(Player p) {
        this.player = p;
    }

    public void collide(Entity e) {
        
    }
}