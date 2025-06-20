package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

@Service
public class MessageService {

    private MessageRepository<Message, Integer> messageRepository;

    @Autowired
    public MessageService(MessageRepository<Message, Integer> messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessageList() {
        return (List<Message>) messageRepository.findAll();
    }

    public Message findMessage(int id) {
        List<Message> messages = getMessageList();
        for(Message m : messages) {
            if(m.getMessageId() == id) {
                return m;
            }
        }
        return null;
    }

    public Message addNewMessage(Message message) {
        return messageRepository.save(message);
    }

    public Integer deleteMessage(int messageId) {
        if(findMessage(messageId) != null) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        else {
            return null;
        }
    }

    public void updateMessage(String messageText, Message message) {
        message.setMessageText(messageText);
        messageRepository.save(message);
    }

    public List<Message> getMessagesByUser(int id) {
        List<Message> userMessages = new ArrayList<>();
        List<Message> allMessages = getMessageList();
        for(Message m : allMessages) {
            if(m.getPostedBy() == id) {
                userMessages.add(m);
            }
        }
        return userMessages;
    }
}
