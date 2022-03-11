package unsw.dungeon;

public class TriggerTypePlayerInventory implements TriggerType {

    private Item item;

    public TriggerTypePlayerInventory(Item i) {
        super();
        this.item = i;
    }

    public boolean performTriggerCheck(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;
            if (p.isHoldingInstance(item)) {
                if (!(item.canUseAgain())) {
                    p.removeItem(item);
                }
                return true;
            }

        }
        return false;
    }    



}