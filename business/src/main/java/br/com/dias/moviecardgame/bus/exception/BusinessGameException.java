package br.com.dias.moviecardgame.bus.exception;

import br.com.dias.moviecardgame.abs.exception.GameException;

/**
 * Business exception class.
 * If some business rules is not validated correctly, a {@link BusinessGameException} will be thrown.
 */
public class BusinessGameException extends GameException {

    /**
     * Constructor with message to explain the failed rule.
     *
     * @param message the message
     */
    public BusinessGameException(String message){
        super(message);
    }
}
