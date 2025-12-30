package br.com.projeto.personalfocus.treino.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.personalfocus.compartilhado.util.componente.dao.IDaoUtilComponente;
import br.com.projeto.personalfocus.treino.comando.AtualizarExercicioCmd;
import br.com.projeto.personalfocus.treino.comando.AtualizarTreinoCmd;
import br.com.projeto.personalfocus.treino.comando.CadastrarExercicioCmd;
import br.com.projeto.personalfocus.treino.comando.CadastrarTreinoCmd;
import br.com.projeto.personalfocus.treino.dao.criadordeclaracao.CadastrarTreinoCriadorDeclaracao;
import br.com.projeto.personalfocus.treino.dto.DadoExercicioDto;
import br.com.projeto.personalfocus.treino.dto.DadoTreinoDto;

/**
 * Componente de acesso a dados (DAO) para as entidades Treino e Exercício.
 * Responsável por executar as operações de banco de dados definidas no arquivo de propriedades.
 *
 * @author teteu
 */
@Repository
@PropertySource("classpath:br/com/projeto/personalfocus/treino/dao/TreinoDao.properties")
public class TreinoDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private IDaoUtilComponente daoUtilComponente;

  @Value("${insert.treinoDao.cadastrarTreino}")
  private String sqlCadastrarTreino;

  @Value("${insert.treinoDao.cadastrarExercicio}")
  private String sqlCadastrarExercicio;

  @Value("${select.treinoDao.listarPorAluno}")
  private String sqlListarPorAluno;

  @Value("${select.treinoDao.listarExercicios}")
  private String sqlListarExercicios;

  @Value("${update.treinoDao.atualizarTreino}")
  private String sqlAtualizarTreino;

  @Value("${update.treinoDao.atualizarExercicio}")
  private String sqlAtualizarExercicio;

  @Value("${delete.treinoDao.excluirTreino}")
  private String sqlExcluirTreino;

  @Value("${delete.treinoDao.excluirExercicio}")
  private String sqlExcluirExercicio;

  /**
   * Insere um novo treino no banco de dados e retorna o ID gerado.
   *
   * @param cmd
   *        Objeto contendo os dados do treino.
   * @return O ID do treino inserido.
   */
  @Transactional
  public long cadastrarTreino(CadastrarTreinoCmd cmd) {
    return daoUtilComponente.insertRecuperandoId(jdbcTemplate,
        new CadastrarTreinoCriadorDeclaracao(sqlCadastrarTreino, cmd));
  }

  /**
   * Insere uma lista de exercícios vinculados a um treino em lote (não utiliza batch do JDBC, mas itera sobre a lista).
   *
   * @param idTreino
   *        O ID do treino ao qual os exercícios pertencem.
   * @param exercicios
   *        Lista de comandos de cadastro de exercício.
   */
  @Transactional
  public void cadastrarExerciciosBatch(long idTreino, List<CadastrarExercicioCmd> exercicios) {
    for (CadastrarExercicioCmd ex : exercicios) {
      jdbcTemplate.update(sqlCadastrarExercicio, ex.getNomeExercicio(), ex.getRepeticoes(), ex.getCargaKg(), idTreino);
    }
  }

  /**
   * Recupera a lista de treinos de um aluno.
   *
   * @param idAluno
   *        O ID do aluno.
   * @return Lista de DTOs com informações resumidas dos treinos.
   */
  @Transactional(readOnly = true)
  public List<DadoTreinoDto> listarTreinosPorAluno(long idAluno) {
    return jdbcTemplate.query(sqlListarPorAluno, new Object[] { idAluno },
        (rs, rowNum) -> new DadoTreinoDto(rs.getLong("id_treino"), rs.getString("nome_treino"),
            rs.getString("dia_semana"), rs.getString("descricao"), rs.getInt("qtd_exercicios")));
  }

  /**
   * Recupera a lista de exercícios de um treino.
   *
   * @param idTreino
   *        O ID do treino.
   * @return Lista de DTOs com detalhes dos exercícios.
   */
  @Transactional(readOnly = true)
  public List<DadoExercicioDto> listarExerciciosPorTreino(long idTreino) {
    return jdbcTemplate.query(sqlListarExercicios, new Object[] { idTreino },
        (rs, rowNum) -> new DadoExercicioDto(rs.getLong("id_exercicio"), rs.getString("nome_exercicio"),
            rs.getString("repeticoes"), rs.getBigDecimal("carga_kg")));
  }

  /**
   * Atualiza os dados de um treino no banco de dados.
   *
   * @param cmd
   *        Objeto contendo os dados atualizados do treino.
   */
  @Transactional
  public void atualizarTreino(AtualizarTreinoCmd cmd) {
    jdbcTemplate.update(sqlAtualizarTreino, cmd.getNomeTreino(), cmd.getDescricao(), cmd.getIdTreino());
  }

  /**
   * Atualiza os dados de um exercício no banco de dados.
   *
   * @param cmd
   *        Objeto contendo os dados atualizados do exercício.
   */
  @Transactional
  public void atualizarExercicio(AtualizarExercicioCmd cmd) {
    jdbcTemplate.update(sqlAtualizarExercicio, cmd.getNomeExercicio(), cmd.getRepeticoes(), cmd.getCargaKg(),
        cmd.getIdExercicio());
  }

  /**
   * Remove um treino do banco de dados.
   *
   * @param idTreino
   *        O ID do treino a ser removido.
   */
  @Transactional
  public void excluirTreino(long idTreino) {
    jdbcTemplate.update(sqlExcluirTreino, idTreino);
  }

  /**
   * Remove um exercício do banco de dados.
   *
   * @param idExercicio
   *        O ID do exercício a ser removido.
   */
  @Transactional
  public void excluirExercicio(long idExercicio) {
    jdbcTemplate.update(sqlExcluirExercicio, idExercicio);
  }
}