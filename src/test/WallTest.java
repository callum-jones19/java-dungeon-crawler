package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.LeftOrientation;
import unsw.dungeon.Wall;
import unsw.dungeon.Player;
import unsw.dungeon.PlayerOrientation;
import unsw.dungeon.Boulder;
import unsw.dungeon.Sword; 

public class WallTest {
    
    @Test
    public void testWallCreation() {
        // test singular wall creation
        Dungeon d = new Dungeon(5, 5);
        Wall wall1 = new Wall(1, 1);
        d.addEntity(wall1);
        assertEquals(wall1, d.getTopmostEntity(1, 1));
        
        // test multiple wall creation
        Wall wall2 = new Wall(1, 2);
        d.addEntity(wall2);
        Wall wall3 = new Wall(1, 3);
        d.addEntity(wall3);
        Wall wall4 = new Wall(2, 3);
        d.addEntity(wall4);
        assertEquals(wall2, d.getTopmostEntity(1, 2));
        assertEquals(wall3, d.getTopmostEntity(1, 3));
        assertEquals(wall4, d.getTopmostEntity(2, 3));
    }

    @Test 
    public void testWallPermeability() {
        // test player moving through wall
        Dungeon d = new Dungeon(10, 10);
        Wall wall1 = new Wall(1, 1);
        d.addEntity(wall1);
        Player player1 = new Player(d, 2, 1);
        d.setPlayer(player1);
        player1.moveLeft();
        assertEquals(player1, d.getTopmostEntity(2, 1));
        assertEquals(wall1, d.getTopmostEntity(1, 1));

        // test enemy moving through wall
        Enemy enemy1 = new Enemy(1, 2, d);
        d.addEntity(enemy1);
        enemy1.move(1, 1);
        assertEquals(enemy1, d.getTopmostEntity(1, 2));
        assertEquals(wall1, d.getTopmostEntity(1, 1));

        // test boulder moving through wall
        enemy1.die();
        Boulder boulder1 = new Boulder(d, 5, 1);
        d.addEntity(boulder1);
        boulder1.move(1, 1);
        assertEquals(boulder1, d.getTopmostEntity(5, 1));
        assertEquals(wall1, d.getTopmostEntity(1, 1));

        // test player pushing boulder through wall
        d.printDungeon();
        player1.move(3, 1);
        boulder1.move(2, 1);
        player1.moveLeft();
        assertEquals(boulder1, d.getTopmostEntity(2, 1));
        assertEquals(wall1, d.getTopmostEntity(1, 1));
        assertEquals(player1, d.getTopmostEntity(3, 1));


    }

    @Test
    public void testWallDurability() {
        // test player striking wall with sword
        Dungeon d = new Dungeon(5, 5);
        Player p1 = new Player(d, 2, 2);
        d.setPlayer(p1);
        Wall wall1 = new Wall(1, 2);
        d.addEntity(wall1);
        Sword sword = new Sword(3, 3, p1);
        p1.addToInventory(sword);
        PlayerOrientation left = new LeftOrientation(p1, d);
        p1.setOrientation(left);
        p1.attack();

        assertEquals(wall1, d.getTopmostEntity(1, 2));
    }

}