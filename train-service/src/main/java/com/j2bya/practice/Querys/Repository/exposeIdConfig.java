package com.j2bya.practice.Querys.Repository;


  import com.j2bya.practice.Querys.Entry.trainShiftEntry;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
  import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
  import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class exposeIdConfig {
  @Bean
  public RepositoryRestConfigurer repositoryRestConfigurer() {

    return new RepositoryRestConfigurerAdapter() {
      @Override
      public void configureRepositoryRestConfiguration(

        RepositoryRestConfiguration config) {
        config.exposeIdsFor(trainShiftEntry.class);
      }
    };
  }
}
