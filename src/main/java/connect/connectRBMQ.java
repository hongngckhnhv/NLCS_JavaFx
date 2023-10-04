package connect;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
//

public class connectRBMQ {
    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        String uri = "amqps://lcxfyvaq:pthGBrIAMbs628vXigdmzq60B1-da86z@armadillo.rmq.cloudamqp.com/lcxfyvaq";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);

        try (Connection connection = factory.newConnection()) {
            // Kết nối thành công
            System.out.println("Kết nối thành công đến RabbitMQ trên CloudAMQP.");
        } catch (Exception e) {
            // Xử lý lỗi kết nối
            System.out.println("Lỗi kết nối đến RabbitMQ trên CloudAMQP: " + e.getMessage());
        }
    }
}
