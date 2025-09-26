package SubFlow.com.controller;

import SubFlow.com.dto.AssinaturaCriarDto;
import SubFlow.com.model.Assinatura;
import SubFlow.com.model.Usuario;
import SubFlow.com.repository.UsuarioRepository;
import SubFlow.com.services.AssinaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/assinaturas")
@Tag(name = "Controlador de assinaturas", description = "Camada responsável pelo gerencimento de assinaturas da API")
public class AssinaturaController {

    @Autowired
    private AssinaturaService assinaturaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Criação de assinatura
    @PostMapping
    @Operation(summary = "Registro de assinatura", description = "Método responsável por registro de assinatura")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ASSINANTE')")
    public ResponseEntity<Assinatura> registrarAssinatura(@RequestBody AssinaturaCriarDto assinaturaDto){
        try{
            var assinaturaResponse = assinaturaService.registroAssinatura(assinaturaDto);
            return ResponseEntity.ok(assinaturaResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Visualização de status da assinatura (assinante)
    @GetMapping("/minhaAssinatura")
    @Operation(summary = "Visualização de status da assinatura", description = "Método responsável por visualização do status da assinatura do usuário logado")
    //@PreAuthorize("hasAnyRole('ROLE_ASSINANTE', 'ROLE_ADMIN')")
    public ResponseEntity<Assinatura> visualizarAssinatura(Authentication authentication){
        String emailUsuario = authentication.getName();
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(emailUsuario);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = usuarioOptional.get();

        Optional<Assinatura> assinaturaOptional = assinaturaService.visualizarAssinaturas(usuario.getId());

        if (assinaturaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assinaturaOptional.get());
    }

    //Visualização de status da assinatura por id (admin)
    @GetMapping("/{id}")
    @Operation(summary = "Visualização de status da assinatura por id", description = "Método responsável por visualização do status da assinatura de usuários por id")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Assinatura> visualizarAssinaturaPorId(@PathVariable Long id){
        Optional<Assinatura> assinaturaOptional = assinaturaService.visualizarAssinaturasPorId(id);

        if (assinaturaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assinaturaOptional.get());
    }

    //Listagem de status da assinatura de usuários no geral (admin)
    @GetMapping
    @Operation(summary = "Listagem de status da assinatura de usuários no geral", description = "Método responsável por visualização do status da assinatura de usuários de maneira geral")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<Assinatura>> visualizarAssinaturasGeral(){
        List<Assinatura> assinaturas = assinaturaService.visualizarAssinaturasGeral();
        return ResponseEntity.ok(assinaturas);
    }
}
