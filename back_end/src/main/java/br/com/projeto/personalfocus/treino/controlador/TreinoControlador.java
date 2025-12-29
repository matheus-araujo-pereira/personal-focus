package br.com.projeto.personalfocus.treino.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.personalfocus.treino.comando.AtualizarExercicioCmd;
import br.com.projeto.personalfocus.treino.comando.AtualizarTreinoCmd;
import br.com.projeto.personalfocus.treino.comando.CadastrarTreinoCmd;
import br.com.projeto.personalfocus.treino.dto.DadoExercicioDto;
import br.com.projeto.personalfocus.treino.dto.DadoTreinoDto;
import br.com.projeto.personalfocus.treino.servico.TreinoServico;

@RestController
@RequestMapping("treino")
public class TreinoControlador {

  @Autowired
  private TreinoServico treinoServico;

  @PostMapping("/cadastrar")
  public ResponseEntity<String> cadastrarTreino(@RequestBody CadastrarTreinoCmd cmd) {
    return ResponseEntity.ok(treinoServico.cadastrarTreino(cmd));
  }

  @GetMapping("/listar/{idAluno}")
  public ResponseEntity<List<DadoTreinoDto>> listarTreinos(@PathVariable long idAluno) {
    return ResponseEntity.ok(treinoServico.listarTreinosDoAluno(idAluno));
  }

  @GetMapping("/exercicios/{idTreino}")
  public ResponseEntity<List<DadoExercicioDto>> listarExercicios(@PathVariable long idTreino) {
    return ResponseEntity.ok(treinoServico.listarExerciciosDoTreino(idTreino));
  }

  @PutMapping("/atualizar")
  public ResponseEntity<String> atualizarTreino(@RequestBody AtualizarTreinoCmd cmd) {
    return ResponseEntity.ok(treinoServico.atualizarTreino(cmd));
  }

  @PutMapping("/exercicio/atualizar")
  public ResponseEntity<String> atualizarExercicio(@RequestBody AtualizarExercicioCmd cmd) {
    return ResponseEntity.ok(treinoServico.atualizarExercicio(cmd));
  }

  @DeleteMapping("/excluir/{idTreino}")
  public ResponseEntity<String> excluirTreino(@PathVariable long idTreino) {
    return ResponseEntity.ok(treinoServico.excluirTreino(idTreino));
  }

  @DeleteMapping("/exercicio/excluir/{idExercicio}")
  public ResponseEntity<String> excluirExercicio(@PathVariable long idExercicio) {
    return ResponseEntity.ok(treinoServico.excluirExercicio(idExercicio));
  }
}