package net.nemo.whatever.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.nemo.whatever.controller.mav.UserAgentModelAndView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.service.ChatService;

@Controller
@RequestMapping("/chat")
public class ChatController {

	private Logger logger = Logger.getLogger(ChatController.class);

	@Autowired
	private ChatService chatService;

	@Autowired
	private Environment env;

	@RequestMapping("/list.html")
	public ModelAndView chatList(HttpServletRequest request) {
		ModelAndView mav = new UserAgentModelAndView(request, "chat/index", "wecord");
		User currentUser = (User)mav.getModelMap().get("currentUser");
		List<Chat> chats = this.chatService.listChats(currentUser.getId());
		mav.addObject("chats", chats);
		mav.addObject("lastCommit", env.getProperty("LAST_COMMIT"));

		return mav;
	}

}
