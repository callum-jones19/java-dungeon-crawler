package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");
        JSONObject goals = json.getJSONObject("goal-condition");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        loadGoals(dungeon, goals);
        return dungeon;
    }

    private void loadGoals(Dungeon dungeon, JSONObject goals) {
        
        String goal = goals.getString("goal");
        if (!goal.equals("AND") && !goal.equals("OR")) {
            GoalObserverParent currGoal = (GoalObserverParent) dungeon.getCompositeGoal();
            switch (goal) {
                case "treasure":
                    if (currGoal != null) {
                        currGoal.addChildGoal(new TreasureGoal());
                    } else {
                        dungeon.setGoal(new TreasureGoal());
                    }
                    System.out.println("Added Treasure Goal (singleton)");
                    break;
                case "enemies":
                    if (currGoal != null) {
                        currGoal.addChildGoal(new EnemyGoal());
                    } else {
                        dungeon.setGoal(new EnemyGoal());
                    }
                    System.out.println("Added Enemy Goal (singleton)");
                    break;
                case "switches":
                    if (currGoal != null) {
                        currGoal.addChildGoal(new SwitchGoal());
                    } else {
                        dungeon.setGoal(new SwitchGoal());
                    }
                    System.out.println("Added Switch Goal (singleton)");
                    break;
                case "exit":
                    if (currGoal != null) {
                        currGoal.addChildGoal(new ExitGoal());
                    } else {
                        dungeon.setGoal(new ExitGoal());
                    }
                    System.out.println("Added Exit Goal (singleton)");
                    break;
            }
        } else {
            dungeon.setGoal(new CompositeGoal(goal.equals("AND")));
            System.out.println("Set composite goal");
            JSONArray subgoals = goals.getJSONArray("subgoals");
            for (int i = 0; i < subgoals.length(); i++) {
                loadGoals(dungeon, subgoals.getJSONObject(i));
            }

        }

    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "exit":
            Exit exit = new Exit(x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "switch":
            FloorSwitch floorSwitch = new FloorSwitch(x, y);
            onLoad(floorSwitch);
            entity = floorSwitch;
            break;
        case "sword":
            Sword sword = new Sword(x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "invincibility":
            Potion potion = new Potion(x, y);
            onLoad(potion);
            entity = potion;
            break;
        case "enemy":
            Enemy enemy = new Enemy(x, y, dungeon);
            onLoad(enemy);
            entity = enemy;
            break;
        case "treasure":
            Treasure treasure = new Treasure(x, y);
            onLoad(treasure);
            entity = treasure;
            break;

        // TODO Handle other possible entities
        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Potion potion);

    public abstract void onLoad(FloorSwitch floorSwitch);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Treasure treasure);

    // TODO Create additional abstract methods for the other entities

}
