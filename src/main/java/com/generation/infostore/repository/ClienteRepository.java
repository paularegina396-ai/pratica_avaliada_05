package com.generation.infostore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.infostore.model.Cliente;



public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	public List<Cliente> findAllByNomeContainingIgnoreCase(String nome);
	// SELECT * FROM tb_produtos WHERE nome LIKE "%?%" onde esta o ?  na variavel nome

}
