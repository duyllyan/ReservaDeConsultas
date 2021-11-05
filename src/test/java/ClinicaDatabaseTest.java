import br.com.duyllyan.reservaconsultas.database.ClinicaDatabase;
import br.com.duyllyan.reservaconsultas.database.Conexao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClinicaDatabaseTest {

    @Test
    void createDatabase() {
        assertNotNull(ClinicaDatabase.getInstance());
    }

    @AfterAll
    static void closeConnection() {
        Conexao.closeConnection();
    }
}
