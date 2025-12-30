package br.com.projeto.personalfocus.historico.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.com.projeto.personalfocus.compartilhado.util.componente.dao.IDaoUtilComponente;
import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;
import br.com.projeto.personalfocus.historico.dto.HistoricoCalendarioDto;

public class HistoricoDaoTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @Mock
  private IDaoUtilComponente daoUtilComponente;

  @InjectMocks
  private HistoricoDao historicoDao;

  @Captor
  private ArgumentCaptor<RowMapper<HistoricoCalendarioDto>> captorCalendario;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(historicoDao, "sqlFinalizar", "INSERT_HISTORICO");
    ReflectionTestUtils.setField(historicoDao, "sqlBuscarPorAluno", "SELECT_HISTORICO");
  }

  @Test
  public void deveRegistrarFinalizacao() {
    FinalizarTreinoCmd cmd = new FinalizarTreinoCmd();
    when(daoUtilComponente.insertRecuperandoId(any(), any(PreparedStatementCreator.class))).thenReturn(1L);

    historicoDao.registrarFinalizacao(cmd);

    verify(daoUtilComponente).insertRecuperandoId(eq(jdbcTemplate), any(PreparedStatementCreator.class));
  }

  @Test
  public void deveBuscarHistoricoPorAluno() throws SQLException {
    historicoDao.buscarHistoricoPorAluno(1L);

    verify(jdbcTemplate).query(eq("SELECT_HISTORICO"), any(Object[].class), captorCalendario.capture());

    ResultSet rs = org.mockito.Mockito.mock(ResultSet.class);
    when(rs.getDate("data_finalizacao")).thenReturn(Date.valueOf(LocalDate.now()));
    when(rs.getString("nome_treino")).thenReturn("Treino A");

    HistoricoCalendarioDto dto = captorCalendario.getValue().mapRow(rs, 1);
    assertNotNull(dto);
  }
}