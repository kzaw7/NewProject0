package com.khinezaw.Repository;

import com.khinezaw.Model.Account;
import com.khinezaw.Model.Customer;
import com.khinezaw.Model.User;

import java.sql.SQLException;
import java.util.List;

public interface BankingDao {
 //employee

   public User getUser(int id, String password, boolean isEmployee) throws SQLException;
   void newAccount(String name, String email, String password) throws SQLException;
   void applyAccount(User customer) throws SQLException;
    void withdraw(User customer, int id, int amount, boolean bypass) throws SQLException;
    void deposit(User customer, int id, int amount, boolean bypass) throws SQLException;
    void transferMoney (User customer,int yourAccount, int otherAccount, int amount, Money_Transfer moneyTransfer) throws SQLException;
    List<Transaction> viewTransaction () throws SQLException;
    List<Account> getCustomerAccount (int customerID) throws SQLException;
    void accountApproval (Account account) throws SQLException;
    void accountDenial (Account account) throws SQLException;
}

