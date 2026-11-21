package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BargeTest {
    @ParameterizedTest
    @EnumSource(Compass.class)
    @DisplayName("A barca ocupa uma única célula em qualquer orientação")
    void singleCell(Compass bearing) {
        Position origin = new Position(2, 3);
        Barge barge = new Barge(bearing, origin);
        assertAll(() -> assertEquals("Barca", barge.getCategory()),
                () -> assertEquals(1, barge.getSize()),
                () -> assertEquals(bearing, barge.getBearing()),
                () -> assertSame(origin, barge.getPosition()),
                () -> assertEquals(List.of(new Position(2, 3)), barge.getPositions()),
                () -> assertNotSame(origin, barge.getPositions().get(0)));
    }
}
