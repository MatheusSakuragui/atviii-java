package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.repositorios.RepositorioMercadoria;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControler {
	@Autowired
	private RepositorioMercadoria repositorio;
	
	
	@GetMapping("/buscar")
	public ResponseEntity<List<Mercadoria>> pegarTodos(){
		List<Mercadoria> mercadoria = repositorio.findAll();
		return new ResponseEntity<List<Mercadoria>>(mercadoria, HttpStatus.FOUND);
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Mercadoria> pegarUm(@PathVariable Long id){
		Mercadoria mercadoria = repositorio.findById(id).orElse(null);
		HttpStatus status = HttpStatus.CONFLICT;
		if(mercadoria == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Mercadoria>(mercadoria, status);
		
	}
	
	
}
