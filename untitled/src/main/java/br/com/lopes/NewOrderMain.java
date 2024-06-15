package br.com.lopes;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Cria o produtor para as mensagens, recebe as propriedades de um produtor como parametro
        var producer = new KafkaProducer<String, String>(properties());
        String value = "1123123,123123,123123212312";
        //Cria a mensagem a ser enviada, precisa passar o tópica, a chave e o valor
        var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER",value, value);
        //Método que envia a mensagem, sen devolve um future (observable), get() faz esperar essa resposta
        //
        producer.send(record, (data, ex) -> {
            if (ex != null){
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso" + data.topic() + ":::" + data.partition() + "/" + data.offset() + "/" + data.timestamp());
        }).get();
    }

    private static Properties properties() {

        //Definição das propriedades
        var properties = new Properties();
        //Indica onde o servbidor kafka está rodando, no caso localhost
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // Indica o serializador para as chaves, no caso, como a chave é String, usa-se stringSerializaer
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // Indica o serializador para os valores, no caso, como a chave é String, usa-se stringSerializaer
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }


}
