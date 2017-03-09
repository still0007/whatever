package net.nemo.whatever.api.resources;

import net.nemo.whatever.controller.UserController;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.exception.BusinessException;
import net.nemo.whatever.service.UserService;
import net.nemo.whatever.service.WechatService;
import net.nemo.whatever.util.SecurityHelper;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tonyshi on 2017/3/6.
 */
@Service
@Path("/user")
@Singleton
public class UserResource {

    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private WechatService wechatService;

    @POST
    @Path("/setPassword.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> setPassword(@FormParam("username") String username, @FormParam("password") String password) {
        Map<String, Object> result = new HashMap<String, Object>();

        User user = this.userService.findByEmail(username);
        user.setPassword(password);
        user.setStatus(2);
        this.userService.update(user);

        result.put("success", true);
        return result;
    }

    @GET
    @Path("/loginStatus.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> loginStatus() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", SecurityUtils.getSubject().isAuthenticated());

        return result;
    }

    @POST
    @Path("/checkLogin.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> checkLogin(@Context HttpServletRequest request,
                                          @FormParam("username") String username,
                                          @FormParam("password") String password,
                                          @FormParam("rememberMe") boolean rememberMe,
                                          @FormParam("openid") String openid) throws Exception {
        logger.info(String.format("Client is trying to log in with username: %s, password: %s", username, password));

        Map<String, Object> result = new HashMap<String, Object>();
        try{
            SecurityHelper.login(this.userService, username, password, rememberMe);
            result.put("success", true);
        }catch (Exception ex) {
            throw new BusinessException(ex.getMessage());
        }
        return result;
    }

    @POST
    @Path("/autologin.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> autoLogin() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", false);

        Subject subject = SecurityUtils.getSubject();
        if(subject.isRemembered()){
            String username = SecurityHelper.getCurrentUsername();
            User user = userService.findByEmail(username);
            try {
                SecurityHelper.login(this.userService, user.getEmail(), user.getPassword(), true);
                result.put("success", true);
            }catch (Exception ex){
                throw new BusinessException(ex.getMessage());
            }
        }

        return result;
    }

    @GET
    @Path("/contacts.json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> queryFriends(@Context HttpServletRequest request){
        User currentUser = SecurityHelper.getCurrentUser(this.userService);
        List<String> contacts =  this.userService.findContacts(currentUser.getId());
        return contacts;
    }
}