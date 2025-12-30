package br.com.projeto.personalfocus.compartilhado.excecao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Interceptador global de exceções.
 * Captura erros lançados nos serviços e controladores para retornar JSONs amigáveis.
 * Centraliza o tratamento de erros de validação, regras de negócio e erros internos.
 *
 * @author teteu
 */
@ControllerAdvice
public class ManipuladorExcecoesGlobal {

  private static final String CHAVE_DATA_HORA = "dataHora";
  private static final String CHAVE_STATUS = "status";
  private static final String CHAVE_ERRO = "erro";
  private static final String CHAVE_MENSAGEM = "mensagem";

  /**
   * Trata exceções de validação de argumentos (@Valid, @NotNull, etc).
   * Captura erros gerados pelo Bean Validation quando os dados de entrada não atendem aos requisitos.
   *
   * @param ex
   *        A exceção lançada contendo os detalhes da falha de validação.
   * @return Um ResponseEntity contendo o mapa com os detalhes do erro e status HTTP 400.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> tratarValidacao(MethodArgumentNotValidException ex) {
    Map<String, Object> resposta = montarMapaValidacao(ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
  }

  /**
   * Trata exceções de regra de negócio (IllegalArgumentException).
   * Captura erros lançados explicitamente pelos serviços quando uma regra é violada.
   *
   * @param ex
   *        A exceção lançada contendo a mensagem de erro da regra de negócio.
   * @return Um ResponseEntity contendo o mapa com os detalhes do erro e status HTTP 400.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> tratarRegraNegocio(IllegalArgumentException ex) {
    Map<String, Object> resposta = montarMapaRegraNegocio(ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
  }

  /**
   * Trata exceções gerais não previstas (Exception).
   * Captura qualquer outro erro que não tenha sido tratado especificamente, evitando que o cliente receba stack traces.
   *
   * @param ex
   *        A exceção genérica capturada.
   * @return Um ResponseEntity contendo uma mensagem genérica e status HTTP 500.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> tratarGeral(Exception ex) {
    Map<String, Object> resposta = montarMapaErroInterno();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
  }

  private static Map<String, Object> montarMapaValidacao(MethodArgumentNotValidException ex) {
    Map<String, Object> resposta = new HashMap<>();
    resposta.put(CHAVE_DATA_HORA, LocalDateTime.now());
    resposta.put(CHAVE_STATUS, HttpStatus.BAD_REQUEST.value());
    resposta.put(CHAVE_ERRO, "Erro de Validação");
    resposta.put(CHAVE_MENSAGEM, extrairMensagemValidacao(ex));
    return resposta;
  }

  private static String extrairMensagemValidacao(MethodArgumentNotValidException ex) {
    FieldError fieldError = ex.getBindingResult().getFieldError();
    return fieldError != null ? fieldError.getDefaultMessage() : "Erro nos parâmetros enviados";
  }

  private static Map<String, Object> montarMapaRegraNegocio(IllegalArgumentException ex) {
    Map<String, Object> resposta = new HashMap<>();
    resposta.put(CHAVE_DATA_HORA, LocalDateTime.now());
    resposta.put(CHAVE_STATUS, HttpStatus.BAD_REQUEST.value());
    resposta.put(CHAVE_ERRO, "Regra de Negócio");
    resposta.put(CHAVE_MENSAGEM, ex.getMessage());
    return resposta;
  }

  private static Map<String, Object> montarMapaErroInterno() {
    Map<String, Object> resposta = new HashMap<>();
    resposta.put(CHAVE_DATA_HORA, LocalDateTime.now());
    resposta.put(CHAVE_STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
    resposta.put(CHAVE_ERRO, "Erro Interno");
    resposta.put(CHAVE_MENSAGEM, "Ocorreu um erro inesperado. Contate o suporte.");
    return resposta;
  }
}