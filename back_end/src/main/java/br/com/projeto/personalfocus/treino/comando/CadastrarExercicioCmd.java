package br.com.projeto.personalfocus.treino.comando;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de comando (CMD) para cadastrar um exercício dentro de um treino.
 *
 * @author teteu
 */
@Getter
@Setter
public class CadastrarExercicioCmd {
  /**
   * Nome do exercício. Campo obrigatório.
   */
  @NotBlank(message = "Nome do exercício obrigatório")
  private String nomeExercicio;

  /**
   * Quantidade de repetições ou tempo (ex: 3x10, 20min). Campo obrigatório.
   */
  @NotBlank(message = "Repetições obrigatórias (ex: 3x10)")
  private String repeticoes;

  /**
   * Carga utilizada no exercício em KG (opcional).
   */
  private BigDecimal cargaKg;
}