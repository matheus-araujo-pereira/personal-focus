package br.com.projeto.personalfocus.historico.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HistoricoCalendarioDto {
  private LocalDate dataFinalizacao;
  private String nomeTreino;
}