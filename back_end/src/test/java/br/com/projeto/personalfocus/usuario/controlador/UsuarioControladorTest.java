package br.com.projeto.personalfocus.usuario.controlador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.projeto.personalfocus.compartilhado.excecao.ManipuladorExcecoesGlobal;
import br.com.projeto.personalfocus.usuario.comando.AtualizarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.CadastrarUsuarioCmd;
import br.com.projeto.personalfocus.usuario.comando.LoginCmd;
import br.com.projeto.personalfocus.usuario.dto.DadoUsuarioDto;
import br.com.projeto.personalfocus.usuario.dto.UsuarioLogadoDto;
import br.com.projeto.personalfocus.usuario.enumerador.PerfilUsuarioEnumerador;
import br.com.projeto.personalfocus.usuario.servico.UsuarioServico;

public class UsuarioControladorTest {

  private MockMvc mockMvc;

  @Mock
  private UsuarioServico usuarioServico;

  @InjectMocks
  private UsuarioControlador usuarioControlador;

  private ObjectMapper objectMapper;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(usuarioControlador).setControllerAdvice(new ManipuladorExcecoesGlobal())
        .build();
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  @Test
  public void deveCadastrarUsuarioComSucesso() throws Exception {
    CadastrarUsuarioCmd cmd = new CadastrarUsuarioCmd("123", "123", "Nome", LocalDate.now(),
        PerfilUsuarioEnumerador.ALUNO);
    when(usuarioServico.cadastrarUsuario(any())).thenReturn("Sucesso");
    mockMvc.perform(post("/usuario/cadastrar").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(cmd))).andExpect(status().isOk());
  }

  @Test
  public void deveLogarComSucesso() throws Exception {
    LoginCmd cmd = new LoginCmd();
    cmd.setCpf("123");
    cmd.setSenha("123");
    when(usuarioServico.login(any())).thenReturn(new UsuarioLogadoDto(1L, "Nome", "ALUNO"));
    mockMvc.perform(
        post("/usuario/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cmd)))
        .andExpect(status().isOk());
  }

  @Test
  public void deveListarAlunosComSucesso() throws Exception {
    when(usuarioServico.listarAlunos()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/usuario/listar-alunos")).andExpect(status().isOk());
  }

  @Test
  public void deveAtualizarUsuarioComSucesso() throws Exception {
    AtualizarUsuarioCmd cmd = new AtualizarUsuarioCmd();
    cmd.setIdUsuario(1L);
    cmd.setNome("Nome");
    cmd.setDataNascimento(LocalDate.now());
    when(usuarioServico.atualizarUsuario(any())).thenReturn("Sucesso");
    mockMvc.perform(
        put("/usuario/atualizar").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cmd)))
        .andExpect(status().isOk());
  }

  @Test
  public void deveExcluirUsuarioComSucesso() throws Exception {
    when(usuarioServico.excluirUsuario(anyLong())).thenReturn("Sucesso");
    mockMvc.perform(delete("/usuario/excluir/1")).andExpect(status().isOk());
  }

  @Test
  public void deveObterPerfilComSucesso() throws Exception {
    when(usuarioServico.obterPerfil(anyLong()))
        .thenReturn(new DadoUsuarioDto(1L, "123", "Nome", "ALUNO", LocalDate.now()));
    mockMvc.perform(get("/usuario/perfil/1")).andExpect(status().isOk());
  }
}