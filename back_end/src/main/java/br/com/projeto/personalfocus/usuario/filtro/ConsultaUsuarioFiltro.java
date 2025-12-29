package br.com.projeto.personalfocus.usuario.filtro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaUsuarioFiltro {

  private String nome;
  private String cpf;
  private String perfil; // Opcional, para filtrar só alunos
}