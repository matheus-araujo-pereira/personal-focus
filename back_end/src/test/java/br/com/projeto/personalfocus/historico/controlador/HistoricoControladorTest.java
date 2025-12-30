package br.com.projeto.personalfocus.historico.controlador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.projeto.personalfocus.compartilhado.excecao.ManipuladorExcecoesGlobal;
import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;
import br.com.projeto.personalfocus.historico.servico.HistoricoServico;

public class HistoricoControladorTest {

  private MockMvc mockMvc;

  @Mock
  private HistoricoServico historicoServico;

  @InjectMocks
  private HistoricoControlador historicoControlador;

  private ObjectMapper objectMapper;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(historicoControlador).setControllerAdvice(new ManipuladorExcecoesGlobal())
        .build();

    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  @Test
  public void deveFinalizarTreino() throws Exception {
    FinalizarTreinoCmd cmd = new FinalizarTreinoCmd();
    cmd.setIdAluno(1L);
    cmd.setIdTreino(1L);

    when(historicoServico.finalizarTreino(any())).thenReturn("Sucesso");

    mockMvc.perform(post("/historico/finalizar-treino").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(cmd))).andExpect(status().isOk());
  }

  @Test
  public void deveObterCalendario() throws Exception {
    when(historicoServico.obterCalendarioAluno(anyLong())).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/historico/calendario/1")).andExpect(status().isOk());
  }
}