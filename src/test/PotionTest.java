package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
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

        p.update(11);
        p.update(1);

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

        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);

        // Enemy should have run 3 spaces away
        assertEquals(7, e.getX());
        assertEquals(2, e.getY());
        
        // Potion runs off - enemy should chase and kill player.
        p.update(15);
        p.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        e.update(1);
        d.printDungeon();
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
        p.update(4);
        p.update(1);

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
        Potion pot2 = new Potion(1, 1);
        d.addEntity(pot);
        d.addEntity(pot2);
        Player p = new Player(d, 1, 2);
        d.setPlayer(p);
        Enemy e = new Enemy(4, 2, d);
        d.addEntity(e);

        pot2.pickup(p);
        p.moveRight();
    }

}