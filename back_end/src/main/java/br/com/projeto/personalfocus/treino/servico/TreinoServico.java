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
import br.com.projeto.personalfocus.usuario.dao.UsuarioDao;

/**
 * Serviço responsável pelas regras de negócio relacionadas aos treinos e exercícios.
 * Gerencia operações como cadastro de treinos (com exercícios em lote), listagem, atualização e exclusão.
 *
 * @author teteu
 */
@Service
public class TreinoServico {

  private static final String LABEL_ID_TREINO = "ID do Treino";
  private static final String LABEL_ID_ALUNO = "ID do Aluno";
  private static final String LABEL_ID_EXERCICIO = "ID do Exercício";

  @Autowired
  private TreinoDao treinoDao;

  @Autowired
  private UsuarioDao usuarioDao;

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
    validarCmdCadastrar(cmd);
    validarExistenciaAluno(cmd.getIdAluno());

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
    validarId(idAluno, LABEL_ID_ALUNO);
    validarExistenciaAluno(idAluno);
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
    validarId(idTreino, LABEL_ID_TREINO);
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
    validarCmdAtualizarTreino(cmd);

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
    validarCmdAtualizarExercicio(cmd);

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
    validarId(idTreino, LABEL_ID_TREINO);
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
    validarId(idExercicio, LABEL_ID_EXERCICIO);
    treinoDao.excluirExercicio(idExercicio);
    return "Exercício removido com sucesso.";
  }

  private static void validarCmdCadastrar(CadastrarTreinoCmd cmd) {
    Assert.notNull(cmd, "Os dados do treino não podem ser nulos.");
    Assert.notNull(cmd.getIdAluno(), "O ID do aluno é obrigatório.");
    Assert.notNull(cmd.getDiaSemana(), "O dia da semana é obrigatório.");
    Assert.hasText(cmd.getNomeTreino(), "O nome do treino é obrigatório.");
    Assert.notEmpty(cmd.getListaExercicios(), "A lista de exercícios não pode estar vazia.");

    if (cmd.getListaExercicios().size() > 10) {
      throw new IllegalArgumentException("O treino não pode ter mais de 10 exercícios.");
    }
  }

  private static void validarCmdAtualizarTreino(AtualizarTreinoCmd cmd) {
    Assert.notNull(cmd, "Dados do treino não podem ser nulos.");
    Assert.notNull(cmd.getIdTreino(), "ID do Treino é obrigatório.");
    Assert.hasText(cmd.getNomeTreino(), "Nome do treino é obrigatório.");
    validarId(cmd.getIdTreino(), LABEL_ID_TREINO);
  }

  private static void validarCmdAtualizarExercicio(AtualizarExercicioCmd cmd) {
    Assert.notNull(cmd, "Dados do exercício não podem ser nulos.");
    Assert.notNull(cmd.getIdExercicio(), "ID do Exercício é obrigatório.");
    Assert.hasText(cmd.getNomeExercicio(), "Nome do exercício é obrigatório.");
    Assert.hasText(cmd.getRepeticoes(), "Repetições são obrigatórias.");
    validarId(cmd.getIdExercicio(), LABEL_ID_EXERCICIO);
  }

  private static void validarId(long id, String nomeCampo) {
    if (id <= 0) {
      throw new IllegalArgumentException(nomeCampo + " deve ser maior que zero.");
    }
  }

  private void validarExistenciaAluno(long idAluno) {
    if (usuarioDao.getUsuarioPorId(idAluno) == null) {
      throw new IllegalArgumentException("Aluno não encontrado com o ID: " + idAluno);
    }
  }
}