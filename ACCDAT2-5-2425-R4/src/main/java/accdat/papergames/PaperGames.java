/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package accdat.papergames;

import accdat.papergames.Controlador.Controlador;
import accdat.papergames.Modelo.Filtros.FiltrosBBDD_JPQL;
import accdat.papergames.Modelo.Filtros.FiltrosJPAController;
import accdat.papergames.Modelo.HelperOperaciones;
import accdat.papergames.Modelo.ModeloService;
import accdat.papergames.Vista.InterfazVista;
import accdat.papergames.Vista.VentanaPrincipal;

/**
 *
 * @author USUARIO
 */
public class PaperGames {
  public static void main(String[] args) {
    HelperOperaciones modeloOper = new HelperOperaciones();
    ModeloService modeloServicios = new ModeloService();
    FiltrosJPAController modeloFiltros = new FiltrosJPAController();
    FiltrosBBDD_JPQL modeloFiltrosJPQL = new FiltrosBBDD_JPQL();
    InterfazVista vista = new VentanaPrincipal();
    Controlador control = new Controlador(vista, modeloOper, modeloServicios, modeloFiltros, modeloFiltrosJPQL);
    vista.arranca();
  }
}
