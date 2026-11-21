package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    @Test
    @DisplayName("Uma posição nova guarda as coordenadas e começa livre e sem tiro")
    void initialState() {
        Position position = new Position(2, 3);
        assertAll(() -> assertEquals(2, position.getRow()),
                () -> assertEquals(3, position.getColumn()),
                () -> assertFalse(position.isOccupied()),
                () -> assertFalse(position.isHit()),
                () -> assertEquals("Linha = 2 Coluna = 3", position.toString()));
    }

    @Test
    @DisplayName("Ocupar e atingir são operações independentes e idempotentes")
    void occupancyAndShots() {
        Position position = new Position(2, 3);
        position.occupy();
        position.occupy();
        assertTrue(position.isOccupied());
        assertFalse(position.isHit());
        position.shoot();
        position.shoot();
        assertTrue(position.isHit());
        assertTrue(position.isOccupied());
        Position empty = new Position(0, 0);
        empty.shoot();
        assertFalse(empty.isOccupied());
        assertTrue(empty.isHit());
    }

    @Test
    @DisplayName("A igualdade compara coordenadas, incluindo identidade e tipos incompatíveis")
    void equality() {
        Position position = new Position(2, 3);
        Position same = new Position(2, 3);
        assertEquals(position, position);
        assertEquals(position, same);
        assertEquals(position.hashCode(), same.hashCode());
        assertNotEquals(position, new Position(1, 3));
        assertNotEquals(position, new Position(2, 4));
        assertNotEquals(position, null);
        assertNotEquals(position, "2,3");
        same.occupy();
        same.shoot();
        assertEquals(position, same);
    }

    @ParameterizedTest(name = "({0}, {1}) adjacente a (3, 3): {2}")
    @CsvSource({"2,2,true", "2,3,true", "2,4,true", "3,2,true", "3,3,true",
            "3,4,true", "4,2,true", "4,3,true", "4,4,true", "1,3,false",
            "5,3,false", "3,1,false", "3,5,false"})
    @DisplayName("A adjacência inclui diagonais e a própria posição, mas exclui distância superior a um")
    void adjacency(int row, int column, boolean adjacent) {
        assertEquals(adjacent, new Position(3, 3).isAdjacentTo(new Position(row, column)));
    }
}
