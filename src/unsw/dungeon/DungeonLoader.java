package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String pathPattern = "/";
        Pattern pathPatternCompiled = Pattern.compile(pathPattern);

        Matcher m = pathPatternCompiled.matcher(filename);
        if (m.find()) {
            json = new JSONObject(new JSONTokener(new FileReader(filename)));
        } else {
            json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
        }
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
        try {
            JSONObject goals = json.getJSONObject("goal-condition");
            loadGoals(dungeon, goals);
        } catch (JSONException e) {
            // do nothing
        }

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        dungeon.initialiseGoalInfo();
        dungeon.activateSwitches();

        return dungeon;
    }

    private void loadGoals(Dungeon dungeon, JSONObject goals) {
        
        if (goals == null) return;

        String goal = goals.getString("goal");
        GoalObserverParent currGoal = (GoalObserverParent) dungeon.getCompositeGoal();
        if (!goal.equals("AND") && !goal.equals("OR")) {
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
                case "boulders":
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
            if (currGoal != null) {
                GoalObserverParent newCompGoal = new CompositeGoal(goal.equals("AND"));
                currGoal.addChildGoal((GoalObserver) newCompGoal);
                System.out.println("Added new child composite goal!");
                JSONArray subgoals = goals.getJSONArray("subgoals");
                for (int i = 0; i < subgoals.length(); i++) {
                    loadGoals(newCompGoal, subgoals.getJSONObject(i));
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

    }

    private void loadGoals(GoalObserverParent newCompGoal, JSONObject goalJSON) {

        String goal = goalJSON.getString("goal");
        switch (goal) {
            case "treasure":
                newCompGoal.addChildGoal(new TreasureGoal());
                break;
            case "enemies":
                newCompGoal.addChildGoal(new EnemyGoal());
                break;
            case "switches":
                newCompGoal.addChildGoal(new SwitchGoal());
                break;
            case "exit":
                newCompGoal.addChildGoal(new ExitGoal());
                break;
            case "AND":
                GoalObserverParent compGoalAnd = new CompositeGoal(true);
                newCompGoal.addChildGoal((GoalObserver) compGoalAnd);
                System.out.println("Set composite goal");
                JSONArray subgoalsAnd = goalJSON.getJSONArray("subgoals");
                for (int i = 0; i < subgoalsAnd.length(); i++) {
                    loadGoals(compGoalAnd, subgoalsAnd.getJSONObject(i));
                }
                break;
            case "OR":
                GoalObserverParent compGoalOr = new CompositeGoal(false);
                newCompGoal.addChildGoal((GoalObserver) compGoalOr);
                System.out.println("Set composite goal");
                JSONArray subgoalsOr = goalJSON.getJSONArray("subgoals");
                for (int i = 0; i < subgoalsOr.length(); i++) {
                    loadGoals(compGoalOr, subgoalsOr.getJSONObject(i));
                }
                break;
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
        case "portal":
            int id = json.getInt("id");
            Portal p = new Portal(x, y, id);
            Portal link = dungeon.findPortal(id);
            if (link != null) {
                p.linkPortal(link);
                link.linkPortal(p);
            }
            onLoad(p);
            entity = p;
            break;
        case "door":
            int doorID = json.getInt("id");
            Door d = new Door(x, y, doorID);
            onLoad(d);
            Key linkedKey = dungeon.findKey(doorID);
            if (linkedKey != null) {
                d.linkKey(linkedKey);
                linkedKey.linkDoor(d);
            }
            entity = d;
            break;
        case "key":
            int keyID = json.getInt("id");
            Key k = new Key(x, y, keyID);
            onLoad(k);
            Door linkedDoor = dungeon.findDoor(keyID);
            if (linkedDoor != null) {
                k.linkDoor(linkedDoor);
                linkedDoor.linkKey(k);
            }
            entity = k;
            break;
        case "dungeonEntry":
            String dungeonFile = json.getString("dungeonFile");
            DungeonEntry de = new DungeonEntry(x, y, dungeonFile);
            onLoad(de);
            entity = de;
        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Player player);

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

    public abstract void onLoad(DungeonEntry entry);


}
