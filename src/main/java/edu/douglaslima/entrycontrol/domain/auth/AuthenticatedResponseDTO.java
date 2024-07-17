package edu.douglaslima.entrycontrol.domain.auth;

import java.util.Date;

public record AuthenticatedResponseDTO(String username, String token, Date expiration) {

}
