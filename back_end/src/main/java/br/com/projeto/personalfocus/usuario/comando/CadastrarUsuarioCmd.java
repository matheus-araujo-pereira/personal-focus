package br.com.projeto.personalfocus.usuario.comando;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import br.com.projeto.personalfocus.usuario.enumerador.PerfilUsuarioEnumerador;
import lombok.Getter;

/**
 * Objeto de comando (CMD) utilizado para transportar os dados de cadastro de um novo usuário.
 * Inclui validações para garantir a integridade dos dados recebidos.
 *
 * @author teteu
 */
@Getter
public class CadastrarUsuarioCmd {

  /**
   * O CPF do usuário. Deve conter entre 11 e 14 caracteres. Campo obrigatório.
   */
  @NotBlank(message = "O CPF é obrigatório.")
  @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres.")
  private String cpf;

  /**
   * A senha do usuário. Campo obrigatório.
   */
  @NotBlank(message = "A senha é obrigatória.")
  private String senha;

  /**
   * O nome completo do usuário. Campo obrigatório.
   */
  @NotBlank(message = "O nome é obrigatório.")
  private String nome;

  /**
   * A data de nascimento do usuário. Campo obrigatório.
   */
  @NotNull(message = "A data de nascimento é obrigatória.")
  private LocalDate dataNascimento;

  /**
   * O perfil de acesso do usuário (ex: PERSONAL, ALUNO). Campo obrigatório.
   */
  @NotNull(message = "O perfil do usuário é obrigatório.")
  private PerfilUsuarioEnumerador perfil;

  // Construtor privado para Jackson deserializer
  @SuppressWarnings("unused")
  private CadastrarUsuarioCmd() {
  }

  /**
   * Construtor completo para inicialização do comando.
   *
   * @param cpf
   *        O CPF do usuário.
   * @param senha
   *        A senha do usuário.
   * @param nome
   *        O nome do usuário.
   * @param dataNascimento
   *        A data de nascimento.
   * @param perfil
   *        O perfil de acesso.
   */
  public CadastrarUsuarioCmd(String cpf, String senha, String nome, LocalDate dataNascimento,
      PerfilUsuarioEnumerador perfil) {
    this.cpf = cpf;
    this.senha = senha;
    this.nome = nome;
    this.dataNascimento = dataNascimento;
    this.perfil = perfil;
  }
}