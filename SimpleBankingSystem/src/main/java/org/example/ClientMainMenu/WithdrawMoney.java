package org.example.ClientMainMenu;

import org.example.MainMenu;
import org.example.GlobalClass.UserAccount;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class WithdrawMoney {
    static final String url = "jdbc:mysql://localhost:3306/mti_simple_banking_system";
//    static final String url = "jdbc:mysql://localhost:3306/mti_db";
    static final String username = "root";
    static final String password = "";
    static Scanner scan = new Scanner(System.in);
    static String userInputWrongAccNum;

    static double withdrawAmount;
    static double remainingBalance;
    static double totalRemainingBalance;

    private UserAccount userAccount;
    public WithdrawMoney(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    public void withdraw(){
        System.out.println("-----------------------------");
        System.out.println("           WITHDRAW          ");
        System.out.println("-----------------------------");

        checkRecord();
        fetchSpecificRecord();
        withdrawAmountBalance(totalRemainingBalance);
    }

    public void checkRecord(){
        String selectUsernameQuery = "SELECT * FROM transaction_history th LEFT JOIN account ac ON ac.ACCOUNTNUMBER = th.ACCOUNTNUMBER WHERE ac.USERNAME = '" + userAccount.getUsername() + "';";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            ResultSet results = statement.executeQuery(selectUsernameQuery);

            if (results.next()){

            }
            userAccount.setAccNum(results.getString("ACCOUNTNUMBER"));
            userAccount.setAmount(results.getFloat("AMOUNT"));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void fetchSpecificRecord(){
        String selectRecordQuery = "SELECT * FROM account ac LEFT JOIN transaction_history th ON th.ACCOUNTNUMBER = ac.ACCOUNTNUMBER WHERE th.ACCOUNTNUMBER = '" + userAccount.getAccNum() + "';";

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
                    withdraw();
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

    public void withdrawAmountBalance(double totalRemainingBalance){
        System.out.print("\nEnter the amount you want to Withdraw: ");
        withdrawAmount = scan.nextDouble();

        if(withdrawAmount > userAccount.getRemainingBalance()){
            System.out.println("Insufficient balance.");
            MainMenu mainMenu = new MainMenu(userAccount);
            mainMenu.mainMenuClientModule();
        }else{
            totalRemainingBalance = userAccount.getRemainingBalance() - withdrawAmount;
        }

        String updateDepositAmount = "UPDATE account SET ACCOUNTBALANCE = ? WHERE ACCOUNTNUMBER = '" + userAccount.getAccNum() + "';";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = con.prepareStatement(updateDepositAmount)){

            preparedStatement.setDouble(1, totalRemainingBalance);

            int affectrows = preparedStatement.executeUpdate();

            if (affectrows > 0){
                LocalDateTime now = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(now);
                String insertData = "INSERT INTO transaction_history (ACCOUNTNUMBER, WITHDRAW_DATE, AMOUNT) VALUES (?, ?, ?)";

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

                System.out.println("Withdrawal Successful!");
                MainMenu mainMenu = new MainMenu(userAccount);
                mainMenu.mainMenuClientModule();
            }else {
                System.out.println("Error: you input an invalid number");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
