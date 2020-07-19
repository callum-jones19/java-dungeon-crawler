package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;


public class EnemyTest {
    
    /**
     * If the player moves onto a tile an enemy sits on, they should die.
     * (If the player has no potion effect)
     */
    @Test
    public void damagePlayerMoveOn () {
        Dungeon dungeon = new Dungeon(10, 10);
        Enemy e = new Enemy(5, 5, dungeon);
        dungeon.addEntity(e);
        Player p = new Player(dungeon, 4, 5);
        dungeon.setPlayer(p);

        p.moveRight();

        assertEquals(null, dungeon.getPlayer());
    }

    /**
     * If the enemy moves onto the player, the player should die.
     * (If the player has no potion effect)
     */
    @Test
    public void damagePlayerEnemyMoveOn() {
        Dungeon d = new Dungeon(10, 10);
        Enemy e = new Enemy(5, 5, d);
        d.addEntity(e);
        Player p = new Player(d, 4, 5);
        d.setPlayer(p);

        e.chasePlayer();

        assertEquals(null, d.getPlayer());
    }

    /**
     * The enemy should chase the player in a straight line on any of the
     * 4 axes.
     */
    @Test
    public void enemyChaseTest() {
        Dungeon d = new Dungeon(10, 10);
        Enemy e = new Enemy(5, 4, d);
        d.addEntity(e);

        Player p = new Player(d, 5, 1);
        d.setPlayer(p);

        assertEquals(5, e.getX());
        assertEquals(4, e.getY());

        e.chasePlayer();
        assertEquals(5, e.getX());
        assertEquals(3, e.getY());

        e.chasePlayer();
        assertEquals(5, e.getX());
        assertEquals(2, e.getY());

        e.chasePlayer();
        assertEquals(5, e.getX());
        assertEquals(1, e.getY());

        assertEquals(null, d.getPlayer());

        // Enemy at 5,1 - now check it chases left
        p.setPos(4, 1);
        d.setPlayer(p);

        e.chasePlayer();
        assertEquals(4, e.getX());
        assertEquals(1, e.getY());
        assertEquals(null, d.getPlayer());

        // Now check Right
        p.setPos(5, 1);
        d.setPlayer(p);

        e.chasePlayer();
        assertEquals(5, e.getX());
        assertEquals(1, e.getY());
        assertEquals(null, d.getPlayer());
        

        // Now check up.
        p.setPos(5, 2);
        d.setPlayer(p);

        e.chasePlayer();
        assertEquals(5, e.getX());
        assertEquals(2, e.getY());
        assertEquals(null, d.getPlayer());

        // Now check the diagonal
        p.setPos(9,9);
        d.setPlayer(p);

        while (d.getPlayer() != null) {
            e.chasePlayer();
        }
    }
}