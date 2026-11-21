package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarrackTest {
    @ParameterizedTest
    @EnumSource(value = Compass.class, names = {"NORTH", "SOUTH"})
    @DisplayName("A nau vertical ocupa três linhas consecutivas")
    void vertical(Compass bearing) {
        Carrack ship = new Carrack(bearing, new Position(2, 3));
        assertEquals("Nau", ship.getCategory());
        assertEquals(3, ship.getSize());
        assertEquals(List.of(new Position(2, 3), new Position(3, 3), new Position(4, 3)), ship.getPositions());
    }

    @ParameterizedTest
    @EnumSource(value = Compass.class, names = {"EAST", "WEST"})
    @DisplayName("A nau horizontal ocupa três colunas consecutivas")
    void horizontal(Compass bearing) {
        assertEquals(List.of(new Position(2, 3), new Position(2, 4), new Position(2, 5)),
                new Carrack(bearing, new Position(2, 3)).getPositions());
    }

    @Test
    @DisplayName("A nau rejeita UNKNOWN e os argumentos nulos com assertions ativas")
    void invalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> new Carrack(Compass.UNKNOWN, new Position(2, 3)));
        assertThrows(AssertionError.class, () -> new Carrack(null, new Position(2, 3)));
        assertThrows(AssertionError.class, () -> new Carrack(Compass.NORTH, null));
    }
}
