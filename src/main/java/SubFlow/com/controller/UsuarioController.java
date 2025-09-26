package SubFlow.com.controller;

import SubFlow.com.dto.UsuarioCriarDto;
import SubFlow.com.model.Usuario;
import SubFlow.com.repository.UsuarioRepository;
import SubFlow.com.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
@Tag(name = "Controlador de usuários", description = "Camada responsável pelo gerencimento de usuários da API")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // busca de usuário por id
    @GetMapping("/{id}")
    @Operation(summary = "Visualização de usuários por id", description = "Método responsável por busca de usuários por id")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Usuario> consultarUsuarioPorId(@PathVariable Long id){
        var usuario = usuarioRepository.findById(id).orElse(null);

        if(usuario == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    //Listagem de usuários no geral
    @GetMapping
    @Operation(summary = "Listagem de usuários no geral", description = "Método responsável por listagem de usuários em geral")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> consultarTodosUsuarios(){
        return ResponseEntity.ok(usuarioRepository.findAll());
    }


    //Criação de usuários
    @PostMapping
    @Operation(summary = "Criação de usuários", description = "Método responsável por criação de usuários")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ASSINANTE')")
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioCriarDto usuario){

        try{
            var usuarioResponse = usuarioRepository.save(new Usuario(null,usuario.nome(), usuario.email(), usuario.senha(), usuario.role()));

            return ResponseEntity.ok(usuarioResponse);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
