package com.E_CommerceBackendSystem.ecom.Config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kafkaStreamsConfig() {

	    Map<String, Object> props = new HashMap<>();

	    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-app");
	    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
	            "pkc-l7pr2.ap-south-1.aws.confluent.cloud:9092");

	    // ✅ REQUIRED for Confluent Cloud
	    props.put("security.protocol", "SASL_SSL");
	    props.put("sasl.mechanism", "PLAIN");
	    props.put("sasl.jaas.config",
	        "org.apache.kafka.common.security.plain.PlainLoginModule required " +
	        "username='FEUWFTJ3FYIFRW2V' password='cfltCvrM4xJW6VsS7fQ9d8Vm0uFOxpR4HKMXPuYY+KMq0ydFiYbV4Nn0xUsezVrQ';");

	    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonSerde.class);

	    return new KafkaStreamsConfiguration(props);
	}
}