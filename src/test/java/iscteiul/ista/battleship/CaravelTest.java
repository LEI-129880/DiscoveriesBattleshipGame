package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CaravelTest {
    @ParameterizedTest
    @EnumSource(value = Compass.class, names = {"NORTH", "SOUTH"})
    @DisplayName("A caravela vertical ocupa duas linhas consecutivas")
    void vertical(Compass bearing) {
        Caravel ship = new Caravel(bearing, new Position(2, 3));
        assertEquals("Caravela", ship.getCategory());
        assertEquals(2, ship.getSize());
        assertEquals(List.of(new Position(2, 3), new Position(3, 3)), ship.getPositions());
    }

    @ParameterizedTest
    @EnumSource(value = Compass.class, names = {"EAST", "WEST"})
    @DisplayName("A caravela horizontal ocupa duas colunas consecutivas")
    void horizontal(Compass bearing) {
        assertEquals(List.of(new Position(2, 3), new Position(2, 4)),
                new Caravel(bearing, new Position(2, 3)).getPositions());
    }

    @Test
    @DisplayName("A caravela rejeita UNKNOWN e os argumentos nulos com assertions ativas")
    void invalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> new Caravel(Compass.UNKNOWN, new Position(2, 3)));
        assertThrows(AssertionError.class, () -> new Caravel(null, new Position(2, 3)));
        assertThrows(AssertionError.class, () -> new Caravel(Compass.NORTH, null));
    }
}
