/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecological.util;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import java.util.*;
/**
 *
 * @author simon
 */

public class Traductor {

    public static void main(String[] args) {
        try {
            String translatedText = Translate.translate("Hola nuevo Mundo", Language.SPANISH, Language.ENGLISH);
            System.out.println(translatedText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String  traducirPalabra(String palabra) {
        String translatedText = "";
        try {
            Locale locale = Locale.getDefault();
            translatedText = Translate.translate(palabra, Language.SPANISH, Language.ENGLISH);
            System.out.println(translatedText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return translatedText;
    }
}
