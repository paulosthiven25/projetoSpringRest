package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;
/**
 * Classe Dto para informacoes de atualizacão referentes a classe Tópico
 * @author paulo
 *
 */
public class AtualizacaoTopicoForm {
	@NotNull @NotEmpty @Length(min = 5)
	private String titulo;
	@NotNull @NotEmpty @Length(min = 10)
	private String mensagem;
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	/**
	 * método para atualizar um recurso do tipo Tópico no BD,baseado na PK do BD
	 * @param id - valor da PK do recurso no BD
	 * @param tr - objeto que possui a logica de persistencia de um Tópico do BD
	 * @return um Tópico atualizado com as novas informaçoẽs
	 */
	public Topico atualizar(Long id, TopicoRepository tr) {
		Topico topico = tr.getOne(id);
		topico.setMensagem(this.mensagem);
		topico.setTitulo(this.titulo);
		return topico;
	}
	
	

}
