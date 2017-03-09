package net.nemo.whatever.api.exceptionmapper;

import net.nemo.whatever.exception.BusinessException;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tonyshi on 2017/3/3.
 */
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    private Logger logger = Logger.getLogger(BusinessExceptionMapper.class);

    @Override
    public Response toResponse(BusinessException e) {
        logger.error(e);

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error_msg", e.getMessage());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
