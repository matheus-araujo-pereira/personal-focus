package br.com.projeto.personalfocus.treino.servico;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.personalfocus.compartilhado.util.ValidadorUtil;
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
  private static final String LABEL_DADOS_TREINO = "Dados do treino";
  private static final String LABEL_DADOS_EXERCICIO = "Dados do exercício";
  private static final String LABEL_DIA_SEMANA = "Dia da semana";
  private static final String LABEL_NOME_TREINO = "Nome do treino";
  private static final String LABEL_NOME_EXERCICIO = "Nome do exercício";
  private static final String LABEL_REPETICOES = "Repetições";

  private static final String MSG_SUCESSO_CADASTRO = "Treino cadastrado com sucesso.";
  private static final String MSG_SUCESSO_ATUALIZACAO_TREINO = "Treino atualizado com sucesso.";
  private static final String MSG_SUCESSO_ATUALIZACAO_EXERCICIO = "Exercício atualizado com sucesso.";
  private static final String MSG_SUCESSO_EXCLUSAO_TREINO = "Treino removido com sucesso.";
  private static final String MSG_SUCESSO_EXCLUSAO_EXERCICIO = "Exercício removido com sucesso.";

  private static final String MSG_ERRO_ALUNO_NAO_ENCONTRADO = "Aluno não encontrado.";
  private static final String MSG_ERRO_LISTA_EXERCICIOS_VAZIA = "A lista de exercícios não pode estar vazia.";
  private static final String MSG_ERRO_LIMITE_EXERCICIOS = "O treino não pode ter mais de 10 exercícios.";

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
    return executarCadastroTreino(cmd);
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
    ValidadorUtil.validarIdPositivo(idAluno, LABEL_ID_ALUNO);
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
    ValidadorUtil.validarIdPositivo(idTreino, LABEL_ID_TREINO);
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
    return MSG_SUCESSO_ATUALIZACAO_TREINO;
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
    return MSG_SUCESSO_ATUALIZACAO_EXERCICIO;
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
    ValidadorUtil.validarIdPositivo(idTreino, LABEL_ID_TREINO);
    treinoDao.excluirTreino(idTreino);
    return MSG_SUCESSO_EXCLUSAO_TREINO;
  }

  /**
   * Remove um exercício específico de um treino.
   *
   * @param idExercicio
   *        O identificador único do exercício a ser removido.
   * @return Uma mensagem de confirmação da exclusão.
   */
  public String excluirExercicio(long idExercicio) {
    ValidadorUtil.validarIdPositivo(idExercicio, LABEL_ID_EXERCICIO);
    treinoDao.excluirExercicio(idExercicio);
    return MSG_SUCESSO_EXCLUSAO_EXERCICIO;
  }

  /**
   * Encapsula a lógica de persistência do cadastro com tratamento de exceção.
   *
   * @param cmd
   *        O comando já validado.
   * @return Mensagem de sucesso.
   */
  private String executarCadastroTreino(CadastrarTreinoCmd cmd) {
    try {
      long idTreino = treinoDao.cadastrarTreino(cmd);
      treinoDao.cadastrarExerciciosBatch(idTreino, cmd.getListaExercicios());
      return MSG_SUCESSO_CADASTRO;
    } catch (DataIntegrityViolationException e) {
      throw new IllegalArgumentException(
          "Este aluno já possui um treino cadastrado para " + cmd.getDiaSemana().getDescricao(), e);
    }
  }

  /**
   * Verifica se o aluno existe na base de dados.
   *
   * @param idAluno
   *        O ID do aluno a ser verificado.
   * @throws IllegalArgumentException
   *         Se o aluno não for encontrado.
   */
  private void validarExistenciaAluno(long idAluno) {
    if (usuarioDao.getUsuarioPorId(idAluno) == null) {
      throw new IllegalArgumentException(MSG_ERRO_ALUNO_NAO_ENCONTRADO);
    }
  }

  /**
   * Valida os campos do comando de cadastro de treino.
   * Método estático para atender regra S2325 do Sonar.
   *
   * @param cmd
   *        O comando a ser validado.
   */
  private static void validarCmdCadastrar(CadastrarTreinoCmd cmd) {
    ValidadorUtil.validarObjetoNaoNulo(cmd, LABEL_DADOS_TREINO);
    ValidadorUtil.validarIdPositivo(cmd.getIdAluno(), LABEL_ID_ALUNO);
    ValidadorUtil.validarObjetoNaoNulo(cmd.getDiaSemana(), LABEL_DIA_SEMANA);
    ValidadorUtil.validarTextoObrigatorio(cmd.getNomeTreino(), LABEL_NOME_TREINO);
    ValidadorUtil.validarListaNaoVazia(cmd.getListaExercicios(), MSG_ERRO_LISTA_EXERCICIOS_VAZIA);
    ValidadorUtil.validarTamanhoMaximoLista(cmd.getListaExercicios(), 10, MSG_ERRO_LIMITE_EXERCICIOS);
  }

  /**
   * Valida os campos do comando de atualização de treino.
   * Método estático para atender regra S2325 do Sonar.
   *
   * @param cmd
   *        O comando a ser validado.
   */
  private static void validarCmdAtualizarTreino(AtualizarTreinoCmd cmd) {
    ValidadorUtil.validarObjetoNaoNulo(cmd, LABEL_DADOS_TREINO);
    ValidadorUtil.validarIdPositivo(cmd.getIdTreino(), LABEL_ID_TREINO);
    ValidadorUtil.validarTextoObrigatorio(cmd.getNomeTreino(), LABEL_NOME_TREINO);
  }

  /**
   * Valida os campos do comando de atualização de exercício.
   * Método estático para atender regra S2325 do Sonar.
   *
   * @param cmd
   *        O comando a ser validado.
   */
  private static void validarCmdAtualizarExercicio(AtualizarExercicioCmd cmd) {
    ValidadorUtil.validarObjetoNaoNulo(cmd, LABEL_DADOS_EXERCICIO);
    ValidadorUtil.validarIdPositivo(cmd.getIdExercicio(), LABEL_ID_EXERCICIO);
    ValidadorUtil.validarTextoObrigatorio(cmd.getNomeExercicio(), LABEL_NOME_EXERCICIO);
    ValidadorUtil.validarTextoObrigatorio(cmd.getRepeticoes(), LABEL_REPETICOES);
  }
}