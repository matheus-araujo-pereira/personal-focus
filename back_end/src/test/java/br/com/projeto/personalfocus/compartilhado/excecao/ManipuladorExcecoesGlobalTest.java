package br.com.projeto.personalfocus.compartilhado.excecao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Testes unitários para o Manipulador Global de Exceções.
 * Garante que os JSONs de erro sejam formatados corretamente.
 *
 * @author teteu
 */
public class ManipuladorExcecoesGlobalTest {

  private ManipuladorExcecoesGlobal manipulador;

  @BeforeMethod
  public void setUp() {
    manipulador = new ManipuladorExcecoesGlobal();
  }

  @Test
  public void deveTratarRegraNegocioCorretamente() {
    String mensagemErro = "Usuário já existe";
    IllegalArgumentException ex = new IllegalArgumentException(mensagemErro);
    ResponseEntity<Map<String, Object>> response = manipulador.tratarRegraNegocio(ex);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    Map<String, Object> body = response.getBody();
    Assert.assertNotNull(body);
    Assert.assertEquals(body.get("erro"), "Regra de Negócio");
    Assert.assertEquals(body.get("mensagem"), mensagemErro);
    Assert.assertEquals(body.get("status"), 400);
    Assert.assertNotNull(body.get("dataHora"));
  }

  @Test
  public void deveTratarErroGeralCorretamente() {
    Exception ex = new RuntimeException("Erro banco de dados");
    ResponseEntity<Map<String, Object>> response = manipulador.tratarGeral(ex);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    Map<String, Object> body = response.getBody();
    Assert.assertNotNull(body);
    Assert.assertEquals(body.get("erro"), "Erro Interno");
    Assert.assertEquals(body.get("mensagem"), "Ocorreu um erro inesperado. Contate o suporte.");
    Assert.assertEquals(body.get("status"), 500);
  }

  @Test
  public void deveTratarValidacaoComFieldError() {
    MethodArgumentNotValidException exMock = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResultMock = mock(BindingResult.class);
    FieldError fieldError = new FieldError("objeto", "campo", "não pode ser vazio");
    when(exMock.getBindingResult()).thenReturn(bindingResultMock);
    when(bindingResultMock.getFieldError()).thenReturn(fieldError);
    ResponseEntity<Map<String, Object>> response = manipulador.tratarValidacao(exMock);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    Map<String, Object> body = response.getBody();
    Assert.assertNotNull(body);
    Assert.assertEquals(body.get("erro"), "Erro de Validação");
    Assert.assertEquals(body.get("mensagem"), "não pode ser vazio");
  }

  @Test
  public void deveTratarValidacaoSemFieldError() {
    MethodArgumentNotValidException exMock = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResultMock = mock(BindingResult.class);
    when(exMock.getBindingResult()).thenReturn(bindingResultMock);
    when(bindingResultMock.getFieldError()).thenReturn(null);
    ResponseEntity<Map<String, Object>> response = manipulador.tratarValidacao(exMock);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    Map<String, Object> body = response.getBody();
    Assert.assertEquals(body.get("mensagem"), "Erro nos parâmetros enviados");
  }
}