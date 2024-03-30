package org.springframework.core;

// FIXME : It is temporary required for iBatis which is internally used.
// It should be removed after iBatis is no more exist here.
public class NestedIOException extends RuntimeException {

    public NestedIOException(String message) {
        super(message);
    }

    public NestedIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
