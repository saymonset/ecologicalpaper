/*
 * DynamicPageBean.java
 *
 * Created on July 14, 2007, 11:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.util;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.component.html.HtmlOutputText;

import java.util.List;
import java.util.ArrayList;

public class DynamicPageBean {

	//array of values of selected items
	private String[] selectedItems;

	private HtmlOutputText selItemsOutput;

	//List of SelectItem instances
	private List items;

	public DynamicPageBean() {
		items = new ArrayList();
		SelectItem item = new SelectItem("firstName", "First Name");
		items.add(item);
		item = new SelectItem("lastName", "Last Name");
		items.add(item);
		item = new SelectItem("sex", "Sex");
		items.add(item);
		item = new SelectItem("ssn", "SSN");
		items.add(item);
	}

	public void setSelectedItems(String[] items) {
		this.selectedItems = items;
	}

	public String[] getSelectedItems() {
		return selectedItems;
	}

	public void setSelItemsOutput(HtmlOutputText component) {
		selItemsOutput = component;
	}

	public HtmlOutputText getSelItemsOutput() {
		return selItemsOutput;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public List getItems() {
		return items;
	}

	public void showSelected(ActionEvent actionEvent) {
		String str = "";

		int length = selectedItems.length;
		for(int i=0; i<length; i++) {
			str += selectedItems[i]+"\n";
		}

		getSelItemsOutput().setValue(str);
	}
}