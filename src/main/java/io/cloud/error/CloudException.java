package io.cloud.error;

public class CloudException extends RuntimeException{
    public CloudException(String message) {
        super(message);
    }
}
