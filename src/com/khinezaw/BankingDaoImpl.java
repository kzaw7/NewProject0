package com.khinezaw;

import jdk.jshell.Snippet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankingDaoImpl implements BankingDao {

    Connection connection;

    public BankingDaoImpl() {

        this.connection = ConnectionFactory.getConnection();
    }
    @Override
    public User getUser(int id, String password, boolean isEmployee) throws SQLException {
        User user;
        String column;
        String userType;
        if(isEmployee) {
            user = new Employee();
            column = "Employee";
        }
        else {
            user = new Customer();
            column = "Customer";
            userType = "customer";
        }
        user.setId(id);
        user.setPassword(password);
        String sql = "SELECT * FROM " + column + "_ID = " + user.getId() + " AND Password = '" + user.getPassword() + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            user.setId(resultSet.getInt(1));
            user.setPassword(resultSet.getString(2));
            user.setName(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            return user;
        }
        return null;
    }
    @Override
    public void newAccount(String name, String email, String password) throws SQLException {
        String sql;
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        sql = "INSERT INTO customer(Name, Email, Password) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getEmail());
        preparedStatement.setString(3, customer.getPassword());
        if(preparedStatement.executeUpdate() > 0){
            System.out.println("New customer created!");
            sql = "Select * FROM customer WHERE Name = '" + customer.getName() +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                int id = resultSet.getInt(1);
                System.out.println("Your customer ID: " + id);
                customer.setId(id);
                createAccount(customer);
                System.out.println("Please wait until an employee approve of your bank account!");
            }
        }
        else System.out.println("Customer name/email already exist");
    }
    @Override
    public void createAccount(User customer) throws SQLException {
        String sql = "INSERT INTO account(Customer_ID, Account_Type, Amount, Daily_Limit, Status) VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customer.getId());
        preparedStatement.setInt(2, 0);
        preparedStatement.setInt(3, 0);
        //Add account status if possible
        if(preparedStatement.executeUpdate() > 0) System.out.println("Account waiting for approval!");
        else System.out.println("Please try again later");
    }

    //Case 1:so, so
    @Override
    public String getNameByUsername(String username, String password) throws SQLException {
        String sql = "select username from banking where name=" + username;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            username = resultSet.getString(1);
        }
        return username;
    }

    //case 2: apply banking (worked)
    @Override
    public void applyBanking(Customer banking) throws SQLException {
        String sql = "INSERT INTO BANKING (username, password, name, email) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, banking.getUsername());
        preparedStatement.setString(2, banking.getPassword());
        preparedStatement.setString(3, banking.getName());
        preparedStatement.setString(4, banking.getEmail());
        if (preparedStatement.executeUpdate() > 0) System.out.println("Thank you for registering a new account!");
        else System.out.println("Oops! something went wrong");
    }

    // case 3: view balance (worked)
    @Override
    public int getBalanceById(int id) throws SQLException {
        int amount = 0;
        String sql = "select balance from banking where id=" + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            amount = resultSet.getInt(1);
        }
        return amount;
    }

    //Case: 4 deposit worked
    @Override
        public void depositBanking(Customer balance) throws SQLException {
        String sql = "update banking set balance = " + balance + " where id =" + balance.getId();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        System.out.println("You have deposited: ");

    }

    @Override  //Case 4: deposit worked
    public void updateBanking(Customer banking) {
        String sql = "update banking set balance = ? where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, banking.getBalance());
            preparedStatement.setInt(2, banking.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Case: 5 withdraw
    @Override
    public void withdrawBanking(Customer balance) {
        String sql = "update banking set balance = ? where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, balance.getBalance());
            preparedStatement.setInt(2, balance.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transferBanking(int balance) throws SQLException {
        String sql = "insert into banking (balance) values (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("You have withdraw: ");
        else
            System.out.println("Withdrawal Failed: ");
    }

    @Override
    public void acceptBanking(int balance) throws SQLException {
        String sql = "insert into banking (balance) values (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("You have accepted: ");
        else
            System.out.println("Acceptance Failed: ");
    }

    @Override
    public void acceptorrejectBanking(Customer banking) throws SQLException {
        String sql = "insert into banking (username, password) values (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, banking.getUsername());
        preparedStatement.setString(2, banking.getPassword());
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("Thank you, your account is being reviewed");
        else
            System.out.println("Sorry, you can't proceed at the moment");

    }

    @Override
    public void acceptmoneyBanking(int balance) throws SQLException {
        String sql = "insert into banking (balance) values (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("You are accepted: ");
        else
            System.out.println("Acceptance failed: ");
    }

    //Case 11: view log
    @Override
    public List<Customer> viewBanking() throws SQLException {
        List<Customer> users = new ArrayList<>();
        String sql = "select * from banking";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String username = resultSet.getString(2);
            String password = resultSet.getString(3);
            String name = resultSet.getString(4);
            int balance = resultSet.getInt(5);
            User user = new Customer(id, username, password, name, balance);
            users.add((Customer) user);
        }
        return users;
    }
}