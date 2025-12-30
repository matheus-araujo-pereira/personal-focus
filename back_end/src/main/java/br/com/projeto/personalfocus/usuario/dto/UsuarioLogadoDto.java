package br.com.projeto.personalfocus.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Objeto de Transferência de Dados (DTO) que representa as informações de um usuário autenticado.
 * Retornado após um login bem-sucedido.
 *
 * @author teteu
 */
@Getter
@AllArgsConstructor
public class UsuarioLogadoDto {
  /**
   * O ID único do usuário no sistema.
   */
  private long idUsuario;

  /**
   * O nome do usuário.
   */
  private String nome;

  /**
   * O perfil de acesso do usuário.
   */
  private String perfil;
}