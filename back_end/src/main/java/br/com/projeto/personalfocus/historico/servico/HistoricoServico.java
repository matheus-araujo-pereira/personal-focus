package br.com.projeto.personalfocus.historico.servico;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.projeto.personalfocus.compartilhado.util.ValidadorUtil;
import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;
import br.com.projeto.personalfocus.historico.dao.HistoricoDao;
import br.com.projeto.personalfocus.historico.dto.HistoricoCalendarioDto;
import br.com.projeto.personalfocus.usuario.dao.UsuarioDao;

/**
 * Serviço responsável pelas regras de negócio relacionadas ao histórico de treinos.
 * Gerencia o registro de conclusão de treinos e a recuperação de dados para o calendário do aluno.
 *
 * @author teteu
 */
@Service
public class HistoricoServico {

  private static final String LABEL_ID_ALUNO = "ID do Aluno";
  private static final String LABEL_ID_TREINO = "ID do Treino";
  private static final String LABEL_DADOS_FINALIZACAO = "Dados da finalização";

  private static final String MSG_SUCESSO_FINALIZACAO = "Treino finalizado com sucesso!";

  private static final String MSG_ERRO_FINALIZACAO_FK = "Não foi possível finalizar. Verifique se o ID do Treino é válido.";
  private static final String MSG_ERRO_ALUNO_NAO_ENCONTRADO = "Aluno não encontrado.";

  @Autowired
  private HistoricoDao historicoDao;

  @Autowired
  private UsuarioDao usuarioDao;

  /**
   * Registra a finalização de um treino realizado pelo aluno.
   * Realiza validações de entrada e de existência do aluno antes de persistir.
   *
   * @param cmd
   *        Objeto de comando contendo os dados necessários para finalizar o treino.
   * @return Uma mensagem de confirmação do sucesso da operação.
   * @throws IllegalArgumentException
   *         Caso os dados sejam inválidos, o aluno não exista ou o treino informado não seja encontrado.
   */
  public String finalizarTreino(@Valid FinalizarTreinoCmd cmd) {
    validarCmdFinalizarTreino(cmd);
    validarExistenciaAluno(cmd.getIdAluno());
    return executarRegistroFinalizacao(cmd);
  }

  /**
   * Obtém o histórico completo de treinos finalizados por um aluno.
   * Utilizado para popular o calendário de atividades no perfil do aluno.
   *
   * @param idAluno
   *        O identificador único do aluno.
   * @return Uma lista de objetos DTO contendo datas e nomes dos treinos realizados.
   * @throws IllegalArgumentException
   *         Caso o ID do aluno seja inválido ou o aluno não exista.
   */
  public List<HistoricoCalendarioDto> obterCalendarioAluno(long idAluno) {
    ValidadorUtil.validarIdPositivo(idAluno, LABEL_ID_ALUNO);
    validarExistenciaAluno(idAluno);
    return historicoDao.buscarHistoricoPorAluno(idAluno);
  }

  /**
   * Encapsula a persistência da finalização do treino com tratamento de exceção.
   * Captura erros de integridade (Foreign Key) caso o treino não exista.
   *
   * @param cmd
   *        O comando validado.
   * @return A mensagem de sucesso.
   */
  private String executarRegistroFinalizacao(FinalizarTreinoCmd cmd) {
    try {
      historicoDao.registrarFinalizacao(cmd);
      return MSG_SUCESSO_FINALIZACAO;
    } catch (DataIntegrityViolationException e) {
      throw new IllegalArgumentException(MSG_ERRO_FINALIZACAO_FK, e);
    }
  }

  /**
   * Valida os campos obrigatórios do comando de finalização.
   *
   * @param cmd
   *        O comando a ser validado.
   */
  private static void validarCmdFinalizarTreino(FinalizarTreinoCmd cmd) {
    ValidadorUtil.validarObjetoNaoNulo(cmd, LABEL_DADOS_FINALIZACAO);
    ValidadorUtil.validarIdPositivo(cmd.getIdAluno(), LABEL_ID_ALUNO);
    ValidadorUtil.validarIdPositivo(cmd.getIdTreino(), LABEL_ID_TREINO);
  }

  /**
   * Verifica se o aluno existe na base de dados consultando o DAO de usuário.
   *
   * @param idAluno
   *        O ID do aluno.
   * @throws IllegalArgumentException
   *         Se o aluno não for encontrado.
   */
  private void validarExistenciaAluno(long idAluno) {
    if (usuarioDao.getUsuarioPorId(idAluno) == null) {
      throw new IllegalArgumentException(MSG_ERRO_ALUNO_NAO_ENCONTRADO);
    }
  }
}