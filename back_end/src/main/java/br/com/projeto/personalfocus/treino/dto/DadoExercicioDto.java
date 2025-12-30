package br.com.projeto.personalfocus.treino.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Objeto de Transferência de Dados (DTO) para representar os dados detalhados de um exercício.
 * Contém as informações específicas de execução de cada exercício dentro de um treino.
 *
 * @author teteu
 */
@Getter
@AllArgsConstructor
public class DadoExercicioDto {

  /**
   * O ID único do exercício.
   */
  private long idExercicio;

  /**
   * O nome do exercício a ser realizado.
   */
  private String nomeExercicio;

  /**
   * A instrução de repetições ou tempo de execução (ex: 3x10, 15min).
   */
  private String repeticoes;

  /**
   * A carga de peso sugerida para o exercício em quilogramas (KG).
   */
  private BigDecimal cargaKg;
}