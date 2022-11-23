package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.servicos.EmpresaServico;

@RestController
@RequestMapping("/empresa")
public class EmpresaControle {
	
	@Autowired
	private EmpresaServico servico;
	
	
	@GetMapping("/empresas")
	public ResponseEntity<List<Empresa>> pegarTodos(){
		List<Empresa> todos = servico.pegarTodasEmpresas();
		HttpStatus status = HttpStatus.CONFLICT;
		if(todos.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<List<Empresa>>(status);
		}else {
			status = HttpStatus.FOUND;
			ResponseEntity<List<Empresa>> resposta = new ResponseEntity<List<Empresa>>(todos, status);
			return resposta;
		}
	}

	@GetMapping("/empresas/{id}")
	public ResponseEntity<Empresa> pegarUsuarioEspecifico(@PathVariable Long id){
		Empresa empresa = servico.pegarPeloId(id);
		if(empresa == null) {
			return new ResponseEntity<Empresa>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Empresa>(empresa, HttpStatus.FOUND);
		}
	}
	
	/*@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Empresa atualizador){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		List<Empresa> usuarios = empresaServico.pegarTodas();
		atualizador.setId(id);
		Empresa usuario = selecionador.selecionar(usuarios, id);
		if (usuario != null) {
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}*/
}