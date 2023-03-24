package br.com.dias.moviecardgame.webapp.dto;

import lombok.Data;

/**
 * Dto used to return Exception data by REST api.
 */
@Data
public class ErrorResponse {

    /**
     * Exception type name.
     */
    private String errorType;

    /**
     * Exception message.
     */
    private String errorMessage;

}
