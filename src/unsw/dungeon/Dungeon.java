/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements DestroyObserver {

    private int width, height;

    private List<Entity> entities;
    private Player player;

    private GoalObserver goal;
    private HashMap<GoalObserver, Integer> startingGoalInformation;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<Entity>();
        this.startingGoalInformation = new HashMap<GoalObserver, Integer>();
        this.player = null;
    }

    public void update(DestroySubject sub) {
        if (sub instanceof Player) {
            removePlayer((Player) sub);
        } 
        this.entities.remove(sub);
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void executeUpdates(double deltaTime) {
        List<Entity> entCopy = new ArrayList<Entity>(entities);
        for (Entity e : entCopy) {
            if (e instanceof IUpdateable) {
                IUpdateable u = (IUpdateable) e;
                u.update(deltaTime);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerX() {
        return player.getX();
    }

    public int getPlayerY() {
        return player.getY();
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.entities.add(player);
        player.registerObserver(this);
    }

    public void removePlayer(Player p) {
        this.player = null;
    }

    public void addEntity(Entity entity) {

        if (entity == null) {
            System.out.println("Hello!");
        }

        if (entities.contains(entity)) {
            return;
        }
        
        entities.add(entity);
        if (entity instanceof Boulder) {
            registerBoulderObservers((Boulder) entity);
        } else if (entity instanceof FloorSwitch) {
            registerBoulderObservers((FloorSwitch) entity);
        }
        if (goal != null) goal.addGoalEntity(entity);
        entity.registerObserver(this);
    }

    public Portal findPortal(int id) {
        Portal p = null;
        for (Entity e : entities) {
            if (e instanceof Portal) {
                Portal tmp = (Portal) e;
                if (tmp.getID() == id) {
                   p = tmp;
                }
            }
        }
        return p;
    }

    public Door findDoor(int id) {
        Door d = null;
        for (Entity e : entities) {
            if (e instanceof Door) {
                Door tmp = (Door) e;
                if (tmp.getID() == id) {
                    d = tmp;
                }
            }
        }
        System.out.println(d);
        return d;
    }

    public Key findKey(int id) {
        Key d = null;
        for (Entity e : entities) {
            if (e instanceof Key) {
                Key tmp = (Key) e;
                if (tmp.getID() == id) {
                    d = tmp;
                }
            }
        }
        System.out.println(d);
        return d;
    }

    public List<Entity> getEntities(int x, int y) {
        List<Entity> result = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y) {
                result.add(e);
            }
        }
        return result;
    }

    public boolean tileIsEmpty(int x, int y) {
        List<Entity> e = getEntities(x, y);
        if (e.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Entity getTopmostEntity(int x, int y) {
        List<Entity> ens = getEntities(x, y);
        if (ens.isEmpty()) {
            return null;
        } else {
            for (Entity e: ens) {
                if (e instanceof IMoveable) {
                    return e;
                }
            }
            return ens.get(0);
        }
    }

    public Entity getTopmostEntity(int x, int y, Entity ignore) {
        List<Entity> ens = getEntities(x, y);
        Entity result = null;
        // NB: This branch is never called because this version of the function
        // is only called when there is guaranteed to be a collision.
        // Best to leave it in for saftey though.
        if (ens.isEmpty()) {
            result = null;
        } else {
            for (Entity e: ens) {
                if (e instanceof IMoveable && !e.equals(ignore)) {
                    return e;
                }
            }
            for (Entity e : ens) {
                if (! (e instanceof IMoveable)) {
                    return e;
                }
            }
        }
        
        // Again, given context, this should never happen.
        return result;
    }

    public boolean isTileEnterable(int x, int y) {
        Entity e;
        e = getTopmostEntity(x, y);
        if (e == null) {
            // Only called in collisions, so this doesn't need to be tested.
            return true;
        } else {
            return e.isEnterable();
        }
    }

    public void printDungeon() {
        System.out.println("<========= Dungeon =========>");
        for (int i = 0; i  < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                Entity e = getTopmostEntity(j, i);
                if (e == null) {
                    System.out.print("-");
                } else if (e instanceof Player) {
                    System.out.print("P");
                } else if (e instanceof Boulder) {
                    System.out.print("B");
                } else if (e instanceof Wall) {
                    System.out.print("W");
                } else if (e instanceof Door) {
                    System.out.print("D");
                } else if (e instanceof Item) {
                    System.out.print("I");
                } else if (e instanceof FloorSwitch) {
                    FloorSwitch f = (FloorSwitch) e;
                    if (f.isActive()) {
                        System.out.print("F*");
                    } else {
                        System.out.print("F");
                    }
                } else if (e instanceof Portal) {
                    System.out.print("_");
                } else if (e instanceof Enemy) {
                    System.out.print("E");
                } else {
                    System.out.print("*");
                }
            }
            System.out.print("\n");
        }
    }
    
    public List<Enemy> getEnemies() {
        List<Enemy> result = new ArrayList<Enemy>();
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                result.add((Enemy) e);
            }
        }
        return result;
    }

    public void scareEnemies() {
        List<Enemy> enemies = getEnemies();

        for (Enemy e : enemies) {
            e.makeVulnerable();
        }
    }

    public void unScareEnemies() {
        List<Enemy> enemies = getEnemies();

        for (Enemy e : enemies) {
            e.makeHarmful();
        }
    }

	public void processCollision(Entity e, int x, int y) {
        Entity top = getTopmostEntity(x, y, e);
        top.onCollide(e);
    }

    public boolean hasEntity(Entity e) {
        return entities.contains(e);
    }

    public void registerBoulderObservers(Boulder b) {
        
        for(Entity e: entities) {
            if (e instanceof FloorSwitch) {
                b.registerObserver((FloorSwitch) e);
            }
        }
    }

    public void registerBoulderObservers(FloorSwitch f) {
        for (Entity e: entities) {
            if (e instanceof Boulder) {
                Boulder b = (Boulder) e;
                b.registerObserver(f);
            }
        }
    }

    public void setGoal(GoalObserver g) {
        this.goal = g;
    }

    public List<GoalObserver> getGoals() {
        if (this.goal == null) return null;
        return this.goal.getGoal();
    }

    public void initialiseGoalInfo() {
        List<GoalObserver> goals = getGoals();
        if (goals == null) {
            this.startingGoalInformation = null;
            return;
        }
        for (GoalObserver g: goals) {
            this.startingGoalInformation.put(g, g.getGoalEntitySize());
        }
    }

    public boolean isComplete() {
        if (this.goal == null) return false;
        return this.goal.isComplete();
    }

    public boolean areCoordinatesValid(int x, int y) {
        if (y < 0 || x < 0) {
            return false;
        }
        if (x >= getWidth() || y >= getHeight()) {
            return false;
        }
        return true;
    }

	public List<Entity> getEntities(ZLayer layer) {
        List<Entity> answer = new ArrayList<Entity>();

        for (Entity e : entities) {
            if (e.getLayer() == layer) {
                answer.add(e);
            }
        }
        
        return answer;
    }
    
    public GoalObserver getCompositeGoal() {
        if (this.goal instanceof CompositeGoal) {
            return this.goal;
        }
        return null;
    }

	public HashMap<GoalObserver, Integer> getGoalInfo() {
		return this.startingGoalInformation;
    }
    
    public String getGoalString() {
        if (this.goal == null) return null;
        return this.goal.getGoalString();
    }

    public void linkEntrances(EntryObserver o) {
        for (DungeonEntry e : getEntries()) {
            EntrySubject s = (EntrySubject) e;
            s.addEntryObserver(o);
        }
    }

    public List<DungeonEntry> getEntries() {
        List<DungeonEntry> tmp = new ArrayList<DungeonEntry>();
        for (Entity e : entities) {
            if (e instanceof DungeonEntry) {
                tmp.add((DungeonEntry) e);
            }
        }
        return tmp;
    }

    public void activateSwitches() {
        for (Entity e: entities) {
            if (e instanceof Boulder) {
                Boulder b = (Boulder) e;
                b.notifyBoulderObservers();
            }
        }
    }

}
