package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;


public class EnemyTest {
    @Test
    public void EnemyDamageTest () {
        Dungeon dungeon = new Dungeon(10, 10);
        Enemy e = new Enemy(5, 5, dungeon);
        dungeon.addEntity(e);
        Player p = new Player(dungeon, 4, 5);
        dungeon.setPlayer(p);

        p.moveRight();

        assertEquals(null, dungeon.getPlayer());
    }
}