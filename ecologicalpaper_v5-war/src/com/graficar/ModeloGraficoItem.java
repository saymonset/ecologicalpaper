/*
 * ModeloGraficoItem.java
 *
 * Created on 11 de noviembre de 2007, 10:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.graficar;

import java.io.Serializable;

import java.util.ArrayList;

 

public class ModeloGraficoItem implements Serializable {

 

    private String mes;

    private String produto;

    private int quantidade;

 

    public ModeloGraficoItem() {

    }

 

    public ModeloGraficoItem(String mes, String produto, int quantidade) {

        this.mes = mes;

        this.produto = produto;

        this.quantidade = quantidade;

    }

 

    public String getMes() {

        return mes;

    }

 

    public void setMes(String mes) {

        this.mes = mes;

    }

 

    public String getProduto() {

        return produto;

    }

 

    public void setProduto(String produto) {

        this.produto = produto;

    }

 

    public int getQuantidade() {

        return quantidade;

    }

 

    public void setQuantidade(int quantidade) {

        this.quantidade = quantidade;

    }

}