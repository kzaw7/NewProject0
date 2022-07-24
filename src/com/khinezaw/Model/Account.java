package com.khinezaw.Model;

enum Status{
    pending, approved, declined
}
enum Account_Type{
    checking, saving
}

public class Account {
    private int accountNumber;
    private int customerID;
    private Account_Type accountType;
    private int amount;
    private Status status;

    public Account() {
    }

    public Account(int accountNumber, int customerID, Account_Type accountType, int amount, int limit, Status status) {
        this.accountNumber = accountNumber;
        this.customerID = customerID;
        this.accountType = accountType;
        this.amount = amount;
        this.status = status;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Account_Type getAccountType() {
        return accountType;
    }

    public void setAccountType(Account_Type accountType) {
        this.accountType = accountType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", customerID=" + customerID +
                ", accountType=" + accountType +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
