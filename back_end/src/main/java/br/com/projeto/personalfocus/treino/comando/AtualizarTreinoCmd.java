package br.com.projeto.personalfocus.treino.comando;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de comando (CMD) para atualizar os dados básicos de um treino.
 *
 * @author teteu
 */
@Getter
@Setter
public class AtualizarTreinoCmd {

  /**
   * O ID do treino a ser atualizado. Campo obrigatório.
   */
  @NotNull(message = "ID do treino é obrigatório")
  private Long idTreino;

  /**
   * Novo nome do treino. Campo obrigatório.
   */
  @NotBlank(message = "Nome do treino é obrigatório")
  private String nomeTreino;

  /**
   * Nova descrição do treino.
   */
  private String descricao;
}