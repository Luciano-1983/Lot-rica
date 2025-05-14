package br.faccat.sistdist.springbootrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.faccat.sistdist.springbootrestapi.entity.Resultado;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    // Aqui podemos adicionar métodos personalizados, se necessário
}
