package stream.serde;


import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import stream.entity.output.GraphqlJob;

import java.util.HashMap;
import java.util.Map;

public class GraphqlJobSerde implements Serde<GraphqlJob> {

    private final String JSON_POJO_CLASS = "JsonPOJOClass";

    @Override
    public Serializer<GraphqlJob> serializer() {
        Map<String, Object> serdeProps = new HashMap<>();
        final Serializer<GraphqlJob> serializer = new JsonPOJOSerializer<>();
        serdeProps.put(JSON_POJO_CLASS, GraphqlJob.class);
        serializer.configure(serdeProps, false);
        return serializer;
    }

    @Override
    public Deserializer<GraphqlJob> deserializer() {
        Map<String, Object> serdeProps = new HashMap<>();
        final Deserializer<GraphqlJob> deserializer = new JsonPOJODeserializer<>();
        serdeProps.put(JSON_POJO_CLASS, GraphqlJob.class);
        deserializer.configure(serdeProps, false);
        return deserializer;
    }
}