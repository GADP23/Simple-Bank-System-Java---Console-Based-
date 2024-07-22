package org.example.AdminMainMenu;

import org.example.MainMenu;
import org.example.GlobalClass.UserAccount;

import java.sql.*;
import java.util.Scanner;

public class DeleteAccountModule {
    static final String url = "jdbc:mysql://localhost:3306/mti_simple_banking_system";
    //    static final String url = "jdbc:mysql://localhost:3306/mti_db";
    static final String username = "root";
    static final String password = "";
    static Scanner scan = new Scanner(System.in);
    static String accountNumber;
    static String userInputCheckBalMM;

    private UserAccount userAccount;

    public DeleteAccountModule(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    public void deleteAccount(){
        System.out.print("\nEnter Account Number: ");
        accountNumber = scan.nextLine();

        checkAccountDDB();
    }

    public void checkAccountDDB() {
        String selectSpecificRecordQuery = "SELECT * FROM account ac LEFT JOIN users_account ua ON ua.USERNAME = ac.USERNAME WHERE ac.ACCOUNTNUMBER = '" + accountNumber + "';";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            ResultSet results = statement.executeQuery(selectSpecificRecordQuery);

            if (results.next()){
                System.out.println("\nYour Remaining Account Balance: "
                        + "\nRemaining Account Balance: " + results.getString("ACCOUNTBALANCE")
                        + "\nUsername: " + results.getString("USERNAME")
                        + "\nAccount Number: " + results.getString("ACCOUNTNUMBER")
                        + "\nFirstname: " + results.getString("FIRSTNAME")
                        + "\nMiddlename: " + results.getString("MIDDLENAME")
                        + "\nLastname: " + results.getString("LASTNAME")
                        + "\nEmail Address: " + results.getString("EMAIL"));
                System.out.println("[1] Delete Account\n[2] Back to Main Menu");
                userInputCheckBalMM = scan.nextLine();

                if (userInputCheckBalMM.equals("1")){
                    String deleteUsersAccount = "DELETE FROM users_account WHERE USERNAME = ?;";

                    try (Connection con = DriverManager.getConnection(url, username, password);
                         PreparedStatement preparedStatement = con.prepareStatement(deleteUsersAccount)){

                        preparedStatement.setString(1, results.getString("USERNAME"));

                        int affectrows = preparedStatement.executeUpdate();

                        if (affectrows > 0){
                            System.out.println("Account Deleted Successfully!");
                            String deleteAccount = "DELETE FROM account WHERE ACCOUNTNUMBER = ?;";
                            try (Connection connn = DriverManager.getConnection(url, username, password);
                                 PreparedStatement preparedStatement2 = connn.prepareStatement(deleteAccount)){

                                preparedStatement2.setString(1, results.getString("ACCOUNTNUMBER"));

                                preparedStatement2.executeUpdate();


                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            MainMenu mainMenu = new MainMenu(userAccount);
                            mainMenu.mainMenuAdminModule();
                        }else {
                            System.out.println("Error: you input an invalid number");
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else if (userInputCheckBalMM.equals("2")){
                    MainMenu mainMenu = new MainMenu(userAccount);
                    mainMenu.mainMenuAdminModule();
                } else {
                    System.out.println("The menu you wish to access does not exist.");
                }
            }else {
                System.out.println("System is Busy");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
