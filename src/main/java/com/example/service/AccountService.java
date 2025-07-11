package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;
import com.example.exception.ResourceNotFoundException;

@Service
public class AccountService {

    private AccountRepository<Account, Integer> accountRepository;

    @Autowired
    public AccountService(AccountRepository<Account, Integer> accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccountList() {
        return (List<Account>) accountRepository.findAll();
    }

    // Couldn't use this method due to it creating a 404 status code instead of 400 status code
    public Account findAccount(int id) throws ResourceNotFoundException {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with ID=" + id + " not found."));
    }

    public boolean checkId(int id) {
        List<Account> accounts = getAccountList();
        for(Account acc: accounts) {
            if(acc.getAccountId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean checkUsername(String username) {
        boolean result = false;
        List<Account> accounts = getAccountList();
        for(Account acc: accounts) {
            if(acc.getUsername().equals(username)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public Account login(String username, String password) {
        Account account = null;
        List<Account> accounts = getAccountList();
        for(Account acc: accounts) {
            if(acc.getUsername().equals(username)) {
                if(acc.getPassword().equals(password)) {
                    account = acc;
                    break;
                }
                break;
            }
        }
        return account;
    }

    public Account addNewAccount(Account account) {
        return accountRepository.save(account);
    }
}
