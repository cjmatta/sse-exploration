## ksqlDB v2 lightweight queries + Server Sent Events

This project is an exploration of using ksqlDB v2 lightweight push queries to query a stream of events and push the results to a web browser using Server Sent Events.

The project is based on the [ksqlDB v2 lightweight push queries](https://www.confluent.io/blog/push-queries-v2-with-ksqldb-scalable-sql-query-subscriptions/) and [Server Sent Events](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events) (SSE).

The data source is a [Wikipedia Event Stream](https://wikitech.wikimedia.org/wiki/Event_Platform/EventStreams), captured using the [Kafka Connect SSE Source connector](https://www.confluent.io/hub/cjmatta/kafka-connect-sse)

Configuration of the connector is as follows:

```
sse.uri: https://stream.wikimedia.org/v2/stream/recentchange
topic: wikipedia.parsed
transforms: extractData, parseJSON
transforms.extractData.type: org.apache.kafka.connect.transforms.ExtractField$Value
transforms.extractData.field: data
transforms.parseJSON.type: com.github.jcustenborder.kafka.connect.json.FromJson$Value
transforms.parseJSON.json.exclude.locations: "#/properties/log_params,#/properties/$schema,#/$schema"
transforms.parseJSON.json.schema.location: "Url"
transforms.parseJSON.json.schema.url: "https://raw.githubusercontent.com/wikimedia/mediawiki-event-schemas/master/jsonschema/mediawiki/recentchange/1.0.0.json"
transforms.parseJSON.json.schema.validation.enabled: "false"
producer.override.compression.type: "lz4"
producer.override.linger.ms: "10"
```

### Testing
Run bin/curl-query.sh to start a query and curl the SSE endpoint.  The query will return records in real-time.