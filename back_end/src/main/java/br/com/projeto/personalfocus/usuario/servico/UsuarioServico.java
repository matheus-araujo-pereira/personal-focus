package br.com.projeto.personalfocus.usuario.servico;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.projeto.personalfocus.compartilhado.util.ValidadorUtil;
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

  private static final String LABEL_ID_USUARIO = "ID do Usuário";
  private static final String LABEL_DADOS_CADASTRO = "Dados de cadastro";
  private static final String LABEL_DADOS_LOGIN = "Dados de login";
  private static final String LABEL_DADOS_ATUALIZACAO = "Dados de atualização";
  private static final String LABEL_CPF = "CPF";
  private static final String LABEL_NOME = "Nome";
  private static final String LABEL_SENHA = "Senha";
  private static final String LABEL_DATA_NASCIMENTO = "Data de Nascimento";
  private static final String LABEL_PERFIL = "Perfil";

  private static final String MSG_SUCESSO_CADASTRO = "Usuário cadastrado com sucesso.";
  private static final String MSG_SUCESSO_ATUALIZACAO = "Dados do usuário atualizados com sucesso.";
  private static final String MSG_SUCESSO_EXCLUSAO = "Usuário e todos os seus dados vinculados foram removidos.";

  private static final String MSG_ERRO_CPF_JA_CADASTRADO = "CPF já cadastrado no sistema.";
  private static final String MSG_ERRO_CREDENCIAIS_INVALIDAS = "CPF ou Senha inválidos.";
  private static final String MSG_ERRO_USUARIO_NAO_ENCONTRADO = "Usuário não encontrado.";
  private static final String MSG_ERRO_OP_USUARIO_NAO_ENCONTRADO = "Operação não realizada: Usuário não encontrado.";

  @Autowired
  private UsuarioDao usuarioDao;

  /**
   * Realiza o cadastro de um novo usuário no sistema.
   * Verifica se o CPF informado já existe antes de prosseguir com o cadastro.
   *
   * @param cmd
   *        Objeto contendo os dados necessários para o cadastro do usuário.
   * @return Uma mensagem de sucesso.
   * @throws IllegalArgumentException
   *         Caso o CPF informado já esteja cadastrado.
   */
  public String cadastrarUsuario(@Valid CadastrarUsuarioCmd cmd) {
    validarCmdCadastrar(cmd);
    validarCpfJaCadastrado(cmd.getCpf());
    usuarioDao.cadastrarUsuario(cmd);
    return MSG_SUCESSO_CADASTRO;
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
    Assert.notNull(usuario, MSG_ERRO_CREDENCIAIS_INVALIDAS);
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
    return MSG_SUCESSO_ATUALIZACAO;
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
    ValidadorUtil.validarIdPositivo(idUsuario, LABEL_ID_USUARIO);
    validarExistenciaUsuario(idUsuario);
    usuarioDao.excluirUsuario(idUsuario);
    return MSG_SUCESSO_EXCLUSAO;
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
    ValidadorUtil.validarIdPositivo(idUsuario, LABEL_ID_USUARIO);
    DadoUsuarioDto usuario = usuarioDao.getUsuarioPorId(idUsuario);
    validarUsuarioEncontrado(usuario);
    return usuario;
  }

  /**
   * Verifica se o CPF já existe na base de dados.
   *
   * @param cpf
   *        O CPF a ser verificado.
   * @throws IllegalArgumentException
   *         Se o CPF já estiver cadastrado.
   */
  private void validarCpfJaCadastrado(String cpf) {
    if (usuarioDao.getUsuarioPorCpf(cpf) != null) {
      throw new IllegalArgumentException(MSG_ERRO_CPF_JA_CADASTRADO);
    }
  }

  /**
   * Verifica se o usuário existe na base de dados antes de realizar operações de alteração ou exclusão.
   *
   * @param idUsuario
   *        O ID do usuário.
   * @throws IllegalArgumentException
   *         Se o usuário não for encontrado.
   */
  private void validarExistenciaUsuario(long idUsuario) {
    if (usuarioDao.getUsuarioPorId(idUsuario) == null) {
      throw new IllegalArgumentException(MSG_ERRO_OP_USUARIO_NAO_ENCONTRADO);
    }
  }

  /**
   * Valida se o objeto de usuário retornado pelo banco não é nulo.
   *
   * @param usuario
   *        O DTO do usuário retornado.
   * @throws IllegalArgumentException
   *         Se o usuário for nulo.
   */
  private static void validarUsuarioEncontrado(DadoUsuarioDto usuario) {
    if (usuario == null) {
      throw new IllegalArgumentException(MSG_ERRO_USUARIO_NAO_ENCONTRADO);
    }
  }

  /**
   * Valida os campos obrigatórios do comando de cadastro.
   *
   * @param cmd
   *        O comando a ser validado.
   */
  private static void validarCmdCadastrar(CadastrarUsuarioCmd cmd) {
    ValidadorUtil.validarObjetoNaoNulo(cmd, LABEL_DADOS_CADASTRO);
    ValidadorUtil.validarTextoObrigatorio(cmd.getCpf(), LABEL_CPF);
    ValidadorUtil.validarTextoObrigatorio(cmd.getNome(), LABEL_NOME);
    ValidadorUtil.validarTextoObrigatorio(cmd.getSenha(), LABEL_SENHA);
    ValidadorUtil.validarObjetoNaoNulo(cmd.getDataNascimento(), LABEL_DATA_NASCIMENTO);
    ValidadorUtil.validarObjetoNaoNulo(cmd.getPerfil(), LABEL_PERFIL);
  }

  /**
   * Valida os campos obrigatórios do comando de login.
   *
   * @param cmd
   *        O comando a ser validado.
   */
  private static void validarCmdLogin(LoginCmd cmd) {
    ValidadorUtil.validarObjetoNaoNulo(cmd, LABEL_DADOS_LOGIN);
    ValidadorUtil.validarTextoObrigatorio(cmd.getCpf(), LABEL_CPF);
    ValidadorUtil.validarTextoObrigatorio(cmd.getSenha(), LABEL_SENHA);
  }

  /**
   * Valida os campos obrigatórios do comando de atualização.
   *
   * @param cmd
   *        O comando a ser validado.
   */
  private static void validarCmdAtualizar(AtualizarUsuarioCmd cmd) {
    ValidadorUtil.validarObjetoNaoNulo(cmd, LABEL_DADOS_ATUALIZACAO);
    ValidadorUtil.validarIdPositivo(cmd.getIdUsuario(), LABEL_ID_USUARIO);
    ValidadorUtil.validarTextoObrigatorio(cmd.getNome(), LABEL_NOME);
    ValidadorUtil.validarObjetoNaoNulo(cmd.getDataNascimento(), LABEL_DATA_NASCIMENTO);
  }
}