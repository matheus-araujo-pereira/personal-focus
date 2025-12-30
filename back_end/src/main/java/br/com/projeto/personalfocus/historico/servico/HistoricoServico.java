package br.com.projeto.personalfocus.historico.servico;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;
import br.com.projeto.personalfocus.historico.dao.HistoricoDao;
import br.com.projeto.personalfocus.historico.dto.HistoricoCalendarioDto;

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

  /**
   * Registra a finalização de um treino realizado pelo aluno.
   * Cria um novo registro no histórico vinculando o aluno, o treino e a data de execução.
   *
   * @param cmd
   *        Objeto de comando contendo os dados necessários para finalizar o treino.
   * @return Uma mensagem de confirmação com o ID do registro histórico gerado.
   */
  public String finalizarTreino(@Valid FinalizarTreinoCmd cmd) {
    long id = historicoDao.registrarFinalizacao(cmd);
    return "Treino finalizado com sucesso! Registro: " + id;
  }

  /**
   * Obtém o histórico completo de treinos finalizados por um aluno.
   * Utilizado para popular o calendário de atividades no perfil do aluno.
   *
   * @param idAluno
   *        O identificador único do aluno.
   * @return Uma lista de objetos DTO contendo datas e nomes dos treinos realizados.
   */
  public List<HistoricoCalendarioDto> obterCalendarioAluno(long idAluno) {
    return historicoDao.buscarHistoricoPorAluno(idAluno);
  }
}