package br.com.projeto.personalfocus.compartilhado.util.componente.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

/**
 * Interface utilitária para componentes de Objeto de Acesso a Dados (DAO).
 * Define contratos para operações comuns de banco de dados, focando especificamente em
 * cenários onde é necessário recuperar chaves geradas automaticamente.
 *
 * @author teteu
 */
public interface IDaoUtilComponente {

  /**
   * Executa uma operação de inserção e recupera a chave primária gerada.
   * Útil para tabelas com colunas de incremento automático onde o ID é necessário imediatamente após a inserção.
   *
   * @param jdbcTemplate
   *        A instância do Spring JdbcTemplate usada para executar a consulta.
   * @param psc
   *        O PreparedStatementCreator que define a declaração SQL de inserção.
   * @return O ID da chave primária gerada como um valor long.
   */
  long insertRecuperandoId(JdbcTemplate jdbcTemplate, PreparedStatementCreator psc);
}