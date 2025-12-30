package br.com.projeto.personalfocus.compartilhado.util;

import java.util.Collection;
import org.springframework.util.Assert;

/**
 * Classe utilitária para centralizar regras de validação comuns no sistema.
 * Aplica o princípio DRY (Don't Repeat Yourself) para validações de IDs, objetos nulos e coleções.
 *
 * @author teteu
 */
public final class ValidadorUtil {

  // Construtor privado para impedir instância
  private ValidadorUtil() {
  }

  /**
   * Valida se um objeto DTO ou CMD não é nulo.
   *
   * @param objeto
   *        O objeto a ser verificado.
   * @param nomeObjeto
   *        O nome do objeto para compor a mensagem de erro.
   * @throws IllegalArgumentException
   *         Se o objeto for nulo.
   */
  public static void validarObjetoNaoNulo(Object objeto, String nomeObjeto) {
    Assert.notNull(objeto, nomeObjeto + " não pode ser nulo(a).");
  }

  /**
   * Valida se um ID é positivo (maior que zero).
   *
   * @param id
   *        O valor do ID.
   * @param nomeCampo
   *        O nome do campo (ex: "ID do Aluno") para a mensagem de erro.
   * @throws IllegalArgumentException
   *         Se o ID for menor ou igual a zero.
   */
  public static void validarIdPositivo(Long id, String nomeCampo) {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException(nomeCampo + " inválido: deve ser maior que zero.");
    }
  }

  /**
   * Valida se uma string não é nula nem vazia (contém texto).
   *
   * @param texto
   *        A string a ser verificada.
   * @param nomeCampo
   *        O nome do campo para a mensagem de erro.
   * @throws IllegalArgumentException
   *         Se a string for nula ou vazia.
   */
  public static void validarTextoObrigatorio(String texto, String nomeCampo) {
    Assert.hasText(texto, nomeCampo + " é obrigatório(a).");
  }

  /**
   * Valida se uma coleção não é nula e não está vazia.
   *
   * @param colecao
   *        A coleção a ser verificada.
   * @param mensagemErro
   *        A mensagem de erro caso a validação falhe.
   * @throws IllegalArgumentException
   *         Se a coleção for nula ou vazia.
   */
  public static void validarListaNaoVazia(Collection<?> colecao, String mensagemErro) {
    if (colecao == null || colecao.isEmpty()) {
      throw new IllegalArgumentException(mensagemErro);
    }
  }

  /**
   * Valida o tamanho máximo de uma lista.
   *
   * @param colecao
   *        A coleção a ser verificada.
   * @param maximo
   *        O tamanho máximo permitido.
   * @param mensagemErro
   *        A mensagem de erro caso o tamanho exceda o limite.
   */
  public static void validarTamanhoMaximoLista(Collection<?> colecao, int maximo, String mensagemErro) {
    if (colecao != null && colecao.size() > maximo) {
      throw new IllegalArgumentException(mensagemErro);
    }
  }
}