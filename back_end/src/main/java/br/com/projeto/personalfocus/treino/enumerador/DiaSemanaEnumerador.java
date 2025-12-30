package br.com.projeto.personalfocus.treino.enumerador;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumerador que representa os dias da semana disponíveis para agendamento de treinos.
 *
 * @author teteu
 */
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

  /**
   * Obtém o código do dia da semana, utilizado para serialização JSON e persistência.
   *
   * @return O código do dia.
   */
  @JsonValue
  public String getCodigo() {
    return codigo;
  }

  /**
   * Obtém a descrição legível do dia da semana.
   *
   * @return A descrição do dia.
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * Converte uma string (código) para a instância correspondente do enumerador.
   * Utiliza apenas o código para conversão, pois ele é o identificador único.
   *
   * @param valor
   *        A string representando o dia da semana.
   * @return A instância de DiaSemanaEnumerador correspondente ou null se não encontrada.
   */
  @JsonCreator
  public static DiaSemanaEnumerador converter(String valor) {
    if (valor == null) {
      return null;
    }

    return Arrays.stream(DiaSemanaEnumerador.values()).filter(e -> e.codigo.equalsIgnoreCase(valor)).findFirst()
        .orElse(null);
  }
}