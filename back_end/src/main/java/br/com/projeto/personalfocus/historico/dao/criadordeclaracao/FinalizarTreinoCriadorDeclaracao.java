package br.com.projeto.personalfocus.historico.dao.criadordeclaracao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;
import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;

/**
 * Implementação de PreparedStatementCreator para a operação de registrar finalização de treino.
 * Responsável por configurar a declaração SQL de inserção na tabela tb_historico_treino.
 *
 * @author teteu
 */
public class FinalizarTreinoCriadorDeclaracao implements PreparedStatementCreator {
  private String sql;
  private FinalizarTreinoCmd cmd;

  /**
   * Construtor da classe.
   *
   * @param sql
   *        A string SQL de inserção.
   * @param cmd
   *        O comando contendo os dados da finalização do treino.
   */
  public FinalizarTreinoCriadorDeclaracao(String sql, FinalizarTreinoCmd cmd) {
    this.sql = sql;
    this.cmd = cmd;
  }

  /**
   * Cria um PreparedStatement configurado para a inserção do histórico.
   * Caso a data de finalização não seja informada no comando, utiliza a data atual do sistema.
   *
   * @param con
   *        A conexão com o banco de dados.
   * @return O PreparedStatement configurado.
   * @throws SQLException
   *         Se ocorrer um erro ao criar a declaração.
   */
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