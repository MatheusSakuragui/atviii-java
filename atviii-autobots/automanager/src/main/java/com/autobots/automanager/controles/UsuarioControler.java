package com.autobots.automanager.controles;

import java.util.Date;
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

import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.servicos.EmpresaServico;
import com.autobots.automanager.servicos.UsuarioServico;

@RestController
@RequestMapping("/usuario")
public class UsuarioControler {

	@Autowired
	private UsuarioServico usuarioServico;
	
	@Autowired
	private EmpresaServico empresaServico;
	
	@PostMapping("/cadastrar/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario, @PathVariable Long id){
		Empresa empresaSelecionada = empresaServico.pegarPeloId(id);
		
		if(empresaSelecionada != null) {
	        if(usuario.getPerfis().toString().contains("FORNECEDOR")) {
	        	if(usuario.getMercadorias().size() > 0)
	        	empresaSelecionada.getMercadorias().addAll(usuario.getMercadorias());
	        }	
	        
	        usuarioServico.cadastrarUsuario(usuario);
	        
	        empresaSelecionada.getUsuarios().add(usuario);
	        empresaServico.salvar(empresaSelecionada);
	        
	        return new ResponseEntity<> (HttpStatus.CREATED);
	        
		}
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        	
	}
	
	
	@GetMapping("/buscar")
	public ResponseEntity<List<Usuario>> pegarTodos(){
		List<Usuario> usuario = usuarioServico.pegarTodosUsuarios();
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.FOUND);
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Usuario> pegarUm(@PathVariable Long id){
		Usuario usuario = usuarioServico.pegarPeloId(id);
		HttpStatus status = HttpStatus.CONFLICT;
		if(usuario == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Usuario>(usuario, status);
	}
}
