/*
 * Hist_usuarios.java
 *
 * Created on July 2, 2007, 4:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.historico;
import com.ecological.paper.usuario.Usuario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity class Hist_usuarios
 *
 * @author simon
 */
@Entity
@Table(name="Hist_usuarios")
public class Hist_usuarios implements Serializable {
    
    //  @Id
    //  @GeneratedValue(strategy = GenerationType.AUTO)
    @TableGenerator(
    name="HistUserGen",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="HISTUSER_ID",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="HistUserGen")
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FECHA_CONECTO_INICIO")
    private Date fecha_conecto_inicio;
    
    @Transient
    private String fecha_mostrar;
    
    @Column(name="MAQUINA")
    private String maquina;
    @Column(name="USUARIOLOBORRO")
    private Usuario usuarioloborro;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FECHA_BAJA")
    private Date fecha_baja;
    
    private boolean status;

  
    
    @JoinColumn(name="id_usuario")
    @ManyToOne
    private Usuario histusuario;
    
    public Hist_usuarios(Usuario usuario,Date fecha_conecto_inicio,String
            maquina,Usuario usuarioloborro,Date fecha_baja) {
        this.histusuario=usuario;
        this.fecha_conecto_inicio=fecha_conecto_inicio;
        this.maquina=maquina;
        
        this.fecha_baja=fecha_baja;
        this.usuarioloborro=usuarioloborro;
    }
    
    /** Creates a new instance of Hist_usuarios */
    public Hist_usuarios() {
    }
    
    /**
     * Gets the id of this Hist_usuarios.
     * @return the id
     */
    public Long getId() {
        return this.id;
    }
    
    /**
     * Sets the id of this Hist_usuarios to the specified value.
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Returns a hash code value for the object.  This implementation computes
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }
    
    /**
     * Determines whether another object is equal to this Hist_usuarios.  The result is
     * <code>true</code> if and only if the argument is not null and is a Hist_usuarios object that
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hist_usuarios)) {
            return false;
        }
        Hist_usuarios other = (Hist_usuarios)object;
        if (this.getId() != other.getId() && (this.getId() == null || !this.getId().equals(other.getId()))) return false;
        return true;
    }
    
    /**
     * Returns a string representation of the object.  This implementation constructs
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "com.ecological.paper.usuarios.entity.Hist_usuarios[id=" + getId() + "]";
    }
    
 
    public Date getFecha_conecto_inicio() {
        return fecha_conecto_inicio;
    }
    
    public void setFecha_conecto_inicio(Date fecha_conecto_inicio) {
        this.fecha_conecto_inicio = fecha_conecto_inicio;
    }
    
    public String getMaquina() {
        return maquina;
    }
    
    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }
    
    public Usuario getUsuarioloborro() {
        return usuarioloborro;
    }
    
    public void setUsuarioloborro(Usuario usuarioloborro) {
        this.usuarioloborro = usuarioloborro;
    }
    
    
    public Date getFecha_baja() {
        return fecha_baja;
    }
    
    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public Usuario getHistusuario() {
        return histusuario;
    }

    public void setHistusuario(Usuario histusuario) {
        this.histusuario = histusuario;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFecha_mostrar() {
        return fecha_mostrar;
    }

    public void setFecha_mostrar(String fecha_mostrar) {
        this.fecha_mostrar = fecha_mostrar;
    }

  
    
}
