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
package org.apache.myfaces.examples.tree;

 

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
import javax.faces.event.ActionEvent;
import java.io.Serializable;

/**
 * Backer bean for use in example.  Basically makes a TreeNode available.
 *
 * @author Sean Schofield
 * @version $Revision: 472610 $ $Date: 2006-11-08 20:46:34 +0100 (Mi, 08 Nov 2006) $
 */
public class TreeBacker implements Serializable
{
    /**
     * serial id for serialisation versioning
     */
    private static final long serialVersionUID = 1L;
   
    

    /**
     * NOTE: This is just to show an alternative way of supplying tree data.  You can supply either a
     * TreeModel or TreeNode.
     *
     * @return TreeModel
     */
    

    private String _nodePath;

    public void setNodePath(String nodePath)
    {
        _nodePath = nodePath;
    }

    public String getNodePath()
    {
        return _nodePath;
    }

    public void checkPath(FacesContext context, UIComponent component, java.lang.Object value)
    {
        // make sure path is valid (leaves cannot be expanded or renderer will complain)
        FacesMessage message = null;

       
        
    }

    public void expandPath(ActionEvent event)
    {
        
    }
}
