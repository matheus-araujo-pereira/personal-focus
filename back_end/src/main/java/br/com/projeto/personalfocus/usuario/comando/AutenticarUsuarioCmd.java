package br.com.projeto.personalfocus.usuario.comando;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticarUsuarioCmd {
  @NotBlank(message = "CPF é obrigatório")
  @Size(min = 11, max = 14, message = "CPF inválido")
  private String cpf;

  @NotBlank(message = "Senha é obrigatória")
  private String senha;
}