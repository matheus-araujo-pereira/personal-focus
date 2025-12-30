package br.com.projeto.personalfocus.usuario.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Objeto de Transferência de Dados (DTO) que representa os detalhes completos de um usuário.
 * Utilizado em listagens e visualização de perfil.
 *
 * @author teteu
 */
@Getter
@AllArgsConstructor
public class DadoUsuarioDto {
  /**
   * O ID único do usuário.
   */
  private long idUsuario;

  /**
   * O CPF do usuário.
   */
  private String cpf;

  /**
   * O nome completo do usuário.
   */
  private String nome;

  /**
   * O perfil de acesso do usuário.
   */
  private String perfil;

  /**
   * A data de nascimento do usuário.
   */
  private LocalDate dataNascimento;
}