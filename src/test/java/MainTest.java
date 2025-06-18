import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testAddCommand() {
        // Captura System.out
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Executa
        Main.main(new String[] {"add", "Buy groceries"});

        // Verifica
        assertThat(output.toString()).contains("New task title: Buy groceries");
    }

}
