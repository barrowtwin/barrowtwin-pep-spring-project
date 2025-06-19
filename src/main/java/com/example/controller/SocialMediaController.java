package com.example.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.hibernate.event.spi.ResolveNaturalIdEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount) {
        String username = newAccount.getUsername();
        String password = newAccount.getPassword();

        // Check the username and password requirements
        // If requirements not met, repsond with bad request
        if(username.isBlank() || password.length() < 4) {
            return new ResponseEntity<>(newAccount, HttpStatus.BAD_REQUEST);
        }
        // Else if username is already taken, respond with conflict
        else if(accountService.checkUsername(username)) {
            return new ResponseEntity<>(newAccount, HttpStatus.CONFLICT);
        }
        // Else, create the account
        else {
            return new ResponseEntity<>(accountService.addNewAccount(newAccount), HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        String username = account.getUsername();
        String password = account.getPassword();

        Account loggedInAccount = accountService.login(username,password);
        // if login was successful, repsond ok
        if(loggedInAccount != null) {
            return new ResponseEntity<>(loggedInAccount, HttpStatus.OK);
        }
        // else, username/password was incorrect and respond with unauthorized
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) throws ResourceNotFoundException {
        String messageText = message.getMessageText();
        int postedBy = message.getPostedBy();

        // Check the messageText and postedBy requirements
        // If requirements not met, repsond with client error
        if(messageText.isBlank() || messageText.length() > 255 || !accountService.checkId(postedBy)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Else, create the message
        else {
            return new ResponseEntity<>(messageService.addNewMessage(message), HttpStatus.OK);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
         return new ResponseEntity<>(messageService.getMessageList(), HttpStatus.OK);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable int id) {
        Message message = messageService.findMessage(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
