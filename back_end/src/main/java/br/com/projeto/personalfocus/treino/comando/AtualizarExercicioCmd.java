package br.com.projeto.personalfocus.treino.comando;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de comando (CMD) para atualizar um exercício existente.
 *
 * @author teteu
 */
@Getter
@Setter
public class AtualizarExercicioCmd {

  /**
   * O ID do exercício a ser atualizado. Campo obrigatório.
   */
  @NotNull(message = "ID do exercício é obrigatório")
  private Long idExercicio;

  /**
   * Novo nome do exercício. Campo obrigatório.
   */
  @NotBlank(message = "Nome do exercício obrigatório")
  private String nomeExercicio;

  /**
   * Novas repetições ou tempo. Campo obrigatório.
   */
  @NotBlank(message = "Repetições obrigatórias")
  private String repeticoes;

  /**
   * Nova carga em KG.
   */
  private BigDecimal cargaKg;
}