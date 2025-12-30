package br.com.projeto.personalfocus.historico.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;
import br.com.projeto.personalfocus.historico.dto.HistoricoCalendarioDto;
import br.com.projeto.personalfocus.historico.servico.HistoricoServico;

/**
 * Controlador REST para o módulo de Histórico.
 * Expõe endpoints para registrar a conclusão de treinos e consultar o histórico de atividades.
 *
 * @author teteu
 */
@RestController
@RequestMapping("historico")
public class HistoricoControlador {

  @Autowired
  private HistoricoServico historicoServico;

  /**
   * Endpoint para registrar que um aluno finalizou um treino.
   *
   * @param cmd
   *        Objeto JSON contendo os IDs do aluno e do treino, e opcionalmente a data.
   * @return ResponseEntity com a mensagem de sucesso.
   */
  @PostMapping("/finalizar-treino")
  public ResponseEntity<String> finalizarTreino(@RequestBody FinalizarTreinoCmd cmd) {
    return ResponseEntity.ok(historicoServico.finalizarTreino(cmd));
  }

  /**
   * Endpoint para obter os dados do calendário de treinos de um aluno.
   *
   * @param idAluno
   *        O ID do aluno para o qual se deseja ver o histórico.
   * @return ResponseEntity com a lista de treinos realizados.
   */
  @GetMapping("/calendario/{idAluno}")
  public ResponseEntity<List<HistoricoCalendarioDto>> obterCalendario(@PathVariable long idAluno) {
    return ResponseEntity.ok(historicoServico.obterCalendarioAluno(idAluno));
  }
}