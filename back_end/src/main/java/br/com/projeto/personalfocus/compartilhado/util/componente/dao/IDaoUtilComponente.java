package br.com.projeto.personalfocus.compartilhado.util.componente.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

public interface IDaoUtilComponente {
  long insertRecuperandoId(JdbcTemplate jdbcTemplate, PreparedStatementCreator psc);
}