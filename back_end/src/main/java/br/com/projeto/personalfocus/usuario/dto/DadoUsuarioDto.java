package br.com.projeto.personalfocus.usuario.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DadoUsuarioDto {
  private long idUsuario;
  private String cpf;
  private String nome;
  private String perfil;
  private LocalDate dataNascimento;
}