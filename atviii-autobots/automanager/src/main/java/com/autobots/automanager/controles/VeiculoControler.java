package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControler {
	
	@Autowired
	private RepositorioVeiculo repositorio;
	
	@GetMapping("/buscar")
	public ResponseEntity<List<Veiculo>> pegarTodos(){
		List<Veiculo> veiculo = repositorio.findAll();
		return new ResponseEntity<List<Veiculo>>(veiculo, HttpStatus.FOUND);
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Veiculo> pegarUm(@PathVariable Long id){
		Veiculo veiculo = repositorio.findById(id).orElse(null);
		HttpStatus status = HttpStatus.CONFLICT;
		if(veiculo == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Veiculo>(veiculo, status);
		
		
	}
	

}
