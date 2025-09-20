package SubFlow.com.services;

import SubFlow.com.dto.LoginRequestDto;
import SubFlow.com.model.Token;
import SubFlow.com.model.Usuario;
import SubFlow.com.repository.TokenRepository;
import SubFlow.com.repository.UsuarioRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.secretkey}")
    private String secret;

    @Value("${spring.tempo_expiracao}")
    private Long timeExpiration;

    private String emissor = "SubFlow";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String gerarToken(LoginRequestDto loginRequestDto){
        var usuario = usuarioRepository.findByEmail(loginRequestDto.email()).orElse(null);

        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(usuario.getEmail())
                .withExpiresAt(this.gerarDataExpiracao())
                .sign(algorithm);

        tokenRepository.save(new Token(null, token, usuario));
        return token;
    }

    public Usuario validarToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(emissor).build();
        verifier.verify(token);

        var tokenResult = tokenRepository.findByToken(token).orElse(null);

        if (tokenResult == null) {
            throw new IllegalArgumentException("Token inválido!");
        }

        return tokenResult.getUsuario();
    }

    private Instant gerarDataExpiracao() {
        var dataAtual = LocalDateTime.now();
        dataAtual = dataAtual.plusMinutes(timeExpiration);

        return dataAtual.toInstant(ZoneOffset.of("-03:00"));
    }
}
