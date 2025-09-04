package co.com.mrcompany.api.dto.response;

import lombok.Builder;

public class TokenResponse {
    private String     token;
    private String     tokenRefresh;
    private Boolean    isValid;
}
