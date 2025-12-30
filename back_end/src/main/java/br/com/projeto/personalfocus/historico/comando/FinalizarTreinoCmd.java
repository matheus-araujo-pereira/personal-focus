package br.com.projeto.personalfocus.historico.comando;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de comando (CMD) para registrar a finalização de um treino.
 * Transporta os dados necessários para criar um registro no histórico.
 *
 * @author teteu
 */
@Getter
@Setter
public class FinalizarTreinoCmd {

    /**
     * O ID do aluno que realizou o treino. Campo obrigatório.
     */
    @NotNull(message = "ID do Aluno é obrigatório")
    private Long idAluno;

    /**
     * O ID do treino que foi realizado. Campo obrigatório.
     */
    @NotNull(message = "ID do Treino é obrigatório")
    private Long idTreino;

    /**
     * A data em que o treino foi finalizado.
     * Campo opcional. Se não fornecido, o sistema utilizará a data atual.
     */
    private LocalDate dataFinalizacao;
}