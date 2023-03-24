package br.com.dias.moviecardgame.webapp.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Simple dto to return health status.
 */
@Data
@Builder
public class HealthDto {

    /**
     * The status of the service
     */
    private Status status;

    /**
     * Message detail
     */
    private String message;

    /**
     * Health status
     */
    public enum Status {
        UP, DOWN
    }

}

