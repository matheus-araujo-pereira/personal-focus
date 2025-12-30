package br.com.projeto.personalfocus.usuario.dao.criadordeclaracao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import br.com.projeto.personalfocus.usuario.comando.CadastrarUsuarioCmd;

/**
 * Implementação de PreparedStatementCreator para a operação de cadastro de usuário.
 * Responsável por configurar a declaração SQL com os parâmetros fornecidos no comando.
 *
 * @author teteu
 */
public class CadastrarUsuarioCriadorDeclaracao implements PreparedStatementCreator {

  private static final String COLUNA_ID_USUARIO = "id_usuario";

  private String sql;
  private CadastrarUsuarioCmd cmd;

  /**
   * Construtor da classe.
   *
   * @param sql
   *        A string SQL de inserção.
   * @param cmd
   *        O comando contendo os dados do usuário.
   */
  public CadastrarUsuarioCriadorDeclaracao(String sql, CadastrarUsuarioCmd cmd) {
    this.sql = sql;
    this.cmd = cmd;
  }

  /**
   * Cria um PreparedStatement configurado para a inserção de um usuário.
   * Define que a coluna "id_usuario" deve ser retornada após a inserção.
   *
   * @param con
   *        A conexão com o banco de dados.
   * @return O PreparedStatement configurado.
   * @throws SQLException
   *         Se ocorrer um erro ao criar a declaração.
   */
  @Override
  public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
    PreparedStatement ps = con.prepareStatement(sql, new String[] { COLUNA_ID_USUARIO });
    ps.setString(1, cmd.getCpf());
    ps.setString(2, cmd.getSenha());
    ps.setString(3, cmd.getNome());
    ps.setDate(4, Date.valueOf(cmd.getDataNascimento()));
    ps.setString(5, cmd.getPerfil().getSigla());
    return ps;
  }
}