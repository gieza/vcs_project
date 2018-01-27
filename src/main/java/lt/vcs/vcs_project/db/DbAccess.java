package lt.vcs.vcs_project.db;

import lt.vcs.vcs_project.datalayer.Account;

import java.sql.*;

public class DbAccess {
    private static final String dBurl = "jdbc:h2:~/vcs_project;IFEXISTS=TRUE";
    private static final String dBuser = "java";
    private static final String dBpassword = "java";
    //static Connection dbConnection = null;
    //static Statement statement = null;

    public DbAccess() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        recreateAccountTable();

    }

    private static void recreateAccountTable() /*throws SQLException*/ {
        final String createAccountTable = "CREATE TABLE IF NOT EXISTS account (loginId VARCHAR, " +
                "firstName VARCHAR," +
                "secondName VARCHAR," +
                "password VARCHAR," +
                "role VARCHAR," +
                "personalId VARCHAR," +
                ")";
        exectueSql(createAccountTable);
    }

    static void sqlAddAccount(Account account) {

    }

    private static void exectueSql(String sqlstatement) {
        //Connection dbConnection = null;
        //Statement statement = null;
        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                Statement statement = dbConnection.createStatement()) {
            statement.execute(sqlstatement);

        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));
            //[90020-196] - Database may be already in use:
            //Wrong user name or password [28000-196]
            //Database "C:/Users/Giedrius/vcs_project2" not found [90013-196]

            e.printStackTrace();

        }
    }
}
