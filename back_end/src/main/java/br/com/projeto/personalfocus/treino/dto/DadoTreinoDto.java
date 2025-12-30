package br.com.projeto.personalfocus.treino.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Objeto de Transferência de Dados (DTO) para representar os dados resumidos de um treino.
 * Utilizado principalmente em listagens para visualização rápida das informações do treino.
 *
 * @author teteu
 */
@Getter
@AllArgsConstructor
public class DadoTreinoDto {

  /**
   * O ID único do treino.
   */
  private long idTreino;

  /**
   * O nome descritivo do treino (ex: Treino A - Peito).
   */
  private String nomeTreino;

  /**
   * O dia da semana em que o treino deve ser realizado.
   */
  private String diaSemana;

  /**
   * A descrição detalhada ou orientações sobre o treino.
   */
  private String descricao;

  /**
   * A quantidade total de exercícios cadastrados neste treino.
   */
  private int qtdExercicios;
}