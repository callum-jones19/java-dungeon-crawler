package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.EnemyGoal;
import unsw.dungeon.Exit;
import unsw.dungeon.ExitGoal;
import unsw.dungeon.Player;
import unsw.dungeon.SwitchGoal;
import unsw.dungeon.Boulder;
import unsw.dungeon.CompositeGoal;
import unsw.dungeon.Sword;
import unsw.dungeon.Treasure;
import unsw.dungeon.TreasureGoal;
import unsw.dungeon.UpwardsOrientation;
import unsw.dungeon.FloorSwitch;
import unsw.dungeon.GoalObserver;
import unsw.dungeon.GoalObserverParent;

public class GoalsTest {
    
    @Test
    public void testSingleGoals() {

        // Test Setup
        Dungeon dungeon = new Dungeon(10, 10);
        GoalObserver mainGoal = new TreasureGoal();
        dungeon.setGoal(mainGoal);

        Treasure t1 = new Treasure(5, 5);
        Treasure t2 = new Treasure(7, 7);
        Player player = new Player(dungeon, 3, 3);

        dungeon.addEntity(t1);
        dungeon.addEntity(t2);
        dungeon.setPlayer(player);

        // Test treasure goal

        assertEquals(false, mainGoal.isComplete());

        t1.pickup(player);
        t2.pickup(player);

        assertEquals(true, mainGoal.isComplete());

        // Test switches goal
        t1.destroy();
        t2.destroy();
        mainGoal = new SwitchGoal();
        dungeon.setGoal(mainGoal);
        FloorSwitch f1 = new FloorSwitch(5, 5);
        FloorSwitch f2 = new FloorSwitch(7, 7);
        Boulder b1 = new Boulder(dungeon, 5, 6);
        Boulder b2 = new Boulder(dungeon, 7, 8);

        dungeon.addEntity(f1);
        dungeon.addEntity(f2);
        dungeon.addEntity(b1);
        dungeon.addEntity(b2);

        assertEquals(false, mainGoal.isComplete());

        player.move(5, 7);
        player.moveUp();

        player.move(7, 9);
        player.moveUp();

        assertEquals(true, mainGoal.isComplete());

        // Test exit goal
        f1.destroy();
        f2.destroy();
        b1.destroy();
        b2.destroy();
        
        mainGoal = new ExitGoal();
        dungeon.setGoal(mainGoal);
        Exit e1 = new Exit(9, 9);
        dungeon.addEntity(e1);

        assertEquals(false, mainGoal.isComplete());

        player.move(9, 9);

        assertEquals(true, mainGoal.isComplete());

        // Test enemies goal
        e1.destroy();
        
        mainGoal = new EnemyGoal();
        dungeon.setGoal(mainGoal);

        Enemy enemy1 = new Enemy(9, 6, dungeon);
        Enemy enemy2 = new Enemy(9, 7, dungeon);
        Enemy enemy3 = new Enemy(9, 8, dungeon);
        Sword sword = new Sword(1, 1);
        sword.setUser(player);

        dungeon.addEntity(enemy1);
        dungeon.addEntity(enemy2);
        dungeon.addEntity(enemy3);
        dungeon.addEntity(sword);

        assertEquals(false, mainGoal.isComplete());

        sword.pickup(player);
        player.setOrientation(new UpwardsOrientation(player, dungeon));
        player.attack();
        player.moveUp();
        player.attack();
        player.moveUp();
        player.attack();

        assertEquals(true, mainGoal.isComplete());


    }

    @Test
    public void testCompositeGoals() {
        
        // Test treasure goal and switches goal
        Dungeon dungeon = new Dungeon(10, 10);
        GoalObserver compositeGoal1 = new CompositeGoal(true);
        GoalObserverParent compositeParent1 = (GoalObserverParent) compositeGoal1;
        GoalObserver treasure = new TreasureGoal();
        GoalObserver switches = new SwitchGoal();

        compositeParent1.addChildGoal(treasure);
        compositeParent1.addChildGoal(switches);

        dungeon.setGoal(compositeGoal1);

        Treasure t1 = new Treasure(5, 5);
        Treasure t2 = new Treasure(7, 7);
        Player player = new Player(dungeon, 3, 3);

        FloorSwitch f1 = new FloorSwitch(5, 5);
        FloorSwitch f2 = new FloorSwitch(7, 7);
        Boulder b1 = new Boulder(dungeon, 5, 6);
        Boulder b2 = new Boulder(dungeon, 7, 8);

        dungeon.addEntity(t1);
        dungeon.addEntity(t2);
        dungeon.setPlayer(player);
        dungeon.addEntity(f1);
        dungeon.addEntity(f2);
        dungeon.addEntity(b1);
        dungeon.addEntity(b2);

        assertEquals(false, compositeGoal1.isComplete());

        t1.pickup(player);
        t2.pickup(player);

        assertEquals(false, compositeGoal1.isComplete());

        b1.move(5, 5);
        b2.move(7, 7);

        assertEquals(true, compositeGoal1.isComplete());

        // Test treasure goal or exit goal
        b1.destroy();
        b2.destroy();
        f1.destroy();
        f2.destroy();
        compositeParent1.removeChildGoal(treasure);
        compositeParent1.removeChildGoal(switches);

        GoalObserver compositeGoal2 = new CompositeGoal(false);
        GoalObserverParent compositeParent2 = (GoalObserverParent) compositeGoal2;
        GoalObserver treasure2 = new TreasureGoal();
        GoalObserver exits = new ExitGoal();
        compositeParent2.addChildGoal(treasure2);
        compositeParent2.addChildGoal(exits);
        dungeon.setGoal(compositeGoal2);

        Exit e1 = new Exit(9, 9);
        Treasure t3 = new Treasure(5, 5);
        Treasure t4 = new Treasure(7, 7);
        
        dungeon.addEntity(e1);
        dungeon.addEntity(t3);
        dungeon.addEntity(t4);

        assertEquals(false, compositeGoal2.isComplete());

        player.move(9, 9);

        assertEquals(true, compositeGoal2.isComplete());

        e1.destroy();
        t3.destroy();
        t4.destroy();

        GoalObserver compositeGoal3 = new CompositeGoal(false);
        GoalObserverParent compositeParent3 = (GoalObserverParent) compositeGoal3;
        GoalObserver treasure3 = new TreasureGoal();
        GoalObserver exit2 = new ExitGoal();
        compositeParent3.addChildGoal(treasure3);
        compositeParent3.addChildGoal(exit2);
        dungeon.setGoal(compositeGoal3);

        Exit e2 = new Exit(9, 9);
        Treasure t5= new Treasure(5, 5);
        Treasure t6 = new Treasure(7, 7);
        
        dungeon.addEntity(e2);
        dungeon.addEntity(t5);
        dungeon.addEntity(t6);

        assertEquals(false, compositeGoal3.isComplete());

        t5.pickup(player);
        t6.pickup(player);

        assertEquals(true, compositeGoal3.isComplete());

        e2.destroy();
        t5.destroy();
        t6.destroy();

        // Test treasure goal and switches goal or enemies goal
        GoalObserver compositeGoal4 = new CompositeGoal(true);
        GoalObserverParent compositeParent4 = (GoalObserverParent) compositeGoal4;
        GoalObserver compositeGoal5 = new CompositeGoal(false);
        GoalObserverParent compositeParent5 = (GoalObserverParent) compositeGoal5;
        GoalObserver treasure4 = new TreasureGoal();
        GoalObserver switches2 = new SwitchGoal();
        GoalObserver enemies2 = new EnemyGoal();
        compositeParent4.addChildGoal(treasure4);
        compositeParent4.addChildGoal(compositeGoal5);
        compositeParent5.addChildGoal(switches2);
        compositeParent5.addChildGoal(enemies2);
        dungeon.setGoal(compositeGoal4);

        Treasure t7 = new Treasure(5, 5);
        Treasure t8 = new Treasure(7, 7);
        FloorSwitch f3 = new FloorSwitch(5, 5);
        FloorSwitch f4 = new FloorSwitch(7, 7);
        Boulder b3 = new Boulder(dungeon, 5, 6);
        Boulder b4 = new Boulder(dungeon, 7, 8);
        Enemy enemy1 = new Enemy(9, 6, dungeon);
        Enemy enemy2 = new Enemy(9, 7, dungeon);
        Enemy enemy3 = new Enemy(9, 8, dungeon);
        Sword sword = new Sword(1, 1);
        sword.setUser(player);
        
        dungeon.addEntity(t7);
        dungeon.addEntity(t8);
        dungeon.addEntity(f3);
        dungeon.addEntity(f4);
        dungeon.addEntity(b3);
        dungeon.addEntity(b4);
        dungeon.addEntity(enemy1);
        dungeon.addEntity(enemy2);
        dungeon.addEntity(enemy3);
        dungeon.addEntity(sword);

        t7.pickup(player);
        t8.pickup(player);

        assertEquals(false, compositeGoal4.isComplete());

        b3.move(5, 5);
        b4.move(7, 7);

        assertEquals(true, compositeGoal4.isComplete());

        b3.destroy();
        b4.destroy();
        t7.destroy();
        t8.destroy();
        f3.destroy();
        f4.destroy();
        enemy1.destroy();
        enemy2.destroy();
        enemy3.destroy();
        sword.destroy();

        // Test treasure goal and switches goal and enemies goal and exit goal
        GoalObserver compositeGoal6 = new CompositeGoal(true);
        GoalObserverParent compositeParent6 = (GoalObserverParent) compositeGoal6;
        GoalObserver treasure5 = new TreasureGoal();
        GoalObserver switches3 = new SwitchGoal();
        GoalObserver enemies3 = new EnemyGoal();
        GoalObserver exit4 = new ExitGoal();
        compositeParent6.addChildGoal(treasure5);
        compositeParent6.addChildGoal(switches3);
        compositeParent6.addChildGoal(enemies3);
        compositeParent6.addChildGoal(exit4);
        dungeon.setGoal(compositeGoal6);

        Treasure t9 = new Treasure(5, 5);
        Treasure t10 = new Treasure(7, 7);
        FloorSwitch f5 = new FloorSwitch(5, 5);
        FloorSwitch f6 = new FloorSwitch(7, 7);
        Boulder b5 = new Boulder(dungeon, 5, 6);
        Boulder b6 = new Boulder(dungeon, 7, 8);
        Enemy enemy4 = new Enemy(9, 6, dungeon);
        Enemy enemy5 = new Enemy(9, 7, dungeon);
        Enemy enemy6 = new Enemy(9, 8, dungeon);
        Sword sword2 = new Sword(1, 1);
        sword.setUser(player);
        Exit exit6 = new Exit(3, 3);
        Exit exit7 = new Exit(1, 2);

        dungeon.addEntity(t9);
        dungeon.addEntity(t10);
        dungeon.addEntity(f5);
        dungeon.addEntity(f6);
        dungeon.addEntity(b5);
        dungeon.addEntity(b6);
        dungeon.addEntity(enemy4);
        dungeon.addEntity(enemy5);
        dungeon.addEntity(enemy6);
        dungeon.addEntity(sword2);
        dungeon.addEntity(exit6);
        dungeon.addEntity(exit7);

        assertEquals(false, compositeGoal6.isComplete());

        t9.pickup(player);
        t10.pickup(player);
        
        assertEquals(false, compositeGoal6.isComplete());

        b5.move(5, 5);
        b6.move(7, 7);

        // (have the player try to complete the exit goal without
        // completing the others)
        player.move(3, 3);
        assertEquals(false, exit4.isComplete());
        assertEquals(false, compositeGoal6.isComplete());

        enemy4.die();
        enemy5.die();
        enemy6.die();

        assertEquals(false, compositeGoal6.isComplete());

        player.move(1, 2);

        assertEquals(true, compositeGoal6.isComplete());

        t9.destroy();
        t10.destroy();
        f5.destroy();
        f6.destroy();
        b5.destroy();
        b6.destroy();
        sword2.destroy();
        exit6.destroy();
        exit7.destroy();
        
        // Test treasure goal and switches goal and enemies goal or exit goal
        GoalObserver compositeGoal7 = new CompositeGoal(true);
        GoalObserverParent compositeParent7 = (GoalObserverParent) compositeGoal7;
        GoalObserver compositeGoal8 = new CompositeGoal(false);
        GoalObserverParent compositeParent8 = (GoalObserverParent) compositeGoal8;
        GoalObserver treasure6 = new TreasureGoal();
        GoalObserver switches5 = new SwitchGoal();
        GoalObserver enemies6 = new EnemyGoal();
        GoalObserver exit5 = new ExitGoal();
        compositeParent7.addChildGoal(treasure6);
        compositeParent7.addChildGoal(switches5);
        compositeParent7.addChildGoal(compositeGoal8);
        compositeParent8.addChildGoal(enemies6);
        compositeParent8.addChildGoal(exit5);
        dungeon.setGoal(compositeGoal7);

        Treasure t11 = new Treasure(5, 5);
        Treasure t12 = new Treasure(7, 7);
        FloorSwitch f7 = new FloorSwitch(5, 5);
        FloorSwitch f8 = new FloorSwitch(7, 7);
        Boulder b7 = new Boulder(dungeon, 5, 6);
        Boulder b8 = new Boulder(dungeon, 7, 8);
        Enemy enemy7 = new Enemy(9, 6, dungeon);
        Enemy enemy8 = new Enemy(9, 7, dungeon);
        Enemy enemy9 = new Enemy(9, 8, dungeon);
        Sword sword3 = new Sword(1, 1);
        sword.setUser(player);
        Exit exit8 = new Exit(1, 2);

        dungeon.addEntity(t11);
        dungeon.addEntity(t12);
        dungeon.addEntity(f7);
        dungeon.addEntity(f8);
        dungeon.addEntity(b7);
        dungeon.addEntity(b8);
        dungeon.addEntity(enemy7);
        dungeon.addEntity(enemy8);
        dungeon.addEntity(enemy9);
        dungeon.addEntity(sword3);
        dungeon.addEntity(exit8);

        assertEquals(false, compositeGoal7.isComplete());

        t11.pickup(player);
        t12.pickup(player);

        assertEquals(false, compositeGoal7.isComplete());

        b7.move(5, 5);
        b8.move(7, 7);

        assertEquals(false, compositeGoal7.isComplete());

        enemy7.die();
        enemy8.die();
        enemy9.die();

        assertEquals(true, compositeGoal7.isComplete());

        t11.destroy();
        t12.destroy();
        b7.destroy();
        b8.destroy();
        f7.destroy();
        f8.destroy();
        exit8.destroy();
        sword3.destroy();

        // Test treasure goal or switches goal or enemies goal or exit goal
        GoalObserver compositeGoal9 = new CompositeGoal(false);
        GoalObserverParent compositeParent9 = (GoalObserverParent) compositeGoal9;
        GoalObserver treasure7 = new TreasureGoal();
        GoalObserver switches6 = new SwitchGoal();
        GoalObserver enemies5 = new EnemyGoal();
        GoalObserver exit9 = new ExitGoal();
        compositeParent9.addChildGoal(treasure7);
        compositeParent9.addChildGoal(switches6);
        compositeParent9.addChildGoal(enemies5);
        compositeParent9.addChildGoal(exit9);
        dungeon.setGoal(compositeGoal9);

        Treasure t13 = new Treasure(5, 5);
        Treasure t14 = new Treasure(7, 7);
        FloorSwitch f9 = new FloorSwitch(5, 5);
        FloorSwitch f10 = new FloorSwitch(7, 7);
        Boulder b9 = new Boulder(dungeon, 5, 6);
        Boulder b10 = new Boulder(dungeon, 7, 8);
        Enemy enemy10 = new Enemy(9, 6, dungeon);
        Enemy enemy11 = new Enemy(9, 7, dungeon);
        Enemy enemy12 = new Enemy(9, 8, dungeon);
        Sword sword4 = new Sword(1, 1);
        sword.setUser(player);
        Exit exit10 = new Exit(1, 2);

        dungeon.addEntity(t13);
        dungeon.addEntity(t14);
        dungeon.addEntity(f9);
        dungeon.addEntity(f10);
        dungeon.addEntity(b9);
        dungeon.addEntity(b10);
        dungeon.addEntity(enemy10);
        dungeon.addEntity(enemy11);
        dungeon.addEntity(enemy12);
        dungeon.addEntity(sword4);
        dungeon.addEntity(exit10);

        assertEquals(false, compositeGoal9.isComplete());

        t13.pickup(player);
        t14.pickup(player);

        assertEquals(true, compositeGoal9.isComplete());

    }

}