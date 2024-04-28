package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Account;
import com.app.ConStructCompany.Entity.Statistic;
import com.app.ConStructCompany.Repository.AccountRepository;
import com.app.ConStructCompany.Request.dto.AccountDTO;
import com.app.ConStructCompany.Request.dto.StatisticDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<Account> getAll(){
        return accountRepository.findAll();
    }
    public org.springframework.data.domain.Page<AccountDTO> findAccountByPageSize(PageRequest pageRequest){
        org.springframework.data.domain.Page<Account> accountsPage = accountRepository.findAllByIsDeletedFalse(pageRequest);
        return accountsPage.map(this::converToAccountDTO);
    }
    public AccountDTO converToAccountDTO(Account account){
        AccountDTO accountDTO = new AccountDTO();
        BeanUtils.copyProperties(account,accountDTO);
        return accountDTO;
    }
    public ResponseEntity<?> delAcc(Long id){
        try{
            accountRepository.deleteById(id);
            return ResponseEntity.ok().body("success");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
