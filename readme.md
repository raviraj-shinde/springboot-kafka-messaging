# How to run ?

1. Download Kafka 3.9.0 requires JAVA_HOME
2. Run Zookeeper and kafka-server(Broker)
3. Run cab-driver and cab-user Project
4. Hit `"http://localhost:8090/location"`  with Put request to start location update.
5. Watch cab-user: console for location updates comming from **cab-driver to - Kafka Broker - to cab-user.**

<hr style="height: 1px; background-color: black;"> 

## Dependency required for the project

``` 
1. spring-starter-web
2. spring-kafka
```

<hr/>

# Required Kafka properties for producer
```
1. spring.kafka.producer.bootstrap-servers=localhost:9092
2. spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
3. spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer [JSON etc]
```

<hr style="height: 1px; background-color: black;"> 

# Required Kafka properties for consumer
```
1. spring.kafka.consumer.bootstrap-servers=localhost:9092
2. spring.kafka.consumer.key-serializer=org.apache.kafka.common.serialization.StringDeserializer
3. spring.kafka.consumer.value-serializer=org.apache.kafka.common.serialization.StringDeserializer
4. spring.kafka.consumer.group-id=user-group
```

<hr/>

## Producer main Logic
```java
    @Service
    @RequiredArgsConstructor
    public class CabLocationService {
        private final KafkaTemplate<String, Object> kafkaTemplate;

        public void updateLocation(String location){
            kafkaTemplate.send(AppConstant.CAB_LOCATION, location);
        }
    }
```

- Here kafkaTemplate.send(Topic, Data);
- Needs DI of KafkaTemplate<String, Object> kafkaTemplate Bean;

<hr style="height: 1px; background-color: black;"> 

## Consumer main Logic 
```java
   @KafkaListener(topics = "cab-location", groupId = "user-group")
    public void cabLocation(String location){
        System.out.println(location);
    } 
```

<hr/>

# Kafka CMD

## Commands which I actually run
1. Updated log paths in configs
    - You edited config\zookeeper.properties and config\server.properties to point to your custom log directories.
2. Started ZooKeeper
    - `.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties`
    - ZooKeeper must start before Kafka.
3. Started Kafka Broker
    - `.\bin\windows\kafka-server-start.bat .\config\server.properties`
    - This launches the Kafka server on port ** 9092 **
4. Created a Topic
    - `.\bin\windows\kafka-topics.bat --create --topic test-topic --bootstrap-server localhost:9092`
    - This makes the topic where messages will be stored.
5. Started Console Producer
    - `.\bin\windows\kafka-console-producer.bat --topic test-topic --bootstrap-server localhost:9092`
    - Used to send messages into Kafka.
6. Started Console Consumer
    - `.\bin\windows\kafka-console-consumer.bat --topic test-topic --bootstrap-server localhost:9092 --from-beginning`
    - Used to read all messages from the topic.

<hr style="height: 1px; background-color: black;"> 

| Command | Purpose |
| :--- | :--- |
| `kafka-zookeeper-start.sh` | **Starts the ZooKeeper service** for Kafka coordination. |
| `kafka-server-start.sh` | **Starts the Kafka broker** (server) instance. |
| `kafka-topics.sh` | **Manages topics** (create, list, describe, delete, alter). |
| `kafka-console-producer.sh` | Allows **sending messages** |
| `kafka-console-consumer.sh` | Allows **reading messages** |
