package unsw.dungeon;

public class TriggerTypePlayerInventory implements TriggerType {

    Item item;

    public TriggerTypePlayerInventory(Item i) {
        super();
        this.item = i;
    }

    public boolean performTriggerCheck(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;
            if (p.contains(item)) {
                if (!(item.checkCanUse())) {
                    p.removeItem(item);
                }
                return true;
            }

        }
        return false;
    }    



}