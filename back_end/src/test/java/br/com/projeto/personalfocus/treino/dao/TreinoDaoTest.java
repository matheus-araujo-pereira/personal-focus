package br.com.projeto.personalfocus.treino.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

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
import br.com.projeto.personalfocus.treino.comando.AtualizarExercicioCmd;
import br.com.projeto.personalfocus.treino.comando.AtualizarTreinoCmd;
import br.com.projeto.personalfocus.treino.comando.CadastrarExercicioCmd;
import br.com.projeto.personalfocus.treino.comando.CadastrarTreinoCmd;
import br.com.projeto.personalfocus.treino.dto.DadoExercicioDto;
import br.com.projeto.personalfocus.treino.dto.DadoTreinoDto;
import br.com.projeto.personalfocus.treino.enumerador.DiaSemanaEnumerador;

public class TreinoDaoTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @Mock
  private IDaoUtilComponente daoUtilComponente;

  @InjectMocks
  private TreinoDao treinoDao;

  @Captor
  private ArgumentCaptor<RowMapper<DadoTreinoDto>> captorTreinoDto;

  @Captor
  private ArgumentCaptor<RowMapper<DadoExercicioDto>> captorExercicioDto;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(treinoDao, "sqlCadastrarTreino", "INSERT_TREINO");
    ReflectionTestUtils.setField(treinoDao, "sqlCadastrarExercicio", "INSERT_EXERCICIO");
    ReflectionTestUtils.setField(treinoDao, "sqlListarPorAluno", "SELECT_TREINOS");
    ReflectionTestUtils.setField(treinoDao, "sqlListarExercicios", "SELECT_EXERCICIOS");
    ReflectionTestUtils.setField(treinoDao, "sqlAtualizarTreino", "UPDATE_TREINO");
    ReflectionTestUtils.setField(treinoDao, "sqlAtualizarExercicio", "UPDATE_EXERCICIO");
    ReflectionTestUtils.setField(treinoDao, "sqlExcluirTreino", "DELETE_TREINO");
    ReflectionTestUtils.setField(treinoDao, "sqlExcluirExercicio", "DELETE_EXERCICIO");
  }

  @Test
  public void deveCadastrarTreino() {
    CadastrarTreinoCmd cmd = new CadastrarTreinoCmd();
    cmd.setDiaSemana(DiaSemanaEnumerador.SEGUNDA);
    when(daoUtilComponente.insertRecuperandoId(any(), any(PreparedStatementCreator.class))).thenReturn(1L);

    treinoDao.cadastrarTreino(cmd);
    verify(daoUtilComponente).insertRecuperandoId(eq(jdbcTemplate), any(PreparedStatementCreator.class));
  }

  @Test
  public void deveCadastrarExerciciosEmLote() {
    CadastrarExercicioCmd ex1 = new CadastrarExercicioCmd();
    ex1.setNomeExercicio("A");
    CadastrarExercicioCmd ex2 = new CadastrarExercicioCmd();
    ex2.setNomeExercicio("B");

    java.util.List<CadastrarExercicioCmd> lista = new java.util.ArrayList<>();
    lista.add(ex1);
    lista.add(ex2);

    treinoDao.cadastrarExerciciosBatch(1L, lista);

    verify(jdbcTemplate, times(2)).update(eq("INSERT_EXERCICIO"), anyString(), any(), any(), eq(1L));
  }

  @Test
  public void deveListarTreinosMapeandoCorretamente() throws SQLException {
    treinoDao.listarTreinosPorAluno(1L);

    verify(jdbcTemplate).query(eq("SELECT_TREINOS"), any(Object[].class), captorTreinoDto.capture());

    ResultSet rs = org.mockito.Mockito.mock(ResultSet.class);
    when(rs.getLong("id_treino")).thenReturn(1L);
    when(rs.getString("nome_treino")).thenReturn("Treino A");
    when(rs.getInt("qtd_exercicios")).thenReturn(5);

    DadoTreinoDto dto = captorTreinoDto.getValue().mapRow(rs, 1);
    assertNotNull(dto);
    assertNotNull(dto.getNomeTreino());
  }

  @Test
  public void deveListarExerciciosMapeandoCorretamente() throws SQLException {
    treinoDao.listarExerciciosPorTreino(1L);

    verify(jdbcTemplate).query(eq("SELECT_EXERCICIOS"), any(Object[].class), captorExercicioDto.capture());

    ResultSet rs = org.mockito.Mockito.mock(ResultSet.class);
    when(rs.getLong("id_exercicio")).thenReturn(1L);
    when(rs.getString("nome_exercicio")).thenReturn("Supino");
    when(rs.getBigDecimal("carga_kg")).thenReturn(BigDecimal.TEN);

    DadoExercicioDto dto = captorExercicioDto.getValue().mapRow(rs, 1);
    assertNotNull(dto);
    assertNotNull(dto.getNomeExercicio());
  }

  @Test
  public void deveAtualizarTreino() {
    AtualizarTreinoCmd cmd = new AtualizarTreinoCmd();
    cmd.setIdTreino(1L);
    cmd.setNomeTreino("Novo Nome");
    cmd.setDescricao("Desc");

    treinoDao.atualizarTreino(cmd);
    verify(jdbcTemplate).update(eq("UPDATE_TREINO"), anyString(), anyString(), eq(1L));
  }

  @Test
  public void deveAtualizarExercicio() {
    AtualizarExercicioCmd cmd = new AtualizarExercicioCmd();
    cmd.setIdExercicio(1L);
    cmd.setNomeExercicio("Novo Ex");
    cmd.setCargaKg(BigDecimal.ONE);

    treinoDao.atualizarExercicio(cmd);
    verify(jdbcTemplate).update(eq("UPDATE_EXERCICIO"), anyString(), any(), any(), eq(1L));
  }

  @Test
  public void deveExcluirTreino() {
    treinoDao.excluirTreino(1L);
    verify(jdbcTemplate).update(eq("DELETE_TREINO"), eq(1L));
  }

  @Test
  public void deveExcluirExercicio() {
    treinoDao.excluirExercicio(1L);
    verify(jdbcTemplate).update(eq("DELETE_EXERCICIO"), eq(1L));
  }
}