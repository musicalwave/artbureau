package com.itsoft.ab.beans;

import com.itsoft.ab.model.MailModel;
import com.itsoft.ab.persistence.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.List;

/**
 *
 * @author doraemon
 */
@Service
public class MailMaster {
    private MailMaster() {
    }

    @Autowired
    private UsersMapper usersMapper;

    //получаем ресурсы сервера для отправки сообщений
    @Resource(name="jms/JMSPool")
    private ConnectionFactory connectionFactory;

    @Resource(name="jms/MailTopic")
    private Destination destination;

    public void sendMails(List<MailModel> mails){
        try {
            //создаем подключение
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            for(MailModel mail:mails){
                MapMessage message = session.createMapMessage();
                //Добавляем пары ключ/значение
                message.setString("toMail", mail.getToMail());
                message.setString("title", mail.getTitle());
                message.setString("text", mail.getText());

                //отправляем сообщение
                producer.send(message);
            }

            //закрываем соединения
            session.close();
            connection.close();

        } catch (JMSException ex) {
            System.err.println("Sending message error");
            ex.printStackTrace();
        }
    }
}