package com.khinezaw;

import com.khinezaw.Factory.BankingDaoFactory;
import com.khinezaw.Model.Customer;
import com.khinezaw.Model.Employee;
import com.khinezaw.Model.User;
import com.khinezaw.Model.Account;
import com.khinezaw.Model.MoneyTransfer;
import com.khinezaw.Model.Transaction;
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
                                            System.out.println("Viewing your account(s)!");
                                            List<Account> myAccounts = dao.getCustomerAccount(customer.getId());
                                            myAccounts.forEach(System.out::println);
                                        }
                                        case 2 -> {// apply  new account
                                            System.out.println("Apply a new account.");
                                            System.out.print("Please enter an account type you wish to make: ");
                                            Account_Type type = getAccountType();
                                            dao.makeAccount(customer, type);
                                        }


