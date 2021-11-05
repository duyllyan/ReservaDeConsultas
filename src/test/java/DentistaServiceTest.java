import br.com.duyllyan.reservaconsultas.database.ClinicaDatabase;
import br.com.duyllyan.reservaconsultas.database.Conexao;
import br.com.duyllyan.reservaconsultas.model.entities.Dentista;
import br.com.duyllyan.reservaconsultas.model.service.DentistaService;
import br.com.duyllyan.reservaconsultas.model.service.factory.ServiceFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DentistaServiceTest {

    DentistaService dentistaService;
    Dentista dentista1;
    Dentista dentista2;
    Dentista dentista3;

    @BeforeEach
    void getConnection() {
        ClinicaDatabase.getInstance();
        dentistaService = ServiceFactory.createDentistaService();

        dentista1 = new Dentista(12345, "Mauro", "Lopes");
        dentista2 = new Dentista(45678, "Daniela", "Arruda");
        dentista3 = new Dentista(78945, "Julio", "Fernandes");
    }

    @Test
    void insertDentista() {
        dentistaService.insertDentista(dentista1);
        dentistaService.insertDentista(dentista2);
        dentistaService.insertDentista(dentista3);
        assertNotNull(dentistaService.selectAllDentistas());
    }

    @Test
    void getAllDentistas() {
        assertEquals(3, dentistaService.selectAllDentistas().size());
        assertEquals("Julio", dentistaService.selectAllDentistas().get(2).getNome());
    }

    @AfterAll
    static void closeConnection() {
        Conexao.closeConnection();
    }
}
