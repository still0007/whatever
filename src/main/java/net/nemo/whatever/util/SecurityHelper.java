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

    public static User login(UserService userService, String username, String password, boolean rememberMe) throws AuthenticationException {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe);

        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);

        User loginUser = userService.findByEmail(username);
        currentUser.getSession().setAttribute("currentUser", loginUser);
        return loginUser;
    }

    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
