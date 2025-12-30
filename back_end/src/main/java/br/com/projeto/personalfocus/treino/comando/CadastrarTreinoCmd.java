package br.com.projeto.personalfocus.treino.comando;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import br.com.projeto.personalfocus.treino.enumerador.DiaSemanaEnumerador;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de comando (CMD) para cadastrar um novo treino.
 * Contém informações sobre o dia, nome, descrição e a lista de exercícios.
 *
 * @author teteu
 */
@Getter
@Setter
public class CadastrarTreinoCmd {

  /**
   * Dia da semana do treino. Campo obrigatório.
   */
  @NotNull(message = "Dia da semana é obrigatório")
  private DiaSemanaEnumerador diaSemana;

  /**
   * Nome descritivo do treino (ex: Treino A - Peito). Campo obrigatório.
   */
  @NotNull(message = "Nome do treino é obrigatório")
  private String nomeTreino;

  /**
   * Descrição detalhada do treino (opcional).
   */
  private String descricao;

  /**
   * ID do aluno ao qual o treino pertence. Campo obrigatório.
   */
  @NotNull(message = "ID do aluno é obrigatório")
  private Long idAluno;

  /**
   * Lista de exercícios que compõem o treino.
   * Deve conter entre 1 e 10 exercícios.
   */
  @Valid
  @NotNull(message = "Lista de exercícios não pode ser nula")
  @Size(min = 1, max = 10, message = "O treino deve ter entre 1 e 10 exercícios.")
  private List<CadastrarExercicioCmd> listaExercicios;
}