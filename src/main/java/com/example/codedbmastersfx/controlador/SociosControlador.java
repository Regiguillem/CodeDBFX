package com.example.codedbmastersfx.controlador;

import com.example.codedbmastersfx.modelo.*;
import com.example.codedbmastersfx.vista.*;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SociosControlador {
    private Session session;
    private Connection connection;
    public SociosModeloDAO sociosDAO;
    private SociosVista sociosVista;
    private FederacionesModeloDAO federacioneDAO;

    public SociosControlador(Session session) {
        this.session = session;
        this.connection = connection;
        this.sociosDAO = new SociosModeloDAO(session);
        this.sociosVista = new SociosVista(session);
        this.federacioneDAO= new FederacionesModeloDAO(session);

    }

    public boolean iniciar() {
        int opcion;
        do {
            opcion = sociosVista.mostrarMenu();
            switch (opcion) {
                case 1:
                    agregarSocioEst();
                    break;
                case 2:
                    modificarTipoSeguro();
                    break;
                case 3:
                    agregarSocioFederado();
                    break;
                case 4:
                    agregarSocioInfantil();
                    break;
                case 5:
                    eliminarSocio();
                    break;
                case 6:
                    mostrarSociosFiltrados();
                    break;
                case 7:
                    // mostrarFacturaMensual();
                    break;
                case 0:
                    System.out.println("Saliendo del módulo de socios.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
        return true;
    }

    private void agregarSocioEst() {
        String nombre = sociosVista.solicitarNombreSocio();
        String nif = sociosVista.solicitarNifSocio();
        int seguro = sociosVista.solicitarSeguroSocio();
        sociosDAO.agregarSocioEst(nombre, nif, seguro);
    }


    private void modificarTipoSeguro() {
       // ArrayList<SociosModelo> sociosDisponibles = sociosDAO.obtenerTodosLosSocios();
        sociosVista.mostrarSociosEstandar();  //sociosVista.mostrarSocios(sociosDisponibles);
        int numeroSocio = sociosVista.solicitarNumeroSocio();
        SociosModelo socio = sociosDAO.obtenerSocioPorNumero(numeroSocio);

        if (socio != null) {
            int nuevoSeguro = sociosVista.solicitarSeguroSocio();
            if (sociosDAO.actualizarTipoSeguro(numeroSocio, nuevoSeguro)) {
                sociosVista.mostrarMensaje("Tipo de seguro actualizado correctamente.");
            } else {
                sociosVista.mostrarMensaje("Error al actualizar el tipo de seguro.");
            }
        } else {
            sociosVista.mostrarMensaje("El socio con el número especificado no existe.");
        }
    }

    private void agregarSocioFederado() {
        List<FederacionesModelo> federacionesDisponibles = federacioneDAO.obtenerTodasLasFederaciones();
        sociosVista.mostrarFederacionesDisponibles(federacionesDisponibles);
        int opcionFederacion = sociosVista.solicitarFederacion();
        if (opcionFederacion >= 1 && opcionFederacion <= federacionesDisponibles.size()) {
            FederacionesModelo federacionSeleccionada = federacionesDisponibles.get(opcionFederacion - 1);
            String nombre = sociosVista.solicitarNombreSocio();
            String nif = sociosVista.solicitarNifSocio();

            if (sociosDAO.agregarSocioFederado(nombre, nif, federacionSeleccionada.getCodigo())) {
                sociosVista.mostrarMensaje("Socio federado agregado correctamente.");
            } else {
                sociosVista.mostrarMensaje("Error al agregar el socio federado.");
            }
        } else {
            sociosVista.mostrarMensaje("Opción de federación no válida. Inténtelo de nuevo.");
        }
    }


    private void agregarSocioInfantil() {
        String nombre = sociosVista.solicitarNombreSocio();
        String nif = sociosVista.solicitarNifSocio();
        int numSocioPadreMadre = sociosVista.solicitarNumeroSocioPadreMadre();
        SociosControlador controlador = new SociosControlador(session); // Crear una instancia del controlador
        controlador.agregarSocioInfantil(nombre, nif, numSocioPadreMadre);

    }

    private void eliminarSocio() {
        int numSocio = sociosVista.solicitarNumeroSocio();
        sociosDAO.eliminarSocio(numSocio);
    }

    private void mostrarSociosFiltrados() {
        ArrayList<SociosModelo> sociosFiltrados = new ArrayList<>();
        int opcion = sociosVista.mostrarMenuFiltrado();
        switch (opcion) {
            case 1:
                sociosVista.mostrarSociosEstandar();
                break;
            case 2:
                sociosVista.mostrarSociosFederados();
                break;
            case 3:
                sociosVista.mostrarSociosInfantiles();
                break;
            case 4:
                sociosVista.mostrarSocios();
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }
        if (!sociosFiltrados.isEmpty()) {
            sociosVista.mostrarSocios();
        } else {
            System.out.println("No hay socios que mostrar.");
        }
    }
    /* private void mostrarFacturaMensual() {
        int numSocio = sociosVista.solicitarNumeroSocio();
        double importe = sociosDAO.calcularFacturaMensual(numSocio);
        if (importe != -1) {
            sociosVista.mostrarFactura(numSocio, importe);
        } else {
            System.out.println("No se pudo calcular la factura mensual para el socio con número " + numSocio);
        }
    } */

    public SociosModelo obtenerSocioPorCodigo(int codigoSocio) {
        return sociosDAO.obtenerSocioPorCodigo(codigoSocio);
    }
    public void agregarSocioInfantil(String nombre, String nif, int numSocioPadreMadre) {
        try {
            // Verificar si el socio padre o madre existe
            if (existeSocio(numSocioPadreMadre)) {
                // Agregar el nuevo socio infantil
                sociosDAO.agregarSocioInfantil(nombre, nif, numSocioPadreMadre);
                sociosVista.mostrarMensaje("Socio infantil agregado correctamente.");
            } else {
                sociosVista.mostrarMensaje("El socio padre o madre especificado no existe.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sociosVista.mostrarMensaje("Error al agregar el socio infantil.");
        }
    }

    private boolean existeSocio(int numSocio) throws SQLException {
        return sociosDAO.obtenerSocioPorNumero(numSocio) != null;
    }
}
