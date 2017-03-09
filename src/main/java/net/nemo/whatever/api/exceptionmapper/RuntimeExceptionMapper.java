package net.nemo.whatever.api.exceptionmapper;

import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tonyshi on 2017/3/3.
 */
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    private Logger logger = Logger.getLogger(RuntimeExceptionMapper.class);

    public Response toResponse(RuntimeException e){
        logger.error(e);

        Response.Status status = null;
        Map<String, Object> errorBody = new HashMap<>();

        if(e instanceof IllegalArgumentException){
            status = Response.Status.BAD_REQUEST;
            errorBody.put("error_msg", e.getMessage());
        }
        else{
            status = Response.Status.INTERNAL_SERVER_ERROR;
            errorBody.put("error_msg", e.getMessage());
        }
        return Response.status(status)
                .entity(errorBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
