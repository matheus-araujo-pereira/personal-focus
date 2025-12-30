package br.com.projeto.personalfocus.usuario.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.personalfocus.compartilhado.util.componente.dao.IDaoUtilComponente;
import br.com.projeto.personalfocus.usuario.comando.AtualizarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.CadastrarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.LoginCmd;
import br.com.projeto.personalfocus.usuario.dao.criadordeclaracao.CadastrarUsuarioCriadorDeclaracao;
import br.com.projeto.personalfocus.usuario.dto.DadoUsuarioDto;
import br.com.projeto.personalfocus.usuario.dto.UsuarioLogadoDto;

/**
 * Componente de acesso a dados (DAO) para a entidade Usuário.
 * Responsável por executar as operações de banco de dados definidas no arquivo de propriedades.
 *
 * @author teteu
 */
@Repository
@PropertySource("classpath:br/com/projeto/personalfocus/usuario/dao/UsuarioDao.properties")
public class UsuarioDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private IDaoUtilComponente daoUtilComponente;

  @Value("${insert.usuarioDao.cadastrarUsuario}")
  private String sqlCadastrar;

  @Value("${select.usuarioDao.login}")
  private String sqlLogin;

  @Value("${select.usuarioDao.listarAlunos}")
  private String sqlListarAlunos;

  @Value("${update.usuarioDao.atualizarUsuario}")
  private String sqlAtualizar;

  @Value("${update.usuarioDao.atualizarSenha}")
  private String sqlAtualizarSenha;

  @Value("${delete.usuarioDao.excluirUsuario}")
  private String sqlExcluir;

  @Value("${select.usuarioDao.getPorId}")
  private String sqlGetPorId;

  @Value("${select.usuarioDao.getPorCpf}")
  private String sqlGetPorCpf;

  /**
   * Insere um novo usuário no banco de dados e retorna o ID gerado.
   *
   * @param cmd
   *        Objeto contendo os dados do usuário a ser cadastrado.
   * @return O ID do usuário inserido.
   */
  @Transactional
  public long cadastrarUsuario(CadastrarUsuarioCmd cmd) {
    return daoUtilComponente.insertRecuperandoId(jdbcTemplate,
        new CadastrarUsuarioCriadorDeclaracao(sqlCadastrar, cmd));
  }

  /**
   * Busca um usuário no banco de dados com base no CPF e senha fornecidos.
   * Utilizado para autenticação.
   *
   * @param cmd
   *        Objeto contendo as credenciais de login.
   * @return Um DTO com os dados do usuário se encontrado, ou null caso contrário.
   */
  @Transactional(readOnly = true)
  public UsuarioLogadoDto autenticar(LoginCmd cmd) {
    try {
      return jdbcTemplate.queryForObject(sqlLogin, new Object[] { cmd.getCpf(), cmd.getSenha() },
          (rs, rowNum) -> new UsuarioLogadoDto(rs.getLong("id_usuario"), rs.getString("nome"), rs.getString("perfil")));
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  /**
   * Recupera a lista de todos os usuários com perfil de ALUNO.
   *
   * @return Uma lista de DTOs contendo as informações dos alunos.
   */
  @Transactional(readOnly = true)
  public List<DadoUsuarioDto> listarAlunos() {
    return jdbcTemplate.query(sqlListarAlunos,
        (rs, rowNum) -> new DadoUsuarioDto(rs.getLong("id_usuario"), rs.getString("cpf"), rs.getString("nome"),
            rs.getString("perfil"), rs.getDate("data_nascimento").toLocalDate()));
  }

  /**
   * Atualiza as informações básicas de um usuário no banco de dados.
   * Se uma nova senha for fornecida, ela também será atualizada.
   *
   * @param cmd
   *        Objeto contendo os dados atualizados.
   */
  @Transactional
  public void atualizarUsuario(AtualizarUsuarioCmd cmd) {
    jdbcTemplate.update(sqlAtualizar, cmd.getNome(), java.sql.Date.valueOf(cmd.getDataNascimento()),
        cmd.getIdUsuario());

    if (cmd.getNovaSenha() != null && !cmd.getNovaSenha().isEmpty()) {
      jdbcTemplate.update(sqlAtualizarSenha, cmd.getNovaSenha(), cmd.getIdUsuario());
    }
  }

  /**
   * Remove um usuário do banco de dados pelo seu ID.
   *
   * @param idUsuario
   *        O ID do usuário a ser removido.
   */
  @Transactional
  public void excluirUsuario(long idUsuario) {
    jdbcTemplate.update(sqlExcluir, idUsuario);
  }

  /**
   * Busca os detalhes de um usuário específico pelo seu ID.
   *
   * @param idUsuario
   *        O ID do usuário a ser buscado.
   * @return Um DTO com os dados do usuário, ou null se não encontrado.
   */
  @Transactional(readOnly = true)
  public DadoUsuarioDto getUsuarioPorId(long idUsuario) {
    try {
      return jdbcTemplate.queryForObject(sqlGetPorId, new Object[] { idUsuario },
          (rs, rowNum) -> new DadoUsuarioDto(rs.getLong("id_usuario"), rs.getString("cpf"), rs.getString("nome"),
              rs.getString("perfil"), rs.getDate("data_nascimento").toLocalDate()));
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  /**
   * Verifica a existência de um usuário pelo CPF e retorna seu ID.
   * Útil para validações de unicidade.
   *
   * @param cpf
   *        O CPF a ser verificado.
   * @return O ID do usuário se encontrado, ou null caso contrário.
   */
  public Long getUsuarioPorCpf(String cpf) {
    try {
      return jdbcTemplate.queryForObject(sqlGetPorCpf, new Object[] { cpf }, Long.class);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
}