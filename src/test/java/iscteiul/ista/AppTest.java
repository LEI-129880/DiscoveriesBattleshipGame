package iscteiul.ista;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {
    @Test
    @DisplayName("A aplicação apresenta o título e termina com o comando desisto")
    void startsAndQuits() {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream("desisto\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(capturedOut);

            assertDoesNotThrow(() -> App.main(new String[0]));
            assertTrue(output.toString(StandardCharsets.UTF_8).contains("***  Battleship Game ***"));
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
