package br.com.projeto.personalfocus.treino.comando;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarExercicioCmd {
  @NotBlank(message = "Nome do exercício obrigatório")
  private String nomeExercicio;

  @NotBlank(message = "Repetições obrigatórias (ex: 3x10)")
  private String repeticoes;

  private BigDecimal cargaKg;
}