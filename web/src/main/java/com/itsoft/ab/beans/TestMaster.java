package com.itsoft.ab.beans;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 22.01.14
 * Time: 13:55
 */
@Service
public class TestMaster {
    //получаем ресурсы сервера для отправки сообщений
    @Resource(name="jms/JMSPool")
    private ConnectionFactory connectionFactory;

    @Resource(name="jms/MailTopic")
    private Destination destination;

    public void sendString(String enterString) {
        try {
            //создаем подключение
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage();
            //добавим в JMS сообщение собственное свойство в поле сообщения со свойствами
            message.setStringProperty("clientType", "web client");
            //добавляем payload в сообщение
            message.setText(enterString);
            //отправляем сообщение
            producer.send(message);
            System.out.println("message sent");
            //закрываем соединения
            session.close();
            connection.close();

        } catch (JMSException ex) {
            System.err.println("Sending message error");
            ex.printStackTrace();
        }
    }
}
