package exception;

public class DepartmentExceptionAlreadyExists extends RuntimeException {
    public DepartmentExceptionAlreadyExists(String message) {
        super(message);
    }
}
