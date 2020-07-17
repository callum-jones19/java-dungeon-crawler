package unsw.dungeon;

public class PotionPlayerState implements PlayerState {
 
    Player player;

    public PotionPlayerState(Player p) {
        this.player = p;
    }

    public void collide(Entity e) {
        
    }
}