package com.sens.pot.model.repository;
import com.sens.pot.model.domain.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByEmail(String email);
}
