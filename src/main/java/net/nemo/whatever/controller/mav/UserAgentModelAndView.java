package net.nemo.whatever.controller.mav;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Created by tonyshi on 2017/3/7.
 */
public class UserAgentModelAndView extends ModelAndView {

    private Logger logger = Logger.getLogger(UserAgentModelAndView.class);

    private Properties props = new Properties();

    private final String PUBLIC_PATH = "static/build";

    public UserAgentModelAndView(HttpServletRequest request, String viewName){
        super(getUserAgentViewName(request, viewName));

        loadProperties();

        addObject("lastCommit", StringUtils.trimToEmpty(System.getProperty("LAST_COMMIT")));
        addObject("currentUser", SecurityUtils.getSubject().getSession().getAttribute("currentUser"));
    }

    public UserAgentModelAndView(HttpServletRequest request, String viewName, String assetName){
        this(request, viewName);

        addObject("assets", assetsPath(assetName));
    }

    private void loadProperties(){
        try {
            props.load(new ClassPathResource("props/application.properties").getInputStream());
        }catch (IOException e){
            logger.error(e);
        }
    }

    private Map<String, String> assetsPath(String assetName){
        Map<String, String> assetsPath = new HashMap<>();

        if("development".equals(System.getProperty("DEV_ENV"))){
            String assetsDevDomain = props.getProperty("app.assets.development.domain.name");
            for(String type : new String[]{"css", "js"}){
                assetsPath.put(type, String.format("%s/%s/%s.%s", assetsDevDomain, PUBLIC_PATH, assetName, type));
            }
        }
        else{
            Map<String, List<String>> assetsByChunkName = (Map<String, List<String>>) jsonToMap("props/manifest.json").get("assetsByChunkName");
            for(String assets : assetsByChunkName.get(assetName)){
                assetsPath.put(assets.split("\\.")[1], String.format("/%s/%s", PUBLIC_PATH, assets));
            }
        }

        return assetsPath;
    }

    private Map<String, Object> jsonToMap(String fileName){
        ObjectMapper mapper = new ObjectMapper();
        Resource r = new ClassPathResource(fileName);
        try {
            Map<String, Object> map = mapper.readValue(r.getFile(), Map.class);
            return map;
        }catch (IOException e){
            return null;
        }
    }

    private static String getUserAgentViewName(HttpServletRequest request, String viewName) {
        String userAgent = request.getHeader("User-Agent");

        String os = "";

        // =================OS=======================
        if (userAgent.toLowerCase().indexOf("android") >= 0) {
            os = "mobile";
        } else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
            os = "ios";
        } else if (userAgent.toLowerCase().indexOf("windows") >= 0) {
            os = "web";
        } else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
            os = "web";
        } else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
            os = "web";
        } else {
            os = "web";
        }

        return  "web/" + viewName;
    }
}
