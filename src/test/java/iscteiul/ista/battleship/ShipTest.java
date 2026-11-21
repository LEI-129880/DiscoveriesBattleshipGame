package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {
    @ParameterizedTest
    @CsvSource({"barca,Barge,1", "caravela,Caravel,2", "nau,Carrack,3",
            "fragata,Frigate,4", "galeao,Galleon,5"})
    @DisplayName("A fábrica cria cada um dos cinco tipos de navio")
    void factory(String kind, String className, int size) {
        Ship ship = Ship.buildShip(kind, Compass.NORTH, new Position(2, 3));
        assertEquals(className, ship.getClass().getSimpleName());
        assertEquals(size, ship.getSize());
    }

    @Test
    @DisplayName("Um tipo desconhecido não produz navio")
    void unknownKind() {
        assertNull(Ship.buildShip("desconhecido", Compass.NORTH, new Position(2, 3)));
    }

    @Test
    @DisplayName("O navio expõe categoria, origem, orientação e representação textual")
    void metadata() {
        Position origin = new Position(2, 3);
        Ship ship = new Barge(Compass.WEST, origin);
        assertEquals("Barca", ship.getCategory());
        assertSame(origin, ship.getPosition());
        assertEquals(Compass.WEST, ship.getBearing());
        assertEquals("[Barca o Linha = 2 Coluna = 3]", ship.toString());
    }

    @Test
    @DisplayName("Tiros fora do navio e tiros repetidos não o afundam prematuramente")
    void hitsAndSinking() {
        Ship ship = new Caravel(Compass.EAST, new Position(2, 3));
        ship.shoot(new Position(0, 0));
        assertTrue(ship.stillFloating());
        assertFalse(ship.getPositions().get(0).isHit());
        assertFalse(ship.getPositions().get(1).isHit());
        ship.shoot(new Position(2, 3));
        ship.shoot(new Position(2, 3));
        assertTrue(ship.getPositions().get(0).isHit());
        assertTrue(ship.stillFloating());
        ship.shoot(new Position(2, 4));
        assertFalse(ship.stillFloating());
    }

    @ParameterizedTest
    @CsvSource({"NORTH,3,5", "SOUTH,2,4", "EAST,1,3", "WEST,3,5"})
    @DisplayName("Os limites do navio resultam de todas as células, independentemente da ordem")
    void bounds(Compass bearing, int left, int right) {
        Ship ship = new Galleon(bearing, new Position(2, 3));
        assertBounds(ship, left, right);
        Collections.reverse(ship.getPositions());
        assertBounds(ship, left, right);
    }

    private static void assertBounds(Ship ship, int left, int right) {
        assertAll(() -> assertEquals(2, ship.getTopMostPos()),
                () -> assertEquals(4, ship.getBottomMostPos()),
                () -> assertEquals(left, ship.getLeftMostPos()),
                () -> assertEquals(right, ship.getRightMostPos()));
    }

    @Test
    @DisplayName("A ocupação distingue a primeira célula, a última célula e a água")
    void occupation() {
        Ship ship = new Caravel(Compass.EAST, new Position(2, 3));
        assertTrue(ship.occupies(new Position(2, 3)));
        assertTrue(ship.occupies(new Position(2, 4)));
        assertFalse(ship.occupies(new Position(2, 5)));
    }

    @Test
    @DisplayName("A proximidade reconhece sobreposição, contacto diagonal e navios afastados")
    void proximity() {
        Ship ship = new Caravel(Compass.EAST, new Position(2, 3));
        assertTrue(ship.tooCloseTo(new Position(2, 3)));
        assertTrue(ship.tooCloseTo(new Position(3, 5)));
        assertFalse(ship.tooCloseTo(new Position(6, 6)));
        assertTrue(ship.tooCloseTo(new Barge(Compass.NORTH, new Position(1, 2))));
        assertFalse(ship.tooCloseTo(new Caravel(Compass.EAST, new Position(6, 6))));
    }

    @Test
    @DisplayName("As pré-condições rejeitam origem, orientação, tiro e navio nulos")
    void preconditions() {
        Ship ship = new Barge(Compass.NORTH, new Position(2, 3));
        assertThrows(AssertionError.class, () -> new Barge(null, new Position(2, 3)));
        assertThrows(AssertionError.class, () -> new Barge(Compass.NORTH, null));
        assertThrows(AssertionError.class, () -> ship.occupies(null));
        assertThrows(AssertionError.class, () -> ship.shoot(null));
        assertThrows(AssertionError.class, () -> ship.tooCloseTo((IShip) null));
    }
}
