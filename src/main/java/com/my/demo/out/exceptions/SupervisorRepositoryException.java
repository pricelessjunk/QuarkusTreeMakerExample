package com.my.demo.out.exceptions;

import com.my.demo.out.repositories.SupervisorRepository;

/**
 * An exception that represents an error in the {@link SupervisorRepository}
 */
public class SupervisorRepositoryException extends RuntimeException {
    /**
     * Constructor
     *
     * @param message the exception message
     */
    public SupervisorRepositoryException(String message) {
        super(message);
    }
}
