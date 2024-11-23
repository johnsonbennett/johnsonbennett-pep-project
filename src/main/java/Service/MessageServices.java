package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.Deque;

public class MessageServices {
    private MessageDAO messageDAO;
    
    public MessageServices(){
        messageDAO = new MessageDAO();
    }

    //Constructor when AccountDAO is provided
    public MessageServices(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    //Method to retrieve all messages posted on the blog site
    public Deque<Message> getAllMessage(){
        return messageDAO.getAllMessages();
    }

    //Method to insert new message into the database
    public Message addMessage(Message message){
        if(message.getMessage_text() != "") return messageDAO.insertMessage(message);
        else return null;
    }

    //Method to retreive message based on message id
    public Message getMessageOnID(int message){
        return messageDAO.getMessageByID(message);
    }

    //Method to delete a message with an id
    public Message deleteById(int message_id){
        if(messageDAO.deleteMessageByID(message_id) != null){
            return messageDAO.deleteMessageByID(message_id);
        }
        else return null;
    }
    public Message updateById(String message_text,int message_id){
        if(messageDAO.UpdateMessageByID(message_text,message_id) != null){
            return messageDAO.deleteMessageByID(message_id);
        }
        else return null;
    }

    //Method to get all messages posted by a user
    public Deque<Message> getMessageOnUser(int user){
        return messageDAO.getMessageByUser(user);
    }

    
}
