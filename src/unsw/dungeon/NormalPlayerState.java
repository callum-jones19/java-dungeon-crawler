package unsw.dungeon;

public class NormalPlayerState implements PlayerState {

    Player player;

    public NormalPlayerState(Player p) {
        this.player = p;
    }

    public void collide(Entity e) {
        
    }
}