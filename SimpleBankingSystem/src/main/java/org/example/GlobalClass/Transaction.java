package org.example.GlobalClass;

import java.util.Date;

public class Transaction {
    private int transactionID;
    private Date depositDate;
    private Date withdrawDate;
    private String accNumber;
    private float amount;


    public Transaction(int transactionID, String accNumber, Date depositDate, Date withdrawDate, float amount) {
        this.transactionID =  transactionID;
        this.accNumber = accNumber;
        this.depositDate = depositDate;
        this.withdrawDate = withdrawDate;
        this.amount = amount;
    }

    public void setAccNumber(String accNumber){
        this.accNumber = accNumber;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setDepositDate(Date depositDate){
        this.depositDate = depositDate;
    }

    public java.sql.Date getDepositDate() {
        return (java.sql.Date) depositDate;
    }

    public void setWithdrawDate(Date withdrawDate){
        this.withdrawDate = withdrawDate;
    }

    public java.sql.Date getWithdrawDate() {
        return (java.sql.Date) withdrawDate;
    }

    public void setAmount(float amount){
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

}
