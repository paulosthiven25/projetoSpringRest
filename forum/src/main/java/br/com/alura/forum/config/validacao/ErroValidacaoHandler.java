package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * classe responsável pelo tratamento de exceções de todas as controllers da aplicação
 * @author paulo
 *
 */
@RestControllerAdvice
public class ErroValidacaoHandler {
	@Autowired
	private MessageSource ms;
	
	/**
	 * método que retorna um uma lista
	 * que contém cada mensagem de erro e seus respectivos campos,
	 * quando a exceção MethodArgumentNotValidException for lançada
	 * @param exception - a exceção
	 * @return uma lista de ErrosFormularioDto
	 */
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrosFormularioDto> handle (MethodArgumentNotValidException exception) {
		List<ErrosFormularioDto> errosDto = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		
		fieldErrors.forEach(e -> {
			String msg = ms.getMessage(e,LocaleContextHolder.getLocale());
			ErrosFormularioDto erro = new ErrosFormularioDto(e.getField(),msg); 
			errosDto.add(erro);
		});
		
		return errosDto;
		
	}
}
