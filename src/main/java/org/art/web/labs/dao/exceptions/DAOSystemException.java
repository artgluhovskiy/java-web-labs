package org.art.web.labs.dao.exceptions;

/**
 * {@code DAOSystemException} is actually a wrapper
 * for low-level exception types in DAO classes.
 */
public class DAOSystemException extends Exception {

    public DAOSystemException(String message) {
        super(message);
    }

    public DAOSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
