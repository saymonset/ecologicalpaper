/*
 * Seguridad_Role_Lineal.java
 *
 * Created on 4 de octubre de 2007, 10:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.paper.permisologia.seguridad;

import com.ecological.paper.permisologia.role.Role;
import com.ecological.paper.tree.Tree;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

/**
 * Clase Entidad Seguridad_Role_Lineal
 *
 * @author simon
 */
@Entity
public class Seguridad_Role_Lineal implements Serializable {
    
    @TableGenerator(
    name="SeguridadRole_lineal",
            table="SEQUENCE_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="SEGURIDADROLE_LINEAL",
            allocationSize=1)
            @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="SeguridadRole_lineal")
    private Long codigo;
    
    private boolean toView;
    private boolean status;
    
    @ManyToOne
    @JoinColumn(name="TREE")
    private Tree tree;
    
    @ManyToOne
    @JoinColumn(name="ROLE")
    private Role role;
    
    private boolean toAddDocumentoSvn;
    private boolean toListGruposWorkflow;
    //CONTROL TIEMPO
    private boolean toListarDiasFeriados;
    private boolean toListarDiasHabiles;
    private boolean toAddDiaFeriado;
    private boolean toModDiasHabiles;
    private boolean toModDiaFeriado;
    private boolean toDelDiaFeriado;
    
    
    
    
    
    private boolean toListUsuarios;
    private boolean toAddUsuario;
    private boolean toModUsuario;
    private boolean toDelUsuario;
    
    private boolean toListProfesiones;
    private boolean toAddProfesiones;
    private boolean toModProfesiones;
    private boolean toDelProfesiones;
    
    private boolean toListGrupos;
    private boolean toAddGrupos;
    private boolean toModGrupos;
    private boolean toDelGrupos;
    
    private boolean toListTipoDocumentos;
    private boolean toAddTipoDocumentos;
    private boolean toModTipoDocumentos;
    private boolean toDelTipoDocumentos;
    
    private boolean toMod;
    private boolean toDel;
    private boolean  toMove;
    
    
    //Raiz
    private boolean toAddRaiz;
    
    private boolean  confSeguridad;
    private boolean  confSeguridadGlobal;
    
    //Principal
    private boolean toAddPrincipal;
    
    
    //Area
    private boolean toAddArea;
    
    
    //proceso
    private boolean toAddProceso;
    
    //carpeta
    private boolean toAddCarpeta;
    
    
    //cargo
    private boolean toAddCargo;
    
    
    
    //Lotes de Documentos
    private boolean toAddLotesDeDocumentos;
    
    
    
    //Documentos
    private boolean toAddDocumentos;
    
    
    
    private boolean  toVincDoc;
    private boolean  toDoRegistros;
    private boolean  toDoPublico;
    private boolean toDoFlow;
    //
    
    //solo para configuracion de seguridad
    /*
       Guardar Datos Basicos
       Dar Permisos a Usuarios
       Activar Permiso de los Grupos
     */
    private boolean  toSaveDataBasic;
    private boolean  toGivePermUser;
    private boolean  toActivePermGroup;
    
    //seguridad historico
    private boolean  toHistFlow;
    private boolean  toHistDoc;
  //alterar un workflows.. cambiar sus usuarios por otros
    private boolean toModFlow;
  //guardar una plantilla de flujos para un documento tipo plantilla 
    private boolean toPlantillaInDocFlowParalelo;
    private boolean toSolicitudImpresion;
    private boolean toSolicitudImpresion0;
    private boolean toImprimirAdministrar;
    
    private boolean toListarArea;
    private boolean toDoFlowRevision;
    private boolean toDownload;
    private boolean toViewComentPublic;
    
    /*
     *     private static final String toSolicitudImpresion="toSolicitudImpresion";
    private static final String toImprimirAdministrar="toImprimirAdministrar";
     * */
    
    
    /** Creates a new instance of Seguridad_Role_Lineal */
    
    
    public Seguridad_Role_Lineal( Seguridad_Role_Lineal segMismaParaLosDos) {
        this.setStatus(true);
        
        this.setToListarArea(segMismaParaLosDos.isToListarArea());
        this.setToDoFlowRevision(segMismaParaLosDos.isToDoFlowRevision());
        this.setToDownload(segMismaParaLosDos.isToDownload());
        this.setToViewComentPublic(segMismaParaLosDos.isToViewComentPublic());
        
        
        this.setToImprimirAutorizacion(segMismaParaLosDos.isToImprimirAdministrar());
        this.setToSolicitudImpresion(segMismaParaLosDos.isToSolicitudImpresion());
        this.setToSolicitudImpresion0(segMismaParaLosDos.isToSolicitudImpresion0());
        this.settoPlantillaInDocFlowParalelo(segMismaParaLosDos.istoPlantillaInDocFlowParalelo());
        this.setToModFlow(segMismaParaLosDos.isToModFlow());
        
        this.setToAddDocumentoSvn(segMismaParaLosDos.isToAddDocumentoSvn());
        this.setToListGruposWorkflow(segMismaParaLosDos.isToListGruposWorkflow());

        this.setToListarDiasFeriados(segMismaParaLosDos.isToListarDiasFeriados());
        this.setToListarDiasHabiles(segMismaParaLosDos.isToListarDiasHabiles());
        this.setToAddDiaFeriado(segMismaParaLosDos.isToAddDiaFeriado());
        this.setToModDiasHabiles(segMismaParaLosDos.isToModDiasHabiles());
        this.setToModDiaFeriado(segMismaParaLosDos.isToModDiaFeriado());
        this.setToDelDiaFeriado(segMismaParaLosDos.isToDelDiaFeriado());
        
        
        this.setConfSeguridad(segMismaParaLosDos.isConfSeguridad());
        this.setConfSeguridadGlobal(segMismaParaLosDos.isConfSeguridadGlobal());
        this.setToAddArea(segMismaParaLosDos.isToAddArea());
        this.setToAddCargo(segMismaParaLosDos.isToAddCargo());
        this.setToAddCarpeta(segMismaParaLosDos.isToAddCarpeta());
        this.setToAddDocumentos(segMismaParaLosDos.isToAddDocumentos());
        this.setToAddGrupos(segMismaParaLosDos.isToAddGrupos());
        this.setToAddLotesDeDocumentos(segMismaParaLosDos.isToAddLotesDeDocumentos());
        this.setToAddPrincipal(segMismaParaLosDos.isToAddPrincipal());
        this.setToAddProceso(segMismaParaLosDos.isToAddProceso());
        
        this.setToAddProfesiones(segMismaParaLosDos.isToAddProfesiones());
        
        this.setToAddRaiz(segMismaParaLosDos.isToAddRaiz());
        this.setToAddTipoDocumentos(segMismaParaLosDos.isToAddTipoDocumentos());
        
        this.setToAddUsuario(segMismaParaLosDos.isToAddUsuario());
        this.setToDel(segMismaParaLosDos.isToDel());
        this.setToDelGrupos(segMismaParaLosDos.isToDelGrupos());
        this.setToDelProfesiones(segMismaParaLosDos.isToDelProfesiones());
        this.setToDelTipoDocumentos(segMismaParaLosDos.isToDelTipoDocumentos());
        this.setToDelUsuario(segMismaParaLosDos.isToDelUsuario());
        this.setToDoFlow(segMismaParaLosDos.isToDoFlow());
        this.setToDoPublico(segMismaParaLosDos.isToDoPublico());
        this.setToDoRegistros(segMismaParaLosDos.isToDoRegistros());
        this.setToGivePermUser(segMismaParaLosDos.isToGivePermUser());
        this.setToListGrupos(segMismaParaLosDos.isToListGrupos());
        this.setToListProfesiones(segMismaParaLosDos.isToListProfesiones());
        this.setToListTipoDocumentos(segMismaParaLosDos.isToListTipoDocumentos());
        this.setToListUsuarios(segMismaParaLosDos.isToListUsuarios());
        this.setToMod(segMismaParaLosDos.isToMod());
        this.setToModGrupos(segMismaParaLosDos.isToModGrupos());
        this.setToModProfesiones(segMismaParaLosDos.isToModProfesiones());
        this.setToModTipoDocumentos(segMismaParaLosDos.isToModTipoDocumentos());
        this.setToModUsuario(segMismaParaLosDos.isToModUsuario());
        this.setToMove(segMismaParaLosDos.isToMove());
        this.setToSaveDataBasic(segMismaParaLosDos.isToSaveDataBasic());
        this.setToView(segMismaParaLosDos.isToView());
        this.setToVincDoc(segMismaParaLosDos.isToVincDoc());
        this.setTree(segMismaParaLosDos.getTree());
        this.setRole(segMismaParaLosDos.getRole());
        this.setToActivePermGroup(segMismaParaLosDos.isToActivePermGroup());
        this.setToHistFlow(segMismaParaLosDos.isToHistFlow());
        this.setToHistDoc(segMismaParaLosDos.isToHistDoc());
        
    }
    
    
    public Seguridad_Role_Lineal( Seguridad_User_Lineal segMismaParaLosDos) {
        this.setStatus(true);
        
        this.setToListarArea(segMismaParaLosDos.isToListarArea());
        this.setToDoFlowRevision(segMismaParaLosDos.isToDoFlowRevision());
        this.setToDownload(segMismaParaLosDos.isToDownload());
        this.setToViewComentPublic(segMismaParaLosDos.isToViewComentPublic());
        
        this.setToImprimirAutorizacion(segMismaParaLosDos.isToImprimirAdministrar());
        this.setToSolicitudImpresion(segMismaParaLosDos.isToSolicitudImpresion());
        this.setToSolicitudImpresion0(segMismaParaLosDos.isToSolicitudImpresion0());

        this.settoPlantillaInDocFlowParalelo(segMismaParaLosDos.istoPlantillaInDocFlowParalelo());
        this.setToModFlow(segMismaParaLosDos.isToModFlow());
        this.setToAddDocumentoSvn(segMismaParaLosDos.isToAddDocumentoSvn());
        this.setToListGruposWorkflow(segMismaParaLosDos.isToListGruposWorkflow());

        
        this.setToListarDiasFeriados(segMismaParaLosDos.isToListarDiasFeriados());
        this.setToListarDiasHabiles(segMismaParaLosDos.isToListarDiasHabiles());
        this.setToAddDiaFeriado(segMismaParaLosDos.isToAddDiaFeriado());
        this.setToModDiasHabiles(segMismaParaLosDos.isToModDiasHabiles());
        this.setToModDiaFeriado(segMismaParaLosDos.isToModDiaFeriado());
        this.setToDelDiaFeriado(segMismaParaLosDos.isToDelDiaFeriado());
        this.setConfSeguridadGlobal(segMismaParaLosDos.isConfSeguridadGlobal());
        this.setConfSeguridad(segMismaParaLosDos.isConfSeguridad());
        this.setToAddArea(segMismaParaLosDos.isToAddArea());
        this.setToAddCargo(segMismaParaLosDos.isToAddCargo());
        this.setToAddCarpeta(segMismaParaLosDos.isToAddCarpeta());
        this.setToAddDocumentos(segMismaParaLosDos.isToAddDocumentos());
        this.setToAddGrupos(segMismaParaLosDos.isToAddGrupos());
        this.setToAddLotesDeDocumentos(segMismaParaLosDos.isToAddLotesDeDocumentos());
        this.setToAddPrincipal(segMismaParaLosDos.isToAddPrincipal());
        this.setToAddProceso(segMismaParaLosDos.isToAddProceso());
        
        this.setToAddProfesiones(segMismaParaLosDos.isToAddProfesiones());
        
        this.setToAddRaiz(segMismaParaLosDos.isToAddRaiz());
        this.setToAddTipoDocumentos(segMismaParaLosDos.isToAddTipoDocumentos());
        
        this.setToAddUsuario(segMismaParaLosDos.isToAddUsuario());
        this.setToDel(segMismaParaLosDos.isToDel());
        this.setToDelGrupos(segMismaParaLosDos.isToDelGrupos());
        this.setToDelProfesiones(segMismaParaLosDos.isToDelProfesiones());
        this.setToDelTipoDocumentos(segMismaParaLosDos.isToDelTipoDocumentos());
        this.setToDelUsuario(segMismaParaLosDos.isToDelUsuario());
        this.setToDoFlow(segMismaParaLosDos.isToDoFlow());
        this.setToDoPublico(segMismaParaLosDos.isToDoPublico());
        this.setToDoRegistros(segMismaParaLosDos.isToDoRegistros());
        this.setToGivePermUser(segMismaParaLosDos.isToGivePermUser());
        this.setToListGrupos(segMismaParaLosDos.isToListGrupos());
        this.setToListProfesiones(segMismaParaLosDos.isToListProfesiones());
        this.setToListTipoDocumentos(segMismaParaLosDos.isToListTipoDocumentos());
        this.setToListUsuarios(segMismaParaLosDos.isToListUsuarios());
        this.setToMod(segMismaParaLosDos.isToMod());
        this.setToModGrupos(segMismaParaLosDos.isToModGrupos());
        this.setToModProfesiones(segMismaParaLosDos.isToModProfesiones());
        this.setToModTipoDocumentos(segMismaParaLosDos.isToModTipoDocumentos());
        this.setToModUsuario(segMismaParaLosDos.isToModUsuario());
        this.setToMove(segMismaParaLosDos.isToMove());
        this.setToSaveDataBasic(segMismaParaLosDos.isToSaveDataBasic());
        this.setToView(segMismaParaLosDos.isToView());
        this.setToVincDoc(segMismaParaLosDos.isToVincDoc());
        this.setTree(segMismaParaLosDos.getTree());
        this.setToActivePermGroup(segMismaParaLosDos.isToActivePermGroup());
        this.setToHistFlow(segMismaParaLosDos.isToHistFlow());
        this.setToHistDoc(segMismaParaLosDos.isToHistDoc());
        
    }
    
    
    
    public Seguridad_Role_Lineal() {
    }
    
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    public boolean isToView() {
        return toView;
    }
    
    public void setToView(boolean toView) {
        this.toView = toView;
    }
    
    public Tree getTree() {
        return tree;
    }
    
    public void setTree(Tree tree) {
        this.tree = tree;
    }
    
    public boolean isToListUsuarios() {
        return toListUsuarios;
    }
    
    public void setToListUsuarios(boolean toListUsuarios) {
        this.toListUsuarios = toListUsuarios;
    }
    
    public boolean isToAddUsuario() {
        return toAddUsuario;
    }
    
    public void setToAddUsuario(boolean toAddUsuario) {
        this.toAddUsuario = toAddUsuario;
    }
    
    public boolean isToModUsuario() {
        return toModUsuario;
    }
    
    public void setToModUsuario(boolean toModUsuario) {
        this.toModUsuario = toModUsuario;
    }
    
    public boolean isToDelUsuario() {
        return toDelUsuario;
    }
    
    public void setToDelUsuario(boolean toDelUsuario) {
        this.toDelUsuario = toDelUsuario;
    }
    
    public boolean isToListProfesiones() {
        return toListProfesiones;
    }
    
    public void setToListProfesiones(boolean toListProfesiones) {
        this.toListProfesiones = toListProfesiones;
    }
    
    public boolean isToAddProfesiones() {
        return toAddProfesiones;
    }
    
    public void setToAddProfesiones(boolean toAddProfesiones) {
        this.toAddProfesiones = toAddProfesiones;
    }
    
    public boolean isToModProfesiones() {
        return toModProfesiones;
    }
    
    public void setToModProfesiones(boolean toModProfesiones) {
        this.toModProfesiones = toModProfesiones;
    }
    
    public boolean isToDelProfesiones() {
        return toDelProfesiones;
    }
    
    public void setToDelProfesiones(boolean toDelProfesiones) {
        this.toDelProfesiones = toDelProfesiones;
    }
    
    public boolean isToListGrupos() {
        return toListGrupos;
    }
    
    public void setToListGrupos(boolean toListGrupos) {
        this.toListGrupos = toListGrupos;
    }
    
    public boolean isToAddGrupos() {
        return toAddGrupos;
    }
    
    public void setToAddGrupos(boolean toAddGrupos) {
        this.toAddGrupos = toAddGrupos;
    }
    
    public boolean isToModGrupos() {
        return toModGrupos;
    }
    
    public void setToModGrupos(boolean toModGrupos) {
        this.toModGrupos = toModGrupos;
    }
    
    public boolean isToDelGrupos() {
        return toDelGrupos;
    }
    
    public void setToDelGrupos(boolean toDelGrupos) {
        this.toDelGrupos = toDelGrupos;
    }
    
    public boolean isToListTipoDocumentos() {
        return toListTipoDocumentos;
    }
    
    public void setToListTipoDocumentos(boolean toListTipoDocumentos) {
        this.toListTipoDocumentos = toListTipoDocumentos;
    }
    
    public boolean isToAddTipoDocumentos() {
        return toAddTipoDocumentos;
    }
    
    public void setToAddTipoDocumentos(boolean toAddTipoDocumentos) {
        this.toAddTipoDocumentos = toAddTipoDocumentos;
    }
    
    public boolean isToModTipoDocumentos() {
        return toModTipoDocumentos;
    }
    
    public void setToModTipoDocumentos(boolean toModTipoDocumentos) {
        this.toModTipoDocumentos = toModTipoDocumentos;
    }
    
    public boolean isToDelTipoDocumentos() {
        return toDelTipoDocumentos;
    }
    
    public void setToDelTipoDocumentos(boolean toDelTipoDocumentos) {
        this.toDelTipoDocumentos = toDelTipoDocumentos;
    }
    
    public boolean isToMod() {
        return toMod;
    }
    
    public void setToMod(boolean toMod) {
        this.toMod = toMod;
    }
    
    public boolean isToDel() {
        return toDel;
    }
    
    public void setToDel(boolean toDel) {
        this.toDel = toDel;
    }
    
    public boolean isToMove() {
        return toMove;
    }
    
    public void setToMove(boolean toMove) {
        this.toMove = toMove;
    }
    
    public boolean isToAddRaiz() {
        return toAddRaiz;
    }
    
    public void setToAddRaiz(boolean toAddRaiz) {
        this.toAddRaiz = toAddRaiz;
    }
    
    public boolean isConfSeguridad() {
        return confSeguridad;
    }
    
    public void setConfSeguridad(boolean confSeguridad) {
        this.confSeguridad = confSeguridad;
    }
    
    public boolean isToAddPrincipal() {
        return toAddPrincipal;
    }
    
    public void setToAddPrincipal(boolean toAddPrincipal) {
        this.toAddPrincipal = toAddPrincipal;
    }
    
    public boolean isToAddArea() {
        return toAddArea;
    }
    
    public void setToAddArea(boolean toAddArea) {
        this.toAddArea = toAddArea;
    }
    
    public boolean isToAddProceso() {
        return toAddProceso;
    }
    
    public void setToAddProceso(boolean toAddProceso) {
        this.toAddProceso = toAddProceso;
    }
    
    public boolean isToAddCarpeta() {
        return toAddCarpeta;
    }
    
    public void setToAddCarpeta(boolean toAddCarpeta) {
        this.toAddCarpeta = toAddCarpeta;
    }
    
    public boolean isToAddCargo() {
        return toAddCargo;
    }
    
    public void setToAddCargo(boolean toAddCargo) {
        this.toAddCargo = toAddCargo;
    }
    
    public boolean isToAddLotesDeDocumentos() {
        return toAddLotesDeDocumentos;
    }
    
    public void setToAddLotesDeDocumentos(boolean toAddLotesDeDocumentos) {
        this.toAddLotesDeDocumentos = toAddLotesDeDocumentos;
    }
    
    public boolean isToAddDocumentos() {
        return toAddDocumentos;
    }
    
    public void setToAddDocumentos(boolean toAddDocumentos) {
        this.toAddDocumentos = toAddDocumentos;
    }
    
    public boolean isToVincDoc() {
        return toVincDoc;
    }
    
    public void setToVincDoc(boolean toVincDoc) {
        this.toVincDoc = toVincDoc;
    }
    
    public boolean isToDoRegistros() {
        return toDoRegistros;
    }
    
    public void setToDoRegistros(boolean toDoRegistros) {
        this.toDoRegistros = toDoRegistros;
    }
    
    public boolean isToDoPublico() {
        return toDoPublico;
    }
    
    public void setToDoPublico(boolean toDoPublico) {
        this.toDoPublico = toDoPublico;
    }
    
    public boolean isToDoFlow() {
        return toDoFlow;
    }
    
    public void setToDoFlow(boolean toDoFlow) {
        this.toDoFlow = toDoFlow;
    }
    
    public boolean isToSaveDataBasic() {
        return toSaveDataBasic;
    }
    
    public void setToSaveDataBasic(boolean toSaveDataBasic) {
        this.toSaveDataBasic = toSaveDataBasic;
    }
    
    public boolean isToGivePermUser() {
        return toGivePermUser;
    }
    
    public void setToGivePermUser(boolean toGivePermUser) {
        this.toGivePermUser = toGivePermUser;
    }
    
    public boolean isToActivePermGroup() {
        return toActivePermGroup;
    }
    
    public void setToActivePermGroup(boolean toActivePermGroup) {
        this.toActivePermGroup = toActivePermGroup;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    boolean setConfSeguridad() {
        return false;
    }
    
    public boolean isToHistFlow() {
        return toHistFlow;
    }
    
    public void setToHistFlow(boolean toHistFlow) {
        this.toHistFlow = toHistFlow;
    }
    
    public boolean isToHistDoc() {
        return toHistDoc;
    }
    
    public void setToHistDoc(boolean toHistDoc) {
        this.toHistDoc = toHistDoc;
    }
    
    
    
    public boolean isToListarDiasFeriados() {
        return toListarDiasFeriados;
    }
    
    public void setToListarDiasFeriados(boolean toListarDiasFeriados) {
        this.toListarDiasFeriados = toListarDiasFeriados;
    }
    
    public boolean isToListarDiasHabiles() {
        return toListarDiasHabiles;
    }
    
    public boolean isToAddDiaFeriado() {
        return toAddDiaFeriado;
    }
    
    public boolean isToModDiasHabiles() {
        return toModDiasHabiles;
    }
    
    public boolean isToModDiaFeriado() {
        return toModDiaFeriado;
    }
    
    public boolean isToDelDiaFeriado() {
        return toDelDiaFeriado;
    }
    
    public void setToListarDiasHabiles(boolean toListarDiasHabiles) {
        this.toListarDiasHabiles = toListarDiasHabiles;
    }
    
    public void setToAddDiaFeriado(boolean toAddDiaFeriado) {
        this.toAddDiaFeriado = toAddDiaFeriado;
    }
    
    public void setToModDiasHabiles(boolean toModDiasHabiles) {
        this.toModDiasHabiles = toModDiasHabiles;
    }
    
    public void setToModDiaFeriado(boolean toModDiaFeriado) {
        this.toModDiaFeriado = toModDiaFeriado;
    }
    
    public void setToDelDiaFeriado(boolean toDelDiaFeriado) {
        this.toDelDiaFeriado = toDelDiaFeriado;
    }



	public boolean isToListGruposWorkflow() {
		return toListGruposWorkflow;
	}


	public void setToListGruposWorkflow(boolean toListGruposWorkflow) {
		this.toListGruposWorkflow = toListGruposWorkflow;
	}


	public boolean isToAddDocumentoSvn() {
		return toAddDocumentoSvn;
	}


	public void setToAddDocumentoSvn(boolean toAddDocumentoSvn) {
		this.toAddDocumentoSvn = toAddDocumentoSvn;
	}


	public boolean isToModFlow() {
		return toModFlow;
	}


	public void setToModFlow(boolean toModFlow) {
		this.toModFlow = toModFlow;
	}


	public boolean istoPlantillaInDocFlowParalelo() {
		return toPlantillaInDocFlowParalelo;
	}


	public void settoPlantillaInDocFlowParalelo(
			boolean toPlantillaInDocFlowParalelo) {
		this.toPlantillaInDocFlowParalelo = toPlantillaInDocFlowParalelo;
	}


	public boolean isToSolicitudImpresion() {
		return toSolicitudImpresion;
	}


	public void setToSolicitudImpresion(boolean toSolicitudImpresion) {
		this.toSolicitudImpresion = toSolicitudImpresion;
	}


	public boolean isToImprimirAdministrar() {
		return toImprimirAdministrar;
	}


	public void setToImprimirAutorizacion(boolean toImprimirAdministrar) {
		this.toImprimirAdministrar = toImprimirAdministrar;
	}


	public boolean isConfSeguridadGlobal() {
		return confSeguridadGlobal;
	}


	public void setConfSeguridadGlobal(boolean confSeguridadGlobal) {
		this.confSeguridadGlobal = confSeguridadGlobal;
	}


	public boolean isToSolicitudImpresion0() {
		return toSolicitudImpresion0;
	}


	public void setToSolicitudImpresion0(boolean toSolicitudImpresion0) {
		this.toSolicitudImpresion0 = toSolicitudImpresion0;
	}


	public boolean isToPlantillaInDocFlowParalelo() {
		return toPlantillaInDocFlowParalelo;
	}


	public void setToPlantillaInDocFlowParalelo(boolean toPlantillaInDocFlowParalelo) {
		this.toPlantillaInDocFlowParalelo = toPlantillaInDocFlowParalelo;
	}


	public boolean isToListarArea() {
		return toListarArea;
	}


	public void setToListarArea(boolean toListarArea) {
		this.toListarArea = toListarArea;
	}


	public boolean isToDoFlowRevision() {
		return toDoFlowRevision;
	}


	public void setToDoFlowRevision(boolean toDoFlowRevision) {
		this.toDoFlowRevision = toDoFlowRevision;
	}


	public boolean isToDownload() {
		return toDownload;
	}


	public void setToDownload(boolean toDownload) {
		this.toDownload = toDownload;
	}


	public boolean isToViewComentPublic() {
		return toViewComentPublic;
	}


	public void setToViewComentPublic(boolean toViewComentPublic) {
		this.toViewComentPublic = toViewComentPublic;
	}


	public void setToImprimirAdministrar(boolean toImprimirAdministrar) {
		this.toImprimirAdministrar = toImprimirAdministrar;
	}
    
    
    
    
    
}
