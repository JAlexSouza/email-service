package br.com.kafkaserviceapi.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig<T> {
		
	private final String bootstrapAdress = "localhost:9092";	
	private Class<T> cls;
	
	public KafkaConfig(Class<T> cls){
		this.cls = cls;
	}
	
	@Bean
	public KafkaTemplate<String, T> kafkaTemplate() {
		return new KafkaTemplate<String, T>(producerFactory());
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, T> kafkaListenerContainerFactory() {			
		ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
			
		return factory;
	}

	private ProducerFactory<String, T> producerFactory() {
		@SuppressWarnings("resource")
		JsonSerializer<T> serializer = new JsonSerializer<T>();
		Map<String, Object> props = new HashMap<String, Object>();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapAdress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializer.getClass().getName()); 
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "shop-id");

		return new DefaultKafkaProducerFactory<>(props);
	}
	
	private ConsumerFactory<String, T> consumerFactory(){		
		JsonDeserializer<T> deserializer = new JsonDeserializer<T>(cls, false);
		Map<String, Object> props = new HashMap<String, Object>();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAdress);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);		
	}

}
