package net.nemo.whatever.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.service.ChatService;
import net.nemo.whatever.service.MessageService;
import net.nemo.whatever.util.StringUtil;

@Controller
@RequestMapping("/chat")
public class ChatController {

	private Logger logger = Logger.getLogger(ChatController.class);

	@Autowired
	private ChatService chatService;

	@Autowired
	private MessageService messageSercice;

	@Autowired
	private Environment env;

	@RequestMapping("/list.html")
	public ModelAndView chatList(HttpServletRequest request) {
		logger.info("Request /list.html");

		ModelAndView mav = new ModelAndView(StringUtil.getUserAgentViewName(request, "chat/list"));
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		List<Chat> chats = this.chatService.listChats(currentUser.getId());
		mav.addObject("chats", chats);
		mav.addObject("lastCommit", env.getProperty("LAST_COMMIT"));

		return mav;
	}

	@RequestMapping(value = "/list.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> chatList() {
		Map<String, Object> result = new HashMap<String, Object>();

		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		List<Chat> chats = this.chatService.listChats(currentUser == null ? 1 : currentUser.getId());
		result.put("chats", chats);
		result.put("success", true);
		return result;
	}

	@RequestMapping(value = "/{chat_id}.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> messages(@PathVariable("chat_id") Integer chatId) {
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
