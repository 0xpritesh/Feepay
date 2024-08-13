package com.app.feepay.JavaClasses;

public class TransactionData {

    private String TransactionId,DateTime,Amount;

    public TransactionData() {
    }

    public TransactionData(String transactionId, String dateTime, String amount) {
        TransactionId = transactionId;
        DateTime = dateTime;
        Amount = amount;

    }


    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
