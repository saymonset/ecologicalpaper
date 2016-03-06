
package org.apache.myfaces.examples.misc;

import java.io.IOException;
 
import javax.faces.context.FacesContext;

/**
 * @author Manfred Geiler (latest modification by $Author: grantsmith $)
 * @version $Revision: 472610 $ $Date: 2006-11-08 20:46:34 +0100 (Mi, 08 Nov 2006) $
 */
public class FileUploadForm {
  
    private String _name = "";
   
    public String getName() {
        return _name;
    }
    
    public void setName(String name) {
        _name = name;
    }
    
    public String upload() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        return "ok";
    }
    
    public boolean isUploaded() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getExternalContext().getApplicationMap().get("fileupload_bytes")!=null;
    }
    
}
