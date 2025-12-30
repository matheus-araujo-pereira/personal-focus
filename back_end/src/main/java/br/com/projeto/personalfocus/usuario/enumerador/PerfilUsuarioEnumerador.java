package br.com.projeto.personalfocus.usuario.enumerador;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumerador que define os perfis de acesso disponíveis no sistema.
 *
 * @author teteu
 */
public enum PerfilUsuarioEnumerador {

  PERSONAL("PERSONAL", "Personal Trainer"), ALUNO("ALUNO", "Aluno");

  private String sigla;

  private String descricao;

  PerfilUsuarioEnumerador(String sigla, String descricao) {
    this.sigla = sigla;
    this.descricao = descricao;
  }

  /**
   * Obtém a sigla do perfil, que é o valor utilizado na serialização JSON.
   *
   * @return A sigla do perfil.
   */
  @JsonValue
  public String getSigla() {
    return sigla;
  }

  /**
   * Obtém a descrição legível do perfil.
   *
   * @return A descrição do perfil.
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * Converte uma string recebida (sigla ou nome) para a instância correspondente do enumerador.
   * Método utilizado pelo Jackson durante a desserialização.
   *
   * @param valor
   *        A string representando o perfil.
   * @return A instância de PerfilUsuarioEnumerador correspondente ou null se não encontrada.
   */
  @JsonCreator
  public static PerfilUsuarioEnumerador converter(String valor) {
    if (valor == null)
      return null;

    return Arrays.stream(PerfilUsuarioEnumerador.values())
        .filter(e -> e.sigla.equalsIgnoreCase(valor) || e.name().equalsIgnoreCase(valor)).findFirst().orElse(null);
  }
}