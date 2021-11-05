import br.com.duyllyan.reservaconsultas.database.ClinicaDatabase;
import br.com.duyllyan.reservaconsultas.database.Conexao;
import br.com.duyllyan.reservaconsultas.model.entities.Endereco;
import br.com.duyllyan.reservaconsultas.model.entities.Paciente;
import br.com.duyllyan.reservaconsultas.model.service.EnderecoService;
import br.com.duyllyan.reservaconsultas.model.service.PacienteService;
import br.com.duyllyan.reservaconsultas.model.service.factory.ServiceFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PacienteServiceTest {

    EnderecoService enderecoService;
    PacienteService pacienteService;
    Endereco endereco1;
    Endereco endereco2;

    Paciente paciente1;
    Paciente paciente2;
    Paciente paciente3;

    @BeforeAll
    static void createDatabase() {
        ClinicaDatabase.createDataBase();
    }

    @BeforeEach
    void prepareConnection() {
        enderecoService = ServiceFactory.createEnderecoService();
        pacienteService = ServiceFactory.createPacienteService();
    }

    @Test
    void searchByID() {
        endereco1 = new Endereco("Rua Jasmim", 18, "Jardim das Flores", "Araguaína", "TO");
        endereco2 = new Endereco("Rua Tulipa", 22, "Jardim das Flores", "Araguaína", "TO");

        paciente1 = new Paciente("Willian", "Neres", "12345", new Date(), endereco1);
        paciente2 = new Paciente("Duyllyan", "Almeida", "54987", new Date(), endereco1);
        paciente3 = new Paciente("Inês", "Brasil", "0000", new Date(), endereco2);

        enderecoService.insertEndereco(endereco1);
        enderecoService.insertEndereco(endereco2);

        pacienteService.insertPaciente(paciente1);
        pacienteService.insertPaciente(paciente2);
        pacienteService.insertPaciente(paciente3);

        assertEquals(2, pacienteService.selectPacienteByID(2).getId());
        assertEquals("Duyllyan", pacienteService.selectPacienteByID(2).getNome());
    }

    @Test
    void deleteAndSearch() {
        assertNotNull(pacienteService.selectPacienteByID(2));
        pacienteService.deletePacienteByID(2);
        assertNull(pacienteService.selectPacienteByID(2));
    }

    @Test
    void searchAll() {
        pacienteService.selectAllPaciente().forEach(System.out::println);
        assertEquals(2, pacienteService.selectAllPaciente().size());
    }

    @AfterAll
    static void closeConnection() {
        Conexao.closeConnection();
    }
}
