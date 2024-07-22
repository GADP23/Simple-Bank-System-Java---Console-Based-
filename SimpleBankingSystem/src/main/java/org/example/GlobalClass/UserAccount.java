package org.example.GlobalClass;

import java.util.Date;

public class UserAccount {
    private String uName;
    private String pWord;
    private String newPassword;
    private String confirmNewPassword;
    private String accNum;
    private String accRole;
    private double remainingBalance;
    //private
    private Date depositDate;
    private Date withdrawDate;
    private float amount;

    private String fName;
    private String mName;
    private String lName;
    private String email;

    public UserAccount(String uName, String pWord, String accNum, String accRole, Date depositDate, Date withdrawDate, float amount){
        this.uName = uName;
        this.pWord = pWord;
        this.accNum = accNum;
        this.accRole = accRole;
        this.depositDate = depositDate;
        this.withdrawDate = withdrawDate;
        this.amount = amount;
    }

    public void setUsername(String uName){
        this.uName = uName;
    }

    public String getUsername(){
        return uName;
    }

    public void setPassword(String pWord){
        this.pWord = pWord;
    }

    public String getPassword(){
        return pWord;
    }

    public void setNewPassword(String newPassword){
        this.newPassword = newPassword;
    }

    public String getNewPassword(){
        return newPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword){
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getConfirmNewPassword(){
        return confirmNewPassword;
    }

    public void setAccNum(String accNum){
        this.accNum = accNum;
    }

    public String getAccNum(){
        return accNum;
    }

    public void setAccRole(String accRole){
        this.accRole = accRole;
    }

    public String getAccRole(){
        return accRole;
    }


    public void setRemainingBalance(double remainingBalance){
        this.remainingBalance = remainingBalance;
    }

    public double getRemainingBalance(){
        return remainingBalance;
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

    public void setfName(String fName){
        this.fName = fName;
    }

    public String getfName(){
        return fName;
    }
    public void setmName(String mName){
        this.mName = mName;
    }

    public String getmName(){
        return mName;
    }
    public void setlName(String lName){
        this.lName = lName;
    }

    public String getlName(){
        return lName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }


}
