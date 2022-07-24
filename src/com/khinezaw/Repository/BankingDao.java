package com.khinezaw.Repository;

import com.khinezaw.Model.Customer;
import com.khinezaw.Model.User;

import java.sql.SQLException;
import java.util.List;

public interface BankingDao {
 //employee

   public User getUser(int id, String password, boolean isEmployee) throws SQLException;
   void newAccount(String name, String email, String password) throws SQLException;
   void createAccount(User customer) throws SQLException;
   String getNameByUsername(String username, String password) throws SQLException;
    int getBalanceById(int id) throws SQLException;
    void applyBanking(Customer banking) throws SQLException;
    void updateBanking(Customer banking) throws SQLException;

    void transferBanking(int balance) throws SQLException;

    void acceptBanking(int balance) throws SQLException;

    void acceptorrejectBanking(Customer banking) throws SQLException;

    void acceptmoneyBanking(int balance) throws SQLException;
    void depositBanking(Customer balance) throws SQLException;
    void withdrawBanking(Customer balance);
    List<Customer> viewBanking() throws SQLException;
}

