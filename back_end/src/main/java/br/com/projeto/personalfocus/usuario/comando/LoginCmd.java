package br.com.projeto.personalfocus.usuario.comando;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCmd {
  @NotBlank(message = "CPF é obrigatório")
  private String cpf;

  @NotBlank(message = "Senha é obrigatória")
  private String senha;
}