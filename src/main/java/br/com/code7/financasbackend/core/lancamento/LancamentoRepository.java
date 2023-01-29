package br.com.code7.financasbackend.core.lancamento;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.code7.financasbackend.model.entity.Lancamento;

public interface LancamentosRepository extends JpaRepository<Lancamento, Long> {

}
