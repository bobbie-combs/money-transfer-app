package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository <Account,Integer> {


    Account findByUserId(@Param("user_Id") int userId);

}
