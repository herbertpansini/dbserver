package br.com.dbserver.repository;

import br.com.dbserver.model.Associado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    Page<Associado> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Associado findByCpf(String cpf);
}
