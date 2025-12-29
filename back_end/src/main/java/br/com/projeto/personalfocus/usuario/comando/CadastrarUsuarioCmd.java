package br.com.projeto.personalfocus.usuario.comando;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.projeto.personalfocus.usuario.enumerador.PerfilUsuarioEnumerador;
import lombok.Getter;

@Getter
public class CadastrarUsuarioCmd {

  @NotBlank(message = "O CPF é obrigatório.")
  @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres.")
  private String cpf;

  @NotBlank(message = "A senha é obrigatória.")
  private String senha;

  @NotBlank(message = "O nome é obrigatório.")
  private String nome;

  @NotNull(message = "A data de nascimento é obrigatória.")
  private LocalDate dataNascimento;

  @NotNull(message = "O perfil do usuário é obrigatório.")
  private PerfilUsuarioEnumerador perfil;

  // Construtor privado para Jackson deserializer
  @SuppressWarnings("unused")
  private CadastrarUsuarioCmd() {
  }

  public CadastrarUsuarioCmd(String cpf, String senha, String nome, LocalDate dataNascimento,
      PerfilUsuarioEnumerador perfil) {
    this.cpf = cpf;
    this.senha = senha;
    this.nome = nome;
    this.dataNascimento = dataNascimento;
    this.perfil = perfil;
  }
}