package br.com.projeto.personalfocus.historico.servico;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;
import br.com.projeto.personalfocus.historico.dao.HistoricoDao;
import br.com.projeto.personalfocus.historico.dto.HistoricoCalendarioDto;
import br.com.projeto.personalfocus.usuario.dao.UsuarioDao;
import br.com.projeto.personalfocus.usuario.dto.DadoUsuarioDto;

public class HistoricoServicoTest {

  @Mock
  private HistoricoDao historicoDao;

  @Mock
  private UsuarioDao usuarioDao;

  @InjectMocks
  private HistoricoServico historicoServico;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void deveFinalizarTreinoComSucesso() {
    FinalizarTreinoCmd cmd = new FinalizarTreinoCmd();
    cmd.setIdAluno(1L);
    cmd.setIdTreino(1L);

    when(usuarioDao.getUsuarioPorId(1L)).thenReturn(mock(DadoUsuarioDto.class));

    String msg = historicoServico.finalizarTreino(cmd);

    assertEquals(msg, "Treino finalizado com sucesso!");
    verify(historicoDao).registrarFinalizacao(cmd);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Aluno não encontrado.")
  public void deveFalharAoFinalizarSeAlunoNaoExiste() {
    FinalizarTreinoCmd cmd = new FinalizarTreinoCmd();
    cmd.setIdAluno(1L);
    cmd.setIdTreino(1L);

    when(usuarioDao.getUsuarioPorId(1L)).thenReturn(null);

    historicoServico.finalizarTreino(cmd);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Não foi possível finalizar. Verifique se o ID do Treino é válido.")
  public void deveTratarErroDeChaveEstrangeiraAoFinalizar() {
    FinalizarTreinoCmd cmd = new FinalizarTreinoCmd();
    cmd.setIdAluno(1L);
    cmd.setIdTreino(99L);

    when(usuarioDao.getUsuarioPorId(1L)).thenReturn(mock(DadoUsuarioDto.class));
    doThrow(new DataIntegrityViolationException("Erro FK")).when(historicoDao).registrarFinalizacao(cmd);

    historicoServico.finalizarTreino(cmd);
  }

  @Test
  public void deveObterCalendarioAluno() {
    long idAluno = 1L;
    when(usuarioDao.getUsuarioPorId(idAluno)).thenReturn(mock(DadoUsuarioDto.class));
    when(historicoDao.buscarHistoricoPorAluno(idAluno))
        .thenReturn(Collections.singletonList(new HistoricoCalendarioDto(LocalDate.now(), "Treino A")));

    List<HistoricoCalendarioDto> lista = historicoServico.obterCalendarioAluno(idAluno);

    assertNotNull(lista);
    assertEquals(lista.size(), 1);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Aluno não encontrado.")
  public void deveFalharObterCalendarioSeAlunoNaoExiste() {
    when(usuarioDao.getUsuarioPorId(1L)).thenReturn(null);
    historicoServico.obterCalendarioAluno(1L);
  }
}