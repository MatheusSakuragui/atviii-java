package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioVenda;
import com.autobots.automanager.servicos.EmpresaServico;
import com.autobots.automanager.servicos.VendaServico;


@RestController
@RequestMapping("/venda")
public class VendaControler {

	@Autowired
	private VendaServico vendaServico;
	
	@Autowired
	private EmpresaServico empresaServico;
	
	/*@PostMapping("/cadastrar/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody Venda venda, @PathVariable Long id){
		Empresa empresaSelecionada = empresaServico.pegarPeloId(id);
		
	}*/
	
	
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
	
	@PutMapping("/atualizar/{id}")
		public ResponseEntity<?> atualizarVenda (@RequestBody Venda venda, @PathVariable Long id){
			Venda vend = vendaServico.pegarPeloId(id);
			if (vend == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			venda.setId(id);
			vendaServico.atualizarVenda(venda);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
	
	
	
}
