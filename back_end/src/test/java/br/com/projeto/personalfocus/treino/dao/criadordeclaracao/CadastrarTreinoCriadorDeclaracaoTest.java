package br.com.projeto.personalfocus.treino.dao.criadordeclaracao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.testng.annotations.Test;

import br.com.projeto.personalfocus.treino.comando.CadastrarTreinoCmd;
import br.com.projeto.personalfocus.treino.enumerador.DiaSemanaEnumerador;

public class CadastrarTreinoCriadorDeclaracaoTest {

  @Test
  public void deveCriarPreparedStatementCorretamente() throws SQLException {
    CadastrarTreinoCmd cmd = new CadastrarTreinoCmd();
    cmd.setDiaSemana(DiaSemanaEnumerador.SEGUNDA);
    cmd.setNomeTreino("Treino A");
    cmd.setDescricao("Descricao");
    cmd.setIdAluno(10L);
    String sql = "INSERT...";

    Connection con = mock(Connection.class);
    PreparedStatement ps = mock(PreparedStatement.class);
    when(con.prepareStatement(anyString(), any(String[].class))).thenReturn(ps);

    CadastrarTreinoCriadorDeclaracao criador = new CadastrarTreinoCriadorDeclaracao(sql, cmd);
    PreparedStatement resultado = criador.createPreparedStatement(con);

    assertNotNull(resultado);
    verify(ps).setString(1, "SEGUNDA");
    verify(ps).setString(2, "Treino A");
    verify(ps).setString(3, "Descricao");
    verify(ps).setLong(4, 10L);
  }
}