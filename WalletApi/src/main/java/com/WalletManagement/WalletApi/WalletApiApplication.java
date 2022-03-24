package com.WalletManagement.WalletApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.WalletManagement.WalletApi.kafka.KafkaFlinkReceiver.StramConsumrer;


@SpringBootApplication
public class WalletApiApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WalletApiApplication.class, args);

		String TOPIC="test";
		String server="localhost:9092";

		StramConsumrer(TOPIC,server);

	}

}
