package br.com.projeto.personalfocus.compartilhado.util.componente.dao.impl;

import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import br.com.projeto.personalfocus.compartilhado.util.componente.dao.IDaoUtilComponente;

/**
 * Implementação concreta da interface IDaoUtilComponente.
 * Utiliza o mecanismo KeyHolder do Spring para interceptar e recuperar chaves geradas
 * pelo banco de dados durante as operações de inserção.
 *
 * @author teteu
 */
@Component
public class DaoUtilComponenteImpl implements IDaoUtilComponente {

  /**
   * Executa a inserção usando o JdbcTemplate e PreparedStatementCreator fornecidos.
   * Um GeneratedKeyHolder é utilizado para capturar o ID gerado pelo banco de dados durante o processo de atualização.
   *
   * @param jdbcTemplate
   *        A instância do Spring JdbcTemplate usada para executar a consulta.
   * @param psc
   *        O PreparedStatementCreator que define a declaração SQL de inserção.
   * @return A chave primária gerada pelo banco de dados.
   */
  @Override
  public long insertRecuperandoId(JdbcTemplate jdbcTemplate, PreparedStatementCreator psc) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(psc, keyHolder);
    return Objects.requireNonNull(keyHolder.getKey()).longValue();
  }
}