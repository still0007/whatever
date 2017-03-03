package net.nemo.whatever.api.exceptionmapper;

import net.nemo.whatever.exception.BusinessException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by tonyshi on 2017/3/3.
 */
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException e) {
        return null;
    }
}
