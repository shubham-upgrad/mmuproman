package proman.service.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

public class UnauthorizedUserException extends Exception {
    private String code;
    private String message;
    public UnauthorizedUserException(String c,String m){
        this.code=c;
        this.message=m;
    }
    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
