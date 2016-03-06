/*
 * DateHolder.java
 *
 * Created on July 7, 2007, 3:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.apache.myfaces.examples.calendarexample;
import java.io.Serializable;
import java.util.Date;

/**
 * DOCUMENT ME!
 * @author Martin Marinschek (latest modification by $Author: grantsmith $)
 * @version $Revision: 472610 $ $Date: 2006-11-08 19:46:34 +0000 (Wed, 08 Nov 2006) $
 */
public class DateHolder implements Serializable
{
    /**
     * added serial 
     */
    private static final long serialVersionUID = 1L;
    
    private Date _date = null;

    public Date getDate()
    {
        return _date;
    }

    public void setDate(Date date)
    {
        _date = date;
    }
}
