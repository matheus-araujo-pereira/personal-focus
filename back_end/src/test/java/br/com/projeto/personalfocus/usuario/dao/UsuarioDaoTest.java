package br.com.projeto.personalfocus.usuario.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
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
import br.com.projeto.personalfocus.usuario.comando.AtualizarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.CadastrarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.LoginCmd;
import br.com.projeto.personalfocus.usuario.dto.DadoUsuarioDto;
import br.com.projeto.personalfocus.usuario.dto.UsuarioLogadoDto;

public class UsuarioDaoTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @Mock
  private IDaoUtilComponente daoUtilComponente;

  @InjectMocks
  private UsuarioDao usuarioDao;

  @Captor
  private ArgumentCaptor<RowMapper<UsuarioLogadoDto>> captorUsuarioLogado;

  @Captor
  private ArgumentCaptor<RowMapper<DadoUsuarioDto>> captorDadoUsuario;

  @Captor
  private ArgumentCaptor<RowMapper<Long>> captorLong;

  private static final String SQL_ATUALIZAR_USER = "UPDATE_USER";
  private static final String SQL_ATUALIZAR_SENHA = "UPDATE_SENHA";
  private static final String SQL_EXCLUIR = "DELETE";

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(usuarioDao, "sqlCadastrar", "INSERT");
    ReflectionTestUtils.setField(usuarioDao, "sqlLogin", "SELECT_LOGIN");
    ReflectionTestUtils.setField(usuarioDao, "sqlListarAlunos", "SELECT_ALUNOS");
    ReflectionTestUtils.setField(usuarioDao, "sqlAtualizar", SQL_ATUALIZAR_USER);
    ReflectionTestUtils.setField(usuarioDao, "sqlAtualizarSenha", SQL_ATUALIZAR_SENHA);
    ReflectionTestUtils.setField(usuarioDao, "sqlExcluir", SQL_EXCLUIR);
    ReflectionTestUtils.setField(usuarioDao, "sqlGetPorId", "SELECT_BY_ID");
    ReflectionTestUtils.setField(usuarioDao, "sqlGetPorCpf", "SELECT_BY_CPF");
  }

  @Test
  public void deveCadastrarUsuario() {
    CadastrarUsuarioCmd cmd = mock(CadastrarUsuarioCmd.class);
    when(daoUtilComponente.insertRecuperandoId(any(), any(PreparedStatementCreator.class))).thenReturn(1L);

    long id = usuarioDao.cadastrarUsuario(cmd);
    assertEquals(id, 1L);
  }

  @Test
  public void deveAtualizarUsuarioComNovaSenha() {
    AtualizarUsuarioCmd cmd = new AtualizarUsuarioCmd();
    cmd.setIdUsuario(1L);
    cmd.setNome("Teste");
    cmd.setDataNascimento(LocalDate.now());
    cmd.setNovaSenha("123");

    usuarioDao.atualizarUsuario(cmd);

    verify(jdbcTemplate).update(eq(SQL_ATUALIZAR_USER), anyString(), any(Date.class), anyLong());
    verify(jdbcTemplate).update(eq(SQL_ATUALIZAR_SENHA), eq("123"), eq(1L));
  }

  @Test
  public void deveAtualizarUsuarioSemAlterarSenhaQuandoNula() {
    AtualizarUsuarioCmd cmd = new AtualizarUsuarioCmd();
    cmd.setIdUsuario(1L);
    cmd.setNome("Teste");
    cmd.setDataNascimento(LocalDate.now());
    cmd.setNovaSenha(null);

    usuarioDao.atualizarUsuario(cmd);

    verify(jdbcTemplate).update(eq(SQL_ATUALIZAR_USER), anyString(), any(Date.class), anyLong());
    verify(jdbcTemplate, never()).update(eq(SQL_ATUALIZAR_SENHA), anyString(), anyLong());
  }

  @Test
  public void deveAtualizarUsuarioSemAlterarSenhaQuandoVazia() {
    AtualizarUsuarioCmd cmd = new AtualizarUsuarioCmd();
    cmd.setIdUsuario(1L);
    cmd.setNome("Teste");
    cmd.setDataNascimento(LocalDate.now());
    cmd.setNovaSenha("");

    usuarioDao.atualizarUsuario(cmd);

    verify(jdbcTemplate).update(eq(SQL_ATUALIZAR_USER), anyString(), any(Date.class), anyLong());
    verify(jdbcTemplate, never()).update(eq(SQL_ATUALIZAR_SENHA), anyString(), anyLong());
  }

  @Test
  public void deveExcluirUsuario() {
    usuarioDao.excluirUsuario(1L);
    verify(jdbcTemplate).update(eq(SQL_EXCLUIR), eq(1L));
  }

  @Test
  public void deveMapearUsuarioLogadoCorretamente() throws SQLException {
    LoginCmd cmd = new LoginCmd();
    cmd.setCpf("123");
    cmd.setSenha("123");

    usuarioDao.autenticar(cmd);

    verify(jdbcTemplate).query(anyString(), any(Object[].class), captorUsuarioLogado.capture());

    ResultSet rs = mockResultSetUsuario();
    UsuarioLogadoDto dto = captorUsuarioLogado.getValue().mapRow(rs, 1);

    assertNotNull(dto);
    assertEquals(dto.getNome(), "Nome Teste");
  }

  @Test
  public void deveMapearListaAlunosCorretamente() throws SQLException {
    usuarioDao.listarAlunos();

    verify(jdbcTemplate).query(anyString(), captorDadoUsuario.capture());

    ResultSet rs = mockResultSetUsuarioCompleto();
    DadoUsuarioDto dto = captorDadoUsuario.getValue().mapRow(rs, 1);

    assertNotNull(dto);
    assertEquals(dto.getCpf(), "123");
  }

  @Test
  public void deveMapearGetPorIdCorretamente() throws SQLException {
    usuarioDao.getUsuarioPorId(1L);

    verify(jdbcTemplate).query(anyString(), any(Object[].class), captorDadoUsuario.capture());

    ResultSet rs = mockResultSetUsuarioCompleto();
    DadoUsuarioDto dto = captorDadoUsuario.getValue().mapRow(rs, 1);

    assertNotNull(dto);
  }

  @Test
  public void deveMapearGetPorCpfCorretamente() throws SQLException {
    usuarioDao.getUsuarioPorCpf("123");

    verify(jdbcTemplate).query(anyString(), any(Object[].class), captorLong.capture());

    ResultSet rs = mock(ResultSet.class);
    when(rs.getLong("id_usuario")).thenReturn(10L);

    Long id = captorLong.getValue().mapRow(rs, 1);
    assertEquals(id, Long.valueOf(10L));
  }

  private ResultSet mockResultSetUsuario() throws SQLException {
    ResultSet rs = mock(ResultSet.class);
    when(rs.getLong("id_usuario")).thenReturn(1L);
    when(rs.getString("nome")).thenReturn("Nome Teste");
    when(rs.getString("perfil")).thenReturn("ALUNO");
    return rs;
  }

  private ResultSet mockResultSetUsuarioCompleto() throws SQLException {
    ResultSet rs = mockResultSetUsuario();
    when(rs.getString("cpf")).thenReturn("123");
    when(rs.getDate("data_nascimento")).thenReturn(Date.valueOf(LocalDate.now()));
    return rs;
  }
}