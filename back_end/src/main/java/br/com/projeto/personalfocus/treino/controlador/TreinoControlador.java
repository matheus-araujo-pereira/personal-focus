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

/**
 * Controlador REST responsável por expor os endpoints do módulo de Treino.
 * Recebe as requisições HTTP para gerenciamento de treinos e exercícios.
 *
 * @author teteu
 */
@RestController
@RequestMapping("treino")
public class TreinoControlador {

  @Autowired
  private TreinoServico treinoServico;

  /**
   * Endpoint para cadastrar um novo treino com seus exercícios.
   *
   * @param cmd
   *        Objeto JSON contendo os dados do treino e lista de exercícios.
   * @return ResponseEntity contendo a mensagem de sucesso.
   */
  @PostMapping("/cadastrar")
  public ResponseEntity<String> cadastrarTreino(@RequestBody CadastrarTreinoCmd cmd) {
    return ResponseEntity.ok(treinoServico.cadastrarTreino(cmd));
  }

  /**
   * Endpoint para listar os treinos de um aluno.
   *
   * @param idAluno
   *        O ID do aluno.
   * @return ResponseEntity com a lista de treinos.
   */
  @GetMapping("/listar/{idAluno}")
  public ResponseEntity<List<DadoTreinoDto>> listarTreinos(@PathVariable long idAluno) {
    return ResponseEntity.ok(treinoServico.listarTreinosDoAluno(idAluno));
  }

  /**
   * Endpoint para listar os exercícios de um treino específico.
   *
   * @param idTreino
   *        O ID do treino.
   * @return ResponseEntity com a lista de exercícios.
   */
  @GetMapping("/exercicios/{idTreino}")
  public ResponseEntity<List<DadoExercicioDto>> listarExercicios(@PathVariable long idTreino) {
    return ResponseEntity.ok(treinoServico.listarExerciciosDoTreino(idTreino));
  }

  /**
   * Endpoint para atualizar um treino existente.
   *
   * @param cmd
   *        Objeto JSON contendo os dados atualizados do treino.
   * @return ResponseEntity contendo a mensagem de sucesso.
   */
  @PutMapping("/atualizar")
  public ResponseEntity<String> atualizarTreino(@RequestBody AtualizarTreinoCmd cmd) {
    return ResponseEntity.ok(treinoServico.atualizarTreino(cmd));
  }

  /**
   * Endpoint para atualizar um exercício existente.
   *
   * @param cmd
   *        Objeto JSON contendo os dados atualizados do exercício.
   * @return ResponseEntity contendo a mensagem de sucesso.
   */
  @PutMapping("/exercicio/atualizar")
  public ResponseEntity<String> atualizarExercicio(@RequestBody AtualizarExercicioCmd cmd) {
    return ResponseEntity.ok(treinoServico.atualizarExercicio(cmd));
  }

  /**
   * Endpoint para excluir um treino.
   *
   * @param idTreino
   *        O ID do treino a ser excluído.
   * @return ResponseEntity contendo a mensagem de sucesso.
   */
  @DeleteMapping("/excluir/{idTreino}")
  public ResponseEntity<String> excluirTreino(@PathVariable long idTreino) {
    return ResponseEntity.ok(treinoServico.excluirTreino(idTreino));
  }

  /**
   * Endpoint para excluir um exercício.
   *
   * @param idExercicio
   *        O ID do exercício a ser excluído.
   * @return ResponseEntity contendo a mensagem de sucesso.
   */
  @DeleteMapping("/exercicio/excluir/{idExercicio}")
  public ResponseEntity<String> excluirExercicio(@PathVariable long idExercicio) {
    return ResponseEntity.ok(treinoServico.excluirExercicio(idExercicio));
  }
}