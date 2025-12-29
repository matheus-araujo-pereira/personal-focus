package br.com.projeto.personalfocus.historico.dao.criadordeclaracao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;
import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;

public class FinalizarTreinoCriadorDeclaracao implements PreparedStatementCreator {
  private String sql;
  private FinalizarTreinoCmd cmd;

  public FinalizarTreinoCriadorDeclaracao(String sql, FinalizarTreinoCmd cmd) {
    this.sql = sql;
    this.cmd = cmd;
  }

  @Override
  public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
    PreparedStatement ps = con.prepareStatement(sql, new String[] { "id_historico" });
    Date dataSql = (cmd.getDataFinalizacao() != null) ? Date.valueOf(cmd.getDataFinalizacao())
        : new Date(System.currentTimeMillis());

    ps.setDate(1, dataSql);
    ps.setLong(2, cmd.getIdAluno());
    ps.setLong(3, cmd.getIdTreino());
    return ps;
  }
}