package br.com.projeto.personalfocus.usuario.comando;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de comando (CMD) utilizado para transportar os dados de login.
 * Contém as credenciais necessárias para autenticação no sistema.
 *
 * @author teteu
 */
@Getter
@Setter
public class LoginCmd {
  /**
   * O CPF do usuário para login. Campo obrigatório.
   */
  @NotBlank(message = "CPF é obrigatório")
  private String cpf;

  /**
   * A senha do usuário para login. Campo obrigatório.
   */
  @NotBlank(message = "Senha é obrigatória")
  private String senha;
}