package br.com.projeto.personalfocus.usuario.comando;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de comando (CMD) utilizado para transportar os dados de atualização de um usuário existente.
 *
 * @author teteu
 */
@Getter
@Setter
public class AtualizarUsuarioCmd {

  /**
   * O ID do usuário a ser atualizado. Campo obrigatório.
   */
  @NotNull(message = "ID do usuário é obrigatório")
  private Long idUsuario;

  /**
   * O novo nome do usuário. Campo obrigatório.
   */
  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  /**
   * A nova data de nascimento do usuário. Campo obrigatório.
   */
  @NotNull(message = "Data de nascimento é obrigatória")
  private LocalDate dataNascimento;

  /**
   * A nova senha do usuário. Campo opcional.
   */
  private String novaSenha;
}