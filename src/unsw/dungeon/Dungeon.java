/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
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
public class Dungeon implements DestroyObserver{

    private int width, height;

    private List<Entity> entities;
    private Player player;
    
    private boolean isComplete;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<Entity>();
        this.player = null;
    }

    public void update(DestroySubject sub) {
        if (sub instanceof Player) {
            removePlayer((Player) sub);
        }
            this.entities.remove(sub);
    }

    public boolean isComplete() {
        return isComplete;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean entityExists(Entity e) {
        return entities.contains(e);
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
        if (!entities.contains(entity)) {
            entities.add(entity);
            entity.registerObserver(this);
        }
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public Portal findPortal(int id) {
        Portal p = null;
        for (Entity e : entities) {
            if (e instanceof Portal) {
                p = (Portal) e;
            }
        }
        return p;
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
        List<Entity> entities = getEntities(x, y);
        if (entities.isEmpty()) {
            return null;
        } else {
            for (Entity e: entities) {
                if (e instanceof IMoveable) {
                    return e;
                }
            }
            return entities.get(0);
        }
    }

    // FIXME ? Is there a cleaner way to do this
    public Entity getTopmostEntity(int x, int y, Entity ignore) {
        List<Entity> entities = getEntities(x, y);
        Entity result = null;
        if (entities.isEmpty()) {
            result = null;
        } else {
            for (Entity e: entities) {
                if (e instanceof IMoveable && !e.equals(ignore)) {
                    return e;
                }
            }
            for (Entity e : entities) {
                if (! (e instanceof IMoveable)) {
                    return e;
                }
            }
        }
        
        return result;
    }

    public boolean isTileEnterable(int x, int y) {
        Entity e;
        e = getTopmostEntity(x, y);
        if (e == null) {
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
                    System.out.print("F");
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

    public boolean checkCoordinatesValidity() {
        // TODO
        return false;
    }

}
