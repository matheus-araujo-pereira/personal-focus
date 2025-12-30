package br.com.projeto.personalfocus.historico.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.personalfocus.compartilhado.util.componente.dao.IDaoUtilComponente;
import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;
import br.com.projeto.personalfocus.historico.dao.criadordeclaracao.FinalizarTreinoCriadorDeclaracao;
import br.com.projeto.personalfocus.historico.dto.HistoricoCalendarioDto;

/**
 * Componente de acesso a dados (DAO) para a entidade Histórico de Treino.
 * Executa operações de persistência e consulta relacionadas ao histórico de atividades dos alunos.
 *
 * @author teteu
 */
@Repository
@PropertySource("classpath:br/com/projeto/personalfocus/historico/dao/HistoricoDao.properties")
public class HistoricoDao {

  private static final String COLUNA_DATA_FINALIZACAO = "data_finalizacao";
  private static final String COLUNA_NOME_TREINO = "nome_treino";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private IDaoUtilComponente daoUtilComponente;

  @Value("${insert.historicoDao.finalizarTreino}")
  private String sqlFinalizar;

  @Value("${select.historicoDao.buscarPorAluno}")
  private String sqlBuscarPorAluno;

  /**
   * Insere um novo registro de treino finalizado na tabela de histórico.
   *
   * @param cmd
   *        Objeto contendo os dados do treino finalizado.
   * @return O ID do registro de histórico inserido.
   */
  @Transactional
  public long registrarFinalizacao(FinalizarTreinoCmd cmd) {
    return daoUtilComponente.insertRecuperandoId(jdbcTemplate, new FinalizarTreinoCriadorDeclaracao(sqlFinalizar, cmd));
  }

  /**
   * Busca todos os registros de histórico de um determinado aluno.
   * Realiza uma junção com a tabela de treinos para retornar também o nome do treino.
   *
   * @param idAluno
   *        O identificador do aluno.
   * @return Lista de DTOs contendo a data da finalização e o nome do treino.
   */
  @Transactional(readOnly = true)
  public List<HistoricoCalendarioDto> buscarHistoricoPorAluno(long idAluno) {
    return jdbcTemplate.query(sqlBuscarPorAluno, new Object[] { idAluno },
        (rs, rowNum) -> new HistoricoCalendarioDto(rs.getDate(COLUNA_DATA_FINALIZACAO).toLocalDate(),
            rs.getString(COLUNA_NOME_TREINO)));
  }
}