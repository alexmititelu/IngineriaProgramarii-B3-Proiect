package info;

public class ExceptionCustom extends Exception {

    public ExceptionCustom(String exception){
        super(exception);
    }
    public String getMessage() {
        return super.getMessage();
    }
}
