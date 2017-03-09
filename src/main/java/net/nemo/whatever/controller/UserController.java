package net.nemo.whatever.controller;

import net.nemo.whatever.controller.mav.UserAgentModelAndView;
import net.nemo.whatever.service.UserService;
import net.nemo.whatever.util.DESCoder;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class UserController {
	
	private Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView root(HttpServletRequest request) throws Exception {
        ModelAndView  mav = new UserAgentModelAndView(request, "index");
        return mav;
    }

	@RequestMapping("/index.html")
	public ModelAndView index(HttpServletRequest request) throws Exception {
		return root(request);
	}

	@RequestMapping("/login.html")
	public ModelAndView login(HttpServletRequest request) throws Exception {
		ModelAndView mav = new UserAgentModelAndView(request, "user/login");
		return mav;
	}

	@RequestMapping("/logout.html")
	public ModelAndView logout(HttpServletRequest request) {
		SecurityUtils.getSubject().logout();
		ModelAndView mav = new ModelAndView("redirect:login.html");
		return mav;
	}

	@RequestMapping("/register/{id}/{token}.html")
	public ModelAndView register(HttpServletRequest request,
								 @PathVariable("id") Integer id,
								 @PathVariable("token") String token) {
		ModelAndView mav = null;

		String encryptedStr = token.trim();
		String key = this.userService.findUserById(id).getPassword().trim();
		
		try {
			String email = new String(DESCoder.decrypt(DESCoder.decryptBASE64(encryptedStr), key));
			mav = new UserAgentModelAndView(request, "user/register");
			mav.addObject("email", email);
		} catch (Exception e) {
			mav = new UserAgentModelAndView(request, "user/invalidtoken");
		}

		return mav;
	}
}
