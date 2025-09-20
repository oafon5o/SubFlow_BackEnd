package SubFlow.com.services;

import SubFlow.com.model.Assinatura;
import SubFlow.com.repository.AssinaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    public Assinatura registroAssinatura(Assinatura assinatura) {
        return assinaturaRepository.save(assinatura);
    }
}
