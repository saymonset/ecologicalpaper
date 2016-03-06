package com.ecological.validator;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class IsNumerico implements Validator, Serializable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	  public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
	            throws ValidatorException {
	        if (!(arg2 instanceof Integer)) {
	            throw new ValidatorException(new FacesMessage("caracteresnumericos"));
	        }

	       
	    }
}
