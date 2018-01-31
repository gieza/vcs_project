package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Account;
import lt.vcs.vcs_project.datalayer.AccountCollection;
import lt.vcs.vcs_project.datalayer.Role;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import static lt.vcs.vcs_project.datalayer.Role.ADMIN;
import static lt.vcs.vcs_project.datalayer.Role.LECTURER;
import static lt.vcs.vcs_project.datalayer.Role.STUDENT;

public class AccountRepository {
    private static final String dBurl = "jdbc:h2:~/vcs_project";
    private static final String dBuser = "java";
    private static final String dBpassword = "java";
    //static Connection dbConnection = null;
    //static Statement statement = null;
    private static final Account defaultAccount = new Account("admin", "admin", "adminas", "adminas", ADMIN);

    //todo: reikia prideti prie inito
    public AccountRepository() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        recreateAccountTable();

    }

    private static void recreateAccountTable() /*throws SQLException*/ {
        final String createAccountTable = "CREATE TABLE IF NOT EXISTS account (" +
                "loginId VARCHAR NOT NULL, " +
                "firstName VARCHAR," +
                "secondName VARCHAR," +
                "password VARCHAR," +
                "role VARCHAR," +
                "personalId VARCHAR," +
                "PRIMARY KEY(loginId)" +
                ")";
        exectueSql(createAccountTable);
        sqlAddAccount(defaultAccount);
    }

    public static void sqlAddAccount(Account account) {
        if (sqlAccountExists(account.getLoginId()))
            return;
        String sqlStatement = "INSERT INTO account (loginId,firstName,secondName,password,role,personalId) " +
                "VALUES (?,?,?,?,?,?)";
        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlStatement)
        ) {
            preparedStatement.setString(1, account.getLoginId());
            preparedStatement.setString(2, account.getFirstName());
            preparedStatement.setString(3, account.getSecondName());
            preparedStatement.setString(4, account.getPassword());
            preparedStatement.setString(5, account.getRoleAsString());
            preparedStatement.setString(6, account.getPersonalId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));
            //[90020-196] - Database may be already in use:
            //Wrong user name or password [28000-196]
            //Database "C:/Users/Giedrius/vcs_project2" not found [90013-196]

            e.printStackTrace();

        }
    }

    static boolean sqlAuthenticate(String accountId, String password) {
        String sqlStatement = "SELECT * FROM account WHERE loginId = ?";
        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlStatement);
        ) {
            preparedStatement.setString(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getString("password").equals(password))
                return true;
            return false;
        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));
            e.printStackTrace();

        }
        return false;
    }

    public static AccountCollection getSqlAccountList(/*String sqlPattern*/) {
        String sqlStatement = "SELECT * FROM account";
        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlStatement);
        ) {
            //preparedStatement.setString(1, accountId);
            AccountCollection accountList = new AccountCollection();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (ADMIN.toString().equals(resultSet.getString("role"))) {
                    accountList.addAccount(
                            new Account(resultSet.getString("loginId"),
                                    resultSet.getString("password"),
                                    resultSet.getString("firstName"),
                                    resultSet.getString("secondName"),
                                    ADMIN));
                } else {
                    Role role;
                    if (resultSet.getString("secondName").equals("STUDENT")) {
                        role = STUDENT;
                    } else {
                        role = LECTURER;
                    }
                    accountList.addAccount(new Account(resultSet.getString("loginId"),
                            resultSet.getString("password"),
                            resultSet.getString("firstName"),
                            resultSet.getString("secondName"),
                            role,
                            resultSet.getString("personalId")));
                }
            }
            return accountList;
        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));
            e.printStackTrace();

        }
        return null;
    }

    public static Set<String> getKeyset() {
        String sqlStatement = "SELECT * FROM account";
        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlStatement);
        ) {
            Set<String> keySet = new HashSet<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                keySet.add(resultSet.getString("loginId"));
            }
            return keySet;
        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));

            e.printStackTrace();
        }
        return null;
    }

  /*  public static AccountCollection getSqlAccountList() {
        return getSqlAccountList("");
    }*/

    public static boolean sqlAccountExists(String accountId) {
        String sqlStatement = "SELECT * FROM account WHERE loginId = ?";
        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlStatement)
        ) {
            preparedStatement.setString(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));

            e.printStackTrace();
        }
        return false;
    }

    public static Account getSqlAccount(String accountId) {
        String sqlStatement = "SELECT * FROM account WHERE loginId = ?";
        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlStatement)
        ) {
            Account accountList = null;
            preparedStatement.setString(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            if (ADMIN.toString().equals(resultSet.getString("role"))) {
                return new Account(resultSet.getString("loginId"),
                        resultSet.getString("password"),
                        resultSet.getString("firstName"),
                        resultSet.getString("secondName"),
                        ADMIN);
            } else {
                Role role;
                if (resultSet.getString("secondName").equals("STUDENT")) {
                    role = STUDENT;
                } else {
                    role = LECTURER;
                }
                return new Account(resultSet.getString("loginId"),
                        resultSet.getString("password"),
                        resultSet.getString("firstName"),
                        resultSet.getString("secondName"),
                        role,
                        resultSet.getString("personalId"));
            }
        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));

            e.printStackTrace();

        }
        return null;
    }

    private static void exectueSql(String sqlStatement) {
        //Connection dbConnection = null;
        //Statement statement = null;
        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                Statement statement = dbConnection.createStatement()) {
            statement.execute(sqlStatement);

        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));

            e.printStackTrace();

        }
    }

    public static boolean sqlIsAdmin(String loginId) {
        String sqlStatement = "SELECT * FROM account WHERE loginId = ? AND role = 'ADMIN'";
        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlStatement)
        ) {
            preparedStatement.setString(1, loginId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));

            e.printStackTrace();
        }
        return false;

    }

    public static void sqlUpdateAccount(Account account) {
        if (!sqlAccountExists(account.getLoginId()))
            return;
        String sqlStatement = "UPDATE account SET ( firstName,secondName,password,personalId ) " +
                "= (?,?,?,?) " +
                "WHERE loginId = ?;";

        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlStatement)
        ) {
            preparedStatement.setString(1, account.getFirstName());
            preparedStatement.setString(2, account.getSecondName());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.setString(4, account.getRoleAsString());
            preparedStatement.setString(5, account.getLoginId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));
            e.printStackTrace();
        }
    }

    public static void sqlDeleteAccount(String loginId) {
        if (!sqlAccountExists(loginId))
            return;
        String sqlStatement = "DELETE FROM account " +
                "WHERE loginId = ?;";

        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlStatement)
        ) {
            preparedStatement.setString(1, loginId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));
            e.printStackTrace();
        }
    }

    public static Integer adminCount() {
        String sqlStatement = "SELECT * FROM account WHERE role= 'ADMIN'";
        try (
                Connection dbConnection = DriverManager.getConnection(dBurl, dBuser, dBpassword);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlStatement)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer adminCount = 0;
            while (resultSet.next()) {
                adminCount++;
            }
            return adminCount;
        } catch (SQLException e) {
            System.out.println("Error code:" + e.getMessage().replaceAll(".*\\[(\\d+-\\d+)\\].*", "$1"));
            e.printStackTrace();
        }
        return 0;
    }
}
