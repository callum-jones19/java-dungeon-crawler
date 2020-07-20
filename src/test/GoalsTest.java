package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.EnemyGoal;
import unsw.dungeon.Exit;
import unsw.dungeon.ExitGoal;
import unsw.dungeon.Wall;
import unsw.dungeon.Player;
import unsw.dungeon.PlayerOrientation;
import unsw.dungeon.RightOrientation;
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

import java.util.*;

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
        Sword sword = new Sword(1, 1, player);

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
        Sword sword = new Sword(1, 1, player);
        
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

        // Test treasure goal and switches goal and enemies goal and exit goal
        

        // Test treasure goal and switches goal and enemies goal or exit goal

        // Test treasure goal or switches goal or enemies goal or exit goal

    }




    public static void main(String[] args) {
        Dungeon dungeon = new Dungeon(10, 10);
        GoalObserver mainGoal = new TreasureGoal();
        dungeon.setGoal(mainGoal);

        Treasure t1 = new Treasure(5, 5);
        Treasure t2 = new Treasure(7, 7);

        dungeon.addEntity(t1);
        dungeon.addEntity(t2);

        dungeon.printDungeon();

        System.out.println(mainGoal.getSubjects());  
        
        Player player = new Player(dungeon, 4, 4);
        dungeon.setPlayer(player);

        player.move(5, 5);
        player.move(7, 7);

        dungeon.printDungeon();
        System.out.println(player.getInventory());
        System.out.println(mainGoal.getSubjects());
        System.out.println(mainGoal.isComplete());
        /*
        GoalObserver compositeGoal = new CompositeGoal(true);
        GoalObserverParent compositeParent = (GoalObserverParent) compositeGoal;
        GoalObserver treasure = new TreasureGoal();
        GoalObserver enemies = new EnemyGoal();
        compositeParent.addChildGoal(treasure);
        compositeParent.addChildGoal(enemies);

        Treasure t1 = new Treasure(5, 5);
        Treasure t2 = new Treasure(7, 7);
        Player p1 = new Player(dungeon, 3, 3);
        Enemy enemy1 = new Enemy(8, 8, dungeon);
        Enemy enemy2 = new Enemy(9, 9, dungeon);
        Sword s = new Sword(4, 4, p1);
        PlayerOrientation right = new RightOrientation(p1, dungeon);
        dungeon.setGoal(compositeGoal);

        dungeon.addEntity(t1);
        dungeon.addEntity(t2);
        dungeon.addEntity(enemy1);
        dungeon.addEntity(enemy2);
        dungeon.addEntity(s);
        dungeon.setPlayer(p1);

        dungeon.printDungeon();
        
        System.out.println(treasure.getSubjects());
        System.out.println(enemies.getSubjects());

        t1.pickup(p1);
        t2.pickup(p1);

        System.out.println(treasure.isComplete());
        System.out.println(compositeGoal.isComplete());

        //s.pickup(p1);
        //p1.setOrientation(right);

        //p1.move(7, 8);
        //p1.attack();

        //p1.move(8, 9);
        //p1.attack();

        //dungeon.printDungeon();
        //System.out.println(enemies.isComplete());
        //System.out.println(compositeGoal.isComplete());
        */
        GoalObserver compositeGoal1 = new CompositeGoal(true);
        GoalObserverParent compositeParent1 = (GoalObserverParent) compositeGoal1;
        GoalObserver compositeGoal2 = new CompositeGoal(false);
        GoalObserverParent compositeParent2 = (GoalObserverParent) compositeGoal2;
        
        GoalObserver treasure = new TreasureGoal();
        GoalObserver enemies = new EnemyGoal();
        GoalObserver switches = new SwitchGoal();
        
        compositeParent1.addChildGoal(treasure);
        compositeParent1.addChildGoal(compositeGoal2);
        compositeParent2.addChildGoal(enemies);
        compositeParent2.addChildGoal(switches);

        Treasure t1 = new Treasure(5, 5);
        Treasure t2 = new Treasure(7, 7);
        Player p1 = new Player(dungeon, 3, 3);
        Enemy enemy1 = new Enemy(8, 8, dungeon);
        Enemy enemy2 = new Enemy(9, 9, dungeon);
        Sword s = new Sword(4, 4, p1);
        PlayerOrientation right = new RightOrientation(p1, dungeon);
        FloorSwitch s1 = new FloorSwitch(3, 1);
        Boulder b1 = new Boulder(dungeon, 3, 2);
        dungeon.setGoal(compositeGoal1);

        dungeon.addEntity(t1);
        dungeon.addEntity(t2);
        dungeon.addEntity(enemy1);
        dungeon.addEntity(enemy2);
        dungeon.addEntity(s);
        dungeon.setPlayer(p1);
        dungeon.addEntity(s1);
        dungeon.addEntity(b1);

        dungeon.printDungeon();

        System.out.println(switches.getSubjects());
        System.out.println(enemies.getSubjects());
        System.out.println(treasure.getSubjects());

        p1.moveUp();

        dungeon.printDungeon();
        System.out.println(switches.isComplete());

        t1.pickup(p1);
        t2.pickup(p1);

        System.out.println(treasure.isComplete());
        System.out.println(compositeGoal1.isComplete());
        System.out.println(compositeGoal2.isComplete());

    }

}