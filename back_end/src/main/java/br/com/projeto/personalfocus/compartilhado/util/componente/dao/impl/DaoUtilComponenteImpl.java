package br.com.projeto.personalfocus.compartilhado.util.componente.dao.impl;

import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import br.com.projeto.personalfocus.compartilhado.util.componente.dao.IDaoUtilComponente;

@Component
public class DaoUtilComponenteImpl implements IDaoUtilComponente {

  @Override
  public long insertRecuperandoId(JdbcTemplate jdbcTemplate, PreparedStatementCreator psc) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(psc, keyHolder);
    return Objects.requireNonNull(keyHolder.getKey()).longValue();
  }
}