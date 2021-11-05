import br.com.duyllyan.reservaconsultas.database.ClinicaDatabase;
import org.junit.jupiter.api.Test;

public class ClinicaDatabaseTest {

    @Test
    void createDatabase() {
        ClinicaDatabase.getInstance();
    }
}
