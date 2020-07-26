package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.dungeon.Boulder;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;
import unsw.dungeon.Portal;

public class PortalTest {
    
    @Test
    public void playerMoveThroughPortal() {
        Dungeon d = new Dungeon(10, 10);
        Player p = new Player(d, 2, 2);
        d.setPlayer(p);

        Portal portal1 = new Portal(3, 2, 1);
        d.addEntity(portal1);
        Portal portal2 = new Portal(8, 8, 1);
        d.addEntity(portal2);
        portal1.linkPortal(portal2);
        portal2.linkPortal(portal1);

        p.moveRight();
        assertEquals(8, p.getX());
        assertEquals(8, p.getY());


        p.moveRight();

        p.moveLeft();
        assertEquals(3, p.getX());
        assertEquals(2, p.getY());
    }

    @Test
    public void boulderMoveThroughPortal() {
        Dungeon d = new Dungeon(10,10);
        Player p = new Player(d, 2, 2);
        d.setPlayer(p);

        Portal portal1 = new Portal(3, 2, 1);
        d.addEntity(portal1);
        Portal portal2 = new Portal(8, 8, 1);
        d.addEntity(portal2);
        portal1.linkPortal(portal2);
        portal2.linkPortal(portal1);

        Boulder b = new Boulder(d, 2, 2);
        d.addEntity(b);
        b.move(3, 2);

        assertEquals(8, b.getX());
        assertEquals(8, b.getY());
    }

    @Test
    public void enemyMoveThroughPortal() {
        Dungeon d = new Dungeon(10,10);
        Player p = new Player(d, 2, 2);
        d.setPlayer(p);

        Portal portal1 = new Portal(3, 2, 1);
        d.addEntity(portal1);
        Portal portal2 = new Portal(8, 8, 1);
        d.addEntity(portal2);
        portal1.linkPortal(portal2);
        portal2.linkPortal(portal1);

        Enemy e = new Enemy(2, 3, d);
        d.addEntity(e);
        e.move(3, 2);

        assertEquals(8, e.getX());
        assertEquals(8, e.getY());
    }


    @Test
    public void testPortalID() {
        Portal p = new Portal(1, 1, 1);
        assertEquals(1, p.getID());
    }
    
}