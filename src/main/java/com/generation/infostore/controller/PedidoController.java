package com.generation.infostore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.infostore.model.Pedido;
import com.generation.infostore.repository.ClienteRepository;
import com.generation.infostore.repository.PedidoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedidoController {
	//Criando um novo objeto de injeção
			@Autowired
			private PedidoRepository pedidoRepository;

			@Autowired
			private ClienteRepository clienteRepository;

			@GetMapping
			public ResponseEntity<List<Pedido>> getAll(){
				return ResponseEntity.ok(pedidoRepository.findAll());
			}// MESMA COISA QUE SELECT * FROM tb_pedidos;

			//Pesquisa por id
			@GetMapping("/{id}")
			public ResponseEntity<Pedido> getById(@PathVariable Long id){
				return pedidoRepository.findById(id)
						.map(resposta -> ResponseEntity.ok(resposta))
						.orElse(ResponseEntity.notFound().build());
			}// SELECT * FROM tb_pedidos WHERE id = ?;

			//Pesquisa por Produto
			@GetMapping("/produto/{produto}")
			public ResponseEntity<List<Pedido>> getAllByProduto(@PathVariable String produto){
				return ResponseEntity.ok(pedidoRepository.findAllByProdutoContainingIgnoreCase(produto));
			}// MESMA COISA QUE SELECT * FROM tb_pedidos WHERE produto LIKE "%?%";

			//Criar novo
			@PostMapping
			public ResponseEntity<Pedido> post(@Valid @RequestBody Pedido pedido){
				if(pedido.getCliente() != null && pedido.getCliente().getId() != null && clienteRepository.existsById(pedido.getCliente().getId())) {
					pedido.setId(null);

					return ResponseEntity.status(HttpStatus.CREATED)
							.body(pedidoRepository.save(pedido));
				} else {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O cliente não existe!", null);
				}
			}// INSERT INTO tb_pedidos(produto, descricao, valor, cliente_id) VALUES(?,?,?,?)

			//Atualizar
			@PutMapping
			public ResponseEntity<Pedido> put(@Valid @RequestBody Pedido pedido){

				if(pedido.getId() != null && pedidoRepository.existsById(pedido.getId())) {

					if (pedido.getCliente() != null && pedido.getCliente().getId() != null && clienteRepository.existsById(pedido.getCliente().getId())) {
						return ResponseEntity.ok(pedidoRepository.save(pedido));
					}

					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O cliente não existe!", null);

				}

				return ResponseEntity.notFound().build();
			}// UPDATE tb_pedidos SET produto = ?, descricao = ?, valor = ? WHERE id = ?;

			//Delete
			@ResponseStatus(HttpStatus.NO_CONTENT)
			@DeleteMapping("/{id}")
			public void delete(@PathVariable Long id){
				Optional<Pedido> pedido = pedidoRepository.findById(id);

				if(pedido.isEmpty()) 
					throw new ResponseStatusException(HttpStatus.NOT_FOUND);

				pedidoRepository.deleteById(id);
			}// DELETE FROM tb_pedidos WHERE id = ?;
}
