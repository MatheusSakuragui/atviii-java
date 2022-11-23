package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.repositorios.RepositorioEmpresa;

@Service
public class EmpresaServico {
	
	@Autowired
	private RepositorioEmpresa repositorio;
	
	public List<Empresa> pegarTodasEmpresas(){
		List<Empresa> empresas = repositorio.findAll();
		return empresas;
	}
	
	public Empresa pegarPeloId(Long id) {
		Empresa empresa = repositorio.getById(id);
		return empresa;
	}
	
	public Empresa atualizarEmpresa(Empresa empresa) {
		Empresa novaEmpresa = pegarPeloId(empresa.getId());
		updateData(novaEmpresa, empresa);
		return repositorio.save(novaEmpresa);
	}
	
	private void updateData(Empresa newObj, Empresa obj) {
		newObj.setEndereco(null);
		newObj.setNomeFantasia(null);
		
	}
}