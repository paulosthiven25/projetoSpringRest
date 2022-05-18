package br.com.alura.forum.config.validacao;

public class ErrosFormularioDto {
	private String campo;
	private String erro;
	
	public ErrosFormularioDto(String campo, String erro) {
		
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}
	
	
	
	
}
