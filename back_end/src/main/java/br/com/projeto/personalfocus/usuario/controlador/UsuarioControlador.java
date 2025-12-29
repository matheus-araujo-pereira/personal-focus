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

@RestController
@RequestMapping("usuario")
public class UsuarioControlador {

  @Autowired
  private UsuarioServico usuarioServico;

  @PostMapping("/cadastrar")
  public ResponseEntity<String> cadastrar(@RequestBody CadastrarUsuarioCmd cmd) {
    return ResponseEntity.ok(usuarioServico.cadastrarUsuario(cmd));
  }

  @PostMapping("/login")
  public ResponseEntity<UsuarioLogadoDto> login(@RequestBody LoginCmd cmd) {
    return ResponseEntity.ok(usuarioServico.login(cmd));
  }

  @GetMapping("/listar-alunos")
  public ResponseEntity<List<DadoUsuarioDto>> listarAlunos() {
    return ResponseEntity.ok(usuarioServico.listarAlunos());
  }

  @PutMapping("/atualizar")
  public ResponseEntity<String> atualizar(@RequestBody AtualizarUsuarioCmd cmd) {
    return ResponseEntity.ok(usuarioServico.atualizarUsuario(cmd));
  }

  @DeleteMapping("/excluir/{idUsuario}")
  public ResponseEntity<String> excluir(@PathVariable long idUsuario) {
    return ResponseEntity.ok(usuarioServico.excluirUsuario(idUsuario));
  }

  @GetMapping("/perfil/{idUsuario}")
  public ResponseEntity<DadoUsuarioDto> obterPerfil(@PathVariable long idUsuario) {
    return ResponseEntity.ok(usuarioServico.obterPerfil(idUsuario));
  }
}