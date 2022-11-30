package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.servicos.EmpresaServico;
import com.autobots.automanager.servicos.ServicoServico;

@RestController
@RequestMapping("/servico")
public class ServicoControler {

	@Autowired
	private ServicoServico servicoServico;
	
	@Autowired
	private EmpresaServico servicoEmpresa;
	
	@PostMapping("/cadastrar/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody Servico servico, @PathVariable Long id ){
	Empresa empresa = servicoEmpresa.pegarPeloId(id);
	if (empresa == null) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	empresa.getServicos().add(servico);
	servicoEmpresa.salvar(empresa);
	return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<List<Servico>> pegarTodos(){
		List<Servico> servicos = servicoServico.pegarTodosServicos();
		return new ResponseEntity<List<Servico>>(servicos, HttpStatus.FOUND);
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Servico> pegarUm(@PathVariable Long id){
		Servico servico = servicoServico.pegarPeloId(id);
		HttpStatus status = HttpStatus.CONFLICT;
		if(servico == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Servico>(servico, status);
	}	
}
