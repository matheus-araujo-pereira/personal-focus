package br.com.projeto.personalfocus.treino.comando;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarExercicioCmd {

  @NotNull(message = "ID do exercício é obrigatório")
  private Long idExercicio;

  @NotBlank(message = "Nome do exercício obrigatório")
  private String nomeExercicio;

  @NotBlank(message = "Repetições obrigatórias")
  private String repeticoes;

  private BigDecimal cargaKg;
}