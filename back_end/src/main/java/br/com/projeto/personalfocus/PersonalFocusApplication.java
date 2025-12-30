package br.com.projeto.personalfocus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal responsável pela inicialização e execução da aplicação PersonalFocus.
 * Esta classe aciona a configuração automática do Spring Boot e o escaneamento de componentes.
 *
 * @author teteu
 */
@SpringBootApplication
public class PersonalFocusApplication {

  /**
   * Ponto de entrada da aplicação.
   * Delega a execução para a classe SpringApplication para inicializar o contexto da aplicação.
   *
   * @param args
   *        Argumentos de linha de comando passados durante a inicialização.
   */
  public static void main(String[] args) {
    SpringApplication.run(PersonalFocusApplication.class, args);
  }
}