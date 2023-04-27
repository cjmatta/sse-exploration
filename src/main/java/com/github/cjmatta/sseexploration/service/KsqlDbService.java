package com.github.cjmatta.sseexploration.service;

import io.confluent.ksql.api.client.Client;
import io.confluent.ksql.api.client.Row;
import io.confluent.ksql.api.client.StreamedQueryResult;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

@Service
public class KsqlDbService {

  private final Client ksqlDbClient;

  public KsqlDbService(Client ksqlDbClient) {
    this.ksqlDbClient = ksqlDbClient;
  }

  public Flux<ServerSentEvent<Row>> streamQuery(String query, Map<String, Object> queryProperties) {
    return Flux.create(sink -> {
      try {
        StreamedQueryResult queryResult = ksqlDbClient.streamQuery(query, queryProperties).get();
        KsqlDbRowSubscriber rowSubscriber = new KsqlDbRowSubscriber(sink);
        queryResult.subscribe(rowSubscriber);
      } catch (Exception e) {
        sink.error(e);
      }
    });
  }
}
