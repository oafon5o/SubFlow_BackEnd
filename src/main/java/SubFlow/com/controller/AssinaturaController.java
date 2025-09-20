package SubFlow.com.controller;

import SubFlow.com.dto.AssinaturaCriarDto;
import SubFlow.com.enums.StatusAssinatura;
import SubFlow.com.model.Assinatura;
import SubFlow.com.repository.AssinaturaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/assinaturas")
@Tag(name = "Controlador de assinaturas", description = "Camada responsável pelo gerencimento de assinaturas da API")
public class AssinaturaController {

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    @PostMapping
    @Operation(summary = "Registro de assinatura", description = "Método responsável por registro de assinatura")
    public ResponseEntity<?> registrarAssinatura(@RequestBody AssinaturaCriarDto assinatura){

        try{
            var assinaturaResponse = assinaturaRepository.save(new Assinatura(null, assinatura.nomeServico(), assinatura.datavencimento(), StatusAssinatura.ATIVA));

            return ResponseEntity.ok(assinaturaResponse);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
