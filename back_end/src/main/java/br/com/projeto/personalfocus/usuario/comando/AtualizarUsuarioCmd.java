package br.com.projeto.personalfocus.usuario.comando;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarUsuarioCmd {

  @NotNull(message = "ID do usuário é obrigatório")
  private Long idUsuario;

  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @NotNull(message = "Data de nascimento é obrigatória")
  private LocalDate dataNascimento;

  private String novaSenha;
}