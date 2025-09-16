package SubFlow.com.model;

import SubFlow.com.enums.StatusAssinatura;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "assinatura")
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeServico;
    private Date dataVencimento;

    @Enumerated(EnumType.STRING)
    private StatusAssinatura statusAssinatura;
}
