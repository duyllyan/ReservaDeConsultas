import br.com.duyllyan.reservaconsultas.database.ClinicaDatabase;
import br.com.duyllyan.reservaconsultas.model.dao.IDao;
import br.com.duyllyan.reservaconsultas.model.dao.factory.DaoFactory;
import br.com.duyllyan.reservaconsultas.model.dao.impl.EnderecoDao;
import br.com.duyllyan.reservaconsultas.model.entities.Endereco;
import br.com.duyllyan.reservaconsultas.model.entities.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClinicaDatabaseTest {

    private Endereco endereco;
    private Paciente paciente;

    @BeforeEach
    void createDatabase() {
        ClinicaDatabase.getInstance();
    }
    IDao enderecoDao = DaoFactory.createEnderecoDao();
    IDao pacienteDao = DaoFactory.createPacienteDao();

    @Test
    void insert() {
        endereco = new Endereco("Rua 02", 25, "Bairro Sul", "Araguaína", "Tocantins");
        paciente = new Paciente("Duyllyan", "Almeida", "12345", new Date(), endereco);
        enderecoDao.insert(endereco);
        pacienteDao.insert(paciente);
        assertEquals(1, endereco.getId());
        assertEquals(1, paciente.getId());
    }

    @Test
    void update() {

        endereco = (Endereco) enderecoDao.selectByID(1);
        paciente = (Paciente) pacienteDao.selectByID(1);

        endereco.setRua("Rua Amarela");
        endereco.setNumero(15);
        paciente.setRg("14785");
        paciente.setEndereco(endereco);
        enderecoDao.update(endereco);
        pacienteDao.update(paciente);
        Paciente pacienteRecuperado = (Paciente) pacienteDao.selectByID(paciente.getId());
        assertEquals("Rua Amarela", endereco.getRua());
        assertEquals(15, endereco.getNumero());
        assertEquals("14785", paciente.getRg());
        assertEquals("14785", pacienteRecuperado.getRg());
    }


}
