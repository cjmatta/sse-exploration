package com.github.cjmatta.sseexploration.controller;

import com.github.cjmatta.sseexploration.service.KsqlDbService;
import io.confluent.ksql.api.client.Row;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/ksql")
public class KsqlDbSseController {

  private final KsqlDbService ksqlDbService;

  public KsqlDbSseController(KsqlDbService ksqlDbService) {
    this.ksqlDbService = ksqlDbService;
  }

  @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<Row>> streamQuery(@RequestParam String query) {
    Map<String, Object> queryProperties = new HashMap<>();
    queryProperties.put("auto.offset.reset", "earliest");
    queryProperties.put("ksql.query.push.v2.enabled", "true");
    return ksqlDbService.streamQuery(query, queryProperties);
  }
}
