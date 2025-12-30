package br.com.projeto.personalfocus.treino.controlador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import br.com.projeto.personalfocus.compartilhado.excecao.ManipuladorExcecoesGlobal;
import br.com.projeto.personalfocus.treino.comando.AtualizarExercicioCmd;
import br.com.projeto.personalfocus.treino.comando.AtualizarTreinoCmd;
import br.com.projeto.personalfocus.treino.comando.CadastrarTreinoCmd;
import br.com.projeto.personalfocus.treino.enumerador.DiaSemanaEnumerador;
import br.com.projeto.personalfocus.treino.servico.TreinoServico;

public class TreinoControladorTest {

  private MockMvc mockMvc;

  @Mock
  private TreinoServico treinoServico;

  @InjectMocks
  private TreinoControlador treinoControlador;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(treinoControlador).setControllerAdvice(new ManipuladorExcecoesGlobal())
        .build();
  }

  @Test
  public void deveCadastrarTreino() throws Exception {
    CadastrarTreinoCmd cmd = new CadastrarTreinoCmd();
    cmd.setIdAluno(1L);
    cmd.setDiaSemana(DiaSemanaEnumerador.SEGUNDA);
    cmd.setNomeTreino("Treino A");
    cmd.setListaExercicios(Collections.emptyList());

    when(treinoServico.cadastrarTreino(any())).thenReturn("Sucesso");

    mockMvc.perform(
        post("/treino/cadastrar").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cmd)))
        .andExpect(status().isOk());
  }

  @Test
  public void deveListarTreinos() throws Exception {
    when(treinoServico.listarTreinosDoAluno(anyLong())).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/treino/listar/1")).andExpect(status().isOk());
  }

  @Test
  public void deveListarExercicios() throws Exception {
    when(treinoServico.listarExerciciosDoTreino(anyLong())).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/treino/exercicios/1")).andExpect(status().isOk());
  }

  @Test
  public void deveAtualizarTreino() throws Exception {
    AtualizarTreinoCmd cmd = new AtualizarTreinoCmd();
    when(treinoServico.atualizarTreino(any())).thenReturn("Sucesso");

    mockMvc.perform(
        put("/treino/atualizar").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cmd)))
        .andExpect(status().isOk());
  }

  @Test
  public void deveAtualizarExercicio() throws Exception {
    AtualizarExercicioCmd cmd = new AtualizarExercicioCmd();
    when(treinoServico.atualizarExercicio(any())).thenReturn("Sucesso");

    mockMvc.perform(put("/treino/exercicio/atualizar").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(cmd))).andExpect(status().isOk());
  }

  @Test
  public void deveExcluirTreino() throws Exception {
    when(treinoServico.excluirTreino(anyLong())).thenReturn("Sucesso");

    mockMvc.perform(delete("/treino/excluir/1")).andExpect(status().isOk());
  }

  @Test
  public void deveExcluirExercicio() throws Exception {
    when(treinoServico.excluirExercicio(anyLong())).thenReturn("Sucesso");

    mockMvc.perform(delete("/treino/exercicio/excluir/1")).andExpect(status().isOk());
  }
}