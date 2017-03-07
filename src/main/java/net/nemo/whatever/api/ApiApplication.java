package net.nemo.whatever.api;

import net.nemo.whatever.api.exceptionmapper.RuntimeExceptionMapper;
import net.nemo.whatever.api.filter.CORSResponseFilter;
import net.nemo.whatever.api.resources.ChatResource;
import net.nemo.whatever.api.resources.MessageResource;
import net.nemo.whatever.api.resources.TodosResource;
import net.nemo.whatever.api.resources.UserResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Created by tonyshi on 2017/3/3.
 */
public class ApiApplication extends ResourceConfig {


    public ApiApplication(){
        register(TodosResource.class);
        register(ChatResource.class);
        register(MessageResource.class);
        register(UserResource.class);

        register(RequestContextFilter.class);
        register(CORSResponseFilter.class);

        register(RuntimeExceptionMapper.class);

        register(JacksonFeature.class);
    }
}
