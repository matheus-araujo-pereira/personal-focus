package br.com.projeto.personalfocus.treino.comando;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import br.com.projeto.personalfocus.treino.enumerador.DiaSemanaEnumerador;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarTreinoCmd {

  @NotNull(message = "Dia da semana é obrigatório")
  private DiaSemanaEnumerador diaSemana;

  @NotNull(message = "Nome do treino é obrigatório")
  private String nomeTreino;

  private String descricao;

  @NotNull(message = "ID do aluno é obrigatório")
  private Long idAluno;

  @Valid
  @NotNull(message = "Lista de exercícios não pode ser nula")
  @Size(min = 1, max = 10, message = "O treino deve ter entre 1 e 10 exercícios.")
  private List<CadastrarExercicioCmd> listaExercicios;
}