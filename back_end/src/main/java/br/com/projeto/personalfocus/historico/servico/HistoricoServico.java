package br.com.projeto.personalfocus.historico.servico;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.personalfocus.historico.comando.FinalizarTreinoCmd;
import br.com.projeto.personalfocus.historico.dao.HistoricoDao;
import br.com.projeto.personalfocus.historico.dto.HistoricoCalendarioDto;

@Service
public class HistoricoServico {

  @Autowired
  private HistoricoDao historicoDao;

  public String finalizarTreino(@Valid FinalizarTreinoCmd cmd) {
    long id = historicoDao.registrarFinalizacao(cmd);
    return "Treino finalizado com sucesso! Registro: " + id;
  }

  public List<HistoricoCalendarioDto> obterCalendarioAluno(long idAluno) {
    return historicoDao.buscarHistoricoPorAluno(idAluno);
  }
}