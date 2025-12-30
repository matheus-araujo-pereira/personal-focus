package br.com.projeto.personalfocus.treino.servico;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.com.projeto.personalfocus.treino.comando.AtualizarExercicioCmd;
import br.com.projeto.personalfocus.treino.comando.AtualizarTreinoCmd;
import br.com.projeto.personalfocus.treino.comando.CadastrarExercicioCmd;
import br.com.projeto.personalfocus.treino.comando.CadastrarTreinoCmd;
import br.com.projeto.personalfocus.treino.dao.TreinoDao;
import br.com.projeto.personalfocus.treino.dto.DadoExercicioDto;
import br.com.projeto.personalfocus.treino.dto.DadoTreinoDto;
import br.com.projeto.personalfocus.treino.enumerador.DiaSemanaEnumerador;
import br.com.projeto.personalfocus.usuario.dao.UsuarioDao;
import br.com.projeto.personalfocus.usuario.dto.DadoUsuarioDto;

public class TreinoServicoTest {

  @Mock
  private TreinoDao treinoDao;

  @Mock
  private UsuarioDao usuarioDao;

  @InjectMocks
  private TreinoServico treinoServico;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void deveCadastrarTreinoComSucesso() {
    CadastrarTreinoCmd cmd = criarCmdCadastroValido();

    when(usuarioDao.getUsuarioPorId(cmd.getIdAluno())).thenReturn(mock(DadoUsuarioDto.class));
    when(treinoDao.cadastrarTreino(any())).thenReturn(1L);

    String msg = treinoServico.cadastrarTreino(cmd);

    assertEquals(msg, "Treino cadastrado com sucesso.");
    verify(treinoDao).cadastrarTreino(cmd);
    verify(treinoDao).cadastrarExerciciosBatch(1L, cmd.getListaExercicios());
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Aluno não encontrado.")
  public void deveFalharCadastroSeAlunoNaoExiste() {
    CadastrarTreinoCmd cmd = criarCmdCadastroValido();
    when(usuarioDao.getUsuarioPorId(cmd.getIdAluno())).thenReturn(null);

    treinoServico.cadastrarTreino(cmd);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Este aluno já possui um treino cadastrado para .*")
  public void deveTratarErroDeChaveUnicaNoCadastro() {
    CadastrarTreinoCmd cmd = criarCmdCadastroValido();
    when(usuarioDao.getUsuarioPorId(cmd.getIdAluno())).thenReturn(mock(DadoUsuarioDto.class));

    when(treinoDao.cadastrarTreino(any())).thenThrow(new DataIntegrityViolationException("Erro SQL"));

    treinoServico.cadastrarTreino(cmd);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "O treino não pode ter mais de 10 exercícios.")
  public void deveFalharSeListaExerciciosExcedeLimite() {
    CadastrarTreinoCmd cmd = criarCmdCadastroValido();
    List<CadastrarExercicioCmd> listaGrande = new ArrayList<>();
    for (int i = 0; i < 11; i++)
      listaGrande.add(new CadastrarExercicioCmd());
    cmd.setListaExercicios(listaGrande);

    treinoServico.cadastrarTreino(cmd);
  }

  @Test
  public void deveListarTreinosDoAluno() {
    long idAluno = 1L;
    when(usuarioDao.getUsuarioPorId(idAluno)).thenReturn(mock(DadoUsuarioDto.class));
    when(treinoDao.listarTreinosPorAluno(idAluno)).thenReturn(Collections.singletonList(mock(DadoTreinoDto.class)));

    List<DadoTreinoDto> lista = treinoServico.listarTreinosDoAluno(idAluno);

    assertNotNull(lista);
    assertEquals(lista.size(), 1);
  }

  @Test
  public void deveListarExerciciosDoTreino() {
    long idTreino = 1L;
    when(treinoDao.listarExerciciosPorTreino(idTreino))
        .thenReturn(Collections.singletonList(mock(DadoExercicioDto.class)));

    List<DadoExercicioDto> lista = treinoServico.listarExerciciosDoTreino(idTreino);

    assertNotNull(lista);
    assertEquals(lista.size(), 1);
  }

  @Test
  public void deveAtualizarTreino() {
    AtualizarTreinoCmd cmd = new AtualizarTreinoCmd();
    cmd.setIdTreino(1L);
    cmd.setNomeTreino("Treino B");

    String msg = treinoServico.atualizarTreino(cmd);

    assertEquals(msg, "Treino atualizado com sucesso.");
    verify(treinoDao).atualizarTreino(cmd);
  }

  @Test
  public void deveAtualizarExercicio() {
    AtualizarExercicioCmd cmd = new AtualizarExercicioCmd();
    cmd.setIdExercicio(1L);
    cmd.setNomeExercicio("Supino");
    cmd.setRepeticoes("3x10");

    String msg = treinoServico.atualizarExercicio(cmd);

    assertEquals(msg, "Exercício atualizado com sucesso.");
    verify(treinoDao).atualizarExercicio(cmd);
  }

  @Test
  public void deveExcluirTreino() {
    treinoServico.excluirTreino(1L);
    verify(treinoDao).excluirTreino(1L);
  }

  @Test
  public void deveExcluirExercicio() {
    treinoServico.excluirExercicio(1L);
    verify(treinoDao).excluirExercicio(1L);
  }

  private CadastrarTreinoCmd criarCmdCadastroValido() {
    CadastrarTreinoCmd cmd = new CadastrarTreinoCmd();
    cmd.setIdAluno(1L);
    cmd.setDiaSemana(DiaSemanaEnumerador.SEGUNDA);
    cmd.setNomeTreino("Treino A");

    List<CadastrarExercicioCmd> exercicios = new ArrayList<>();
    CadastrarExercicioCmd ex = new CadastrarExercicioCmd();
    ex.setNomeExercicio("Supino");
    ex.setRepeticoes("3x10");
    ex.setCargaKg(BigDecimal.TEN);
    exercicios.add(ex);

    cmd.setListaExercicios(exercicios);
    return cmd;
  }
}