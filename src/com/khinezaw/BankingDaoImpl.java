package com.khinezaw;

import com.khinezaw.Factory.ConnectionFactory;
import com.khinezaw.Model.Customer;
import com.khinezaw.Model.Employee;
import com.khinezaw.Model.Account;
import com.khinezaw.Model.User;
import com.khinezaw.Repository.BankingDao;

import java.io.ObjectInputFilter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankingDaoImpl implements BankingDao {

    Connection connection;

    public BankingDaoImpl() {

        this.connection = ConnectionFactory.getConnection();
    }
    @Override//1
    public User getUser(int id, String password, boolean isEmployee) throws SQLException {
        User user;
        String column;
        String userType;
        if(isEmployee) {
            user = new Employee();
            column = "Employee";
            userType = "employee";
        }
        else {
            user = new Customer();
            column = "Customer";
            userType = "customer";
        }
        user.setId(id);
        user.setPassword(password);
        String sql = "SELECT * FROM " + column + "_ID = " + user.getId() + " AND Password = '" + user.getPassword() + "'";
        ResultSet resultSet = getQuery(sql);
        if(resultSet.next()){
            user.setId(resultSet.getInt(1));
            user.setPassword(resultSet.getString(2));
            user.setName(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            return user;
        }
        return null;
    }

    @Override//2
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
        Statement statement = connection.createStatement();
        if(preparedStatement.executeUpdate() > 0){
            System.out.println("New customer created!");
            sql = "Select * FROM customer WHERE Name = '" + customer.getName() +"'";
            ResultSet resultSet = getQuery(sql);
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

    @Override//3
    public void applyAccount(User customer) throws SQLException {
        String sql = "INSERT INTO account(Customer_ID, Account_Type, Amount, Status) VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customer.getId());
        preparedStatement.setInt(2, String.valueOf(type));
        preparedStatement.setInt(3, 0);
        preparedStatement.setInt(4, 0);
        preparedStatement.setString(5, String.valueOf(Status.pending));
        if(preparedStatement.executeUpdate() > 0) System.out.println("Account waiting for approval!");
        else System.out.println("Please try again later");
    }
    @Override//4
    public void withdraw(User customer, int id, int amount, boolean bypass) throws SQLException {
        if(amount < 0) System.out.println("Please enter an amount larger than 0!");
        else {
            //Account account = new Account();
            int pass;
            if(bypass) pass = 1;
            else pass = 0;
            String sql = "CALL withdraw(?, ?, ?, ?, ?)";
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, customer.getId());
            callableStatement.setInt(2, id);
            callableStatement.setInt(3, amount);
            callableStatement.setInt(4, pass);
            callableStatement.registerOutParameter(5, Types.VARCHAR);
            callableStatement.execute();
            System.out.println(callableStatement.getString(5));
        }
    @Override//5
    public void deposit(User customer, int id, int amount, boolean bypass) throws SQLException {
            if (amount < 0) System.out.println("Please enter an amount larger than 0!");
            else {
                int pass;
                if (bypass) pass = 1;
                else pass = 0;
                String sql = "CALL deposit(?, ?, ?, ?, ?)";
                CallableStatement callableStatement = connection.prepareCall(sql);
                callableStatement.setInt(1, customer.getId());
                callableStatement.setInt(2, id);
                callableStatement.setInt(3, amount);
                callableStatement.setInt(4, pass);
                callableStatement.registerOutParameter(5, Types.VARCHAR);
                callableStatement.execute();
                System.out.println(callableStatement.getString(5));
            }
            @Override//6
            public void transferMoney (User customer,int yourAccount, int otherAccount, int amount, Money_Transfer moneyTransfer) throws SQLException {
                String sql;
                sql = "SELECT * FROM account WHERE Account_Number = " + yourAccount + " AND Customer_ID = " + customer.getId();
                ResultSet resultSet = query.getQuery(sql);
                if (resultSet.next()) {
                    sql = "INSERT INTO money_transfer(Starting_Account, type, Amount, Ending_Account) VALUES (?,?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, yourAccount);
                    preparedStatement.setString(2, String.valueOf(moneyTransfer));
                    preparedStatement.setInt(3, amount);
                    preparedStatement.setInt(4, otherAccount);
                    if (preparedStatement.executeUpdate() > 0) System.out.println("Money transfer is now pending!");
                    else System.out.println("Something went wrong!");
                } else System.out.println("At least 1 of your account must be involved!");
            }
            @Override//7
            public List<Transaction> viewTransaction () throws SQLException {
                List<Transaction> transactionList = new ArrayList<>();
                String sql = "SELECT * FROM transaction";
                ResultSet resultSet = getQuery(sql);
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    int account = resultSet.getInt(2);
                    String transaction = resultSet.getString(3);
                    int amount = resultSet.getInt(4);
                    transactionList.add(new Transaction(id, account, transaction, amount));
                }
                return transactionList;
            }
            @Override//8
            public List<Account> getCustomerAccount ( int customerID) throws SQLException {
                List<Account> accounts = new ArrayList<>();
                String sql = "SELECT * FROM account WHERE Customer_ID = " + customerID;
                ResultSet resultSet = getQuery(sql);
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    Account_Type type = Account_Type.valueOf(resultSet.getString(3));
                    int amount = resultSet.getInt(4);
                    int limit = resultSet.getInt(5);
                    Status status = Status.valueOf(resultSet.getString(6));
                    accounts.add(new Account(id, customerID, type, amount, limit, status));
                }
                return accounts;
            }
            @Override//9
            public void accountApproval (Account account) throws SQLException {
                String sql;
                boolean accountExist = false;
                List<Account> accountList = new ArrayList<>();
                sql = "Select * FROM account WHERE Status = 'pending'";
                ResultSet resultSet = getQuery(sql);

                while (resultSet.next()) {
                    Account newAccount = new Account();
                    newAccount.setAccountNumber(resultSet.getInt(1));
                    newAccount.setStatus(Status.valueOf(resultSet.getString(6)));
                    accountList.add(newAccount);
                }
                for (Account value : accountList) {
                    if (account.getAccountNumber() == value.getAccountNumber()) {
                        if (account.getStatus() == Status.approved) {
                            if (account.getAccountType() == Account_Type.saving) {
                                System.out.print("Please enter a daily limit for the customer: ");
                                int limit = Main.getNumber();
                                sql = "UPDATE account SET Status = ? , Daily_Limit " + limit + " WHERE Account number";
                            } else sql = "UPDATE account SET Status = ? WHERE Account_Number = ?";
                        } else sql = "UPDATE account SET status = ?, Daily_Limit = 0 WHERE Account_Number = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, account.getStatus().toString());
                        preparedStatement.setInt(2, account.getAccountNumber());
                        if (preparedStatement.executeUpdate() > 0)
                            System.out.println("Account Number #" + account.getAccountNumber() + " have been " + account.getStatus() + "!");
                        accountExist = true;
                    }
                }
                if (!accountExist) System.out.println("Account not found in pending!");
            }
            @Override
            public void accountDenial (Account account) throws SQLException {

            }

    // case 3: view balance (worked)
   /* @Override
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

