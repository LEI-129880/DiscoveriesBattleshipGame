package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CompassTest {
    @ParameterizedTest(name = "{0} representa {1}")
    @CsvSource({"n,NORTH", "s,SOUTH", "e,EAST", "o,WEST", "u,UNKNOWN"})
    @DisplayName("Cada orientação mantém a letra e a representação textual portuguesas")
    void directions(char letter, Compass compass) {
        assertEquals(letter, compass.getDirection());
        assertEquals(String.valueOf(letter), compass.toString());
        assertEquals(compass, Compass.charToCompass(letter));
    }

    @ParameterizedTest
    @CsvSource({"x", "N", "w", "0"})
    @DisplayName("Letras desconhecidas ou maiúsculas devolvem UNKNOWN")
    void unknownDirection(char letter) {
        assertEquals(Compass.UNKNOWN, Compass.charToCompass(letter));
    }
}
