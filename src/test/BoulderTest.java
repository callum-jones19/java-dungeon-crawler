package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Wall;
import unsw.dungeon.Player;
import unsw.dungeon.PlayerOrientation;
import unsw.dungeon.RightOrientation;
import unsw.dungeon.Boulder;
import unsw.dungeon.Sword; 
import unsw.dungeon.FloorSwitch;

public class BoulderTest {
    
    @Test
    public void testBoulderCreation() {

        // Test setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 1, 1);
        dungeon.setPlayer(player);

        // Single boulder creation
        Boulder boulder1 = new Boulder(dungeon, 3, 3);
        dungeon.addEntity(boulder1);

        assertEquals(boulder1, dungeon.getTopmostEntity(3, 3));

        // Multiple boulder creation
        Boulder boulder2 = new Boulder(dungeon, 5, 5);
        Boulder boulder3 = new Boulder(dungeon, 5, 6);
        Boulder boulder4 = new Boulder(dungeon, 5, 7);
        Boulder boulder5 = new Boulder(dungeon, 5, 8);

        dungeon.addEntity(boulder2);
        dungeon.addEntity(boulder3);
        dungeon.addEntity(boulder4);
        dungeon.addEntity(boulder5);

        assertEquals(boulder2, dungeon.getTopmostEntity(5, 5));
        assertEquals(boulder3, dungeon.getTopmostEntity(5, 6));
        assertEquals(boulder4, dungeon.getTopmostEntity(5, 7));
        assertEquals(boulder5, dungeon.getTopmostEntity(5, 8));

    }

    @Test
    public void testBoulderPlayerPush() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 1, 1);
        dungeon.setPlayer(player);

        Boulder boulder1 = new Boulder(dungeon, 3, 3);
        Boulder boulder2 = new Boulder(dungeon, 5, 5);
        Boulder boulder3 = new Boulder(dungeon, 5, 6);
        Wall wall1 = new Wall(3, 5);
        FloorSwitch fs1 = new FloorSwitch(7, 7);
        Sword sword = new Sword(7, 8);
        sword.setUser(player);

        dungeon.addEntity(boulder1);
        dungeon.addEntity(boulder2);
        dungeon.addEntity(boulder3);
        dungeon.addEntity(wall1);
        dungeon.addEntity(sword);
        dungeon.addEntity(fs1);

        // Test player pushing boulder where there is space
        player.move(3, 2);
        player.moveDown();

        assertEquals(player, dungeon.getTopmostEntity(3, 3));
        assertEquals(boulder1, dungeon.getTopmostEntity(3, 4));

        // Test player pushing boulder into wall
        player.moveDown();

        assertEquals(player, dungeon.getTopmostEntity(3, 3));
        assertEquals(boulder1, dungeon.getTopmostEntity(3, 4));

        // Test player pushing boulder into boulder
        player.move(2, 4);
        player.moveRight();
        player.moveRight();
        player.moveUp();
        player.moveRight();
        player.moveDown();

        assertEquals(player, dungeon.getTopmostEntity(5, 3));
        assertEquals(boulder1, dungeon.getTopmostEntity(5, 4));
        assertEquals(boulder2, dungeon.getTopmostEntity(5, 5));
        assertEquals(boulder3, dungeon.getTopmostEntity(5, 6));

        // Test player pushing boulder onto floor switch
        player.move(4, 6);
        player.moveRight();
        player.moveRight();
        player.moveUp();
        player.moveRight();
        player.moveDown();

        assertEquals(boulder3, dungeon.getTopmostEntity(7, 7));
        assertEquals(7, fs1.getX());
        assertEquals(7, fs1.getY());

        // Test player pushing boulder onto item
        player.moveDown();
        player.moveDown();
        player.moveDown();

        assertEquals(true, player.contains(sword));

        // Test player pushing boulder onto exit
        // TODO

    }

    @Test
    public void testBoulderEnemyPush() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Boulder boulder1 = new Boulder(dungeon, 5, 5);
        Boulder boulder2 = new Boulder(dungeon, 5, 6);
        Enemy enemy1 = new Enemy(4, 6, dungeon);

        dungeon.addEntity(boulder1);
        dungeon.addEntity(boulder2);
        dungeon.addEntity(enemy1);

        // Test enemy trying to push boulder where there is space
        enemy1.move(5, 6);

        assertEquals(boulder2, dungeon.getTopmostEntity(5, 6));
        assertEquals(enemy1, dungeon.getTopmostEntity(4, 6));

        // Test enemy trying to push boulder where there is no space
        enemy1.move(5, 4);
        enemy1.move(5, 5);

        assertEquals(boulder1, dungeon.getTopmostEntity(5, 5));
        assertEquals(enemy1, dungeon.getTopmostEntity(5, 4));

    }

    @Test
    public void testBoulderDurability() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 1, 1);
        Boulder boulder1 = new Boulder(dungeon, 5, 5);
        Sword sword = new Sword(1, 2);
        sword.setUser(player);
        PlayerOrientation right = new RightOrientation(player, dungeon);

        dungeon.setPlayer(player);
        dungeon.addEntity(boulder1);
        dungeon.addEntity(sword);
        
        // Test player striking boulder with sword
        player.moveDown();

        assertEquals(true, player.contains(sword));

        player.move(4, 5);
        player.setOrientation(right);
        player.attack();

        assertEquals(boulder1, dungeon.getTopmostEntity(5, 5));


        // Test player hitting boulder while invincible
        // TODO

    }

    public static void main(String[] args) {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 1, 1);
        Boulder boulder1 = new Boulder(dungeon, 5, 5);
        Sword sword = new Sword(1, 2);
        sword.setUser(player);
        PlayerOrientation right = new RightOrientation(player, dungeon);

        dungeon.setPlayer(player);
        dungeon.addEntity(boulder1);
        dungeon.addEntity(sword);
        
        // Test player striking boulder with sword

        dungeon.printDungeon();

        player.moveDown();

        dungeon.printDungeon();

        System.out.println(player.getX());
        System.out.println(player.getY());

        player.move(4, 5);
        player.setOrientation(right);
        player.attack();
        
        // Test player pushing boulder onto exit
        // TODO
    }

}