package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class AccountDAO {
    private AccountDAO userDAO;

    //Checking for username and password in the database
    public Account loginUser(Account user){
        Connection connection = ConnectionUtil.getConnection();
        Deque <Account> users = new ArrayDeque<>();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                users.add(account);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage()); //If an exception occurs, this will print out the error message in the debug console and returns null which is used for a different purpose
            return null;
        }
        System.out.println(users.peekLast());
        if(users.isEmpty()) return null;
        else return users.peekLast();
    }

    //DAO to insert user to the database
    public Account insertUser(Account user){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username,password) VALUES (?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
                
                preparedStatement.executeUpdate();
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_author_id = (int) pkeyResultSet.getLong(1);
                    return new Account(generated_author_id, user.getUsername(),user.getPassword());
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }
    
}
