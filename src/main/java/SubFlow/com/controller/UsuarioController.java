package SubFlow.com.controller;

import SubFlow.com.dto.UsuarioCriarDto;
import SubFlow.com.model.Usuario;
import SubFlow.com.repository.UsuarioRepository;
import SubFlow.com.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "usuarios", description = "Método responsável por busca de usuários por id")
    public ResponseEntity<Usuario> consultarUsuarioPorId(@PathVariable Long id){
        var usuario = usuarioRepository.findById(id).orElse(null);

        if(usuario == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @Operation(summary = "usuarios", description = "Método responsável por listagem de usuários em geral")
    public ResponseEntity<?> consultarTodosUsuarios(){
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "Criação de usuários", description = "Método responsável por criação de usuários")
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioCriarDto usuario){

        try{
            var usuarioResponse = usuarioRepository.save(new Usuario(null,usuario.nome(), usuario.role(), usuario.email(), usuario.senha()));

            return ResponseEntity.ok(usuarioResponse);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
