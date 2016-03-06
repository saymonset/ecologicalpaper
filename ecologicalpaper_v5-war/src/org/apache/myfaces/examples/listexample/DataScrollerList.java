/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.examples.listexample;

import com.ecological.util.ContextSessionRequest;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;



/**
 * DOCUMENT ME!
 * @author Thomas Spiegl (latest modification by $Author: grantsmith $)
 * @version $Revision: 472610 $ $Date: 2006-11-08 19:46:34 +0000 (Wed, 08 Nov 2006) $
 */
public class DataScrollerList extends ContextSessionRequest {
    private List _list = new ArrayList();
    
    public DataScrollerList() {
        for (int i = 1; i < 995; i++) {
            _list.add(new SimpleCar(i, "Car Type " + i, "blue"));
        }
    }
    
    public List getList() {
        return _list;
    }
    
    public void scrollerAction(ActionEvent event) {
        
        FacesContext context = FacesContext.getCurrentInstance();
        String rowsCount = super.getRequestParameter(context, "rowsCount");
        String displayedRowsCountVar = super.getRequestParameter(context, "displayedRowsCountVar");
        String firstRowIndex = super.getRequestParameter(context, "firstRowIndex");

        
        
        
      
       /* FacesContext.getCurrentInstance().getExternalContext().log(
                "scrollerAction: facet: "
                + scrollerEvent.getScrollerfacet()
                + ", pageindex: "
                + scrollerEvent.getPageIndex());*/
    }
}
