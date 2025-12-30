package br.com.projeto.personalfocus.compartilhado.util.componente.dao.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Testes unitários para o componente utilitário de DAO.
 * Verifica se a recuperação de ID gerado funciona corretamente simulando o comportamento do JDBC.
 *
 * @author teteu
 */
public class DaoUtilComponenteImplTest {

  private DaoUtilComponenteImpl daoUtil;
  private JdbcTemplate jdbcTemplateMock;

  @BeforeMethod
  public void setUp() {
    daoUtil = new DaoUtilComponenteImpl();
    jdbcTemplateMock = mock(JdbcTemplate.class);
  }

  @Test
  public void deveRetornarIdGeradoCorretamente() {
    PreparedStatementCreator pscMock = mock(PreparedStatementCreator.class);
    long idEsperado = 123L;

    doAnswer(invocation -> {
      KeyHolder keyHolder = invocation.getArgument(1);
      Map<String, Object> keys = new HashMap<>();
      keys.put("id", idEsperado);
      ((GeneratedKeyHolder) keyHolder).getKeyList().add(keys);
      return 1;
    }).when(jdbcTemplateMock).update(eq(pscMock), any(KeyHolder.class));

    long idRetornado = daoUtil.insertRecuperandoId(jdbcTemplateMock, pscMock);

    Assert.assertEquals(idRetornado, idEsperado);
    verify(jdbcTemplateMock).update(eq(pscMock), any(KeyHolder.class));
  }
}