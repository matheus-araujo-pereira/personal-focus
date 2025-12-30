package br.com.projeto.personalfocus.treino.dao.criadordeclaracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import br.com.projeto.personalfocus.treino.comando.CadastrarTreinoCmd;

/**
 * Implementação de PreparedStatementCreator para a operação de cadastro de treino.
 * Responsável por configurar a declaração SQL para inserção na tabela tb_treino.
 *
 * @author teteu
 */
public class CadastrarTreinoCriadorDeclaracao implements PreparedStatementCreator {
  private String sql;
  private CadastrarTreinoCmd cmd;

  /**
   * Construtor da classe.
   *
   * @param sql
   *        A string SQL de inserção.
   * @param cmd
   *        O comando contendo os dados do treino.
   */
  public CadastrarTreinoCriadorDeclaracao(String sql, CadastrarTreinoCmd cmd) {
    this.sql = sql;
    this.cmd = cmd;
  }

  /**
   * Cria um PreparedStatement configurado para a inserção de um treino.
   * Define que a coluna "id_treino" deve ser retornada após a inserção.
   *
   * @param con
   *        A conexão com o banco de dados.
   * @return O PreparedStatement configurado.
   * @throws SQLException
   *         Se ocorrer um erro ao criar a declaração.
   */
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