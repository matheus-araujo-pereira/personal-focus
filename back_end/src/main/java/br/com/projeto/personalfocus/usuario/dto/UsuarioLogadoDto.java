package br.com.projeto.personalfocus.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioLogadoDto {
  private long idUsuario;
  private String nome;
  private String perfil;
}