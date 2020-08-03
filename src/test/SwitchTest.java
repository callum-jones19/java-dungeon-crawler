package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.FloorSwitch;
import unsw.dungeon.Player;
import unsw.dungeon.Boulder;

public class SwitchTest {
    
    @Test
    public void testSwitchCreation() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);

        // Create one switch
        FloorSwitch f1 = new FloorSwitch(5, 5);

        dungeon.addEntity(f1);

        assertEquals(f1, dungeon.getTopmostEntity(5, 5));

        // Create many switches
        FloorSwitch f2 = new FloorSwitch(5, 6);
        FloorSwitch f3 = new FloorSwitch(5, 7);
        FloorSwitch f4 = new FloorSwitch(5, 8);

        dungeon.addEntity(f2);
        dungeon.addEntity(f3);
        dungeon.addEntity(f4);

        assertEquals(f2, dungeon.getTopmostEntity(5, 6));
        assertEquals(f3, dungeon.getTopmostEntity(5, 7));
        assertEquals(f4, dungeon.getTopmostEntity(5, 8));


    }

    @Test
    public void testSwitchActivation() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Boulder boulder = new Boulder(dungeon, 4, 4);
        Player player = new Player(dungeon, 4, 3);
        Enemy enemy = new Enemy(7, 7, dungeon);
        FloorSwitch f1 = new FloorSwitch(4, 5);

        dungeon.addEntity(boulder);
        dungeon.addEntity(enemy);
        dungeon.setPlayer(player);
        dungeon.addEntity(f1);

        // Boulder activates switch by being moved onto it
        player.moveDown();

        assertEquals(boulder, dungeon.getTopmostEntity(4, 5));
        assertEquals(true, f1.isActive());

        // Player does NOT activate switch by moving onto it
        player.moveDown();
        player.moveDown();
        player.moveDown();
        assertEquals(player, dungeon.getTopmostEntity(4, 5));
        assertEquals(false, f1.isActive());

        // Enemy does NOT activate switch by moving onto it
        player.moveDown();
        enemy.move(4, 5);

        assertEquals(enemy, dungeon.getTopmostEntity(4, 5));
        assertEquals(false, f1.isActive());
    }

    @Test
    public void testSwitchDeactivation() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Boulder boulder = new Boulder(dungeon, 4, 4);
        FloorSwitch f1 = new FloorSwitch(4, 5);

        dungeon.addEntity(boulder);
        dungeon.addEntity(f1);

        // Switch deactivates if boulder pushed off it
        boulder.move(4, 5);

        assertEquals(true, f1.isActive());

        boulder.move(8, 8);

        assertEquals(false, f1.isActive());

    }


}