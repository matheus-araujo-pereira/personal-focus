package br.com.projeto.personalfocus.treino.servico;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.projeto.personalfocus.treino.comando.AtualizarExercicioCmd;
import br.com.projeto.personalfocus.treino.comando.AtualizarTreinoCmd;
import br.com.projeto.personalfocus.treino.comando.CadastrarTreinoCmd;
import br.com.projeto.personalfocus.treino.dao.TreinoDao;
import br.com.projeto.personalfocus.treino.dto.DadoExercicioDto;
import br.com.projeto.personalfocus.treino.dto.DadoTreinoDto;

@Service
public class TreinoServico {

  @Autowired
  private TreinoDao treinoDao;

  @Transactional
  public String cadastrarTreino(@Valid CadastrarTreinoCmd cmd) {
    try {
      long idTreino = treinoDao.cadastrarTreino(cmd);
      treinoDao.cadastrarExerciciosBatch(idTreino, cmd.getListaExercicios());
      return "Treino cadastrado com sucesso.";
    } catch (DataIntegrityViolationException e) {
      throw new IllegalArgumentException(
          "Este aluno já possui um treino cadastrado para " + cmd.getDiaSemana().getDescricao());
    }
  }

  public List<DadoTreinoDto> listarTreinosDoAluno(long idAluno) {
    Assert.isTrue(idAluno > 0, "Aluno inválido.");
    return treinoDao.listarTreinosPorAluno(idAluno);
  }

  public List<DadoExercicioDto> listarExerciciosDoTreino(long idTreino) {
    return treinoDao.listarExerciciosPorTreino(idTreino);
  }

  @Transactional
  public String atualizarTreino(@Valid AtualizarTreinoCmd cmd) {
    treinoDao.atualizarTreino(cmd);
    return "Treino atualizado com sucesso.";
  }

  @Transactional
  public String atualizarExercicio(@Valid AtualizarExercicioCmd cmd) {
    treinoDao.atualizarExercicio(cmd);
    return "Exercício atualizado com sucesso.";
  }

  public String excluirTreino(long idTreino) {
    treinoDao.excluirTreino(idTreino);
    return "Treino removido com sucesso.";
  }

  public String excluirExercicio(long idExercicio) {
    treinoDao.excluirExercicio(idExercicio);
    return "Exercício removido com sucesso.";
  }
}