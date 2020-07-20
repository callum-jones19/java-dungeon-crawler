package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Treasure;


public class TreasureTest {
    
    @Test
    public void testTreasureCreate() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        
        // Create one piece of treasure
        Treasure treasure = new Treasure(4, 4);
        dungeon.addEntity(treasure);

        assertEquals(treasure, dungeon.getTopmostEntity(4, 4));

        // Create multiple pieces of treasure
        Treasure treasure1 = new Treasure(5, 5);
        Treasure treasure2 = new Treasure(5, 6);
        Treasure treasure3 = new Treasure(5, 7);

        dungeon.addEntity(treasure1);
        dungeon.addEntity(treasure2);
        dungeon.addEntity(treasure3);

        assertEquals(treasure1, dungeon.getTopmostEntity(5, 5));
        assertEquals(treasure2, dungeon.getTopmostEntity(5, 6));
        assertEquals(treasure3, dungeon.getTopmostEntity(5, 7));

    }

    @Test
    public void testTreasurePickup() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        Treasure treasure1 = new Treasure(5, 4);
        Treasure treasure2 = new Treasure(7, 7);
        Treasure treasure3 = new Treasure(7, 8);

        dungeon.setPlayer(player);
        dungeon.addEntity(treasure1);
        dungeon.addEntity(treasure2);
        dungeon.addEntity(treasure3);
        
        // Test player can pick up one piece of treasure
        player.moveUp();

        assertEquals(true, player.exactContains(treasure1));

        // Test player can pick up more pieces of treasure
        player.move(7, 6);
        player.moveDown();
        player.moveDown();
        player.moveDown();

        assertEquals(true, player.exactContains(treasure2));
        assertEquals(true, player.exactContains(treasure3));
        assertEquals(null, dungeon.getTopmostEntity(7, 7));
        assertEquals(null, dungeon.getTopmostEntity(7, 8));

    }

}