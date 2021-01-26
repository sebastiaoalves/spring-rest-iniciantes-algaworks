package br.uern.di.poo.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.uern.di.poo.domain.exception.EntidadeNaoEncontradaException;
import br.uern.di.poo.domain.exception.NegocioException;
import br.uern.di.poo.domain.model.Cliente;
import br.uern.di.poo.domain.model.Comentario;
import br.uern.di.poo.domain.model.OrdemServico;
import br.uern.di.poo.domain.model.StatusOrdemServico;
import br.uern.di.poo.domain.repository.ClienteRepository;
import br.uern.di.poo.domain.repository.ComentarioRepository;
import br.uern.di.poo.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemDeServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar (OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow( () -> new NegocioException("Cliente não encontrado."));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemDeServicoRepository.save(ordemServico);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
		
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		ordemServico.finalizar();
		ordemDeServicoRepository.save(ordemServico);		
	}

	private OrdemServico buscar(Long ordemServicoId) {
		return ordemDeServicoRepository.findById(ordemServicoId)
				.orElseThrow( () -> new EntidadeNaoEncontradaException("Ordem de servico não encontrada."));
	}
	
}
