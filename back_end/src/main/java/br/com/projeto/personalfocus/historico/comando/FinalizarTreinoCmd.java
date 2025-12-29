package br.com.projeto.personalfocus.historico.comando;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FinalizarTreinoCmd {
    @NotNull(message = "ID do Aluno é obrigatório")
    private Long idAluno;

    @NotNull(message = "ID do Treino é obrigatório")
    private Long idTreino;
    
    // Opcional, se não vier, usa a data atual
    private LocalDate dataFinalizacao;
}