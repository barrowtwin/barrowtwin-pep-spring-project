package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;

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

    public Message findMessage(int id) throws ResourceNotFoundException {
        return messageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message with ID=" + id + " not found."));
    }
}
