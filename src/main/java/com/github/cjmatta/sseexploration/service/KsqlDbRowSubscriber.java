package com.github.cjmatta.sseexploration.service;

import io.confluent.ksql.api.client.Row;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.FluxSink;

public class KsqlDbRowSubscriber implements Subscriber<Row> {
  private final FluxSink<ServerSentEvent<Row>> sink;
  private Subscription subscription;

  public KsqlDbRowSubscriber(FluxSink<ServerSentEvent<Row>> sink) {
    this.sink = sink;
  }

  @Override
  public void onSubscribe(Subscription subscription) {
    this.subscription = subscription;
    this.subscription.request(1);
  }

  @Override
  public void onNext(Row row) {
    sink.next(ServerSentEvent.builder(row).build());
    subscription.request(1);
  }

  @Override
  public void onError(Throwable throwable) {
    sink.error(throwable);
  }

  @Override
  public void onComplete() {
    sink.complete();
  }
}
