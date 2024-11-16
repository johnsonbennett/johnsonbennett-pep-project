package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

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
    public List<Message> getAllMessage(){
        return messageDAO.getAllMessages();
    }

    //Method to insert new message into the database
    public Message addMessage(Message message){
        if(message.getMessage_text() != "") return messageDAO.insertMessage(message);
        else return null;
    }

    //Method to retreive message based on message id
    public List<Message> getMessageOnID(int message){
        return messageDAO.getMessageByID(message);
    }

    
}
