package net.nemo.whatever.util;

import net.nemo.whatever.entity.User;
import net.nemo.whatever.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * Created by tonyshi on 2016/12/14.
 */
public class SecurityHelper {

    public static String getCurrentUsername() {
        Subject subject = getSubject();
        PrincipalCollection collection = subject.getPrincipals();
        if (null != collection && !collection.isEmpty()) {
            return (String) collection.iterator().next();
        }
        return null;
    }

    public static User getCurrentUser(UserService userService){
        return userService.findByEmail(getCurrentUsername());
    }

    public static User login(UserService userService, String username, String password, boolean rememberMe) throws AuthenticationException {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe);
        SecurityUtils.getSubject().login(token);

        User user = userService.findByEmail(username);
        SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
        return user;
    }

    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
