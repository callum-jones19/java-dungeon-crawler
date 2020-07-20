package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Key;
import unsw.dungeon.Door;

public class KeyTest {

    @Test
    public void testKeyCreation() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);

        // Create a single key
        Key key1 = new Key(5, 5);
        Door door1 = new Door(5, 6, key1);
        dungeon.addEntity(key1);
        dungeon.addEntity(door1);

        assertEquals(key1, dungeon.getTopmostEntity(5, 5));

        // Create multiple keys
        Key key2 = new Key(6, 6);
        Key key3 = new Key(7, 7);
        Key key4 = new Key(8, 8);
        Door door2 = new Door(6, 7, key2);
        Door door3 = new Door(7, 8, key3);
        Door door4 = new Door(8, 9, key4);

        dungeon.addEntity(key2);
        dungeon.addEntity(key3);
        dungeon.addEntity(key4);
        dungeon.addEntity(door2);
        dungeon.addEntity(door3);
        dungeon.addEntity(door4);

        assertEquals(key2, dungeon.getTopmostEntity(6, 6));
        assertEquals(key3, dungeon.getTopmostEntity(7, 7));
        assertEquals(key4, dungeon.getTopmostEntity(8, 8));

    }

    @Test
    public void testKeyUnlock() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        Key key1 = new Key(5, 6);
        Door door1 = new Door(5, 7, key1);
        Key key2 = new Key(8, 8);
        Door door2 = new Door(3, 3, key2);
        Key key3 = new Key(1, 1);
        Door door3 = new Door(9, 9, key3);

        dungeon.setPlayer(player);
        dungeon.addEntity(key1);
        dungeon.addEntity(key2);
        dungeon.addEntity(key3);
        dungeon.addEntity(door1);
        dungeon.addEntity(door2);
        dungeon.addEntity(door3);

        // Test key unlocks paired door
        player.moveDown();

        assertEquals(true, player.contains(key1));

        player.moveDown();
        assertEquals(true, door1.isEnterable());
        
        player.moveDown();
        assertEquals(player, dungeon.getTopmostEntity(5, 7));

        // Test key vanishes after use
        assertEquals(false, player.contains(key1));
        
        // Test key does not unlock door it is not paired with
        player.addToInventory(key2);
        player.move(9, 8);
        player.moveDown();

        assertEquals(false, door3.isEnterable());
        assertEquals(player, dungeon.getTopmostEntity(9, 8));
        assertEquals(door3, dungeon.getTopmostEntity(9, 9));

        
    }

    @Test
    public void testKeyUnique() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        Key key1 = new Key(5, 6);
        Door door1 = new Door(5, 7, key1);
        Key key2 = new Key(8, 8);
        Door door2 = new Door(3, 3, key2);

        dungeon.setPlayer(player);
        dungeon.addEntity(key1);
        dungeon.addEntity(key2);
        dungeon.addEntity(door1);
        dungeon.addEntity(door2);
        // Test that a player can pick up a single key
        player.addToInventory(key1);

        assertEquals(true, player.isHoldingInstance(key1));

        // Test that a player cannot pickup another key whilst holding a key already
        player.addToInventory(key2);

        assertEquals(false, player.isHoldingInstance(key2));

        player.move(5, 6);
        player.moveDown();

        assertEquals(false, player.isHoldingInstance(key1));

        // Test that a player can pick up a second key after using the first one
        player.addToInventory(key2);

        assertEquals(true, player.contains(key2));

    }
    
}