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

@RestController
@RequestMapping("historico")
public class HistoricoControlador {

  @Autowired
  private HistoricoServico historicoServico;

  @PostMapping("/finalizar-treino")
  public ResponseEntity<String> finalizarTreino(@RequestBody FinalizarTreinoCmd cmd) {
    return ResponseEntity.ok(historicoServico.finalizarTreino(cmd));
  }

  @GetMapping("/calendario/{idAluno}")
  public ResponseEntity<List<HistoricoCalendarioDto>> obterCalendario(@PathVariable long idAluno) {
    return ResponseEntity.ok(historicoServico.obterCalendarioAluno(idAluno));
  }
}