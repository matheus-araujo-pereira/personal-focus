package br.com.projeto.personalfocus.treino.enumerador;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DiaSemanaEnumerador {
  DOMINGO("DOMINGO", "Domingo"), SEGUNDA("SEGUNDA", "Segunda-feira"), TERCA("TERCA", "Terça-feira"),
  QUARTA("QUARTA", "Quarta-feira"), QUINTA("QUINTA", "Quinta-feira"), SEXTA("SEXTA", "Sexta-feira"),
  SABADO("SABADO", "Sábado");

  private String codigo;
  private String descricao;

  DiaSemanaEnumerador(String codigo, String descricao) {
    this.codigo = codigo;
    this.descricao = descricao;
  }

  @JsonValue
  public String getCodigo() {
    return codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  @JsonCreator
  public static DiaSemanaEnumerador converter(String valor) {
    if (valor == null)
      return null;

    return Arrays.stream(DiaSemanaEnumerador.values())
        .filter(e -> e.codigo.equalsIgnoreCase(valor) || e.name().equalsIgnoreCase(valor)).findFirst().orElse(null);
  }
}