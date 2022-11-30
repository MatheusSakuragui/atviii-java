package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.servicos.EmpresaServico;
import com.autobots.automanager.servicos.MercadoriaServico;
import com.autobots.automanager.servicos.UsuarioServico;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControler {
	@Autowired
	private MercadoriaServico servico;
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	@Autowired 
	EmpresaServico empresaServico;
	
	
	@PostMapping("/cadastrar/{id}")
	
	public ResponseEntity<?> cadastrar(@RequestBody Mercadoria mercadoria, @PathVariable Long id){
		servico.salvar(mercadoria);
		Usuario usuario = usuarioServico.pegarPeloId(id);
		if(usuario == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		for(Empresa empresa : empresaServico.pegarTodasEmpresas()) {
			for (Usuario usuarios : usuarioServico.pegarTodosUsuarios()) {
				if(usuarios.getId().equals(usuario.getId())) {
					empresa.getMercadorias().add(mercadoria);
				}
			}
		}
		usuario.getMercadorias().add(mercadoria);
		usuarioServico.cadastrarUsuario(usuario);
		return new ResponseEntity<> (HttpStatus.CREATED);
	}
	
	
	@GetMapping("/buscar")
	public ResponseEntity<List<Mercadoria>> pegarTodos(){
		List<Mercadoria> mercadoria = servico.pegarTodos();
		if(mercadoria.isEmpty()) {
			return new ResponseEntity<List<Mercadoria>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Mercadoria>>(mercadoria, HttpStatus.FOUND);
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Mercadoria> pegarUm(@PathVariable Long id){
		Mercadoria mercadoria = servico.pegarPeloId(id);
		HttpStatus status = HttpStatus.CONFLICT;
		if(mercadoria == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Mercadoria>(mercadoria, status);
		
	}
	
	
}
