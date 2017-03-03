package net.nemo.whatever.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.nemo.whatever.controller.mav.UserAgentModelAndView;
import net.nemo.whatever.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
