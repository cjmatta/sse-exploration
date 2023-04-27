package com.github.cjmatta.sseexploration.config;

import io.confluent.ksql.api.client.Client;
import io.confluent.ksql.api.client.ClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KsqlDbConfiguration {

  private final KsqlDbProperties ksqlDbProperties;

  public KsqlDbConfiguration(KsqlDbProperties ksqlDbProperties) {
    this.ksqlDbProperties = ksqlDbProperties;
  }

  @Bean
  public Client ksqlDbClient() {
    ClientOptions options = ClientOptions.create()
      .setHost(ksqlDbProperties.getHost())
      .setPort(ksqlDbProperties.getPort());
    return Client.create(options);
  }
}

