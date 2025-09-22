package SubFlow.com.repository;

import SubFlow.com.model.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssinaturaRepository extends JpaRepository <Assinatura, Long> {

    Optional<Assinatura> findById(Long id);
}
