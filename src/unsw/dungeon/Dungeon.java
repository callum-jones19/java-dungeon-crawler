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

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<Entity>();
        this.player = null;
    }

    public void update(DestroySubject sub) {
        if (sub instanceof Player) {
            removePlayer((Player) sub);
        } else if (sub instanceof Entity) {
            this.entities.remove(sub);
            sub.removeObserver(this);
        }
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
        player.registerObserver(this);
    }

    public void removePlayer(Player p) {
        this.player = null;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        entity.registerObserver(this);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public List<Entity> getEntities(int x, int y) {
        List<Entity> result = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y) {
                result.add(e);
            }
        }
        if (player.getX() == x && player.getY() == y) {
            result.add(player);
        }
        return result;
    }

    public boolean tileIsEmpty(int x, int y) {
        List<Entity> e = getEntities(x, y);
        if (e.isEmpty() && (player.getX() != x || player.getY() != y)) {
            return true;
        } else {
            return false;
        }
    }
}
