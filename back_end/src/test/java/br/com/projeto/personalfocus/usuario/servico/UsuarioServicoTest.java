package br.com.projeto.personalfocus.usuario.servico;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.com.projeto.personalfocus.usuario.comando.AtualizarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.CadastrarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.LoginCmd;
import br.com.projeto.personalfocus.usuario.dao.UsuarioDao;
import br.com.projeto.personalfocus.usuario.dto.DadoUsuarioDto;
import br.com.projeto.personalfocus.usuario.dto.UsuarioLogadoDto;
import br.com.projeto.personalfocus.usuario.enumerador.PerfilUsuarioEnumerador;

/**
 * Testes unitários para o Serviço de Usuário.
 * Verifica a lógica de negócio, validações e interações com o DAO.
 *
 * @author teteu
 */
public class UsuarioServicoTest {

  @Mock
  private UsuarioDao usuarioDao;

  @InjectMocks
  private UsuarioServico usuarioServico;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void deveCadastrarUsuarioComSucesso() {
    CadastrarUsuarioCmd cmd = criarCmdCadastroValido();
    when(usuarioDao.getUsuarioPorCpf(cmd.getCpf())).thenReturn(null);
    when(usuarioDao.cadastrarUsuario(any(CadastrarUsuarioCmd.class))).thenReturn(1L);
    String mensagem = usuarioServico.cadastrarUsuario(cmd);
    assertEquals(mensagem, "Usuário cadastrado com sucesso.");
    verify(usuarioDao).cadastrarUsuario(cmd);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "CPF já cadastrado no sistema.")
  public void deveFalharCadastroQuandoCpfJaExiste() {
    CadastrarUsuarioCmd cmd = criarCmdCadastroValido();
    when(usuarioDao.getUsuarioPorCpf(cmd.getCpf())).thenReturn(10L);
    usuarioServico.cadastrarUsuario(cmd);
    verify(usuarioDao, never()).cadastrarUsuario(any());
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".* não pode ser nulo\\(a\\)\\.")
  public void deveFalharCadastroQuandoCmdInvalido() {
    usuarioServico.cadastrarUsuario(null);
  }

  @Test
  public void deveLogarComSucesso() {
    LoginCmd cmd = new LoginCmd();
    cmd.setCpf("12345678900");
    cmd.setSenha("senha123");
    UsuarioLogadoDto usuarioMock = new UsuarioLogadoDto(1L, "Nome Teste", "ALUNO");
    when(usuarioDao.autenticar(cmd)).thenReturn(usuarioMock);
    UsuarioLogadoDto resultado = usuarioServico.login(cmd);
    assertNotNull(resultado);
    assertEquals(resultado.getNome(), "Nome Teste");
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "CPF ou Senha inválidos.")
  public void deveFalharLoginComCredenciaisInvalidas() {
    LoginCmd cmd = new LoginCmd();
    cmd.setCpf("12345678900");
    cmd.setSenha("senhaErrada");
    when(usuarioDao.autenticar(cmd)).thenReturn(null);
    usuarioServico.login(cmd);
  }

  @Test
  public void deveListarAlunos() {
    when(usuarioDao.listarAlunos()).thenReturn(Collections.singletonList(criarDtoUsuario()));
    List<DadoUsuarioDto> lista = usuarioServico.listarAlunos();
    assertNotNull(lista);
    assertEquals(lista.size(), 1);
    verify(usuarioDao).listarAlunos();
  }

  @Test
  public void deveAtualizarUsuarioComSucesso() {
    AtualizarUsuarioCmd cmd = criarCmdAtualizacaoValido();
    when(usuarioDao.getUsuarioPorId(cmd.getIdUsuario())).thenReturn(criarDtoUsuario());
    String mensagem = usuarioServico.atualizarUsuario(cmd);
    assertEquals(mensagem, "Dados do usuário atualizados com sucesso.");
    verify(usuarioDao).atualizarUsuario(cmd);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Operação não realizada: Usuário não encontrado.")
  public void deveFalharAtualizacaoQuandoUsuarioNaoExiste() {
    AtualizarUsuarioCmd cmd = criarCmdAtualizacaoValido();
    when(usuarioDao.getUsuarioPorId(cmd.getIdUsuario())).thenReturn(null);
    usuarioServico.atualizarUsuario(cmd);
    verify(usuarioDao, never()).atualizarUsuario(any());
  }

  @Test
  public void deveExcluirUsuarioComSucesso() {
    long idUsuario = 1L;
    when(usuarioDao.getUsuarioPorId(idUsuario)).thenReturn(criarDtoUsuario());
    String mensagem = usuarioServico.excluirUsuario(idUsuario);
    assertEquals(mensagem, "Usuário e todos os seus dados vinculados foram removidos.");
    verify(usuarioDao).excluirUsuario(idUsuario);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Operação não realizada: Usuário não encontrado.")
  public void deveFalharExclusaoQuandoUsuarioNaoExiste() {
    long idUsuario = 99L;
    when(usuarioDao.getUsuarioPorId(idUsuario)).thenReturn(null);
    usuarioServico.excluirUsuario(idUsuario);
    verify(usuarioDao, never()).excluirUsuario(anyLong());
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".* inválido: deve ser maior que zero\\.")
  public void deveFalharExclusaoComIdInvalido() {
    usuarioServico.excluirUsuario(0L);
    verify(usuarioDao, never()).getUsuarioPorId(anyLong());
  }

  @Test
  public void deveObterPerfilComSucesso() {
    long idUsuario = 1L;
    DadoUsuarioDto dto = criarDtoUsuario();
    when(usuarioDao.getUsuarioPorId(idUsuario)).thenReturn(dto);
    DadoUsuarioDto resultado = usuarioServico.obterPerfil(idUsuario);
    assertNotNull(resultado);
    assertEquals(resultado.getNome(), dto.getNome());
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Usuário não encontrado.")
  public void deveFalharObterPerfilQuandoUsuarioNaoExiste() {
    long idUsuario = 1L;
    when(usuarioDao.getUsuarioPorId(idUsuario)).thenReturn(null);
    usuarioServico.obterPerfil(idUsuario);
  }

  private CadastrarUsuarioCmd criarCmdCadastroValido() {
    return new CadastrarUsuarioCmd("12345678900", "senha123", "Teste", LocalDate.of(1990, 1, 1),
        PerfilUsuarioEnumerador.ALUNO);
  }

  private AtualizarUsuarioCmd criarCmdAtualizacaoValido() {
    AtualizarUsuarioCmd cmd = new AtualizarUsuarioCmd();
    cmd.setIdUsuario(1L);
    cmd.setNome("Nome Atualizado");
    cmd.setDataNascimento(LocalDate.of(1990, 1, 1));
    return cmd;
  }

  private DadoUsuarioDto criarDtoUsuario() {
    return new DadoUsuarioDto(1L, "12345678900", "Teste", "ALUNO", LocalDate.of(1990, 1, 1));
  }
}