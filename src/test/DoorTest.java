package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Key;
import unsw.dungeon.Door;
import unsw.dungeon.LeftOrientation;
import unsw.dungeon.Wall;
import unsw.dungeon.Player;
import unsw.dungeon.PlayerOrientation;
import unsw.dungeon.Boulder;
import unsw.dungeon.Sword; 

public class DoorTest {
    
    @Test
    public void testLocked() {

        // Test Setup
        Dungeon d = new Dungeon (5, 5);
        Key key1 = new Key(4, 3);
        d.addEntity(key1);
        Door door1 = new Door(2, 3, key1);
        d.addEntity(door1);
        key1.setDoor(door1);
        Player p1 = new Player(d, 1, 1);
        d.setPlayer(p1);
        Enemy enemy1 = new Enemy(2, 2, d);
        d.addEntity(enemy1);
        Boulder boulder1 = new Boulder(d, 3, 3);
        Sword sword = new Sword(4, 4, p1);


        // Player collides with the door without having the appropriate key
        p1.moveDown();
        p1.moveDown();
        
        assertEquals(3, p1.getY());
        assertEquals(1, p1.getX());
        
        p1.moveRight();

        assertEquals(p1, d.getTopmostEntity(1, 3));
        assertEquals(door1, d.getTopmostEntity(2, 3));


        // Enemy collides with the door while it is locked
        enemy1.move(2, 3);
        assertEquals(enemy1, d.getTopmostEntity(2, 2));
        assertEquals(door1, d.getTopmostEntity(2, 3));

        // Boulder is pushed into locked door
        p1.move(4, 3);
        p1.moveLeft();
        assertEquals(boulder1, d.getTopmostEntity(3, 3));
        assertEquals(p1, d.getTopmostEntity(4, 3));
        assertEquals(door1, d.getTopmostEntity(2, 3));

        // Player strikes locked door with sword

        // Player collides with locked door while invincible

    }

    public void testUnlocked() {

        // Test Setup 

        // Player collides with door having picked up the appropriate key

        // Player can move through unlocked door (??)

        // Player pushes boulder through unlocked door

        // Enemy goes through unlocked door

    }

}