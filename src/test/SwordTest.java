package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.DownwardsOrientation;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;
import unsw.dungeon.PlayerOrientation;
import unsw.dungeon.RightOrientation;
import unsw.dungeon.Sword;

public class SwordTest {
    
    @Test
    public void testSwordCreate() {
        
        // Test create one sword
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        Sword sword = new Sword(3, 3, player);

        dungeon.setPlayer(player);
        dungeon.addEntity(sword);

        assertEquals(sword, dungeon.getTopmostEntity(3, 3));

        // Test create many swords
        Sword sword1 = new Sword(4, 4, player);
        Sword sword2 = new Sword(6, 6, player);
        Sword sword3 = new Sword(7, 7, player);

        dungeon.addEntity(sword1);
        dungeon.addEntity(sword2);
        dungeon.addEntity(sword3);

        assertEquals(sword1, dungeon.getTopmostEntity(4, 4));
        assertEquals(sword2, dungeon.getTopmostEntity(6, 6));
        assertEquals(sword3, dungeon.getTopmostEntity(7, 7));

    }

    @Test
    public void testSwordKill() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        Sword sword = new Sword(3, 3, player);
        Enemy enemy1 = new Enemy(8, 8, dungeon);
        PlayerOrientation right = new RightOrientation(player, dungeon);

        dungeon.setPlayer(player);
        dungeon.addEntity(enemy1);
        dungeon.addEntity(sword);

        // Test sword can kill enemies in one hit
        player.pickup(sword);

        assertEquals(true, player.exactContains(sword));

        player.move(7, 8);
        player.setOrientation(right);

        player.attack();

        assertEquals(null, dungeon.getTopmostEntity(8, 8));
        assertEquals(player, dungeon.getTopmostEntity(7, 8));
        assertEquals(false, dungeon.getEnemies().contains(enemy1));

    }

    @Test
    public void testSwordUniq() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        Sword sword = new Sword(3, 3, player);
        Sword sword2 = new Sword(3, 2, player);
        Enemy enemy1 = new Enemy(8, 8, dungeon);

        dungeon.setPlayer(player);
        dungeon.addEntity(enemy1);
        dungeon.addEntity(sword);
        dungeon.addEntity(sword2);

        // Test player can pick up sword
        player.moveLeft();
        player.moveLeft();
        player.moveUp();
        player.moveUp();

        assertEquals(true, player.exactContains(sword));
        
        // Test player cannot pick up second sword while in possession 
        // of existing sword

        player.moveUp();
        player.moveRight();

        assertEquals(false, player.exactContains(sword2));

    }

    @Test
    public void testSwordUsage() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        PlayerOrientation down = new DownwardsOrientation(player, dungeon);
        Sword sword = new Sword(3, 3, player);
        Sword sword2 = new Sword(3, 2, player);
        Enemy enemy1 = new Enemy(8, 8, dungeon);
        Enemy enemy2 = new Enemy(8, 7, dungeon);
        Enemy enemy3 = new Enemy(8, 6, dungeon);
        Enemy enemy4 = new Enemy(8, 5, dungeon);
        Enemy enemy5 = new Enemy(8, 4, dungeon);

        dungeon.setPlayer(player);
        dungeon.addEntity(enemy1);
        dungeon.addEntity(enemy2);
        dungeon.addEntity(enemy3);
        dungeon.addEntity(enemy4);
        dungeon.addEntity(enemy5);
        dungeon.addEntity(sword);
        dungeon.addEntity(sword2);

        // Test sword disappears after five kills
        player.pickup(sword);

        assertEquals(true, player.exactContains(sword));

        player.move(8, 3);
        player.setOrientation(down);

        player.attack();
        player.moveDown();
        player.attack();
        player.moveDown();
        player.attack();
        player.moveDown();
        player.attack();
        player.moveDown();
        player.attack();

        assertEquals(false, player.exactContains(sword));

        // Test player can pick up second sword after first sword
        // has broken
        player.pickup(sword2);

        assertEquals(true, player.exactContains(sword2));

    }

}