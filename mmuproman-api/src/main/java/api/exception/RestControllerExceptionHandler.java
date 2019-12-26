package api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import proman.service.exceptions.AuthenticationFailedException;
import proman.service.exceptions.ResourceNotFoundException;
import proman.service.exceptions.UnauthorizedUserException;
import promanapp.api.model.ErrorResponse;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> resourceNotFoundHandler(ResourceNotFoundException e, WebRequest request){
        ErrorResponse er=new ErrorResponse().message(e.getMessage()).code(e.getCode());
        return new ResponseEntity<ErrorResponse>(er, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({AuthenticationFailedException.class})
    public ResponseEntity<ErrorResponse> authenticationFailedHandler(AuthenticationFailedException e, WebRequest request){
        ErrorResponse er=new ErrorResponse().message(e.getMessage()).code(e.getCode());
        return new ResponseEntity<ErrorResponse>(er, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler({UnauthorizedUserException.class})
    public ResponseEntity<ErrorResponse> authenticationFailedHandler(UnauthorizedUserException e, WebRequest request){
        ErrorResponse er=new ErrorResponse().message(e.getMessage()).code(e.getCode());
        return new ResponseEntity<ErrorResponse>(er, HttpStatus.UNAUTHORIZED);
    }
}
