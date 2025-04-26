package Parcial.model.jtw;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
    @JsonProperty("access_token")
    String accesssToken,
    @JsonProperty("refresh_token")
    String refreshToken
) {

}