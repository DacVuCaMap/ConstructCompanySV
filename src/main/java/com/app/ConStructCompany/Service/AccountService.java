package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Account;
import com.app.ConStructCompany.Repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<Account> getAll(){
        return accountRepository.findAll();
    }
}
