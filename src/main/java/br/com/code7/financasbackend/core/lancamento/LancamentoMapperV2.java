package br.com.code7.financasbackend.core.lancamento;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.code7.financasbackend.model.dto.LancamentoDTOV1;
import br.com.code7.financasbackend.model.entity.Lancamento;

@Mapper
public interface LancamentoMapperV2 {

	LancamentoMapperV2 MAPPER = Mappers.getMapper(LancamentoMapperV2.class);

	LancamentoDTOV1 mapperLancamentoToDto(Lancamento lancamento);

	Lancamento mapperDtoToLancamento(LancamentoDTOV1 dto);
}
