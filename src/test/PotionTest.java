package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonState;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;
import unsw.dungeon.Potion;
import unsw.dungeon.Wall;

public class PotionTest {
    
    @Test
    public void potionWearsOff() {
        Dungeon d = new Dungeon(5, 5);
        Potion pot = new Potion(2, 2);
        d.addEntity(pot);
        Player p = new Player(d, 1, 2);
        d.setPlayer(p);

        assertEquals(true, pot.checkItemType(new Potion(1, 1)));

        assertEquals(false, p.isInvincible());
        p.moveRight();

        assertEquals(true, p.isInvincible());

        DungeonState ds = new DungeonState(d);
        ds.run(11);

        assertEquals(false, p.isInvincible());
    }

    @Test
    public void enemiesFleeInvincible() {
        Dungeon d = new Dungeon(10, 10);
        Potion pot = new Potion(2, 2);
        d.addEntity(pot);
        Player p = new Player(d, 1, 2);
        d.setPlayer(p);
        Enemy e = new Enemy(4, 2, d);
        d.addEntity(e);

        p.move(2, 2);

        DungeonState ds = new DungeonState(d);
        ds.run(3.1);

        // Enemy should have run 3 spaces away
        assertEquals(7, e.getX());
        assertEquals(2, e.getY());
        
        // Potion runs off - enemy should chase and kill player.
        ds.run(14);
        assertEquals(null, d.getPlayer());

    }

    @Test
    public void enemiesDieToInvincible() {
        Dungeon d = new Dungeon(10, 10);
        Potion pot = new Potion(2, 2);
        d.addEntity(pot);
        Player p = new Player(d, 1, 2);
        d.setPlayer(p);
        Enemy e = new Enemy(3, 2, d);
        d.addEntity(e);
        Wall w = new Wall(5, 2);
        d.addEntity(w);

        p.move(2,2);

        d.printDungeon();
        DungeonState ds = new DungeonState(d);
        ds.run(4);

        // Potion should not have worn off in 4 seconds, so...
        p.moveRight();
        p.moveRight();

        // Check there is no longer an enemy in the dungeon.
        assertEquals(false, d.hasEntity(e));
    }

    @Test
    public void edgeCase() {
        Dungeon d = new Dungeon(10, 10);
        Potion pot = new Potion(2, 2);
        d.addEntity(pot);
        Player p = new Player(d, 1, 2);
        d.setPlayer(p);
        Enemy e = new Enemy(4, 2, d);
        d.addEntity(e);

        p.addToInventory(new Potion(1, 1));
        p.moveRight();
    }

}