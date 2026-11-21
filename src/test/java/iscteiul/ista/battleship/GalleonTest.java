package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GalleonTest {
    @ParameterizedTest(name = "Galeão orientado a {0}")
    @CsvSource(value = {
            "NORTH;2,3 2,4 2,5 3,4 4,4",
            "SOUTH;2,3 3,3 4,2 4,3 4,4",
            "EAST;2,3 3,1 3,2 3,3 4,3",
            "WEST;2,3 3,3 3,4 3,5 4,3"}, delimiter = ';')
    @DisplayName("O galeão conserva as cinco células e a ordem de cada orientação")
    void layout(Compass bearing, String coordinates) {
        Position origin = new Position(2, 3);
        Galleon ship = new Galleon(bearing, origin);
        List<Position> expected = Arrays.stream(coordinates.split(" "))
                .map(pair -> pair.split(","))
                .map(pair -> new Position(Integer.parseInt(pair[0]), Integer.parseInt(pair[1])))
                .toList();
        assertAll(() -> assertEquals("Galeao", ship.getCategory()),
                () -> assertEquals(5, ship.getSize()),
                () -> assertEquals(bearing, ship.getBearing()),
                () -> assertSame(origin, ship.getPosition()),
                () -> assertEquals(expected, ship.getPositions()));
    }

    @Test
    @DisplayName("O galeão rejeita UNKNOWN e os argumentos nulos com assertions ativas")
    void invalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> new Galleon(Compass.UNKNOWN, new Position(2, 3)));
        assertThrows(AssertionError.class, () -> new Galleon(null, new Position(2, 3)));
        assertThrows(AssertionError.class, () -> new Galleon(Compass.NORTH, null));
    }
}
