package net.nemo.whatever.api.resources;

import net.nemo.whatever.entity.Message;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.service.ChatService;
import net.nemo.whatever.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by tonyshi on 2017/3/7.
 */
@Service
@Path("/message")
public class MessageResource {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatService chatService;

    @GET
    @Path("/link/tags.json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> allMessages(@Context HttpServletRequest request) {
        User currentUser = (User) request.getSession(true).getAttribute("currentUser");
        return this.messageService.findAllLinkTags(currentUser);
    }

    @POST
    @Path("/link/tags.json")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateTag(@FormParam("message_id") Integer messageId, @FormParam("tagname") String tagName) {
            this.messageService.addTagForMessage(messageId, tagName);
    }

    @DELETE
    @Path("/link/tags.json")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteTag(@FormParam("message_id") Integer messageId, @FormParam("tagname") String tagName) {

        this.messageService.deleteTag(messageId, tagName);
    }

    @GET
    @Path("/links.json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> allLinkMessages(@Context HttpServletRequest request, @QueryParam("tagname") String tagName) {
        User currentUser = (User) request.getSession(true).getAttribute("currentUser");
        return this.messageService.findLinkMessageByType(currentUser, tagName);
    }

    @GET
    @Path("/list.json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map> allMessages(@Context HttpServletRequest request, @QueryParam("contact") String contact) {
        User currentUser = (User) request.getSession(true).getAttribute("currentUser");
        Integer chatId = this.chatService.findIdBySender(currentUser.getId(), contact);
        return this.messageService.findMessages(chatId);
    }
}
