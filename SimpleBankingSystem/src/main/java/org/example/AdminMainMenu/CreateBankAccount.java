package org.example.AdminMainMenu;

import org.example.MainMenu;
import org.example.GlobalClass.UserAccount;

import java.security.SecureRandom;
import java.sql.*;
import java.util.Scanner;

public class CreateBankAccount {
    static final String url = "jdbc:mysql://localhost:3306/mti_simple_banking_system";
//    static final String url = "jdbc:mysql://localhost:3306/mti_db";
    static final String username = "root";
    static final String password = "";
    static Scanner scan = new Scanner(System.in);
    static String accountNumber;
    static String firstName;
    static String middleName;
    static String lastName;
    static String emailAddress;
    static String uName;

    static String userInputDuplicateAccMM;
    final static double accountBalanceType1 = 10_000;
    final static double accountBalanceType2 = 5_000;
    final static double accountBalanceType3 = 1_000;

    private UserAccount userAccount;

    public CreateBankAccount(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    public void createAccount() {

        createAccountFunction();

    }

    public void autoGenerateAccountNumber(){
        SecureRandom random = new SecureRandom();
        StringBuilder number = new StringBuilder();

        // Generate 12 random digits
        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10); // Generate a random number between 0 and 9
            number.append(digit);
        }
        accountNumber = "2024" + number;
        userAccount.setAccNum(accountNumber);

    }

    public void createAccountFunction(){


        System.out.println("-----------------------------");
        System.out.println("CREATE BANK ACCOUNT");
        System.out.println("-----------------------------");
        System.out.print("Enter Firstname: ");
        firstName = scan.nextLine();
        userAccount.setfName(firstName);

        System.out.print("Enter Middlename: ");
        middleName = scan.nextLine();
        userAccount.setmName(middleName);

        System.out.print("Enter Lastname: ");
        lastName = scan.nextLine();
        userAccount.setlName(lastName);

        System.out.print("Enter Email Address: ");
        emailAddress = scan.nextLine();
        userAccount.setEmail(emailAddress);

        System.out.print("Enter Username: ");
        uName = scan.nextLine();
        userAccount.setUsername(uName);

        checkRecordValidator();


    }

    public void checkRecordValidator(){
        String selectSpecificRecordQuery = "SELECT * FROM account WHERE USERNAME = '" + userAccount.getUsername() + "';";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            ResultSet results = statement.executeQuery(selectSpecificRecordQuery);

            if (results.next()){
                System.out.println("\nThis Record is already existing. Account Info: "
                        + "\nAccount Number: " + results.getString("ACCOUNTNUMBER")
                        + "\nUsername: " + results.getString("USERNAME")
                        + "\nFirstname: " + results.getString("FIRSTNAME")
                        + "\nMiddlename: " + results.getString("MIDDLENAME")
                        + "\nLastname: " + results.getString("LASTNAME"));
                System.out.println("[1] Continue to create another bank account"
                               + "\n[2] Back to Main Menu");
                userInputDuplicateAccMM = scan.nextLine();

                if (userInputDuplicateAccMM.equals("1")){
//                    selectAccountType();
                    enterUsername();
                    duplicateUsernameChecker();

                } else if (userInputDuplicateAccMM.equals("2")) {
                    MainMenu mainMenu = new MainMenu(userAccount);
                    mainMenu.mainMenuAdminModule();
                } else {
                    System.out.println("The menu you wish to access does not exist.");
                }
            }else {
                selectAccountType();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void enterUsername(){
        System.out.print("\nEnter Username: ");
        uName = scan.nextLine();
        userAccount.setUsername(uName);
    }

    public void duplicateUsernameChecker(){
        String selectSpecificRecordQuery = "SELECT * FROM account WHERE USERNAME = '" + userAccount.getUsername() + "';";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            ResultSet results = statement.executeQuery(selectSpecificRecordQuery);

            if (results.next()){
                System.out.println("\nUsername already exists.");
                enterUsername();
            }else {
                selectAccountType();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void assignPassword(){
        String insertData = "INSERT INTO users_account (USERNAME, PASSWORD, ACCESS_ROLE) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = conn.prepareStatement(insertData)){

            statement.setString(1, userAccount.getUsername());
            statement.setString(2, "12345");
            statement.setString(3, "Client");

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.print("\nAccount created successfully!");
            } else {
                System.out.println("\nAccount creation was unsuccessful!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void selectAccountType(){
        System.out.println("\n[1] Minimum Deposit of Php 10,000.00"
                         + "\n[2] Minimum Deposit of Php 5,000.00"
                         + "\n[3] Minimum Deposit of Php 1,000.00");
        String balType = scan.nextLine();

        if(balType.equals("1")){
            insertAccountDataFunctionType1();
//            accountType1.insertAccountDataFunctionType();//INTERFACE
        }else if (balType.equals("2")){
            insertAccountDataFunctionType2();
//            accountType2.insertAccountDataFunctionType();
        }else if (balType.equals("3")) {
            insertAccountDataFunctionType3();
//            accountType3.insertAccountDataFunctionType();
        }else {
            System.out.println("The menu you wish to access does not exist.");
            selectAccountType();
        }
    }

    public void insertAccountDataFunctionType1(){


        autoGenerateAccountNumber();

        System.out.println("\n" + userAccount.getfName() + " " + userAccount.getlName() + "'s Account Info: "
                + "\nAccount Number: " + userAccount.getAccNum()
                + "\nUsername: " + userAccount.getUsername()
                + "\nFirstname: " + userAccount.getfName()
                + "\nMiddlename: " + userAccount.getmName()
                + "\nLastname: " + userAccount.getlName()
                + "\nEmail Address: " + userAccount.getEmail());




        String insertData = "INSERT INTO account (USERNAME, ACCOUNTNUMBER, FIRSTNAME, MIDDLENAME, LASTNAME, EMAIL, ACCOUNTBALANCE) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = conn.prepareStatement(insertData)){

            statement.setString(1, userAccount.getUsername());
            statement.setString(2, userAccount.getAccNum());
            statement.setString(3, userAccount.getfName());
            statement.setString(4, userAccount.getmName());
            statement.setString(5, userAccount.getlName());
            statement.setString(6, userAccount.getEmail());
            statement.setDouble(7, accountBalanceType1);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                assignPassword();
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
        mainMenu.mainMenuAdminModule();
    }

    public void insertAccountDataFunctionType2(){

        autoGenerateAccountNumber();

        System.out.println("\n" + userAccount.getfName() + " " + userAccount.getlName() + "'s Account Info: "
                + "\nAccount Number: " + userAccount.getAccNum()
                + "\nUsername: " + userAccount.getUsername()
                + "\nFirstname: " + userAccount.getfName()
                + "\nMiddlename: " + userAccount.getmName()
                + "\nLastname: " + userAccount.getlName()
                + "\nEmail Address: " + userAccount.getEmail());




        String insertData = "INSERT INTO account (USERNAME, ACCOUNTNUMBER, FIRSTNAME, MIDDLENAME, LASTNAME, EMAIL, ACCOUNTBALANCE) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = conn.prepareStatement(insertData)){

            statement.setString(1, userAccount.getUsername());
            statement.setString(2, userAccount.getAccNum());
            statement.setString(3, userAccount.getfName());
            statement.setString(4, userAccount.getmName());
            statement.setString(5, userAccount.getlName());
            statement.setString(6, userAccount.getEmail());
            statement.setDouble(7, accountBalanceType2);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                assignPassword();
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
        mainMenu.mainMenuAdminModule();
    }

    public void insertAccountDataFunctionType3(){

        autoGenerateAccountNumber();

        System.out.println("\n" + userAccount.getfName() + " " + userAccount.getlName() + "'s Account Info: "
                + "\nAccount Number: " + userAccount.getAccNum()
                + "\nUsername: " + userAccount.getUsername()
                + "\nFirstname: " + userAccount.getfName()
                + "\nMiddlename: " + userAccount.getmName()
                + "\nLastname: " + userAccount.getlName()
                + "\nEmail Address: " + userAccount.getEmail());




        String insertData = "INSERT INTO account (USERNAME, ACCOUNTNUMBER, FIRSTNAME, MIDDLENAME, LASTNAME, EMAIL, ACCOUNTBALANCE) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = conn.prepareStatement(insertData)){

            statement.setString(1, userAccount.getUsername());
            statement.setString(2, userAccount.getAccNum());
            statement.setString(3, userAccount.getfName());
            statement.setString(4, userAccount.getmName());
            statement.setString(5, userAccount.getlName());
            statement.setString(6, userAccount.getEmail());
            statement.setDouble(7, accountBalanceType3);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                assignPassword();
            } else {
                System.out.println("\nAccount creation was unsuccessful!");
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
        mainMenu.mainMenuAdminModule();
    }

}
