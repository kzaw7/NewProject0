package com.khinezaw;

import com.khinezaw.Factory.BankingDaoFactory;
import com.khinezaw.Model.*;
import com.khinezaw.Model.Account;
import com.khinezaw.Repository.BankingDao;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) throws SQLException{
        BankingDao dao = BankingDaoFactory.getBankingDao();
        Scanner scanner = new Scanner(System.in);

        boolean run = true;
        System.out.println("Welcome to Java Virtual Bank");

        while (run) {
            System.out.println("\nPlease select from the following options: ");
            System.out.println("1: Login");
            System.out.println("2: Create customer account");
            System.out.println("3: Create employee account");
            System.out.println("4: Exit");
            int input = getNumber();
            switch (input) {
                case 1 ->{// Login
                    boolean login = true;
                    while (login) {
                        System.out.println("\nPlease select from the following options:");
                        System.out.println("1: Customer");
                        System.out.println("2: Employee");
                        input = getNumber();
                        switch (input) {
                            case 1 -> {
                                System.out.println("Login to Customer Account");
                                boolean customerLogin = false;
                                User customer;
                                System.out.print("Please enter customer id: ");
                                int customerId = getNumber();
                                System.out.print("Please enter the password: ");
                                String password = scanner.next();
                                customer = dao.getUser(customerId, password, false);
                                if (customer != null) {
                                    System.out.println("Login in as " + customer.getName() + "!");
                                    customerLogin = true;
                                } else System.out.println("Invalid login returning to previous screen!");
                                while (customerLogin) {


                                    System.out.println("1: View Account");
                                    System.out.println("2: Apply for a new bank account");
                                    System.out.println("3: Withdraw from an account");
                                    System.out.println("4: Deposit into an account");
                                    System.out.println("5: Transfer money to an account");
                                    System.out.println("6: View Money Transferred ");
                                    System.out.println("7: Exist");

                                    input = getNumber();
                                    switch (input) {
                                        case 1 -> {// viewing my account
                                            System.out.println("View your account(s)!");
                                            List<Account> myAccounts = dao.getCustomerAccount(customer.getId());
                                            myAccounts.forEach(System.out::println);
                                        }
                                        case 2 -> {// apply  new account
                                            System.out.println("Apply a new account.");
                                            System.out.print("Please enter an account type you wish to make: ");
                                            Account_Type type = getAccountType();
                                            dao.makeAccount(customer, type);
                                        }
                                        case 3 -> {// withdraw money
                                            System.out.print("Enter the account number you want to withdraw to: ");
                                            int id = getNumber();
                                            System.out.print("Enter the amount you want to withdraw: ");
                                            int withdrawAmount = getNumber();
                                            dao.withdraw(customer, id, withdrawAmount, false);
                                        }
                                        case 4 -> {//deposit
                                            System.out.print("Enter the account number you want to deposit to: ");
                                            int id = getNumber();
                                            System.out.print("Enter the amount you want to deposit: ");
                                            int depositAmount = getNumber();
                                            dao.deposit(customer, id, depositAmount, false);
                                        }
                                        case 5 -> {// transfer money
                                            System.out.print("Enter the account you like to send/request money from: ");
                                            int account1 = getNumber();
                                            System.out.print("Enter the account that will be part of this transaction: ");
                                            int account2 = getNumber();
                                            System.out.print("How much money is involved: ");
                                            int amount = getNumber();
                                            System.out.print("Are you sending or receiving money: ");
                                            Money_Transfer moneyTransfer = getTransferType();
                                            dao.transferMoney(customer, account1, account2, amount, moneyTransfer);
                                        }
                                        case 6 -> {//view money transfer you are involved in
                                            System.out.println("Viewing money transfer list!");
                                            System.out.print("Which account do you want to see pending money transfer for: ");
                                            int account = getNumber();
                                            List<MoneyTransfer> moneyTransferList = dao.viewMoneyTransferList(customer, account);
                                            if(moneyTransferList.size() != 0) moneyTransferList.forEach(System.out::println);
                                            else System.out.println("There is no money transfer associated with this account or this account does not belong to you!");
                                        }
                                        case 7-> {//logout
                                            customerLogin = false;
                                            System.out.println("Logging out of employee");
                                        }
                                        default -> System.out.println("Please choose from the following!!!");

                                    }
                                    case 2 -> {
                                        System.out.println("Employee Login!");
                                        boolean employeeLogin = false;
                                        User employee;
                                        System.out.print("Please enter employee id: ");
                                        int employeeId = getNumber();
                                        System.out.print("Please enter the password: ");
                                        String password = scanner.next();
                                        employee = dao.getUser(employeeId, password, true);
                                        if(employee != null){
                                            System.out.println("Login in as " + employee.getName() +"!");
                                            employeeLogin = true;
                                        }
                                        else System.out.println("Invalid login returning to previous screen!");

                                        while(employeeLogin) {
                                            System.out.println("\nChoose 1 of the following!");
                                            System.out.println("1)View account pending list");
                                            System.out.println("2)List all customer");
                                            System.out.println("3)List all account");
                                            System.out.println("4)List all account own by a customer");
                                            System.out.println("5)View transaction");
                                            System.out.println("6)Approving new account");
                                            System.out.println("7)Resetting daily limit");
                                            System.out.println("8)Delete transaction history");
                                            System.out.println("9)Log out");

                                            input = getNumber();
                                            switch (input) {
                                                case 1 -> {//view pending account list
                                                    System.out.println("Listing accounts need for approval!");
                                                    List<Account> pendingAccounts = dao.viewPending();
                                                    if (pendingAccounts.size() != 0)
                                                        pendingAccounts.forEach(System.out::println);
                                                    else System.out.println("There is no pending accounts!");
                                                }
                                                case 2 -> {// approve new account
                                                    Account account = new Account();
                                                    System.out.print("Enter account number you trying to approve/deny: ");
                                                    int id = getNumber();
                                                    System.out.print("Are you approving(true) or denying(false) the account: ");
                                                    boolean approve = getBoolean();
                                                    account.setAccountNumber(id);
                                                    account.setStatus(approve);
                                                    dao.accountApproval(account);
                                                }
                                                case 3 -> {// deny new account
                                                    Account account = new Account();
                                                    System.out.print("Enter account number you trying to approve/deny: ");
                                                    int id = getNumber();
                                                    System.out.print("Are you approving(true) or denying(false) the account: ");
                                                    boolean deny = getBoolean();
                                                    account.setAccountNumber(id);
                                                    account.setStatus(false);
                                                    dao.accountApproval(account);
                                                }
                                                case 4 -> {
                                                    employeeLogin = false;
                                                    System.out.println("Logging out of employee");
                                                }
                                                default -> System.out.println("Please choose from the following!!!");
                                            }
                                        }

                                        public static int getNumber(){
                                            int num;
                                            Scanner scanner = new Scanner(System.in);
                                            try{
                                                num = scanner.nextInt();
                                            } catch(InputMismatchException ignore){
                                                num = 0;
                                            }
                                            return num;
                                        }
                                        public static boolean getBoolean(){
                                            boolean falseInput = true;
                                            boolean bool = false;
                                            do {
                                                try{
                                                    Scanner scanner = new Scanner(System.in);
                                                    boolean newBool = scanner.nextBoolean();
                                                    falseInput = false;
                                                    bool = newBool;
                                                }catch(InputMismatchException e) {
                                                    System.out.println("Enter a boolean");
                                                }
                                            } while(falseInput);
                                            return bool;
                                        }
                                        public static Account_Type getAccountType(){
                                            do{
                                                try{
                                                    Scanner scanner = new Scanner(System.in);
                                                    return Account_Type.valueOf(scanner.next());
                                                } catch(Exception ignore){
                                                    System.out.print("Please enter either 'checking' or 'saving': ");
                                                }
                                            } while(true);
                                        }
                                        public static Money_Transfer getTransferType(){
                                            do{
                                                try{
                                                    Scanner scanner = new Scanner(System.in);
                                                    return Money_Transfer.valueOf(scanner.next());
                                                } catch(Exception ignore){
                                                    System.out.print("Please enter either 'send' or 'request': ");
                                                }
                                            } while(true);
                                        }
                                    }

