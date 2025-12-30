package br.com.projeto.personalfocus.historico.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Objeto de Transferência de Dados (DTO) para exibição no calendário do aluno.
 * Representa um dia em que um treino foi concluído.
 *
 * @author teteu
 */
@Getter
@AllArgsConstructor
public class HistoricoCalendarioDto {

  /**
   * A data em que o treino foi finalizado.
   */
  private LocalDate dataFinalizacao;

  /**
   * O nome do treino realizado (ex: Treino A - Peito).
   */
  private String nomeTreino;
}