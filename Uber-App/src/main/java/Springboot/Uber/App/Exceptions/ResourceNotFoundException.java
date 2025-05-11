package Springboot.Uber.App.Exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
