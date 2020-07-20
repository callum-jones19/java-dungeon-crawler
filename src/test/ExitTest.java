package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Exit;
import unsw.dungeon.Player;
import unsw.dungeon.Treasure;
import unsw.dungeon.TreasureGoal;
import unsw.dungeon.Boulder;
import unsw.dungeon.CompositeGoal;
import unsw.dungeon.ExitGoal;
import unsw.dungeon.GoalObserver;
import unsw.dungeon.GoalObserverParent;
import unsw.dungeon.Enemy;

public class ExitTest {
    
    @Test
    public void testExitCreation() {

        Dungeon dungeon = new Dungeon(10, 10);

        // Create one exit
        Exit exit1 = new Exit(5, 5);
        dungeon.addEntity(exit1);

        assertEquals(exit1, dungeon.getTopmostEntity(5, 5));

        // Create multiple exits
        Exit exit2 = new Exit(6, 6);
        Exit exit3 = new Exit(7, 7);
        Exit exit4 = new Exit(8, 8);

        dungeon.addEntity(exit2);
        dungeon.addEntity(exit3);
        dungeon.addEntity(exit4);

        assertEquals(exit2, dungeon.getTopmostEntity(6, 6));
        assertEquals(exit3, dungeon.getTopmostEntity(7, 7));
        assertEquals(exit4, dungeon.getTopmostEntity(8, 8));

    }

    @Test
    public void testExitCompletion() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 5, 5);
        GoalObserver mainGoal = new ExitGoal();
        Exit exit1 = new Exit(5, 6);

        dungeon.setGoal(mainGoal);
        dungeon.setPlayer(player);
        dungeon.addEntity(exit1);

        // Test player reaching exit in level with exit goal completes level
        player.moveDown();

        assertEquals(true, exit1.isActivated());
        assertEquals(true, mainGoal.isComplete());

        player.moveDown();

        // Test player reaching exit in level without exit goal does not complete level
        mainGoal = new TreasureGoal();
        Treasure t1 = new Treasure(3, 3);

        dungeon.setGoal(mainGoal);
        dungeon.addEntity(t1);

        player.moveUp();
        assertEquals(true, exit1.isActivated());
        assertEquals(false, mainGoal.isComplete());

        player.moveDown();

        // Test player not reaching exit last in composite goal level does not complete
        // exit goal

        exit1.destroy();
        t1.destroy();

        GoalObserverParent compGoalParent = new CompositeGoal(true);
        GoalObserver compGoal = (GoalObserver) compGoalParent;
        GoalObserver treasureGoal = new TreasureGoal();
        GoalObserver exitGoal = new ExitGoal();
        Exit exit2 = new Exit(5, 6);
        Treasure t2 = new Treasure(3, 3);
        compGoalParent.addChildGoal(treasureGoal);
        compGoalParent.addChildGoal(exitGoal);
        dungeon.setGoal(compGoal);
        dungeon.addEntity(exit2);
        dungeon.addEntity(t2);

        player.moveUp();

        assertEquals(true, exit1.isActivated());
        assertEquals(false, compGoal.isComplete());

        player.moveDown();
        t2.pickup(player);

        player.moveUp();

        assertEquals(true, exit1.isActivated());
        assertEquals(true, compGoal.isComplete());


    }

    @Test
    public void testEntityExitInteraction() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        Boulder b1 = new Boulder(dungeon, 5, 5);
        Exit exit1 = new Exit(5, 6);
        Enemy enemy1 = new Enemy(5, 7, dungeon);

        dungeon.addEntity(b1);
        dungeon.addEntity(exit1);
        dungeon.addEntity(enemy1);
        
        // Test boulder being pushed onto exit
        b1.move(5, 6);

        assertEquals(false, exit1.isActivated());

        // Test enemy going onto exit
        enemy1.move(5, 6);
        assertEquals(false, exit1.isActivated());

    }

}