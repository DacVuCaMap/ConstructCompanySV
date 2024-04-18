package com.app.ConStructCompany.Repository;

import com.app.ConStructCompany.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    @Query("select t from Token t inner join Account a on t.account.id = a.id where a.id=:accId and" +
            "(t.expiration=false or t.revoked=false)")
    List<Token> findAccTokenExist(Long accId);
    Optional<Token> findTokenByTokenString(String token);
}
