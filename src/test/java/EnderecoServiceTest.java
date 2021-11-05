import br.com.duyllyan.reservaconsultas.database.ClinicaDatabase;
import br.com.duyllyan.reservaconsultas.database.Conexao;
import br.com.duyllyan.reservaconsultas.model.entities.Endereco;
import br.com.duyllyan.reservaconsultas.model.service.EnderecoService;
import br.com.duyllyan.reservaconsultas.model.service.factory.ServiceFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnderecoServiceTest {

    EnderecoService enderecoService;
    Endereco endereco1;
    Endereco endereco2;

    @BeforeAll
    static void createDatabase() {
        ClinicaDatabase.createDataBase();
    }

    @BeforeEach
    void prepareConnection() {
        enderecoService = ServiceFactory.createEnderecoService();
        endereco1 = new Endereco("Rua Jasmim", 18, "Jardim das Flores", "Araguaína", "TO");
        endereco2 = new Endereco("Rua Tulipa", 22, "Jardim das Flores", "Araguaína", "TO");

        enderecoService.insertEndereco(endereco1);
        enderecoService.insertEndereco(endereco2);
    }

    @Test
    void getEnderecos() {
        assertEquals(endereco1.getId(), enderecoService.selectEnderecoByID(endereco1.getId()).getId());
        assertEquals(endereco2.getId(), enderecoService.selectEnderecoByID(endereco2.getId()).getId());
    }

    @AfterAll
    static void closeConnection() {
        Conexao.closeConnection();
    }


}
