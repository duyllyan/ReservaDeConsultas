import br.com.duyllyan.reservaconsultas.database.ClinicaDatabase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClinicaDatabaseTest {

    @Test
    void createDatabase() {
        assertNotNull(ClinicaDatabase.getInstance());
    }
}
