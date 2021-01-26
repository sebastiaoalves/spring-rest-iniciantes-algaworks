package br.uern.di.poo.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.uern.di.poo.domain.exception.NegocioException;
import br.uern.di.poo.domain.model.Cliente;
import br.uern.di.poo.domain.model.OrdemServico;
import br.uern.di.poo.domain.model.StatusOrdemServico;
import br.uern.di.poo.domain.repository.ClienteRepository;
import br.uern.di.poo.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemDeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar (OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow( () -> new NegocioException("Cliente não encontrado."));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemDeRepository.save(ordemServico);
	}
	
}
