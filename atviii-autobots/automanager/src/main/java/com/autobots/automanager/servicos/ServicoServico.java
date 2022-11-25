package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.repositorios.RepositorioServico;

public class ServicoServico {
	@Autowired
	private RepositorioServico repositorio;
	
	public List<Servico> pegarTodosServicos(){
		List<Servico> servicos = repositorio.findAll();
		return servicos;
	}
	
	public Servico pegarPeloId(Long id) {
		Servico achar = repositorio.getById(id);
		return achar;
	}
	
	public Servico update(Servico obj) {
		Servico newObj = pegarPeloId(obj.getId());
		updateData(newObj, obj);
		return repositorio.save(newObj);
	}
	
	private void updateData(Servico newObj, Servico obj) {
		newObj.setDescricao(obj.getDescricao());
		newObj.setNome(obj.getNome());
		newObj.setValor(obj.getValor());
	}
}