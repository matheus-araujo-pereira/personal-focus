package br.com.projeto.personalfocus.historico.servico;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;
import br.com.projeto.personalfocus.historico.dao.HistoricoDao;
import br.com.projeto.personalfocus.historico.dto.HistoricoCalendarioDto;
import br.com.projeto.personalfocus.usuario.dao.UsuarioDao;
import br.com.projeto.personalfocus.usuario.dto.DadoUsuarioDto;

/**
 * Serviço responsável pelas regras de negócio relacionadas ao histórico de treinos.
 * Gerencia o registro de conclusão de treinos e a recuperação de dados para o calendário do aluno.
 *
 * @author teteu
 */
@Service
public class HistoricoServico {

  @Autowired
  private HistoricoDao historicoDao;

  @Autowired
  private UsuarioDao usuarioDao;

  /**
   * Registra a finalização de um treino realizado pelo aluno.
   * Cria um novo registro no histórico vinculando o aluno, o treino e a data de execução.
   *
   * @param cmd
   *        Objeto de comando contendo os dados necessários para finalizar o treino.
   * @return Uma mensagem de confirmação com o ID do registro histórico gerado.
   * @throws IllegalArgumentException
   *         Caso o aluno não exista, os IDs sejam inválidos ou o treino não seja encontrado.
   */
  public String finalizarTreino(@Valid FinalizarTreinoCmd cmd) {
    validarCmdFinalizarTreino(cmd);
    validarExistenciaAluno(cmd.getIdAluno());

    try {
      long id = historicoDao.registrarFinalizacao(cmd);
      return "Treino finalizado com sucesso! Registro: " + id;
    } catch (DataIntegrityViolationException e) {
      throw new IllegalArgumentException("Não foi possível finalizar. Verifique se o ID do Treino é válido.", e);
    }
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
    if (idAluno <= 0) {
      throw new IllegalArgumentException("O ID do aluno deve ser maior que zero.");
    }
    validarExistenciaAluno(idAluno);

    return historicoDao.buscarHistoricoPorAluno(idAluno);
  }

  /**
   * Valida manualmente os campos obrigatórios do comando de finalização.
   * Garante que as mensagens de erro sejam claras antes de acessar o banco.
   *
   * @param cmd
   *        O comando a ser validado.
   */
  private static void validarCmdFinalizarTreino(FinalizarTreinoCmd cmd) {
    Assert.notNull(cmd, "Os dados da finalização não podem ser nulos.");
    Assert.notNull(cmd.getIdAluno(), "ID do Aluno é obrigatório.");
    Assert.notNull(cmd.getIdTreino(), "ID do Treino é obrigatório.");

    if (cmd.getIdAluno() <= 0) {
      throw new IllegalArgumentException("ID do Aluno inválido.");
    }

    if (cmd.getIdTreino() <= 0) {
      throw new IllegalArgumentException("ID do Treino inválido.");
    }
  }

  /**
   * Verifica se o aluno existe na base de dados.
   *
   * @param idAluno
   *        O ID do aluno.
   * @throws IllegalArgumentException
   *         Se o aluno não for encontrado.
   */
  private void validarExistenciaAluno(long idAluno) {
    DadoUsuarioDto aluno = usuarioDao.getUsuarioPorId(idAluno);
    if (aluno == null) {
      throw new IllegalArgumentException("Aluno não encontrado com o ID: " + idAluno);
    }
  }
}