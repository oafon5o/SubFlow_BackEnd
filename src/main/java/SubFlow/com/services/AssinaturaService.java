package SubFlow.com.services;

import SubFlow.com.dto.AssinaturaCriarDto;
import SubFlow.com.enums.StatusAssinatura;
import SubFlow.com.model.Assinatura;
import SubFlow.com.repository.AssinaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    // Metodo para registro de assinaturas
    public Assinatura registroAssinatura(AssinaturaCriarDto assinaturaDto) {
        return assinaturaRepository.save(new Assinatura(null, assinaturaDto.nomeServico(), assinaturaDto.dataVencimento(), StatusAssinatura.ATIVA));
    }

    // Metodo responsavel para admin ver todas os status de assinaturas
    public List<Assinatura> visualizarAssinaturasGeral(){
        return assinaturaRepository.findAll();
    }

    // Metodo responsavel para admin ver assinaturas por id
    public Optional<Assinatura> visualizarAssinaturasPorId(Long id){
        return assinaturaRepository.findById(id);
    }

    // Metodo responsavel para admin e assinante ver o status de sua assinatura
    public Optional<Assinatura> visualizarAssinaturas(Long usuarioId){
        return assinaturaRepository.findById(usuarioId);
    }
}
