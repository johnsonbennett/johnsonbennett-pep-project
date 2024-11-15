package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.List;

public class AccountServices {
    private AccountDAO userDAO;

    //Constructor
    public AccountServices(){
        userDAO = new AccountDAO();
    }

    //Constructor when AccountDAO is provided
    public AccountServices(AccountDAO userDAO){
        this.userDAO = userDAO;
    }

    //Service to add user.
    // The method checks to see if the username is blank or if the password is smaller than 4 characters. If that is the case, it returns null. IF not the insert is carried out
    public Account addUser(Account user){
        if(user.getUsername() != "" && user.getPassword().length() >=4){
            return userDAO.insertUser(user);
        }
        else return null;
    }

    public Account loginUser(Account user){
        return userDAO.getUserByCredentials(user);
    }
    
}
