package org.example.ClientMainMenu;


import org.example.MainMenu;
import org.example.GlobalClass.UserAccount;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class DepositMoney {
    static final String url = "jdbc:mysql://localhost:3306/mti_simple_banking_system";
//    static final String url = "jdbc:mysql://localhost:3306/mti_db";
    static final String username = "root";
    static final String password = "";
    static Scanner scan = new Scanner(System.in);
    static String userInputWrongAccNum;

    static double depositAmount;
    static double remainingBalance = 0;
    static double totalRemainingBalance = 0;

    private UserAccount userAccount;
    public DepositMoney(UserAccount userAccount){

        this.userAccount = userAccount;
    }

    public void deposit(){
        System.out.println("-----------------------------");
        System.out.println("           DEPOSIT           ");
        System.out.println("-----------------------------");


        checkRecord();
        fetchSpecificRecord();
        depositAmountBalance();
    }

    public void checkRecord(){

        String selectUsernameQuery = "SELECT * FROM users_account ac LEFT JOIN account at ON at.USERNAME = ac.USERNAME WHERE ac.USERNAME = '" + userAccount.getUsername() + "';";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            ResultSet results = statement.executeQuery(selectUsernameQuery);

            if (results.next()){
                userAccount.setAccNum(results.getString("ACCOUNTNUMBER"));
                userAccount.setAmount(results.getFloat("ACCOUNTBALANCE"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void fetchSpecificRecord(){

        String selectRecordQuery = "SELECT * FROM account WHERE ACCOUNTNUMBER = '" + userAccount.getAccNum() + "';";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            ResultSet results = statement.executeQuery(selectRecordQuery);

                if (results.next()){
                    remainingBalance = results.getDouble("ACCOUNTBALANCE");
                    userAccount.setRemainingBalance(remainingBalance);



                    System.out.println("Your Account Info: "
                            + "\nAccount Number: " + results.getString("ACCOUNTNUMBER")
                            + "\nFirstname: " + results.getString("FIRSTNAME")
                            + "\nMiddlename: " + results.getString("MIDDLENAME")
                            + "\nLastname: " + results.getString("LASTNAME"));

                }else {
                    System.out.println("This account number is not existing!");
                    System.out.println("[1] Re-enter Account Number"
                                     + "\n[2] Back to Main Menu");
                    userInputWrongAccNum = scan.nextLine();

                    if(userInputWrongAccNum.equals("1")){
                        deposit();
                    }else if (userInputWrongAccNum.equals("2")){
                        MainMenu mainMenu = new MainMenu(userAccount);
                        mainMenu.mainMenuClientModule();
                    }else {
                        System.out.println("The menu you wish to access does not exist.");
                    }
                }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void depositAmountBalance(){
        System.out.print("\nEnter the amount you want to Deposit: ");
        depositAmount = scan.nextDouble();

        totalRemainingBalance = depositAmount + userAccount.getRemainingBalance();

        String updateDepositAmount = "UPDATE account SET ACCOUNTBALANCE = ? WHERE ACCOUNTNUMBER = '" + userAccount.getAccNum() + "';";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = con.prepareStatement(updateDepositAmount)){

            preparedStatement.setDouble(1, totalRemainingBalance);

            int affectrows = preparedStatement.executeUpdate();

            if (affectrows > 0){
                LocalDateTime now = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(now);
                String insertData = "INSERT INTO transaction_history (ACCOUNTNUMBER, DEPOSIT_DATE, AMOUNT) VALUES (?, ?, ?)";

                try (Connection conn = DriverManager.getConnection(url, username, password);
                     PreparedStatement statement = conn.prepareStatement(insertData)){

                    statement.setString(1, userAccount.getAccNum());
                    statement.setTimestamp(2, timestamp);
                    statement.setFloat(3, userAccount.getAmount());


                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {

                    } else {
                        System.out.println("\nAccount creation was unsuccessful.");
                    }
                    try {
                        if(conn != null){
                            conn.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                MainMenu mainMenu = new MainMenu(userAccount);
                System.out.println("Deposit Successful!");
                mainMenu.mainMenuClientModule();
            }else {
                System.out.println("Error: you input an invalid number");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
