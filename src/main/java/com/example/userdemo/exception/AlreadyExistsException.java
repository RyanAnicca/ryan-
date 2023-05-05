package com.example.userdemo.exception;



/**
 * AlreadyExistsException
 *
 * @author star
 */
public class AlreadyExistsException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 4775907845387588528L;

    public AlreadyExistsException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.CONFLICT);
    }
}