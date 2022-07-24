package com.khinezaw.Model;

enum Transaction_Type{
    deposit, withdraw
}

public class Transaction {
    int id;
    int accountNumber;
    String transactionType;
    int amount;

    public Transaction(int id, int accountNumber, String transactionType, int amount) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountNum=" + accountNumber +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                '}';
    }
}
