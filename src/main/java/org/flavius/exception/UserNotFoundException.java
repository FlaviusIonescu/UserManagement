package org.flavius.exception;

public class UserNotFoundException extends Exception {

    private final String username;

    public UserNotFoundException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
