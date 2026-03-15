package io.github.martinezdom.repairshop.dtos;

public class TokenResponseDTO {
    private String token;

    public TokenResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
