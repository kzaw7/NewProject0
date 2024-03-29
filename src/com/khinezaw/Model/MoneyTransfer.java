package com.khinezaw.Model;

enum Money_Transfer{
    send, request
}
public class MoneyTransfer {
    private final int transferID;
    private final int originalAccount;
    private final Money_Transfer transferType;
    private final int amount;
    private final int otherPartyAccount;

    public MoneyTransfer(int transferID, int originalAccount, Money_Transfer transferType, int amount, int otherPartyAccount) {
        this.transferID = transferID;
        this.originalAccount = originalAccount;
        this.transferType = transferType;
        this.amount = amount;
        this.otherPartyAccount = otherPartyAccount;
    }

    @Override
    public String toString() {
        return "MoneyTransfer{" +
                "transferID=" + transferID +
                ", originalAccount=" + originalAccount +
                ", transferType=" + transferType +
                ", amount=" + amount +
                ", otherPartyAccount=" + otherPartyAccount +
                '}';
    }
}
