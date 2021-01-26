package br.uern.di.poo.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.uern.di.poo.domain.model.Cliente;
import br.uern.di.poo.domain.repository.ClienteRepository;
import br.uern.di.poo.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	// @PersistenceContext private EntityManager manager;
	 
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroCliente;
	
	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	/*
	 * var cliente1 = new Cliente(); cliente1.setId(1L);
	 * cliente1.setNome("João da Silva"); cliente1.setTelefone("8888888888");
	 * cliente1.setEmail("joao@joao.com");
	 * 
	 * var cliente2 = new Cliente(); cliente2.setId(2L);
	 * cliente2.setNome("Maria Alves"); cliente2.setTelefone("999999999");
	 * cliente2.setEmail("maria@maria.com");
	 * return Arrays.asList(cliente1, cliente2);
	 *
	 * return manager.createQuery("from Cliente", Cliente.class).getResultList();
	 */
	
	@GetMapping("/{clienteId}")
	//public Cliente buscar(@PathVariable Long clienteId) {
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		if(cliente.isPresent())
			return ResponseEntity.ok(cliente.get());
		else
			return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @Valid @RequestBody Cliente cliente){
		if(!clienteRepository.existsById(clienteId))
			return ResponseEntity.notFound().build();
		
		cliente.setId(clienteId);
		cadastroCliente.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}		
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return cadastroCliente.salvar(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		if(!clienteRepository.existsById(clienteId))
			return ResponseEntity.notFound().build();
		
		cadastroCliente.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}
}