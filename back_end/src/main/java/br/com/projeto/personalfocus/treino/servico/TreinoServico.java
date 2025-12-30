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

/**
 * Serviço responsável pelas regras de negócio relacionadas aos treinos e exercícios.
 * Gerencia operações como cadastro de treinos (com exercícios em lote), listagem, atualização e exclusão.
 *
 * @author teteu
 */
@Service
public class TreinoServico {

  @Autowired
  private TreinoDao treinoDao;

  /**
   * Realiza o cadastro de um novo treino e seus respectivos exercícios.
   * A operação é transacional, garantindo que o treino e os exercícios sejam salvos atomicamente.
   *
   * @param cmd
   *        Objeto contendo os dados do treino e a lista de exercícios.
   * @return Uma mensagem de confirmação do cadastro.
   * @throws IllegalArgumentException
   *         Caso já exista um treino cadastrado para o dia da semana informado.
   */
  @Transactional
  public String cadastrarTreino(@Valid CadastrarTreinoCmd cmd) {
    try {
      long idTreino = treinoDao.cadastrarTreino(cmd);
      treinoDao.cadastrarExerciciosBatch(idTreino, cmd.getListaExercicios());
      return "Treino cadastrado com sucesso.";
    } catch (DataIntegrityViolationException e) {
      throw new IllegalArgumentException(
          "Este aluno já possui um treino cadastrado para " + cmd.getDiaSemana().getDescricao(), e);
    }
  }

  /**
   * Lista todos os treinos cadastrados para um determinado aluno.
   *
   * @param idAluno
   *        O identificador único do aluno.
   * @return Uma lista de objetos DTO contendo os dados básicos dos treinos.
   * @throws IllegalArgumentException
   *         Caso o ID do aluno seja inválido.
   */
  public List<DadoTreinoDto> listarTreinosDoAluno(long idAluno) {
    Assert.isTrue(idAluno > 0, "Aluno inválido.");
    return treinoDao.listarTreinosPorAluno(idAluno);
  }

  /**
   * Lista todos os exercícios vinculados a um treino específico.
   *
   * @param idTreino
   *        O identificador único do treino.
   * @return Uma lista de objetos DTO contendo os detalhes dos exercícios.
   */
  public List<DadoExercicioDto> listarExerciciosDoTreino(long idTreino) {
    return treinoDao.listarExerciciosPorTreino(idTreino);
  }

  /**
   * Atualiza as informações de um treino existente (nome e descrição).
   *
   * @param cmd
   *        Objeto contendo os dados atualizados do treino.
   * @return Uma mensagem de confirmação da atualização.
   */
  @Transactional
  public String atualizarTreino(@Valid AtualizarTreinoCmd cmd) {
    treinoDao.atualizarTreino(cmd);
    return "Treino atualizado com sucesso.";
  }

  /**
   * Atualiza as informações de um exercício existente (nome, repetições e carga).
   *
   * @param cmd
   *        Objeto contendo os dados atualizados do exercício.
   * @return Uma mensagem de confirmação da atualização.
   */
  @Transactional
  public String atualizarExercicio(@Valid AtualizarExercicioCmd cmd) {
    treinoDao.atualizarExercicio(cmd);
    return "Exercício atualizado com sucesso.";
  }

  /**
   * Remove um treino do sistema.
   * Devido à integridade referencial (cascade), os exercícios vinculados também serão removidos.
   *
   * @param idTreino
   *        O identificador único do treino a ser removido.
   * @return Uma mensagem de confirmação da exclusão.
   */
  public String excluirTreino(long idTreino) {
    treinoDao.excluirTreino(idTreino);
    return "Treino removido com sucesso.";
  }

  /**
   * Remove um exercício específico de um treino.
   *
   * @param idExercicio
   *        O identificador único do exercício a ser removido.
   * @return Uma mensagem de confirmação da exclusão.
   */
  public String excluirExercicio(long idExercicio) {
    treinoDao.excluirExercicio(idExercicio);
    return "Exercício removido com sucesso.";
  }
}