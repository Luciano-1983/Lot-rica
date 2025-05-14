package br.faccat.sistdist.springbootrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.faccat.sistdist.springbootrestapi.entity.Aposta;

@Repository
public interface ApostaRepository extends JpaRepository<Aposta, Long> {
    // Aqui podemos adicionar consultas personalizadas se necessário, por exemplo:
    // List<Aposta> findByNumero(int numero);
}
