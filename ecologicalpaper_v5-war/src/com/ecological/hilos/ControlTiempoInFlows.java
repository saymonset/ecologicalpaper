/*
 * ControlTiempoInFlows.java
 *
 * Created on August 17, 2008, 10:42 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ecological.hilos;

import com.ecological.control.timeflow.FlowControlByUsuarioBean;
import com.ecological.delegados.ServicioDelegado;
import com.ecological.delegados.ServicioDelegadoEcological;
import com.ecological.dias.feriados.DiasFeriadosBean;
import com.ecological.paper.cliente.flows.ClienteFlows;
import com.ecological.paper.usuario.Usuario;
import com.ecological.util.ContextSessionRequest;
import com.util.Utilidades;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
 

/**
 *
 * @author simon
 */
public class ControlTiempoInFlows extends Thread{
    private ContextSessionRequest  contextSessionRequest  = new ContextSessionRequest();
    private ServicioDelegado delegado = new ServicioDelegado();
    private ServicioDelegadoEcological delegadoEcological = ServicioDelegadoEcological
	.getInstance();
    private FlowControlByUsuarioBean flowControlByUsuarioBean;
    private List<FlowControlByUsuarioBean> flowControlByUsuarioBeans;
    
    int i = 0;
    int hora = 0;
   private static String m1 = null;
   private static String m2=null;
    public void run() {
        boolean swStart=true;
        while (swStart) {
            
            try {
                //5000
            //    System.out.println("--------------hilo 1-----------");
                Thread.sleep(60000);
                Date minutos = new Date();
                //sleep(60000); //cada minuto
                m2 = String.valueOf(minutos.getMinutes());
                if (this.m1==null || this.m1!=m2){
              //      System.out.println("--------------hilo 2-----------");
                    this.m1=m2;
                    runTask();
                }
                // sleep(1000); //cada segundo
                /*i++;
                // System.out.println("minuto ="+i);
                //System.out.println("i depues="+i);
                
                if (i == 60) {
                    i=0;
                    ++hora;
                    //  System.out.println("hora="+hora);
                }*/
                
                
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    private synchronized void runTask() {
        try {
            //esdto no sirve... ya no c usa esta clase
            Usuario userVacio= new Usuario();
            boolean swDiaFeeriado=false;
            boolean swCumpleConHora=false;
            Date fecha = new Date();
            String fechaActual= Utilidades.sdfShowWithoutHour.format(fecha);
            Date horas = new Date();
            Date minutos = new Date();
            Date segundos = new Date();
            
            String h = String.valueOf(horas.getHours());
            String m = String.valueOf(minutos.getMinutes());
            String s = String.valueOf(segundos.getSeconds());
            String meridiano=contextSessionRequest.meridiano(h);
            
            String hora = contextSessionRequest.laHora(h) + ":" + m + ":" + s + " " + contextSessionRequest.meridiano(h);
            
            Integer ini=0;
            Integer fin=0;
            Integer hActaul=0;
            
            //FLOWCONTROLBYUSUARIOBEAN
          //  FlowControlByUsuarioBean lista = delegado.
            //BUSCAMOS QUE NO SEA UN DIA FERIADO
            
            List<DiasFeriadosBean> diasFeriadosBeans=delegado.find_allDiasFeriadosBean( userVacio);
            if (diasFeriadosBeans !=null && !diasFeriadosBeans.isEmpty()){
                for (DiasFeriadosBean d:diasFeriadosBeans ){
                    d.setFechaStr(Utilidades.sdfShowWithoutHour.format(d.getFechaonly()));
                /*System.out.println("fechaActual="+fechaActual);
                System.out.println("d.getFechaStr()="+d.getFechaStr());*/
                    if (fechaActual.equalsIgnoreCase(d.getFechaStr())){
                        swDiaFeeriado=true;
                        break;
                    }
                }
                
            }
          
            com.ecological.dias.habiles.DiasHabiles dia= delegado.find_DiasHabilesByDia(userVacio,contextSessionRequest.diaSemanaInterno());
            if (dia!=null ){
                //AM
                //      System.out.println("meridiano="+meridiano);
                if (meridiano.equalsIgnoreCase(Utilidades.getAm())){
                    if (dia.getH_InicialAM()!=null &&
                            dia.getH_FinalAM()!=null &&
                            !dia.getH_InicialAM().equalsIgnoreCase("00:00") && !dia.getH_FinalAM().equalsIgnoreCase("00:00")){
                        ini=Integer.valueOf(dia.getH_InicialAM().substring(0,2));
                        fin=Integer.valueOf(dia.getH_FinalAM().substring(0,2));
                        hActaul=Integer.valueOf(contextSessionRequest.laHora(h));
                        
                        if ((hActaul>=ini) && (hActaul<fin)){
                      /*      System.out.println("hora inicial "+ini);
                            System.out.println("hora final "+fin);
                            System.out.println("hora hActaul "+hActaul);
                            System.out.println("meridiano "+meridiano);*/
                            swCumpleConHora=true;
                        }
                        
                    }
                }
                //PM
                if (meridiano.equalsIgnoreCase(Utilidades.getPm())){
                    if (dia.getH_InicialPM()!=null &&
                            dia.getH_FinalPM()!=null &&
                            !dia.getH_InicialPM().equalsIgnoreCase("00:00") && !dia.getH_FinalPM().equalsIgnoreCase("00:00")){
                        ini=Integer.valueOf(dia.getH_InicialPM().substring(0,2));
                        fin=Integer.valueOf(dia.getH_FinalPM().substring(0,2));
                        hActaul=Integer.valueOf(contextSessionRequest.laHora(h));
                        if ((hActaul>=ini) && (hActaul<fin)){
                          /*  System.out.println("hora inicial "+ini);
                            System.out.println("hora final "+fin);
                            System.out.println("hora hActaul "+hActaul);
                            System.out.println("meridiano "+meridiano);*/
                            
                            swCumpleConHora=true;
                        }
                    }
                }
            }
        
            if (!swDiaFeeriado && swCumpleConHora){
                
                flowControlByUsuarioBeans=delegado.find_allFlowControlByHilo();
                for (FlowControlByUsuarioBean f:flowControlByUsuarioBeans){
                    
                    
                    f.setMinutosAcumulados(f.getMinutosAcumulados()+1);
                    if (f.getMinutosAcumulados()==60){
                        f.setHorasAcumuladas(f.getHorasAcumuladas()+1);
                        f.setMinutosAcumulados(0);
                    }
                    
                    if (f.isEnvMailRemember()){
//                        System.out.println("f.getHorasAcumuladas()="+f.getHorasAcumuladas());
                        //                       System.out.println("f.getHrsAntesToEnvMailRemember()*f.getContEnvMailRemember()="+(f.getHrsAntesToEnvMailRemember()*f.getContEnvMailRemember()));
                        //                     System.out.println("f.getHrsAntesToEnvMailRemember()="+f.getHrsAntesToEnvMailRemember());
                        
                        
                        //                   System.out.println("enviando mails...");
                        //                  System.out.println("f.getContEnvMailRemember()="+f.getContEnvMailRemember());
                        if (f.getContEnvMailRemember().equals(new Integer(0))){
                            //            System.out.println("-------------------UNO----------------------");
                            if (f.getHrsAntesToEnvMailRemember()<=f.getHorasAcumuladas()){
                                //                System.out.println("--------------------pasa--------1--------");
                                
                                
                                try {
                                    //enviamos mails
                                    ClienteFlows clienteFlows = new ClienteFlows();
                                    delegadoEcological.MyHiloEnvioMails(f.getFlow());
                                } catch (Exception e) {
                                    System.out.println("No c pudo mandar mail "+e.toString());
                                }finally{
                                    f.setContEnvMailRemember(2);
                                }
                                
                                //empezamos por dos, el cero lo colocamos como 1 y le sumamos 1 mas
                                // f.setContEnvMailRemember(0)==f.setContEnvMailRemember(1);
                                
                            }
                        }else{
                            if ((f.getHrsAntesToEnvMailRemember()*f.getContEnvMailRemember())<=f.getHorasAcumuladas()){
                                //    System.out.println("--------------------pasa--------2--------");
                                
                                try {
                                    //envianmos mails
                                    ClienteFlows clienteFlows = new ClienteFlows();
                                	delegadoEcological.MyHiloEnvioMails(f.getFlow());
                                } catch (Exception e) {
                                    System.out.println("No c pudo mandar mail "+e.toString());
                                }finally{
                                    f.setContEnvMailRemember(f.getContEnvMailRemember()+1);
                                }
                                
                                
                                
                            }
                        }
                        // System.out.println("--------------------pasa--------33--------");
                    }
            /*        System.out.println("Grabando hora y minutos ");
                    System.out.println("getFlow_Participantes="+f.getFlow_Participantes().getParticipante().getLogin());
                     System.out.println("f unico "+f.getCodigo());*/
                    delegado.edit(f);
                    //System.out.println("Se incrementa el tiempo, cumple con la condicion");
                    //System.out.println("meridiano="+meridiano);
                    
                    
                    
                }
            }
            
            
            
            
            
        } catch (Exception ex) {
            System.out.println("No pudo el hilo conectarse con base de datos 2 :"+ex);
        } finally {
            
        }
    }
}
