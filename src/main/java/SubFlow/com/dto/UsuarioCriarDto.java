package SubFlow.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UsuarioCriarDto (
        @JsonProperty("nome") String nome,
        @JsonProperty("email") String email,
        @JsonProperty("senha") String senha,
        @JsonProperty("role") String role) {
}
