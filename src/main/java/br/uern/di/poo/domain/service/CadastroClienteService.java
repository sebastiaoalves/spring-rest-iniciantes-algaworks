package br.uern.di.poo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.uern.di.poo.domain.exception.NegocioException;
import br.uern.di.poo.domain.model.Cliente;
import br.uern.di.poo.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente salvar (Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if(clienteExistente != null  && !clienteExistente.equals(cliente))
			throw new NegocioException("Já existe um cliente com este e-mail");
		
		return clienteRepository.save(cliente);
	}
	
	public void excluir (Long idCliente) {
		clienteRepository.deleteById(idCliente);
	}

}
