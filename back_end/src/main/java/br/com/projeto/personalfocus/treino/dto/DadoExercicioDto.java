package br.com.projeto.personalfocus.treino.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DadoExercicioDto {
  private long idExercicio;
  private String nomeExercicio;
  private String repeticoes;
  private BigDecimal cargaKg;
}