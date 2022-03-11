package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.dungeon.Door;
import unsw.dungeon.DownwardsOrientation;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Key;
import unsw.dungeon.LeftOrientation;
import unsw.dungeon.Player;
import unsw.dungeon.PlayerOrientation;
import unsw.dungeon.Potion;
import unsw.dungeon.RightOrientation;
import unsw.dungeon.Sword;
import unsw.dungeon.Treasure;
import unsw.dungeon.UpwardsOrientation;

public class PlayerTest {
    
    @Test
    public void testPlayerCreation() {

        // Create a player
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        dungeon.setPlayer(player);

        assertEquals(player, dungeon.getPlayer());

    }

    @Test
    public void testPlayerMove() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        dungeon.setPlayer(player);

        // Test player move up
        player.moveUp();

        assertEquals(player, dungeon.getTopmostEntity(5, 4));

        // Test player move down
        player.moveDown();

        assertEquals(player, dungeon.getTopmostEntity(5, 5));

        // Test player move right
        player.moveRight();

        assertEquals(player, dungeon.getTopmostEntity(6, 5));

        // Test player move left
        player.moveLeft();

        assertEquals(player, dungeon.getTopmostEntity(5, 5));

    }

    @Test
    public void testPlayerInteraction() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        Sword sword = new Sword(5, 6);
        sword.setUser(player);
        Key key = new Key(5, 7, 1);
        Door door = new Door(6, 6, 1);
        Potion potion = new Potion(5, 8);
        Treasure treasure = new Treasure(5, 9);

        dungeon.setPlayer(player);
        dungeon.addEntity(sword);
        dungeon.addEntity(key);
        dungeon.addEntity(potion);
        dungeon.addEntity(treasure);
        key.linkDoor(door);
        door.linkKey(key);

        // Test Player picking up Sword
        player.moveDown();

        assertEquals(true, player.isHoldingInstance(sword));

        // Test Player picking up key
        player.moveDown();

        assertEquals(true, player.isHoldingInstance(key));

        // Test Player picking up potion
        player.moveDown();

        assertEquals(true, player.isInvincible());

        // Test Player picking up treasure
        player.moveDown();

        assertEquals(true, player.isHoldingInstance(treasure));

    }

    @Test
    public void testPlayerOrientation() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        PlayerOrientation up = new UpwardsOrientation(player, dungeon);
        PlayerOrientation down = new DownwardsOrientation(player, dungeon);
        PlayerOrientation left = new LeftOrientation(player, dungeon);
        PlayerOrientation right = new RightOrientation(player, dungeon);
        Enemy enemy1 = new Enemy(5, 6, dungeon);
        Enemy enemy2 = new Enemy(5, 4, dungeon);
        Enemy enemy3 = new Enemy(4, 5, dungeon);
        Enemy enemy4 = new Enemy(6, 5, dungeon);
        Sword sword = new Sword(8, 8);
        sword.setUser(player);

        dungeon.addEntity(enemy1);
        dungeon.addEntity(enemy2);
        dungeon.addEntity(enemy3);
        dungeon.addEntity(enemy4);
        dungeon.addEntity(sword);

        sword.pickup(player);
        
        // Test upwards

        player.setOrientation(up);
        player.attack();

        assertEquals(null, dungeon.getTopmostEntity(5, 4));

        // Test downwards
        player.setOrientation(down);
        player.attack();

        assertEquals(null, dungeon.getTopmostEntity(5, 6));

        // Test right
        player.setOrientation(right);
        player.attack();

        assertEquals(null, dungeon.getTopmostEntity(6, 5));

        // Test left
        player.setOrientation(left);
        player.attack();

        assertEquals(null, dungeon.getTopmostEntity(4, 5));

    }


    @Test
    public void moveInvalid() {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        dungeon.setPlayer(player);

        player.move(-1,-1);

        assertEquals(5, player.getX());
        assertEquals(5, player.getY());
    }

    @Test
    public void potionTime() {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        dungeon.setPlayer(player);

        assertEquals(0.0, player.getInvincTimeLeft());
    }

    public void hasWeapon() {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        dungeon.setPlayer(player);

        assertEquals(false, player.hasWeapon());
    }
}