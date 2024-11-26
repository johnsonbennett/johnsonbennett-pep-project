package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class MessageDAO {


    //DAO to retrieve all messages available
    public Deque<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        Deque<Message> messages = new ArrayDeque<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    

    //DAO to insert a message to the database
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by,message_text,time_posted_epoch) VALUES (?,?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,message.getPosted_by());
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());
                
                preparedStatement.executeUpdate();
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_author_id = (int) pkeyResultSet.getLong(1);
                    return new Message(generated_author_id, message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }

        //Get message based on message id
        public Message getMessageByID(int message_id){
            Connection connection = ConnectionUtil.getConnection();
            Deque <Message> userMessages = new ArrayDeque<>();
            try{
                String sql = "SELECT * FROM message WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,message_id);
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Message uMessage = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                    userMessages.add(uMessage);
                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
                return null;
            };
            return userMessages.peekLast();
        }

        //Delete from blog with message id
        public Message deleteMessageByID(int message_id){
            Connection connection = ConnectionUtil.getConnection();
            Deque <Message> deletedMessage = new ArrayDeque<>();
            try{
                String sql = "SELECT * FROM message WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,message_id);
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Message uMessage = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                    deletedMessage.add(uMessage);
                    System.out.println(deletedMessage);
                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
                return null;
            }
            return deletedMessage.peekLast();
        }

        //Update blog based on message ID
        public Message UpdateMessageByID(String message_text,int message_id){
            Connection connection = ConnectionUtil.getConnection();
            Deque <Message> updateMessage = new ArrayDeque<>();
            if( !message_text.isBlank() && message_text.length() <=255 ){
                try{
                    String sql = "SELECT * FROM message WHERE message_id = ?";
                    String update = "UPDATE message SET message_text=? WHERE message_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    PreparedStatement updateStatement = connection.prepareStatement(update);
                    preparedStatement.setInt(1,message_id);
                    updateStatement.setString(1,message_text);
                    updateStatement.setInt(2,message_id);
                    updateStatement.executeUpdate();
                    ResultSet rs = preparedStatement.executeQuery();
                    while(rs.next()){
                        Message uMessage = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                        updateMessage.addLast(uMessage);
                    }
                }
                catch (SQLException e){
                    System.out.println(e.getMessage());
                    return null;
                }
                return updateMessage.peekLast();
        }
        else return null;
        }

        //Show all message based on user
        public Deque<Message> getMessageByUser(int user){
            Connection connection = ConnectionUtil.getConnection();
            Deque <Message> userMessages = new ArrayDeque<>();
            try{
                String sql = "SELECT * FROM message WHERE posted_by = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,user);
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Message uMessage = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                    userMessages.addLast(uMessage);
                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
                return null;
            };
            return userMessages;
        }


}
