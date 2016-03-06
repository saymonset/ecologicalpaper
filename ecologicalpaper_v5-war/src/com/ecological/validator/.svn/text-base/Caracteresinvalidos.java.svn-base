package com.ecological.validator;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;

import com.ecological.util.ContextSessionRequest;

public class Caracteresinvalidos implements Validator, Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		String cadena = arg2.toString();
		if (!esValidaCadena(cadena)) {
			throw new ValidatorException(new FacesMessage("caracteresinvalidos"));
		}
		// TODO Auto-generated method stub
	}

	private boolean esValidaCadena(String cadena) {
		boolean sw = true;
		for (int i = 0; i < cadena.length(); i++) {
			if (!esValido(new Character(cadena.charAt(i)))) {
				return false;
			}
		}
		return sw;
	}

	private boolean esValido(Character caracter) {
		char c = caracter.charValue();
		char comillita = '\'';
		// if ( !(Character.isLetter(c)) || c==' ' || c==8 ||
		// !(Character.isDigit(c))) { /* aceptamos el ingreso de espacios o el
		// backspace*/
		if (c == comillita) { /* aceptamos el ingreso de espacios o el backspace */
			return false;
		} else {
			return true;
		}
	}
	
	
	 
}
