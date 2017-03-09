package net.nemo.whatever.api.resources;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.service.ChatService;
import net.nemo.whatever.service.MessageService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tonyshi on 2017/3/6.
 */
@Service
@Path("/chat")
public class ChatResource {

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageSercice;

    @GET
    @Path("/list.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> chatList() {
        Map<String, Object> result = new HashMap<String, Object>();

        User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        List<Chat> chats = this.chatService.listChats(currentUser == null ? 1 : currentUser.getId());
        result.put("chats", chats);
        result.put("success", true);
        return result;
    }

    @GET
    @Path("/{chat_id}.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> messages(@PathParam("chat_id") Integer chatId) {
        List<Map> messages = this.messageSercice.findMessages(chatId);

        for(Map msg: messages){
            Integer msgId = (Integer)msg.get("id");
            msg.put("tags", StringUtils.join(this.messageSercice.findTags(msgId).toArray(), ","));
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("messages", messages);
        result.put("success", true);
        return result;
    }
}
