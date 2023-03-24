package br.com.dias.moviecardgame.abs.exception;

public abstract class GameException extends Exception {
    public GameException(String message){
        super(message);
    }
}
