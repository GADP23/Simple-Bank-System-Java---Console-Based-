package org.example.AdminMainMenu;

import org.example.MainMenu;
import org.example.GlobalClass.Transaction;
import org.example.GlobalClass.UserAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionHistoryBank {
    static final String url = "jdbc:mysql://localhost:3306/mti_simple_banking_system";
    //    static final String url = "jdbc:mysql://localhost:3306/mti_db";
    static final String username = "root";
    static final String password = "";
    Scanner scan = new Scanner(System.in);
    private static String backMainMenu;

    private UserAccount userAccount;

    public TransactionHistoryBank(UserAccount userAccount){
        this.userAccount = userAccount;

    }

    public List<Transaction> getAllTransactions(){
        String selectSpecificRecordQuery = "SELECT * FROM transaction_history ORDER BY TRANSACTIONID DESC;";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(selectSpecificRecordQuery)){

            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {

                System.out.println("Transaction ID: " + results.getInt("TRANSACTIONID") + " Account Number: " + results.getString("ACCOUNTNUMBER") + " Deposit Date: " + results.getTimestamp("DEPOSIT_DATE") + " Withdraw Date: " + results.getTimestamp("WITHDRAW_DATE") + " Ammount: " + results.getFloat("AMOUNT"));
            }


            System.out.println("\n[1] Back to Main Menu");
            backMainMenu = scan.nextLine();

            if (backMainMenu.equals("1")){
                MainMenu mainMenu = new MainMenu(userAccount);
                mainMenu.mainMenuAdminModule();
            } else {
                System.out.println("The menu you wish to access does not exist.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return transactions;
    }

}
