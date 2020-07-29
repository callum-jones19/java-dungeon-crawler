package unsw.dungeon;

public enum ZLayer {
    BACKGROUND(0),
    FLOOR(1),
    ITEM(2),
    MOVEABLE(3),
    STOPPABLE(4);

    private final int z;

    private ZLayer(int z) {
        this.z = z;
    }

    public int getZIndex() {
        return this.z;
    }
}
