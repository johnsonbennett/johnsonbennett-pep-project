package Controller;

import Model.Account;
import Model.Message;
import Service.AccountServices;
import Service.MessageServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Deque;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountServices accountServices = new AccountServices();
    MessageServices messageServices = new MessageServices();
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages",this::getAllPostsHandler);
        app.get("/messages/{message_id}",this::getIDMessage);
        app.delete("/messages/{message_id}",this::deleteIDMessage);
        app.get("/accounts/{account_id}/messages",this::getMessageUser);
        app.patch("/messages/{message_id}",this::updateIDMessage);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(ctx.body(), Account.class);
        Account addedUser = accountServices.addUser(user);
        if(addedUser!=null){
            ctx.json(mapper.writeValueAsString(addedUser));
        }else{
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(ctx.body(), Account.class);
        Account loginUser = accountServices.loginUser(user);
        if(loginUser!=null){
            ctx.json(mapper.writeValueAsString(loginUser));
            ctx.status(200);
        }else{
            ctx.status(401);
        }
    }

    //Handler to post a new message to the blog
    private void postNewMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageServices.addMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    //Handler to retrieve all messages available on the blog site
    private void getAllPostsHandler(Context ctx) throws JsonProcessingException {
        Deque <Message> messages = messageServices.getAllMessage();
        ctx.json(messages);
        ctx.status(200);
    }

    //Handler to retrieve a message based on message id
    private void getIDMessage(Context ctx) throws JsonProcessingException {
        String id = ctx.pathParam("message_id");
        int message_id = Integer.parseInt(id);
        Message message = messageServices.getMessageOnID(message_id);
        if(message != null) ctx.json(message);
        ctx.status(200);
    }

    //Handler to delete message by message id
    private void deleteIDMessage(Context ctx) throws JsonProcessingException {
        String id = ctx.pathParam("message_id");
        int message_id = Integer.parseInt(id);
        Message message = messageServices.deleteById(message_id);
        System.out.println(message);
        if(message !=null) ctx.json(message);
        ctx.status(200);
    }
    
    //Handler to retrieve a message based on poster
    private void getMessageUser(Context ctx) throws JsonProcessingException {
        System.out.println("Enter Handler");
        String id = ctx.pathParam("account_id");
        System.out.println(id);
        int message_id = Integer.parseInt(id);
        System.out.println("Accound_id: " + message_id);
        Deque<Message> message = messageServices.getMessageOnUser(message_id);
        System.out.println(message);
        if(message != null) ctx.json(message);
        ctx.status(200);
    }
    //Handle to update by ID
    private void updateIDMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String id = ctx.pathParam("message_id");
        int message_id = Integer.parseInt(id);
        String message = ctx.body();
        JsonNode rootNode = mapper.readTree(message);
        String message_text = rootNode.path("message_text").asText();
        Message messages = messageServices.updateById(message_text,message_id);
        System.out.println(message);
        if(messages !=null) {
            ctx.json(messages);
            ctx.status(200);
        }
        else ctx.status(400);
    }



}