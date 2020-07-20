package unsw.dungeon;

/**
 * Interface for any object that should be able to process being killed by a
 * damaging collision (vulnerable or damage) - or potentially anything else
 * that 'does damage'.
 */
public interface IDamagable {
    void die();
}
