package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/usuario")
public class UsuarioControler {

	@Autowired
	private RepositorioUsuario repositorio;
	
	@GetMapping("/buscar")
	public ResponseEntity<List<Usuario>> pegarTodos(){
		List<Usuario> usuario = repositorio.findAll();
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.FOUND);
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Usuario> pegarUm(@PathVariable Long id){
		Usuario usuario = repositorio.findById(id).orElse(null);
		HttpStatus status = HttpStatus.CONFLICT;
		if(usuario == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Usuario>(usuario, status);
		
		
	}
	
}
