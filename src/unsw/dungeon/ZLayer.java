package unsw.dungeon;

public enum ZLayer {
    // TODO - having the code that rendered this like it was set up made it extensible
    // when writing the static part, realised I should separate walls from doors, and
    // it was easy to do so because of this.
    BACKGROUND(0, true),
    FLOOR(1, true),
    ITEM(2, false),
    OBSTACLE(3, false),
    MOVEABLE(4, false),
    WALL(5, true);

    // Layer of the entity.
    private final int z;
    // Can this layer's entities ever change?
    private final boolean isStatic;

    private ZLayer(int z, boolean isStatic) {
        this.z = z;
        this.isStatic = isStatic;
    }

    public int getZIndex() {
        return this.z;
    }

    public boolean isStatic() {
        return this.isStatic;
    }
}
