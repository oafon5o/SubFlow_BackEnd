package SubFlow.com.services;

import SubFlow.com.dto.LoginRequestDto;
import SubFlow.com.model.Usuario;
import SubFlow.com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // salvará o usuário com senha criptografada
    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // irá validar o login usando a DTO
    public boolean validarSenha(LoginRequestDto login) {
        Usuario usuario = usuarioRepository.findByEmail(login.email()).orElse(null);

        // se o usuario existir, vai comparar a senha informada com a criptografada no banco
        if (usuario != null){
            return login.senha().equals(usuario.getSenha());
        }

        // se não retorna falso
        return false;
    }
}
