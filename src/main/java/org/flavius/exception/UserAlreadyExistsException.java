package org.flavius.exception;

public class UserAlreadyExistsException extends Exception {

    private final String username;

    public UserAlreadyExistsException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
