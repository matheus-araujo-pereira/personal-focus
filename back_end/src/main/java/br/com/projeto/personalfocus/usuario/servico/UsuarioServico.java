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

/**
 * Serviço responsável pelas regras de negócio relacionadas aos usuários do sistema.
 * Gerencia operações como cadastro, autenticação, listagem, atualização e exclusão de usuários.
 *
 * @author teteu
 */
@Service
public class UsuarioServico {

  @Autowired
  private UsuarioDao usuarioDao;

  /**
   * Realiza o cadastro de um novo usuário no sistema.
   * Verifica se o CPF informado já existe antes de prosseguir com o cadastro.
   *
   * @param cmd
   *        Objeto contendo os dados necessários para o cadastro do usuário.
   * @return Uma mensagem de sucesso contendo o ID do usuário gerado.
   * @throws IllegalArgumentException
   *         Caso o CPF informado já esteja cadastrado.
   */
  public String cadastrarUsuario(@Valid CadastrarUsuarioCmd cmd) {
    validarCmdCadastrar(cmd);
    validarCpfJaCadastrado(cmd.getCpf());
    long id = usuarioDao.cadastrarUsuario(cmd);
    return "Usuário cadastrado com sucesso. ID: " + id;
  }

  /**
   * Realiza a autenticação de um usuário no sistema.
   * Valida as credenciais (CPF e senha) fornecidas.
   *
   * @param cmd
   *        Objeto contendo as credenciais de login.
   * @return Um objeto DTO contendo as informações básicas do usuário logado.
   * @throws IllegalArgumentException
   *         Caso as credenciais sejam inválidas.
   */
  public UsuarioLogadoDto login(@Valid LoginCmd cmd) {
    validarCmdLogin(cmd);
    UsuarioLogadoDto usuario = usuarioDao.autenticar(cmd);
    Assert.notNull(usuario, "CPF ou Senha inválidos.");
    return usuario;
  }

  /**
   * Lista todos os usuários com perfil de ALUNO cadastrados no sistema.
   *
   * @return Uma lista de objetos DTO contendo os dados dos alunos.
   */
  public List<DadoUsuarioDto> listarAlunos() {
    return usuarioDao.listarAlunos();
  }

  /**
   * Atualiza os dados cadastrais de um usuário existente.
   * Permite a alteração de nome, data de nascimento e opcionalmente a senha.
   *
   * @param cmd
   *        Objeto contendo os dados atualizados do usuário.
   * @return Uma mensagem de confirmação da atualização.
   */
  public String atualizarUsuario(@Valid AtualizarUsuarioCmd cmd) {
    validarCmdAtualizar(cmd);
    validarExistenciaUsuario(cmd.getIdUsuario());
    usuarioDao.atualizarUsuario(cmd);
    return "Dados do usuário atualizados com sucesso.";
  }

  /**
   * Remove um usuário do sistema com base no seu identificador.
   * A exclusão também remove todos os dados vinculados ao usuário devido à integridade referencial.
   *
   * @param idUsuario
   *        O identificador único do usuário a ser excluído.
   * @return Uma mensagem de confirmação da exclusão.
   */
  public String excluirUsuario(long idUsuario) {
    validarId(idUsuario);
    validarExistenciaUsuario(idUsuario);
    usuarioDao.excluirUsuario(idUsuario);
    return "Usuário e todos os seus dados vinculados foram removidos.";
  }

  /**
   * Obtém o perfil completo de um usuário específico.
   *
   * @param idUsuario
   *        O identificador único do usuário.
   * @return Um objeto DTO contendo os detalhes do perfil do usuário.
   * @throws IllegalArgumentException
   *         Caso o usuário não seja encontrado.
   */
  public DadoUsuarioDto obterPerfil(long idUsuario) {
    validarId(idUsuario);
    DadoUsuarioDto usuario = usuarioDao.getUsuarioPorId(idUsuario);
    validarUsuario(usuario);
    return usuario;
  }

  private void validarCpfJaCadastrado(String cpf) {
    if (usuarioDao.getUsuarioPorCpf(cpf) != null) {
      throw new IllegalArgumentException("CPF já cadastrado no sistema.");
    }
  }

  private static void validarId(long id) {
    if (id <= 0) {
      throw new IllegalArgumentException("ID de usuário inválido.");
    }
  }

  private static void validarUsuario(DadoUsuarioDto usuario) {
    if (usuario == null) {
      throw new IllegalArgumentException("Usuário não encontrado.");
    }
  }

  private void validarExistenciaUsuario(long idUsuario) {
    if (usuarioDao.getUsuarioPorId(idUsuario) == null) {
      throw new IllegalArgumentException("Operação não realizada: Usuário não encontrado.");
    }
  }

  private static void validarCmdCadastrar(CadastrarUsuarioCmd cmd) {
    Assert.notNull(cmd, "Dados de cadastro não podem ser nulos.");
    Assert.hasText(cmd.getCpf(), "O CPF é obrigatório.");
    Assert.hasText(cmd.getNome(), "O nome é obrigatório.");
    Assert.hasText(cmd.getSenha(), "A senha é obrigatória.");
    Assert.notNull(cmd.getDataNascimento(), "A data de nascimento é obrigatória.");
    Assert.notNull(cmd.getPerfil(), "O perfil é obrigatório.");
  }

  private static void validarCmdLogin(LoginCmd cmd) {
    Assert.notNull(cmd, "Dados de login não podem ser nulos.");
    Assert.hasText(cmd.getCpf(), "O CPF é obrigatório para login.");
    Assert.hasText(cmd.getSenha(), "A senha é obrigatória para login.");
  }

  private static void validarCmdAtualizar(AtualizarUsuarioCmd cmd) {
    Assert.notNull(cmd, "Dados de atualização não podem ser nulos.");
    validarId(cmd.getIdUsuario());
    Assert.hasText(cmd.getNome(), "O nome é obrigatório.");
    Assert.notNull(cmd.getDataNascimento(), "A data de nascimento é obrigatória.");
  }
}