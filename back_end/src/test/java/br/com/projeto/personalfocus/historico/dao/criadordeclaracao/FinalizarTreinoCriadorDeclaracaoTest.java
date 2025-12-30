package br.com.projeto.personalfocus.historico.dao.criadordeclaracao;

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

import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;

public class FinalizarTreinoCriadorDeclaracaoTest {

  @Test
  public void deveCriarPreparedStatementComDataFornecida() throws SQLException {
    FinalizarTreinoCmd cmd = new FinalizarTreinoCmd();
    cmd.setIdAluno(1L);
    cmd.setIdTreino(2L);
    LocalDate dataFixa = LocalDate.of(2023, 1, 1);
    cmd.setDataFinalizacao(dataFixa);

    Connection con = mock(Connection.class);
    PreparedStatement ps = mock(PreparedStatement.class);
    when(con.prepareStatement(anyString(), any(String[].class))).thenReturn(ps);

    FinalizarTreinoCriadorDeclaracao criador = new FinalizarTreinoCriadorDeclaracao("SQL", cmd);
    PreparedStatement resultado = criador.createPreparedStatement(con);

    assertNotNull(resultado);
    verify(ps).setDate(1, Date.valueOf(dataFixa));
    verify(ps).setLong(2, 1L);
    verify(ps).setLong(3, 2L);
  }

  @Test
  public void deveCriarPreparedStatementComDataAtualQuandoNula() throws SQLException {
    FinalizarTreinoCmd cmd = new FinalizarTreinoCmd();
    cmd.setIdAluno(1L);
    cmd.setIdTreino(2L);
    cmd.setDataFinalizacao(null);

    Connection con = mock(Connection.class);
    PreparedStatement ps = mock(PreparedStatement.class);
    when(con.prepareStatement(anyString(), any(String[].class))).thenReturn(ps);

    FinalizarTreinoCriadorDeclaracao criador = new FinalizarTreinoCriadorDeclaracao("SQL", cmd);
    PreparedStatement resultado = criador.createPreparedStatement(con);

    assertNotNull(resultado);
    verify(ps).setDate(eq(1), any(Date.class));
  }
}