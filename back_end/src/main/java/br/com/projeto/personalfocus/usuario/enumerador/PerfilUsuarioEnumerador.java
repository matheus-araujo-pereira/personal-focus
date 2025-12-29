package br.com.projeto.personalfocus.usuario.enumerador;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PerfilUsuarioEnumerador {

  PERSONAL("PERSONAL", "Personal Trainer"), ALUNO("ALUNO", "Aluno");

  private String sigla;

  private String descricao;

  PerfilUsuarioEnumerador(String sigla, String descricao) {
    this.sigla = sigla;
    this.descricao = descricao;
  }

  @JsonValue
  public String getSigla() {
    return sigla;
  }

  public String getDescricao() {
    return descricao;
  }

  @JsonCreator
  public static PerfilUsuarioEnumerador converter(String valor) {
    if (valor == null)
      return null;

    return Arrays.stream(PerfilUsuarioEnumerador.values())
        .filter(e -> e.sigla.equalsIgnoreCase(valor) || e.name().equalsIgnoreCase(valor)).findFirst().orElse(null);
  }
}