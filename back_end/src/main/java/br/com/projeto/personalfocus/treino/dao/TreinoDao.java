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

  @Transactional
  public long cadastrarTreino(CadastrarTreinoCmd cmd) {
    return daoUtilComponente.insertRecuperandoId(jdbcTemplate,
        new CadastrarTreinoCriadorDeclaracao(sqlCadastrarTreino, cmd));
  }

  @Transactional
  public void cadastrarExerciciosBatch(long idTreino, List<CadastrarExercicioCmd> exercicios) {
    for (CadastrarExercicioCmd ex : exercicios) {
      jdbcTemplate.update(sqlCadastrarExercicio, ex.getNomeExercicio(), ex.getRepeticoes(), ex.getCargaKg(), idTreino);
    }
  }

  @Transactional(readOnly = true)
  public List<DadoTreinoDto> listarTreinosPorAluno(long idAluno) {
    return jdbcTemplate.query(sqlListarPorAluno, new Object[] { idAluno },
        (rs, rowNum) -> new DadoTreinoDto(rs.getLong("id_treino"), rs.getString("nome_treino"),
            rs.getString("dia_semana"), rs.getString("descricao"), rs.getInt("qtd_exercicios")));
  }

  @Transactional(readOnly = true)
  public List<DadoExercicioDto> listarExerciciosPorTreino(long idTreino) {
    return jdbcTemplate.query(sqlListarExercicios, new Object[] { idTreino },
        (rs, rowNum) -> new DadoExercicioDto(rs.getLong("id_exercicio"), rs.getString("nome_exercicio"),
            rs.getString("repeticoes"), rs.getBigDecimal("carga_kg")));
  }

  @Transactional
  public void atualizarTreino(AtualizarTreinoCmd cmd) {
    jdbcTemplate.update(sqlAtualizarTreino, cmd.getNomeTreino(), cmd.getDescricao(), cmd.getIdTreino());
  }

  @Transactional
  public void atualizarExercicio(AtualizarExercicioCmd cmd) {
    jdbcTemplate.update(sqlAtualizarExercicio, cmd.getNomeExercicio(), cmd.getRepeticoes(), cmd.getCargaKg(),
        cmd.getIdExercicio());
  }

  @Transactional
  public void excluirTreino(long idTreino) {
    jdbcTemplate.update(sqlExcluirTreino, idTreino);
  }

  @Transactional
  public void excluirExercicio(long idExercicio) {
    jdbcTemplate.update(sqlExcluirExercicio, idExercicio);
  }
}