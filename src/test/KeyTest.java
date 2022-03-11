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
        Key key1 = new Key(5, 5, 1);
        Door door1 = new Door(5, 6, 1);
        dungeon.addEntity(key1);
        dungeon.addEntity(door1);
        door1.linkKey(key1);
        key1.linkDoor(door1);

        assertEquals(key1, dungeon.getTopmostEntity(5, 5));

        // Create multiple keys
        Key key2 = new Key(6, 6, 2);
        Key key3 = new Key(7, 7, 3);
        Key key4 = new Key(8, 8, 4);

        dungeon.addEntity(key2);
        dungeon.addEntity(key3);
        dungeon.addEntity(key4);

        assertEquals(key2, dungeon.getTopmostEntity(6, 6));
        assertEquals(key3, dungeon.getTopmostEntity(7, 7));
        assertEquals(key4, dungeon.getTopmostEntity(8, 8));

    }

    @Test
    public void testKeyUnlock() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        Key key1 = new Key(5, 6, 1);
        Door door1 = new Door(5, 7, 1);
        Key key2 = new Key(8, 8, 2);
        Door door2 = new Door(3, 3, 2);
        Key key3 = new Key(1, 1, 3);
        Door door3 = new Door(9, 9, 3);

        dungeon.setPlayer(player);
        dungeon.addEntity(key1);
        dungeon.addEntity(key2);
        dungeon.addEntity(key3);
        dungeon.addEntity(door1);
        dungeon.addEntity(door2);
        dungeon.addEntity(door3);

        key1.linkDoor(door1);
        door1.linkKey(key1);
        key2.linkDoor(door2);
        door2.linkKey(key2);
        key3.linkDoor(door3);
        door3.linkKey(key3);

        // Test key unlocks paired door
        player.moveDown();

        assertEquals(true, player.isHoldingInstance(key1));

        player.moveDown();
        assertEquals(true, door1.isEnterable());
        
        player.moveDown();
        assertEquals(player, dungeon.getTopmostEntity(5, 7));

        // Test key vanishes after use
        assertEquals(false, player.isHoldingInstance(key1));
        
        // Test key does not unlock door it is not paired with
        key2.pickup(player);
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
        Key key1 = new Key(5, 6, 1);
        Door door1 = new Door(5, 7, 1);
        Key key2 = new Key(8, 8, 2);
        Door door2 = new Door(3, 3, 2);

        dungeon.setPlayer(player);
        dungeon.addEntity(key1);
        dungeon.addEntity(key2);
        dungeon.addEntity(door1);
        dungeon.addEntity(door2);

        key1.linkDoor(door1);
        door1.linkKey(key1);
        key2.linkDoor(door2);
        door2.linkKey(key2);

        // Test that a player can pick up a single key
        key1.pickup(player);

        assertEquals(true, player.isHoldingInstance(key1));

        // Test that a player cannot pickup another key whilst holding a key already
        key2.pickup(player);

        assertEquals(false, player.isHoldingInstance(key2));

        player.move(5, 6);
        player.moveDown();

        assertEquals(false, player.isHoldingInstance(key1));

        // Test that a player can pick up a second key after using the first one
        key2.pickup(player);

        assertEquals(true, player.isHoldingInstance(key2));

    }
    
}