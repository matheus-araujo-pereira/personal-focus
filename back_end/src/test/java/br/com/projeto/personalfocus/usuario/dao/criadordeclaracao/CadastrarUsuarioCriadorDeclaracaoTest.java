package br.com.projeto.personalfocus.usuario.dao.criadordeclaracao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.testng.annotations.Test;

import br.com.projeto.personalfocus.usuario.comando.CadastrarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.enumerador.PerfilUsuarioEnumerador;

public class CadastrarUsuarioCriadorDeclaracaoTest {

  @Test
  public void deveCriarPreparedStatementCorretamente() throws SQLException {
    CadastrarUsuarioCmd cmd = new CadastrarUsuarioCmd("123", "senha", "Nome", LocalDate.of(2000, 1, 1),
        PerfilUsuarioEnumerador.ALUNO);
    String sql = "INSERT INTO...";
    Connection connection = mock(Connection.class);
    PreparedStatement ps = mock(PreparedStatement.class);
    when(connection.prepareStatement(anyString(), any(String[].class))).thenReturn(ps);
    CadastrarUsuarioCriadorDeclaracao criador = new CadastrarUsuarioCriadorDeclaracao(sql, cmd);
    PreparedStatement psRetornado = criador.createPreparedStatement(connection);
    assertNotNull(psRetornado);
    verify(connection).prepareStatement(eq(sql), any(String[].class));
    verify(ps).setString(1, "123");
    verify(ps).setString(2, "senha");
    verify(ps).setString(3, "Nome");
    verify(ps).setDate(eq(4), any(Date.class));
    verify(ps).setString(5, "ALUNO");
  }
}