package br.com.projeto.personalfocus.treino.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DadoTreinoDto {
  private long idTreino;
  private String nomeTreino;
  private String diaSemana;
  private String descricao;
  private int qtdExercicios;
}