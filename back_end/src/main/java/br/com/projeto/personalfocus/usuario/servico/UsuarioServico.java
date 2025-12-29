package br.com.projeto.personalfocus.usuario.servico;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.projeto.personalfocus.usuario.comando.AtualizarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.CadastrarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.LoginCmd;
import br.com.projeto.personalfocus.usuario.dao.UsuarioDao;
import br.com.projeto.personalfocus.usuario.dto.DadoUsuarioDto;
import br.com.projeto.personalfocus.usuario.dto.UsuarioLogadoDto;

@Service
public class UsuarioServico {

  @Autowired
  private UsuarioDao usuarioDao;

  public String cadastrarUsuario(@Valid CadastrarUsuarioCmd cmd) {
    if (cpfJaCadastrado(cmd.getCpf())) {
      throw new IllegalArgumentException("CPF já cadastrado no sistema.");
    }
    long id = usuarioDao.cadastrarUsuario(cmd);
    return "Usuário cadastrado com sucesso. ID: " + id;
  }

  public UsuarioLogadoDto login(@Valid LoginCmd cmd) {
    UsuarioLogadoDto usuario = usuarioDao.autenticar(cmd);
    Assert.notNull(usuario, "CPF ou Senha inválidos.");
    return usuario;
  }

  public List<DadoUsuarioDto> listarAlunos() {
    return usuarioDao.listarAlunos();
  }

  public String atualizarUsuario(@Valid AtualizarUsuarioCmd cmd) {
    usuarioDao.atualizarUsuario(cmd);
    return "Dados do usuário atualizados com sucesso.";
  }

  public String excluirUsuario(long idUsuario) {
    usuarioDao.excluirUsuario(idUsuario);
    return "Usuário e todos os seus dados vinculados foram removidos.";
  }

  public DadoUsuarioDto obterPerfil(long idUsuario) {
    DadoUsuarioDto usuario = usuarioDao.getUsuarioPorId(idUsuario);
    Assert.notNull(usuario, "Usuário não encontrado.");
    return usuario;
  }

  private boolean cpfJaCadastrado(String cpf) {
    return usuarioDao.getUsuarioPorCpf(cpf) != null;
  }
}