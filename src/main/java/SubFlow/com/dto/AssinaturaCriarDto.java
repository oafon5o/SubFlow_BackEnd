package SubFlow.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public record AssinaturaCriarDto(
        @JsonProperty("nomeServico") String nomeServico,
        @JsonProperty("dataVencimento") Date dataVencimento) {
}
