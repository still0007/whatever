package net.nemo.whatever.controller;

import net.nemo.whatever.controller.mav.UserAgentModelAndView;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tonyshi on 2017/2/27.
 */
@Controller
@RequestMapping("/todos")
@PropertySource("classpath:props/application.properties")
public class TodosController {

    @RequestMapping("/*")
    public ModelAndView index(HttpServletRequest request){
        return new UserAgentModelAndView(request, "todos/index", "todos");
    }

}
