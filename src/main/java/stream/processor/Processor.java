package stream.processor;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.state.KeyValueStore;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import stream.config.CustomConfig;
import stream.entity.JobOffer;
import stream.entity.Location;
import stream.entity.Salary;
import stream.entity.output.GraphqlJob;
import stream.entity.output.GraphqlNameItem;
import stream.serde.JobOfferSerde;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
class Processor {
    // https://abhishek1987.medium.com/kafka-streams-interactive-queries-9a05ff92d75a

    @Autowired
    private CustomConfig customConfig;

    @Bean
    public Consumer<KStream<String, GraphqlJob>> process() {

        return input -> input
                .filter((key,value) -> value.getIsPublished())
                .map((key, value) -> new KeyValue<String, JobOffer>(value.getId() + "", createJobOffer(value)))
                .toTable(Named.as(customConfig.tableName),
                        Materialized.<String, JobOffer, KeyValueStore<Bytes, byte[]>>as(
                                customConfig.tableName)
                                .withKeySerde(Serdes.String())
                                .withValueSerde(new JobOfferSerde()));

    }

    public JobOffer createJobOffer(GraphqlJob graphqlJob) {
        boolean hasSalary = false;
        return JobOffer.builder()
                .urlLink(graphqlJob.getApplyUrl())
                .company(graphqlJob.getCompany().getName())
                .hasSalary(hasSalary)
                .salary(null)
                .description(cleanDescription(graphqlJob.getDescription()))
                .title(graphqlJob.getTitle())
                .location(createLocation(graphqlJob))
                .publishedAt(graphqlJob.getPostedAt())
                .remote(false)
                .tags(createTags(graphqlJob))
                .companyLogoUrl(graphqlJob.getCompany().getLogoUrl())
                .build();
    }

    public String cleanDescription(String description) {
        return Jsoup.parse(description).text();
    }

    public Location createLocation(GraphqlJob graphqlJob) {
        if(!graphqlJob.getCities().isEmpty()) {
            String city = graphqlJob.getCities().get(0).getName();
            String country = graphqlJob.getCities().get(0).getCountry().getName();
            return Location.builder()
                    .city(city)
                    .country(country)
                    .build();
        }
        return Location.builder().build();
    }

    public List<String> createTags(GraphqlJob graphqlJob) {
        String description = graphqlJob.getDescription();
        String upperDescription = description.toUpperCase();

        List<String> tags = customConfig.processorTags
                .stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        List<String> myTags = new ArrayList<String>(
                graphqlJob
                        .getTags()
                        .stream()
                        .map(GraphqlNameItem::getName)
                        .map(String::toUpperCase)
                        .collect(Collectors.toList())
        );

        for (String tag : tags) {
            if (upperDescription.contains(tag)) {
                if (!myTags.contains(tag)) {
                    myTags.add(tag);
                }
            }
        }
        return myTags;
    }

}