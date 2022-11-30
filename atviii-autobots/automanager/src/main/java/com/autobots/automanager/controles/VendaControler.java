package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioVenda;
import com.autobots.automanager.servicos.VendaServico;


@RestController
@RequestMapping("/venda")
public class VendaControler {

	@Autowired
	private VendaServico vendaServico;
	
	@GetMapping("/buscar")
	public ResponseEntity<List<Venda>> pegarTodos(){
		List<Venda> venda = vendaServico.pegarTodasVendas();
		return new ResponseEntity<List<Venda>>(venda, HttpStatus.FOUND);
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Venda> pegarUm(@PathVariable Long id){
		Venda venda = vendaServico.pegarPeloId(id);
		HttpStatus status = HttpStatus.CONFLICT;
		if(venda == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Venda>(venda, status);
		
		
	}
	
	
}
