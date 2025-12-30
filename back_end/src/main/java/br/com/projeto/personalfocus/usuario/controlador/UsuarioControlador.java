package br.com.projeto.personalfocus.usuario.controlador;

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

import br.com.projeto.personalfocus.usuario.comando.AtualizarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.CadastrarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.LoginCmd;
import br.com.projeto.personalfocus.usuario.dto.DadoUsuarioDto;
import br.com.projeto.personalfocus.usuario.dto.UsuarioLogadoDto;
import br.com.projeto.personalfocus.usuario.servico.UsuarioServico;

/**
 * Controlador REST responsável por expor os endpoints do módulo de Usuário.
 * Recebe as requisições HTTP e delega o processamento para a camada de serviço.
 *
 * @author teteu
 */
@RestController
@RequestMapping("usuario")
public class UsuarioControlador {

  @Autowired
  private UsuarioServico usuarioServico;

  /**
   * Endpoint para cadastrar um novo usuário.
   *
   * @param cmd
   *        Objeto JSON contendo os dados do usuário.
   * @return ResponseEntity contendo a mensagem de sucesso.
   */
  @PostMapping("/cadastrar")
  public ResponseEntity<String> cadastrar(@RequestBody CadastrarUsuarioCmd cmd) {
    return ResponseEntity.ok(usuarioServico.cadastrarUsuario(cmd));
  }

  /**
   * Endpoint para autenticação de usuários (login).
   *
   * @param cmd
   *        Objeto JSON contendo CPF e senha.
   * @return ResponseEntity com os dados do usuário logado.
   */
  @PostMapping("/login")
  public ResponseEntity<UsuarioLogadoDto> login(@RequestBody LoginCmd cmd) {
    return ResponseEntity.ok(usuarioServico.login(cmd));
  }

  /**
   * Endpoint para listar todos os alunos cadastrados.
   *
   * @return ResponseEntity com a lista de alunos.
   */
  @GetMapping("/listar-alunos")
  public ResponseEntity<List<DadoUsuarioDto>> listarAlunos() {
    return ResponseEntity.ok(usuarioServico.listarAlunos());
  }

  /**
   * Endpoint para atualizar os dados de um usuário existente.
   *
   * @param cmd
   *        Objeto JSON contendo os dados atualizados.
   * @return ResponseEntity contendo a mensagem de sucesso.
   */
  @PutMapping("/atualizar")
  public ResponseEntity<String> atualizar(@RequestBody AtualizarUsuarioCmd cmd) {
    return ResponseEntity.ok(usuarioServico.atualizarUsuario(cmd));
  }

  /**
   * Endpoint para excluir um usuário pelo ID.
   *
   * @param idUsuario
   *        O ID do usuário a ser excluído.
   * @return ResponseEntity contendo a mensagem de sucesso.
   */
  @DeleteMapping("/excluir/{idUsuario}")
  public ResponseEntity<String> excluir(@PathVariable long idUsuario) {
    return ResponseEntity.ok(usuarioServico.excluirUsuario(idUsuario));
  }

  /**
   * Endpoint para obter os dados do perfil de um usuário.
   *
   * @param idUsuario
   *        O ID do usuário.
   * @return ResponseEntity com os dados do perfil do usuário.
   */
  @GetMapping("/perfil/{idUsuario}")
  public ResponseEntity<DadoUsuarioDto> obterPerfil(@PathVariable long idUsuario) {
    return ResponseEntity.ok(usuarioServico.obterPerfil(idUsuario));
  }
}