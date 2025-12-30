package br.com.projeto.personalfocus.usuario.enumerador;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Testes unitários para o enumerador de Perfil de Usuário.
 *
 * @author teteu
 */
public class PerfilUsuarioEnumeradorTest {

  @Test
  public void deveConverterPorSiglaCorretamente() {
    for (PerfilUsuarioEnumerador enumValue : PerfilUsuarioEnumerador.values()) {
      Assert.assertEquals(PerfilUsuarioEnumerador.converter(enumValue.getSigla()), enumValue);
    }
  }

  @Test
  public void deveConverterPorNomeEnumCorretamente() {
    for (PerfilUsuarioEnumerador enumValue : PerfilUsuarioEnumerador.values()) {
      Assert.assertEquals(PerfilUsuarioEnumerador.converter(enumValue.name()), enumValue);
    }
  }

  @Test
  public void deveConverterIgnorandoCase() {
    PerfilUsuarioEnumerador perfil = PerfilUsuarioEnumerador.converter("pErSoNaL");
    Assert.assertEquals(perfil, PerfilUsuarioEnumerador.PERSONAL);
  }

  @Test
  public void deveRetornarNullQuandoValorNulo() {
    PerfilUsuarioEnumerador perfil = PerfilUsuarioEnumerador.converter(null);
    Assert.assertNull(perfil);
  }

  @Test
  public void deveRetornarNullQuandoValorInexistente() {
    PerfilUsuarioEnumerador perfil = PerfilUsuarioEnumerador.converter("DIRETOR");
    Assert.assertNull(perfil);
  }

  @Test
  public void deveRetornarDescricaoCorreta() {
    Assert.assertEquals(PerfilUsuarioEnumerador.PERSONAL.getDescricao(), "Personal Trainer");
    Assert.assertEquals(PerfilUsuarioEnumerador.ALUNO.getDescricao(), "Aluno");
  }

  @Test
  public void deveRetornarSiglaCorreta() {
    Assert.assertEquals(PerfilUsuarioEnumerador.PERSONAL.getSigla(), "PERSONAL");
    Assert.assertEquals(PerfilUsuarioEnumerador.ALUNO.getSigla(), "ALUNO");
  }
}