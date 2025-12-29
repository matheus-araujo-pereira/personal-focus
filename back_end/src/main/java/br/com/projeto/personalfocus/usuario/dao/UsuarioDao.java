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

  @Transactional
  public long cadastrarUsuario(CadastrarUsuarioCmd cmd) {
    return daoUtilComponente.insertRecuperandoId(jdbcTemplate,
        new CadastrarUsuarioCriadorDeclaracao(sqlCadastrar, cmd));
  }

  @Transactional(readOnly = true)
  public UsuarioLogadoDto autenticar(LoginCmd cmd) {
    try {
      return jdbcTemplate.queryForObject(sqlLogin, new Object[] { cmd.getCpf(), cmd.getSenha() },
          (rs, rowNum) -> new UsuarioLogadoDto(rs.getLong("id_usuario"), rs.getString("nome"), rs.getString("perfil")));
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Transactional(readOnly = true)
  public List<DadoUsuarioDto> listarAlunos() {
    return jdbcTemplate.query(sqlListarAlunos,
        (rs, rowNum) -> new DadoUsuarioDto(rs.getLong("id_usuario"), rs.getString("cpf"), rs.getString("nome"),
            rs.getString("perfil"), rs.getDate("data_nascimento").toLocalDate()));
  }

  @Transactional
  public void atualizarUsuario(AtualizarUsuarioCmd cmd) {
    jdbcTemplate.update(sqlAtualizar, cmd.getNome(), java.sql.Date.valueOf(cmd.getDataNascimento()),
        cmd.getIdUsuario());

    if (cmd.getNovaSenha() != null && !cmd.getNovaSenha().isEmpty()) {
      jdbcTemplate.update(sqlAtualizarSenha, cmd.getNovaSenha(), cmd.getIdUsuario());
    }
  }

  @Transactional
  public void excluirUsuario(long idUsuario) {
    jdbcTemplate.update(sqlExcluir, idUsuario);
  }

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

  public Long getUsuarioPorCpf(String cpf) {
    try {
      return jdbcTemplate.queryForObject(sqlGetPorCpf, new Object[] { cpf }, Long.class);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
}