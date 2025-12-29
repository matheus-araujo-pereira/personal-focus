package br.com.projeto.personalfocus.treino.dao.criadordeclaracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import br.com.projeto.personalfocus.treino.comando.CadastrarTreinoCmd;

public class CadastrarTreinoCriadorDeclaracao implements PreparedStatementCreator {
  private String sql;
  private CadastrarTreinoCmd cmd;

  public CadastrarTreinoCriadorDeclaracao(String sql, CadastrarTreinoCmd cmd) {
    this.sql = sql;
    this.cmd = cmd;
  }

  @Override
  public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
    PreparedStatement ps = con.prepareStatement(sql, new String[] { "id_treino" });
    ps.setString(1, cmd.getDiaSemana().getCodigo());
    ps.setString(2, cmd.getNomeTreino());
    ps.setString(3, cmd.getDescricao());
    ps.setLong(4, cmd.getIdAluno());
    return ps;
  }
}