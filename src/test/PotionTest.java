package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonState;
import unsw.dungeon.Player;
import unsw.dungeon.Potion;

public class PotionTest {
    
    @Test
    public void test() {
        Dungeon d = new Dungeon(5, 5);
        Potion pot = new Potion(2, 2);
        d.addEntity(pot);
        Player p = new Player(d, 1, 2);
        d.setPlayer(p);
        p.moveRight();

        assertEquals(true, p.isInvincible());

        DungeonState ds = new DungeonState(d);
        ds.run(11);

        assertEquals(false, p.isInvincible());
    }
}