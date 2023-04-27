package com.github.cjmatta.sseexploration.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.confluent.ksql.api.client.Row;
import io.confluent.ksql.api.client.ColumnType;


import java.io.IOException;
import java.util.stream.Collectors;

public class RowSerializer extends JsonSerializer<Row> {

  @Override
  public void serialize(Row row, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();

    for (int i = 0; i < row.columnNames().size(); i++) {
      ColumnType columnType = row.columnTypes().get(i);
      String columnName = row.columnNames().get(i);
      // not sure why, but I had to pass index + 1 to get the correct value
      Object value = serializeValue(row, i + 1, columnType);
      jsonGenerator.writeObjectField(columnName, value);
    }

    jsonGenerator.writeEndObject();
  }

  private Object serializeValue(Row row, Integer index, ColumnType columnType) {
   try {
     Object value;


     switch (columnType.getType()) {
       case INTEGER:
         value = row.getInteger(index);
         break;
       case BIGINT:
         value = row.getLong(index);
         break;
       case DOUBLE:
         value = row.getDouble(index);
         break;
       case BOOLEAN:
         value = row.getBoolean(index);
         break;
       case STRING:
         value = row.getString(index);
         break;
       case DECIMAL:
         value = row.getDecimal(index);
         break;
       case ARRAY:
       case MAP:
       case STRUCT:
       case BYTES:
       case TIME:
       case TIMESTAMP:
       case DATE:
         value = row.getValue(index);
         break;
       default:
         throw new UnsupportedOperationException("Unsupported column type: " + columnType);
     }

     return value;
   } catch (Exception e) {
     System.err.println("Error processing column: " + row.columnNames().get(index) + ", type: "+ columnType);
     e.printStackTrace();
     throw e;
   }
  }

}
