package rg_oficina_backend.dto;

public class AcessoDTO {

    private String token;

    // 1. Empty Constructor (often needed by frameworks)
    public AcessoDTO() {
    }

    // 2. Constructor with arguments (This fixes your red line)
    public AcessoDTO(String token) {
        this.token = token;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}