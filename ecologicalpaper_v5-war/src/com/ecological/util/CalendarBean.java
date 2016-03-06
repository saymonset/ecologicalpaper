/*
 * CalendarBean.java
 *
 * Created on July 7, 2007, 3:39 PM
 *
 * To change this template, choose Tools | Template ManagersecondDate
 * and open the template in the editor.
 */

package com.ecological.util;
 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Martin Marinschek
 * @version $Revision: $ $Date: $
 *          <p/>
 *          $Log: $
 */
public class CalendarBean implements Serializable
{
    /**
     * serial id for serialisation
     */
    private static final long serialVersionUID = 1L;

    private static Log log = LogFactory.getLog(CalendarBean.class);

    private List _dates;

    private String _text;
    private Date _firstDate;
    private Date _secondDate;

    public List getDates()
    {
        if(_dates == null)
        {
            _dates = new ArrayList();

            for(int i=0; i<3; i++)
                _dates.add(new DateHolder());
        }

        return _dates;
    }

    public void setDates(List dates)
    {
        _dates = dates;
    }

    public String getText()
    {
        return _text;
    }

    public void setText(String text)
    {
        _text = text;
    }

    public Date getFirstDate()
    {
        return _firstDate;
    }

    public void setFirstDate(Date firstDate)
    {
        _firstDate = firstDate;
    }

    public Date getSecondDate()
    {
        return _secondDate;
    }

    public void setSecondDate(Date secondDate)
    {
        _secondDate = secondDate;
    }

    public String submitMethod()
    {
        log.info("submit method called");

        return "submit";
    }


}
