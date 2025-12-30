package br.com.projeto.personalfocus.compartilhado.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.testng.annotations.Test;

/**
 * Testes unitários para a classe utilitária de validação.
 * Verifica se as exceções são lançadas corretamente para dados inválidos.
 *
 * @author teteu
 */
public class ValidadorUtilTest {

  @Test
  public void naoDeveLancarExcecaoQuandoObjetoNaoNulo() {
    ValidadorUtil.validarObjetoNaoNulo(new Object(), "Objeto");
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".* não pode ser nulo\\(a\\)\\.")
  public void deveLancarExcecaoQuandoObjetoNulo() {
    ValidadorUtil.validarObjetoNaoNulo(null, "Objeto Teste");
  }

  @Test
  public void naoDeveLancarExcecaoQuandoIdPositivo() {
    ValidadorUtil.validarIdPositivo(1L, "ID");
    ValidadorUtil.validarIdPositivo(100L, "ID");
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".* inválido: deve ser maior que zero\\.")
  public void deveLancarExcecaoQuandoIdZero() {
    ValidadorUtil.validarIdPositivo(0L, "ID Teste");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void deveLancarExcecaoQuandoIdNegativo() {
    ValidadorUtil.validarIdPositivo(-1L, "ID Teste");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void deveLancarExcecaoQuandoIdNulo() {
    ValidadorUtil.validarIdPositivo(null, "ID Teste");
  }

  @Test
  public void naoDeveLancarExcecaoQuandoTextoValido() {
    ValidadorUtil.validarTextoObrigatorio("Texto Válido", "Campo");
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".* é obrigatório\\(a\\)\\.")
  public void deveLancarExcecaoQuandoTextoNulo() {
    ValidadorUtil.validarTextoObrigatorio(null, "Campo Teste");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void deveLancarExcecaoQuandoTextoVazio() {
    ValidadorUtil.validarTextoObrigatorio("", "Campo Teste");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void deveLancarExcecaoQuandoTextoEmBranco() {
    ValidadorUtil.validarTextoObrigatorio("   ", "Campo Teste");
  }

  @Test
  public void naoDeveLancarExcecaoQuandoListaPreenchida() {
    ValidadorUtil.validarListaNaoVazia(Arrays.asList("A", "B"), "Erro");
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Lista vazia erro")
  public void deveLancarExcecaoQuandoListaVazia() {
    ValidadorUtil.validarListaNaoVazia(Collections.emptyList(), "Lista vazia erro");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void deveLancarExcecaoQuandoListaNula() {
    ValidadorUtil.validarListaNaoVazia(null, "Lista nula erro");
  }

  @Test
  public void naoDeveLancarExcecaoQuandoTamanhoDentroDoLimite() {
    List<String> lista = Arrays.asList("A", "B");
    ValidadorUtil.validarTamanhoMaximoLista(lista, 2, "Erro tamanho");
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Erro tamanho excedido")
  public void deveLancarExcecaoQuandoTamanhoExcedeLimite() {
    List<String> lista = Arrays.asList("A", "B", "C");
    ValidadorUtil.validarTamanhoMaximoLista(lista, 2, "Erro tamanho excedido");
  }

  @Test
  public void naoDeveLancarExcecaoQuandoListaNulaEmValidarTamanho() {
    ValidadorUtil.validarTamanhoMaximoLista(null, 10, "Erro");
  }
}