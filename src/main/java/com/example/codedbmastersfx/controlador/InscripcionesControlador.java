package com.example.codedbmastersfx.controlador;

import com.example.codedbmastersfx.modelo.*;
import com.example.codedbmastersfx.vista.InscripcionesVista;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;

public class InscripcionesControlador {

    private InscripcionesVista vistaInsc;
    private InscripcionesModeloDAO inscripcionesModeloDAO;

    public InscripcionesControlador(Session session) {
        this.vistaInsc = new InscripcionesVista(session);
        this.inscripcionesModeloDAO = new InscripcionesModeloDAO(session);
    }

    public boolean iniciar() {
        int opcion;
        do {
            opcion = vistaInsc.mostrarMenu();
            switch (opcion) {
                case 1:
                    agregarInscripcion();
                    break;
                case 2:
                    mostrarInscripcionesFiltradas();
                    break;
                case 3:
                    eliminarInscripcion();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    return true;
                default:
                    System.out.println("Opción no válida. Introduzca una opción válida.");
            }
        } while (opcion != 4);
        return false;
    }


    public void agregarInscripcion() {
        InscripcionesModelo inscripcion = vistaInsc.DatosInscripcion();
        //datos.getExcursiones().add(excursion); // Utilizamos el Singleton Datos para agregar la excursión
        try {
            inscripcionesModeloDAO.agregarInscripcion(inscripcion); // Llamar al método agregarExcursion() de ExcursionesModeloDAO
            vistaInsc.mostrarMensaje("Inscripción añadida correctamente.");
        } catch (HibernateException e) {
            vistaInsc.mostrarMensaje("Error al añadir la inscripción: " + e.getMessage());
        }
    }

    public void mostrarInscripcionesFiltradas() {
        int n_socio = vistaInsc.obtenerNumeroSocio();
        ArrayList<InscripcionesModelo> inscripciones = null;
        try {
            inscripciones = (ArrayList<InscripcionesModelo>) inscripcionesModeloDAO.obtenerInscripcionesPorSocio(n_socio);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

        if (inscripciones != null && !inscripciones.isEmpty()) {
            vistaInsc.mostrarMensaje("Inscripciones del socio número " + n_socio + ":");
            for (InscripcionesModelo inscripcion : inscripciones) {
                vistaInsc.mostrarInscripcion(inscripcion);
            }
        } else {
            vistaInsc.mostrarMensaje("El socio número " + n_socio + " no tiene inscripciones.");
        }
    }

    public void eliminarInscripcion() {
        InscripcionesModelo inscripcion = vistaInsc.BorrarInscripcion();
        //datos.getExcursiones().add(excursion); // Utilizamos el Singleton Datos para agregar la excursión
        try {
            inscripcionesModeloDAO.eliminarInscripcion(inscripcion); // Llamar al método agregarExcursion() de ExcursionesModeloDAO
            vistaInsc.mostrarMensaje("Inscripción eliminada correctamente.");
        } catch (HibernateException e) {
            vistaInsc.mostrarMensaje("Error al elliminar la inscripción: " + e.getMessage());
        }
    }

}