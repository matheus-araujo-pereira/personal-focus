package br.com.projeto.personalfocus;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PersonalFocusApplicationTest {

  @Test
  public void deveInstanciarAplicacaoParaCobrirConstrutor() {
    PersonalFocusApplication application = new PersonalFocusApplication();
    Assert.assertNotNull(application);
  }

  @Test
  public void deveExecutarMainComSucesso() {
    System.setProperty("spring.devtools.restart.enabled", "false");
    String[] args = { "--spring.main.web-application-type=none",
        "--spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "--spring.datasource.driver-class-name=org.h2.Driver", "--spring.datasource.username=sa",
        "--spring.datasource.password=123", "--spring.jpa.database=H2",
        "--spring.jpa.database-platform=org.hibernate.dialect.H2Dialect", "--spring.jpa.hibernate.ddl-auto=create-drop",
        "--spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false" };
    PersonalFocusApplication.main(args);
  }
}