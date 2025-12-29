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

@Repository
@PropertySource("classpath:br/com/projeto/personalfocus/historico/dao/HistoricoDao.properties")
public class HistoricoDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private IDaoUtilComponente daoUtilComponente;

  @Value("${insert.historicoDao.finalizarTreino}")
  private String sqlFinalizar;
  
  @Value("${select.historicoDao.buscarPorAluno}")
  private String sqlBuscarPorAluno;

  @Transactional
  public long registrarFinalizacao(FinalizarTreinoCmd cmd) {
    return daoUtilComponente.insertRecuperandoId(jdbcTemplate, new FinalizarTreinoCriadorDeclaracao(sqlFinalizar, cmd));
  }

  @Transactional(readOnly = true)
  public List<HistoricoCalendarioDto> buscarHistoricoPorAluno(long idAluno) {
    return jdbcTemplate.query(sqlBuscarPorAluno, new Object[] { idAluno },
        (rs, rowNum) -> new HistoricoCalendarioDto(rs.getDate("data_finalizacao").toLocalDate(),
            rs.getString("nome_treino")));
  }
}