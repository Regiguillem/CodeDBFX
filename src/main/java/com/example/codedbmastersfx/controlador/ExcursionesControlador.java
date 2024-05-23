package com.example.codedbmastersfx.controlador;

import com.example.codedbmastersfx.modelo.Datos;
import com.example.codedbmastersfx.modelo.ExcursionesModelo;
import com.example.codedbmastersfx.modelo.ExcursionesModeloDAO;
import com.example.codedbmastersfx.vista.ExcursionesVista;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.ArrayList;

public class ExcursionesControlador {

    private Datos datos = Datos.getInstance();
    private ExcursionesModeloDAO excursionesModeloDAO;

    //Atributos para relacionar la vista con el arraylist de excursiones
    private ExcursionesVista vistaEx;

    //Controlador
    public ExcursionesControlador(Session session) {
        this.vistaEx = new ExcursionesVista();
        this.excursionesModeloDAO = new ExcursionesModeloDAO(session);
    }

    //Menú interno para seleccionar qué método se ejecutará una vez la vista haya pedido al usuario que seleccione una opción.
    public boolean iniciar(){
        int opcion;
        do {
            //Aqui se extrae la opción de la vista
            opcion = vistaEx.mostrarMenu();
            switch (opcion) {
                case 1:
                    agregarExcursion();
                    break;
                case 2:
                    mostrarExcursionesFiltradas();
                    break;
                case 0:
                    vistaEx.mostrarMensaje("Saliendo del menú de Excursiones...");
                    return true;
                default:
                    vistaEx.mostrarMensaje("Opción no válida. Introduzca una opción válida.");
            }
        } while (opcion != 0);
        return false;
    }
    //Gracias a los datos introducidos por el usuario, el controlador añade el nuevo objeto de excursiones al arraylist
    public void agregarExcursion() {
        ExcursionesModelo excursion = vistaEx.DatosExcursion();
        //datos.getExcursiones().add(excursion); // Utilizamos el Singleton Datos para agregar la excursión
        try {
            excursionesModeloDAO.agregarExcursion(excursion); // Llamar al método agregarExcursion() de ExcursionesModeloDAO
            vistaEx.mostrarMensaje("Excursión añadida correctamente.");
        } catch (Exception e) {
            vistaEx.mostrarMensaje("Error al añadir la excursión: " + e.getMessage());
        }
    }

    //Iteramos sobre los objetos del arraylist para comprobar su fecha, si se encuentra entre las dos fechas facilitadas,
    // se imprimen por pantalla gracias a la función mostrarExcursion de la vista.
    public void mostrarExcursionesFiltradas(){
        //Obtener acceso al ArrayList de excursiones
        ArrayList<ExcursionesModelo> excursiones = datos.getExcursiones();
        LocalDate[] fechas = vistaEx.ExcursionesFechasFiltro();
        LocalDate fechaInicio = fechas[0];
        LocalDate fechaFinal = fechas[1];

        vistaEx.mostrarMensaje("Excursiones entre " + fechaInicio + " y " + fechaFinal + ":");
        /* ESTO ES LO QUE HABÍA ANTES
        for (ExcursionesModelo excursion : excursiones) {
            LocalDate fechaExcursion = excursion.getFecha();
            if ((fechaExcursion.compareTo(fechaInicio) >= 0 && fechaExcursion.compareTo(fechaFinal) <= 0)) {
                vistaEx.mostrarExcursion(excursion);
            }
        }*/
        try {
            excursionesModeloDAO.filtrarExcursion(fechaInicio, fechaFinal);
        } catch (Exception e) {
            vistaEx.mostrarMensaje("Error al mostrar las excursiones filtradas: " + e.getMessage());
        }
    }

    public void mostrarExcursiones() {
        ArrayList<ExcursionesModelo> excursiones = datos.getExcursiones();
        for (ExcursionesModelo excursion : excursiones) {
            System.out.println("Código: " + excursion.getCodigo());
            System.out.println("Nombre: " + excursion.getDescripcion());
            System.out.println("Fecha: " + excursion.getFecha());
            System.out.println("Descripción: " + excursion.getN_dias());
            System.out.println("-------------------------------------");
        }
    }

    public ExcursionesModelo obtenerExcursionPorCodigo(String codigo) {
        ArrayList<ExcursionesModelo> excursiones = datos.getExcursiones();
        for (ExcursionesModelo excursion : excursiones) {
            if (excursion.getCodigo().equals(codigo)) {
                return excursion;
            }
        }
        return null;
    }
}
