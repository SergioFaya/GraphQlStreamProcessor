package stream.serde;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import stream.entity.JobOffer;

import java.util.HashMap;
import java.util.Map;


public class JobOfferSerde implements Serde<JobOffer> {

    private final String JSON_POJO_CLASS = "JsonPOJOClass";

    @Override
    public Serializer<JobOffer> serializer() {
        Map<String, Object> serdeProps = new HashMap<>();

        final Serializer<JobOffer> serializer = new JsonPOJOSerializer<>();
        serdeProps.put(JSON_POJO_CLASS, JobOffer.class);
        serializer.configure(serdeProps, false);
        return serializer;
    }

    @Override
    public Deserializer<JobOffer> deserializer() {
        Map<String, Object> serdeProps = new HashMap<>();

        final Deserializer<JobOffer> deserializer = new JsonPOJODeserializer<>();
        serdeProps.put(JSON_POJO_CLASS, JobOffer.class);
        deserializer.configure(serdeProps, false);

        return deserializer;
    }
}
