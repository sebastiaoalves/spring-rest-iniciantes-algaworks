package br.uern.di.poo.api.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.uern.di.poo.api.model.ComentarioInput;
import br.uern.di.poo.api.model.ComentarioModel;
import br.uern.di.poo.domain.exception.EntidadeNaoEncontradaException;
import br.uern.di.poo.domain.model.Comentario;
import br.uern.di.poo.domain.model.OrdemServico;
import br.uern.di.poo.domain.repository.OrdemServicoRepository;
import br.uern.di.poo.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordemservico/{ordemServicoId}/comentarios")
public class ComentarioController {
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@PathVariable Long ordemServicoId,
			@Valid @RequestBody ComentarioInput comentarioInput) {
		Comentario comentario = gestaoOrdemServico.adicionarComentario(ordemServicoId, 
				comentarioInput.getDescricao());
		
		return toModel(comentario);
	}
	
	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow( () -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada."));
		return toCollectionModel(ordemServico.getComentarios());
	}
	
	private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios) {
		return comentarios.stream()
				.map( comentario -> toModel(comentario))
				.collect(Collectors.toList());
	}

	private ComentarioModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioModel.class);
	}
	
	
}
