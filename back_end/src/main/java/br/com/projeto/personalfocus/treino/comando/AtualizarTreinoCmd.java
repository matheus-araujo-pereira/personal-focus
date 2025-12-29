package br.com.projeto.personalfocus.treino.comando;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarTreinoCmd {

  @NotNull(message = "ID do treino é obrigatório")
  private Long idTreino;

  @NotBlank(message = "Nome do treino é obrigatório")
  private String nomeTreino;

  private String descricao;
}