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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.servicos.UsuarioServico;
import com.autobots.automanager.servicos.VeiculoServico;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControler {
	
	@Autowired
	private VeiculoServico veiculosServico;
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	@PostMapping("cadastrar/{id}")
	public ResponseEntity<?> cadastrar (@RequestBody Veiculo veiculo, @PathVariable Long id){
		Usuario usuario = usuarioServico.pegarPeloId(id);
		if(usuario == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			usuario.getVeiculos().add(veiculo);
			veiculo.setProprietario(usuario);
			veiculosServico.cadastrarVeiculo(veiculo);
			usuarioServico.cadastrarUsuario(usuario);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<List<Veiculo>> pegarTodos(){
		List<Veiculo> veiculo = veiculosServico.pegarTodosVeiculos();
		return new ResponseEntity<List<Veiculo>>(veiculo, HttpStatus.FOUND);
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Veiculo> pegarUm(@PathVariable Long id){
		Veiculo veiculo = veiculosServico.pegarPeloId(id);
		HttpStatus status = HttpStatus.CONFLICT;
		if(veiculo == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Veiculo>(veiculo, status);
		
		
	}
	

}
