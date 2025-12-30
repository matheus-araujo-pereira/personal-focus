package br.com.projeto.personalfocus.treino.enumerador;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Testes unitários para o enumerador de Dias da Semana.
 *
 * @author teteu
 */
public class DiaSemanaEnumeradorTest {

  @Test
  public void deveConverterPorCodigoCorretamente() {
    for (DiaSemanaEnumerador dia : DiaSemanaEnumerador.values()) {
      Assert.assertEquals(DiaSemanaEnumerador.converter(dia.getCodigo()), dia);
    }
  }

  @Test
  public void deveConverterPorNomeEnumCorretamente() {
    for (DiaSemanaEnumerador dia : DiaSemanaEnumerador.values()) {
      Assert.assertEquals(DiaSemanaEnumerador.converter(dia.name()), dia);
    }
  }

  @Test
  public void deveConverterIgnorandoCase() {
    DiaSemanaEnumerador dia = DiaSemanaEnumerador.converter("SeGuNdA");
    Assert.assertEquals(dia, DiaSemanaEnumerador.SEGUNDA);
  }

  @Test
  public void deveRetornarNullQuandoValorNulo() {
    Assert.assertNull(DiaSemanaEnumerador.converter(null));
  }

  @Test
  public void deveRetornarNullQuandoValorInexistente() {
    Assert.assertNull(DiaSemanaEnumerador.converter("FERIADO"));
  }

  @Test
  public void deveRetornarDescricaoCorreta() {
    Assert.assertEquals(DiaSemanaEnumerador.DOMINGO.getDescricao(), "Domingo");
    Assert.assertEquals(DiaSemanaEnumerador.SEGUNDA.getDescricao(), "Segunda-feira");
    Assert.assertEquals(DiaSemanaEnumerador.TERCA.getDescricao(), "Terça-feira");
    Assert.assertEquals(DiaSemanaEnumerador.QUARTA.getDescricao(), "Quarta-feira");
    Assert.assertEquals(DiaSemanaEnumerador.QUINTA.getDescricao(), "Quinta-feira");
    Assert.assertEquals(DiaSemanaEnumerador.SEXTA.getDescricao(), "Sexta-feira");
    Assert.assertEquals(DiaSemanaEnumerador.SABADO.getDescricao(), "Sábado");
  }

  @Test
  public void deveRetornarCodigoCorreto() {
    Assert.assertEquals(DiaSemanaEnumerador.DOMINGO.getCodigo(), "DOMINGO");
    Assert.assertEquals(DiaSemanaEnumerador.SEGUNDA.getCodigo(), "SEGUNDA");
    Assert.assertEquals(DiaSemanaEnumerador.TERCA.getCodigo(), "TERCA");
    Assert.assertEquals(DiaSemanaEnumerador.QUARTA.getCodigo(), "QUARTA");
    Assert.assertEquals(DiaSemanaEnumerador.QUINTA.getCodigo(), "QUINTA");
    Assert.assertEquals(DiaSemanaEnumerador.SEXTA.getCodigo(), "SEXTA");
    Assert.assertEquals(DiaSemanaEnumerador.SABADO.getCodigo(), "SABADO");
  }
}