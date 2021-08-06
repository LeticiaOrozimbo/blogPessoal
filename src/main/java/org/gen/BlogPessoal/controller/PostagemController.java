package org.gen.BlogPessoal.controller;

import java.util.List;

import org.gen.BlogPessoal.model.Postagem;
import org.gen.BlogPessoal.repository.PostagemRepository;
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
import org.springframework.web.bind.annotation.RestController;

@RestController //Indica que essa classe se trata de um controlador
@RequestMapping("/postagens") //Informa a URI que essa classe será acessada 
@CrossOrigin("*") //Aqui estamos habilitando que nossa API aceite requisições de qualquer origem, como por exemplo, o angular 
public class PostagemController {
	
	@Autowired // Essa notação realiza a instaciação através do Spring, permitindo que todos os serviços do PostagemRepository sejam acessados pela PostagemController
	private PostagemRepository repository;
	
	//Inserção de métodos
	@GetMapping //Permite que ao acessar a URI com o método Get, o método abaixo será apresentado
	public ResponseEntity<List<Postagem>> GetAll(){
		return ResponseEntity.ok(repository.findAll()); //Método FindAll
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable Long id){ //A anotação @PathVariable indica que o valor que entrará dentro da variável Long id, virá com o valor do caminho da URI {id}
		return repository.findById(id).map(resp ->ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build()); //Esse método por padrão retorna um optional que pode vir populado ou nulo, então para valores encontrado teremos um status Ok e para valores não encontados um not found
	}
	
	@GetMapping("/titulo/{titulo}") // Aqui utilizamos dessa forma para que não haja confusão, pois a API entende que depois da / vem um atributo, como acima já temos um caminho inciado por / para que não haja confusão escrevemos dessa forma o API o caminho do título
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping //Incluir dados, não precisamos passar o id no postman pois ele entende que é um dado novo e se autoincrementa/autop
	public ResponseEntity<Postagem> post (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	
	}

	@PutMapping // Alterar um dado existente, então precisaremos passar o id
	public ResponseEntity<Postagem> put (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}
	
	@DeleteMapping("/{id}") // método sem retorno(void)
	public void delete (@PathVariable long id){
		repository.deleteById(id);
	}
}
