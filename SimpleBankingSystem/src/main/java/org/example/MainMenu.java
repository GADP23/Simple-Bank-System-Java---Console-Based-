package org.example;

import org.example.AdminMainMenu.CreateBankAccount;
import org.example.AdminMainMenu.CheckCustomerAccountInfo;
import org.example.AdminMainMenu.DeleteAccountModule;
import org.example.AdminMainMenu.TransactionHistoryBank;
import org.example.ClientMainMenu.CheckBankBalance;
import org.example.ClientMainMenu.DepositMoney;
import org.example.ClientMainMenu.TransactionHistoryClient;
import org.example.ClientMainMenu.WithdrawMoney;
import org.example.GlobalClass.UserAccount;

import java.sql.*;
import java.util.Scanner;

public class MainMenu {
    static final String url = "jdbc:mysql://localhost:3306/mti_simple_banking_system";
    //    static final String url = "jdbc:mysql://localhost:3306/mti_db";
    static final String username = "root";
    static final String password = "";

    private UserAccount userAccount;

    Scanner scan = new Scanner(System.in);
    static byte userInputMM;

    public MainMenu(UserAccount userAccount){
        this.userAccount = userAccount;
    }


    public void mainMenuAdminModule(){

        try {
            System.out.println("\n\n[1] Create Customer Bank Account\n[2] Search Client's Bank Account\n[3] Bank Transaction History\n[4] Delete Customer's Bank Account\n[5]Log Out");
            userInputMM = scan.nextByte();

            if (userInputMM == 1){
                CreateBankAccount createBankAccount = new CreateBankAccount(userAccount);
                createBankAccount.createAccount();
            } else if (userInputMM == 2){
                CheckCustomerAccountInfo checkCustomerAccountInfo = new CheckCustomerAccountInfo(userAccount);
                checkCustomerAccountInfo.viewCustomerAccInfo();
            } else if (userInputMM == 3){
                TransactionHistoryBank transactionHistoryBank = new TransactionHistoryBank(userAccount);
                transactionHistoryBank.getAllTransactions();
            } else if (userInputMM == 4) {
                DeleteAccountModule deleteAccountModule = new DeleteAccountModule(userAccount);
                deleteAccountModule.deleteAccount();
            } else if (userInputMM == 5) {
                String[] newArgs = {""};
                Main.main(newArgs);
            } else {
                System.out.println("The menu you wish to access does not exist.");
                mainMenuAdminModule();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void mainMenuClientModule(){
        checkRecord();
        try {
            System.out.println("\n\n[1] Deposit\n[2] Withdraw\n[3] Check Balance\n[4] Transaction History\n[5]Log Out");
            userInputMM = scan.nextByte();

            if (userInputMM == 1){
                DepositMoney depositMoney = new DepositMoney(userAccount/*, transaction*/);
                depositMoney.deposit();
            } else if (userInputMM == 2){
                WithdrawMoney withdrawMoney = new WithdrawMoney(userAccount);
                withdrawMoney.withdraw();
            } else if (userInputMM == 3){
                CheckBankBalance checkBankBalance = new CheckBankBalance(userAccount);
                checkBankBalance.checkBalance();
            } else if (userInputMM == 4) {
                TransactionHistoryClient transactionHistoryClient = new TransactionHistoryClient(userAccount);
                transactionHistoryClient.getAllTransactions();
            } else if (userInputMM == 5) {
                String[] newArgs = {""};
                Main.main(newArgs);
            } else {
                System.out.println("The menu you wish to access does not exist.");
                mainMenuClientModule();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void checkRecord(){
        String selectUsernameQuery = "SELECT ac.ACCOUNTNUMBER FROM account ac LEFT JOIN users_account ua ON ua.USERNAME = ac.USERNAME WHERE ua.USERNAME = '" + userAccount.getUsername() + "' AND ua.PASSWORD = '" + userAccount.getPassword() + "';";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            statement.executeQuery(selectUsernameQuery);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
