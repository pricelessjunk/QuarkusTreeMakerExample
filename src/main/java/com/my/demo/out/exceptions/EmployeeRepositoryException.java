package com.my.demo.out.exceptions;

import com.my.demo.out.repositories.EmployeeRepository;

/**
 * An exception that represents an error in the {@link EmployeeRepository}
 */
public class EmployeeRepositoryException extends RuntimeException {

    /**
     * Constructor
     *
     * @param message the exception message
     */
    public EmployeeRepositoryException(String message) {
        super(message);
    }
}
