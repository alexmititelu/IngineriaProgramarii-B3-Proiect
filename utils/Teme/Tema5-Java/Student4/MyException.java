public class MyException extends Exception {
	
	public MyException () {
		super();
	}
	
	public MyException(String msg) {
		super(msg);
	}
	
	public MyException(String msg, Throwable ex) {
		super(msg, ex);
	}
}

