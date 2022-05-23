package br.com.alura.forum.controller;


import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	@Autowired
	private TopicoRepository tr;
	
	@Autowired
	private CursoRepository cr;
	
	
	
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso){
		
		if(nomeCurso==null) {
			List<Topico> topicos = tr.findAll(); 
			return TopicoDto.converter(topicos);
		}
			List<Topico> topicos = tr.findByCursoNome(nomeCurso); 
		return TopicoDto.converter(topicos);
	}
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@Valid @RequestBody TopicoForm form,UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cr);
		tr.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = tr.findById(id);
		if(topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesTopicoDto(topico.get()));
			
			}
		return ResponseEntity.notFound().build();
		
		
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar (@PathVariable Long id,@Valid @RequestBody AtualizacaoTopicoForm form) {
	Optional<Topico> optional  = tr.findById(id);
		
	if(optional.isPresent()) {
		Topico topico = form.atualizar(id,tr);
		return ResponseEntity.ok(new TopicoDto(topico));
	}
		
		
		return ResponseEntity.notFound().build();
		}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Topico> optional  = tr.findById(id);
		
		if(optional.isPresent()) {
			tr.deleteById(id);
			return ResponseEntity.ok().build();
		}	
		
		return ResponseEntity.notFound().build();
		
	}
	
}
