/*
 * Tree.java
 *
 * Created on June 24, 2007, 7:49 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.ecological.paper.tree;

import com.ecological.configuracion.Configuracion;
import com.ecological.configuracion.ParametrosClientes;
import com.ecological.dias.feriados.DiasFeriadosBean;
import com.ecological.dias.habiles.DiasHabiles;
import com.ecological.paper.documentos.AreaDocumentos;
import com.ecological.paper.documentos.Doc_detalle;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.documentos.Doc_tipo;
import com.ecological.paper.documentos.ScannerDoc;
import com.ecological.paper.flows.FlowParalelo;
import com.ecological.paper.historico.Historico;
import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role;
import com.ecological.paper.permisologia.seguridad.Seguridad_Role_Lineal;
import com.ecological.paper.permisologia.seguridad.Seguridad_User;
import com.ecological.paper.permisologia.seguridad.Seguridad_User_Lineal;
import com.ecological.paper.profesion.Profesion;
import com.ecological.paper.subversion.SvnUrlBase;
import com.ecological.paper.usuario.Usuario;

import java.io.File;
import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Entity class Tree
 *
 * @author simon
 */
@Entity
@Table(name = "TREE")
public class Tree implements Serializable {

    @TableGenerator(name = "Tree_Nodo_Gen",
    table = "SEQUENCE_ID_GEN",
    pkColumnName = "GEN_KEY",
    valueColumnName = "GEN_VALUE",
    pkColumnValue = "NODO_ID",
    allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Tree_Nodo_Gen")
    private Long nodo;
    private boolean status;
    @Column(name = "NODOPADRE")
    private Long nodopadre;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "abreviatura")
    private String abreviatura;
    @Column(name = "DESCRIPCION", length = 4000)
    private String descripcion;
    @Column(name = "MAQUINA")
    private String maquina;
    @Column(name = "TIPONODO")
    private long tiponodo;
    @Column(name = "FECHA_CREADO")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fecha_creado;
    @Column(name = "PREFIX", length = 4000)
    private String prefix;
    @ManyToOne
    @JoinColumn(name = "usuario_creador")
    private Usuario usuario_creador;
    //solo es aplicable al cargo..
    //en el cargo .. se repite mucho por el numero de usuario..
    // el que se agarra como referencia al cargo original, tiene de deBaseToUsuario true
    private boolean deBaseToUsuario;
    @OneToMany(mappedBy = "cargo")
    private Collection<Usuario> usuarios = new ArrayList<Usuario>();
    @OneToMany(mappedBy = "area")
    private Collection<Usuario> areas = new ArrayList<Usuario>();
    @OneToMany(mappedBy = "principal")
    private Collection<Usuario> principals = new ArrayList<Usuario>();
    @OneToMany(mappedBy = "empresa")
    private Collection<Usuario> empresas = new ArrayList<Usuario>();
    
    @OneToMany(mappedBy = "empresa")
    private Collection<ParametrosClientes> parametrosClientes = new ArrayList<ParametrosClientes>();
    
    @OneToMany(mappedBy = "empresa")
    private Collection<Profesion> profesions = new ArrayList<Profesion>();
    @OneToMany(mappedBy = "empresa")
    private Collection<Role> roles = new ArrayList<Role>();
    @OneToMany(mappedBy = "empresa")
    private Collection<Doc_tipo> doc_tipos = new ArrayList<Doc_tipo>();
     @OneToMany(mappedBy = "empresa")
    private Collection<Configuracion> Configuracions = new ArrayList<Configuracion>();
      @OneToMany(mappedBy = "empresa")
    private Collection<Doc_maestro> doc_maestros = new ArrayList<Doc_maestro>();
    
    @OneToMany(mappedBy = "empresa")
    private Collection<AreaDocumentos> areaDocumentosLst = new ArrayList<AreaDocumentos>();
    
    

    @OneToMany(mappedBy = "empresa")
    private Collection<SvnUrlBase> urlBaseSVN = new ArrayList<SvnUrlBase>();

     @OneToMany(mappedBy = "empresa")
    private Collection<DiasHabiles> diasHabiless = new ArrayList<DiasHabiles>();

      @OneToMany(mappedBy = "empresa")
    private Collection<DiasFeriadosBean> diasFeriadosBeans = new ArrayList<DiasFeriadosBean>();

    @OneToMany(mappedBy = "tree")
    private Collection<Seguridad_User> seguridad_User = new ArrayList<Seguridad_User>();
    @OneToMany(mappedBy = "tree")
    private Collection<Seguridad_Role> seguridadRole = new ArrayList<Seguridad_Role>();
    @OneToMany(mappedBy = "tree")
    private Collection<Doc_maestro> doc_maestro = new ArrayList<Doc_maestro>();
    @OneToMany(mappedBy = "tree_origen")
    private Collection<Historico> tree_origen = new ArrayList<Historico>();
    @OneToMany(mappedBy = "tree_anterior")
    private Collection<Historico> tree_anterior = new ArrayList<Historico>();
    @OneToMany(mappedBy = "tree_new")
    private Collection<Historico> tree_new = new ArrayList<Historico>();

    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    @OneToMany(mappedBy = "tree")
    private Collection<Seguridad_Role_Lineal> seguridad_Role_Lineal = new ArrayList<Seguridad_Role_Lineal>();
    @OneToMany(mappedBy = "tree")
    private Collection<Seguridad_User_Lineal> seguridad_User_Lineal = new ArrayList<Seguridad_User_Lineal>();
    
    @OneToMany(mappedBy = "tree")
    private Collection<ScannerDoc> scannerDocTree = new ArrayList<ScannerDoc>();
    @Transient
	private Doc_tipo doc_tipo;
    @Transient
    private String strBuscar;
	private Blob imgOracle;
	private byte[] imgPostgres;
	private double sizeImg;
	private String nameFileImg;
	private Integer userByEmpresa=0;
	@Transient
	private File img;
  

    /** Creates a new instance of Tree */
    public Tree() {
    }

    //es un nueva raiz, y nodopadre es null hasta que se llama en  clienteTree el metodo
    // servicioClientes.actualizaNodoPadreNull();
 /*   public Tree(String nombre,String Descripcion,long tiponodo,Date Fecha_Creado,String maquina) {
    this.nombre=nombre;
    this.descripcion=Descripcion;
    this.fecha_creado=Fecha_Creado;
    this.tiponodo=tiponodo;
    this.maquina=maquina;

    }
    public Tree(Long nodoPadre,String nombre,String Descripcion,long tiponodo,Date Fecha_Creado,String maquina) {
    this.nodopadre=nodoPadre;
    this.nombre=nombre;
    this.descripcion=Descripcion;
    this.fecha_creado=Fecha_Creado;
    this.tiponodo=tiponodo;
    this.maquina=maquina;

    }*/
    /**
     * Returns a hash code value for the object.  This implementation computes
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getNodo() != null ? this.getNodo().hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this Tree.  The result is
     * <code>true</code> if and only if the argument is not null and is a Tree object that
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    /**
     * Returns a string representation of the object.  This implementation constructs
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "com.ecological.paper.tree.Tree[id=" + getNodo() + "]";
    }

    public Long getNodo() {
        return nodo;
    }

    public void setNodo(Long nodo) {
        this.nodo = nodo;
    }

    public Long getNodopadre() {
        return nodopadre;
    }

    public void setNodopadre(Long nodopadre) {
        this.nodopadre = nodopadre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getTiponodo() {
        return tiponodo;
    }

    public void setTiponodo(long tiponodo) {
        this.tiponodo = tiponodo;
    }

    public java.util.Date getFecha_creado() {
        return fecha_creado;
    }

    public void setFecha_creado(java.util.Date fecha_creado) {
        this.fecha_creado = fecha_creado;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    /*public boolean equals(Object tree) {
    if  (tree!=null && tree instanceof Tree)  {
    Tree tree1=(Tree)tree;
    if (tree1!=null && tree1.getNodo()!=null && (tree1.getNodo()-this.nodo==0 )) {
    return true;
    }else{
    return false;
    }
    }else{
    return false;
    }

    }*/
    public boolean equals(Object tree) {
        if (tree != null && tree instanceof Tree) {
            Tree rhs = (Tree) tree;
            if (rhs!=null && rhs.getNodo()!=null && this!=null && this.nodo!=null){
            	return this.nodo.equals(rhs.getNodo());	
            }else{
            	return false;
            }
            
        } else {
            return false;
        }
    }

    public Collection<Seguridad_User> getSeguridad_User() {
        return seguridad_User;
    }

    public void setSeguridad_User(Collection<Seguridad_User> seguridad_User) {
        this.seguridad_User = seguridad_User;
    }

    public Collection<Seguridad_Role> getSeguridadRole() {
        return seguridadRole;
    }

    public void setSeguridadRole(Collection<Seguridad_Role> seguridadRole) {
        this.seguridadRole = seguridadRole;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Collection<Doc_maestro> getDoc_maestro() {
        return doc_maestro;
    }

    public void setDoc_maestro(Collection<Doc_maestro> doc_maestro) {
        this.doc_maestro = doc_maestro;
    }

    public boolean isDeBaseToUsuario() {
        return deBaseToUsuario;
    }

    public void setDeBaseToUsuario(boolean deBaseToUsuario) {
        this.deBaseToUsuario = deBaseToUsuario;
    }

    public Collection<Usuario> getAreas() {
        return areas;
    }

    public void setAreas(Collection<Usuario> areas) {
        this.areas = areas;
    }

    public Collection<Usuario> getPrincipals() {
        return principals;
    }

    public void setPrincipals(Collection<Usuario> principals) {
        this.principals = principals;
    }

    public Collection<Usuario> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Collection<Usuario> empresas) {
        this.empresas = empresas;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Collection<Historico> getTree_origen() {
        return tree_origen;
    }

    public void setTree_origen(Collection<Historico> tree_origen) {
        this.tree_origen = tree_origen;
    }

    public Collection<Historico> getTree_anterior() {
        return tree_anterior;
    }

    public void setTree_anterior(Collection<Historico> tree_anterior) {
        this.tree_anterior = tree_anterior;
    }

    public Collection<Historico> getTree_new() {
        return tree_new;
    }

    public void setTree_new(Collection<Historico> tree_new) {
        this.tree_new = tree_new;
    }

    public Collection<Seguridad_Role_Lineal> getSeguridad_Role_Lineal() {
        return seguridad_Role_Lineal;
    }

    public void setSeguridad_Role_Lineal(Collection<Seguridad_Role_Lineal> seguridad_Role_Lineal) {
        this.seguridad_Role_Lineal = seguridad_Role_Lineal;
    }

    public Usuario getUsuario_creador() {
        return usuario_creador;
    }

    public void setUsuario_creador(Usuario usuario_creador) {
        this.usuario_creador = usuario_creador;
    }

    /**
     * @return the profesions
     */
    public Collection<Profesion> getProfesions() {
        return profesions;
    }

    /**
     * @param profesions the profesions to set
     */
    public void setProfesions(Collection<Profesion> profesions) {
        this.profesions = profesions;
    }

    /**
     * @return the roles
     */
    public Collection<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    /**
     * @return the doc_tipos
     */
    public Collection<Doc_tipo> getDoc_tipos() {
        return doc_tipos;
    }

    /**
     * @param doc_tipos the doc_tipos to set
     */
    public void setDoc_tipos(Collection<Doc_tipo> doc_tipos) {
        this.doc_tipos = doc_tipos;
    }

    /**
     * @return the Configuracions
     */
    public Collection<Configuracion> getConfiguracions() {
        return Configuracions;
    }

    /**
     * @param Configuracions the Configuracions to set
     */
    public void setConfiguracions(Collection<Configuracion> Configuracions) {
        this.Configuracions = Configuracions;
    }

    /**
     * @return the diasHabiless
     */
    public Collection<DiasHabiles> getDiasHabiless() {
        return diasHabiless;
    }

    /**
     * @param diasHabiless the diasHabiless to set
     */
    public void setDiasHabiless(Collection<DiasHabiles> diasHabiless) {
        this.diasHabiless = diasHabiless;
    }

    /**
     * @return the diasFeriadosBeans
     */
    public Collection<DiasFeriadosBean> getDiasFeriadosBeans() {
        return diasFeriadosBeans;
    }

    /**
     * @param diasFeriadosBeans the diasFeriadosBeans to set
     */
    public void setDiasFeriadosBeans(Collection<DiasFeriadosBean> diasFeriadosBeans) {
        this.diasFeriadosBeans = diasFeriadosBeans;
    }

    /**
     * @return the doc_maestros
     */
    public Collection<Doc_maestro> getDoc_maestros() {
        return doc_maestros;
    }

    /**
     * @param doc_maestros the doc_maestros to set
     */
    public void setDoc_maestros(Collection<Doc_maestro> doc_maestros) {
        this.doc_maestros = doc_maestros;
    }

	public Collection<SvnUrlBase> getUrlBaseSVN() {
		return urlBaseSVN;
	}

	public void setUrlBaseSVN(Collection<SvnUrlBase> urlBaseSVN) {
		this.urlBaseSVN = urlBaseSVN;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Collection<Seguridad_User_Lineal> getSeguridad_User_Lineal() {
		return seguridad_User_Lineal;
	}

	public void setSeguridad_User_Lineal(
			Collection<Seguridad_User_Lineal> seguridad_User_Lineal) {
		this.seguridad_User_Lineal = seguridad_User_Lineal;
	}

	

	public Collection<ParametrosClientes> getParametrosClientes() {
		return parametrosClientes;
	}

	public void setParametrosClientes(
			Collection<ParametrosClientes> parametrosClientes) {
		this.parametrosClientes = parametrosClientes;
	}

	public Collection<AreaDocumentos> getAreaDocumentosLst() {
		return areaDocumentosLst;
	}

	public void setAreaDocumentosLst(Collection<AreaDocumentos> areaDocumentosLst) {
		this.areaDocumentosLst = areaDocumentosLst;
	}

	public Collection<ScannerDoc> getScannerDocTree() {
		return scannerDocTree;
	}

	public void setScannerDocTree(Collection<ScannerDoc> scannerDocTree) {
		this.scannerDocTree = scannerDocTree;
	}

	public Doc_tipo getDoc_tipo() {
		return doc_tipo;
	}

	public void setDoc_tipo(Doc_tipo doc_tipo) {
		this.doc_tipo = doc_tipo;
}
	public String getStrBuscar() {
		return strBuscar;
	}
	public void setStrBuscar(String strBuscar) {
		this.strBuscar = strBuscar;
	}

	public Blob getImgOracle() {
		return imgOracle;
	}

	public void setImgOracle(Blob imgOracle) {
		this.imgOracle = imgOracle;
	}

	public byte[] getImgPostgres() {
		return imgPostgres;
	}

	public void setImgPostgres(byte[] imgPostgres) {
		this.imgPostgres = imgPostgres;
	}

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public double getSizeImg() {
		return sizeImg;
	}

	public void setSizeImg(double sizeImg) {
		this.sizeImg = sizeImg;
	}

	public String getNameFileImg() {
		return nameFileImg;
	}

	public void setNameFileImg(String nameFileImg) {
		this.nameFileImg = nameFileImg;
	}

	public Integer getUserByEmpresa() {
		return userByEmpresa;
	}

	public void setUserByEmpresa(Integer userByEmpresa) {
		this.userByEmpresa = userByEmpresa;
	}

}
