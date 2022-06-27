package com.khinezaw;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) throws SQLException{
        BankingDao dao = BankingDaoFactory.getBankingDao();
        Scanner scanner = new Scanner(System.in);

        boolean run = true;
        System.out.println("Welcome to Virtual Bank");

        while (run) {
            /*System.out.println("\nPlease select from the following options: ");
            System.out.println("1)Login\n2)Create customer account\n3)Create employee account\n4)Exit");
            int input = getNumber();
            switch (input) {
                case 1 ->{// Login
                    -------
                    if(){
                        System.out.println("Login in as " + customer.getName() +"!");
                        customerLogin = true;
                    }
                    else System.out.println("Invalid login returning to previous screen!");
                    while(customerLogin){

                        System.out.println("\nChoose 1 of the following!");
                }
                /*-------------------------------------------------------------*/
            /*
                case 2 ->{// viewing my account
                    System.out.println("Viewing your account(s)!");
                    List<Account> myAccounts = dao.getCustomerAccount(customer.getId());
                    myAccounts.forEach(System.out::println);
                }
                /*-------------------------------------------------------------*/
            /*
                case 3 ->{// viewing my account
                    System.out.println("Viewing your account(s)!");
                    List<Account> myAccounts = dao.getCustomerAccount(customer.getId());
                    myAccounts.forEach(System.out::println);
                }
                /*-------------------------------------------------------------*/
            /*
                case 4 ->{// viewing my account
                    System.out.println("Viewing your account(s)!");
                    List<Account> myAccounts = dao.getCustomerAccount(customer.getId());
                    myAccounts.forEach(System.out::println);
                }
            } 
        */
            System.out.println("********************************");
            System.out.println("Select from the options below if you're a customer");
            System.out.println("********************************");
            System.out.println("1: Login");
            System.out.println("2: Apply for a new bank account");
            System.out.println("3: View the balance");
            System.out.println("4: Deposit into an account");
            System.out.println("5: Withdraw from an account");
            System.out.println("6: Transfer payment to an account");
            System.out.println("7: Accept payment from an account");
            System.out.println("8: Exist");
            System.out.println("********************************");
            System.out.println("Select from the options below if you're an employee");
            System.out.println("********************************");
            System.out.println("9: Approve or reject an account");
            System.out.println("10: Accept money from another account");
            System.out.println("11: View log of all transaction");
            System.out.println("12 : Exist");

            int input = getNumber();
            switch (input) {
                case 1: {
                    // Login so, so
                    System.out.print("Please Enter Your Username: ");
                    String username = scanner.next();
                    System.out.print("Please Enter Your Password: ");
                    Customer.setPassword(scanner.next());
                    String password = scanner.next();
                    System.out.println("Hello" + Customer.getUsername() +"!");
                  dao.getNameByUsername(username, password);
                    break;
                }
                case 2: {
                    // Apply worked
                    System.out.print("Enter User Name: ");
                    Customer.setName(scanner.next());
                    System.out.print("Enter Password: ");
                    Customer.setName(scanner.next());
                    System.out.print("Enter Name: ");
                    Customer.setName(scanner.next());
                    System.out.print("Enter Email: ");
                    Customer.setEmail(scanner.next());
                    System.out.print("Thank you for registering a new account!:");
                    dao.applyBanking(Customer);
                    break;
                }
                case 3: {
                    // View worked
                    System.out.print("Enter Account Number: ");
                    int id = scanner.nextInt();
                    int balance = dao.getBalanceById(id);
                    System.out.println("Your balance is " +balance);
                    break;
                }
                case 4: {
                    // Deposit
                    System.out.print("Enter Id: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter deposit amount: ");
                    int deposit = scanner.nextInt();;
                    Customer.setId(id);
                    int balance = dao.getBalanceById(id);
                    Customer.setBalance(deposit + balance);
                    dao.updateBanking(Customer);
                    System.out.println("You have deposited: ");
                    break;
                }
                case 5: {
                    // Withdraw
                    System.out.print("Enter Id: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter withdraw amount: ");
                    int withdraw = scanner.nextInt();;
                    Customer.setId(id);
                    int balance = dao.getBalanceById(id);
                    Customer.setBalance(balance - withdraw);
                    dao.updateBanking(Customer);
                    break;
                }
                case 6: {
                    // Transfer
                    System.out.print("Transfer: ");
                    int balance = scanner.nextInt();
                    dao.transferBanking(balance);
                    break;
                }
                case 7: {
                    // Accept
                    System.out.print("Accept: ");
                    int balance = scanner.nextInt();
                    dao.acceptBanking(balance);
                    break;
                }
                case 8: {
                    // exit
                    System.out.println("Thank you for being a customer");
                    System.out.println("Exiting...");
                    run = false;
                    break;
                }
                // Employee Account
                case 9: {
                    // Approve or Reject an account
                    System.out.print("Enter UserName: ");
                    String username = scanner.next();
                    System.out.print("Enter Password: ");
                    String password = scanner.next();
                    System.out.println("Your account is being reviewed");
                    break;
                }
                case 10: {
                    // Accept money from another account
                    System.out.print("Accept Amount: ");
                    int balance = scanner.nextInt();
                    dao.acceptBanking(balance);
                    break;
                }
                case 11: {
                    // View log of transactions
                    List<Banking> users = dao.viewBanking();
                    for(Banking user: users){
                        System.out.println(user);
                    }
                    break;
                }
                case 12: {
                    // exit
                    System.out.println("Thank you for being an employee");
                    System.out.println("Exiting...");
                    run = false;
                    break;
                }
                default:
                    System.out.println("Choose between 1-11");
            }
        }
    }
    public static int getNumber(){
        int num;
        /* string name = new string*/
        Scanner scanner = new Scanner(System.in);
        try{
            num = scanner.nextInt();
        } catch(InputMismatchException e){
            num = 0;
        }
        return num;
    }
}
