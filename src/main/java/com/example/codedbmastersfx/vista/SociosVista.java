package com.example.codedbmastersfx.vista;

import com.example.codedbmastersfx.modelo.*;
import jakarta.persistence.*;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;


public class SociosVista {
    private Scanner scanner;

    private Session session;

    private SociosModeloDAO sociosDAO;

    public SociosVista(Session session) {
        this.scanner = new Scanner(System.in);
        this.session = session;
        this.sociosDAO = sociosDAO;
    }

    public int mostrarMenu() {
        System.out.println("Gestión de Socios");
        System.out.println("1. Añadir Socio Estándar");
        System.out.println("2. Modificar tipo de seguro de un socio estándar");
        System.out.println("3. Añadir Socio Federado");
        System.out.println("4. Añadir Socio Infantil");
        System.out.println("5. Eliminar socio");
        System.out.println("6. Mostrar socios filtrados");
        System.out.println("7. Mostrar factura mensual de socio");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public int solicitarNumeroSocio() {
        System.out.print("Introduzca el número de socio: ");
        int n_socio = scanner.nextInt();
        scanner.nextLine();
        return n_socio;
    }

    public String solicitarNombreSocio() {
        scanner.nextLine();
        System.out.print("Introduzca el nombre del socio: ");
        String nombre = scanner.nextLine();
        System.out.println("El nombre guardado es: " + nombre);
        return nombre;
    }
    public String solicitarNifSocio() {
        System.out.print("Introduzca el NIF del socio: ");
        String nif = scanner.nextLine();
        return nif;
    }

    public int solicitarSeguroSocio() {
        System.out.println("Seleccione el tipo de seguro para el socio:");
        System.out.println("1. Seguro Básico");
        System.out.println("2. Seguro Completo");
        System.out.print("Elija una opción: ");

        int opcionSeguro = scanner.nextInt();

        switch (opcionSeguro) {
            case 1, 2:
                return opcionSeguro;
            default:
                System.out.println("Opción no válida. Seleccionando Seguro Básico por defecto.");
                return  1;
        }
    }

    public int solicitarNumeroSocioPadreMadre() {
        System.out.print("Introduzca el número de socio del padre o madre: ");
        return scanner.nextInt();
    }

    public int mostrarMenuFiltrado() {
        System.out.println("Seleccione el tipo de socios que desea ver:");
        System.out.println("1. Socios Estándar");
        System.out.println("2. Socios Federados");
        System.out.println("3. Socios Infantiles");
        System.out.println("4. Mostrar todos los socios");
        System.out.println("0. Volver al menú anterior");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                System.out.println("Mostrando socios estándar:");
                break;
            case 2:
                System.out.println("Mostrando socios federados:");
                break;
            case 3:
                System.out.println("Mostrando socios infantiles:");
                break;
            case 4:
                System.out.println("Mostrando todos los socios:");
                break;
            case 0:
                System.out.println("Volviendo al menú anterior.");
                break;
            default:
                System.out.println("Opción no válida.");
        }return opcion;
    }

public void mostrarSocios() {
    System.out.println("Lista de Todos los Socios:");
    mostrarSociosInfantiles();
    mostrarSociosFederados();
    mostrarSociosEstandar();
}

    public void mostrarSociosInfantiles() {
        List<SociosModelo> sociosInfantiles = sociosDAO.obtenerSociosInf();
        if (!sociosInfantiles.isEmpty()) {
            System.out.println("Lista de Socios Infantiles:");
            for (SociosModelo socio : sociosInfantiles) {
                System.out.println("Número de socio: " + socio.getN_socio() + ", Nombre: " + socio.getNombre() + ", NIF: " + socio.getNif());
            }
        } else {
            System.out.println("No hay socios infantiles registrados.");
        }
    }

    public void mostrarSociosFederados() {
        try {
            // Iniciar la transacción
            session.beginTransaction();

            // Crear una consulta HQL para obtener los socios federados
            Query query = session.createQuery("FROM SociosModelo WHERE tipo_socio = :tipo", SociosModelo.class);
            query.setParameter("tipo", "Federado");

            // Obtener la lista de socios federados
            List<SociosModelo> sociosFederados = query.getResultList();

            // Comprobar si la lista no está vacía
            if (!sociosFederados.isEmpty()) {
                System.out.println("Lista de Socios Federados:");
                for (SociosModelo socio : sociosFederados) {
                    // System.out.println("Número de socio: " + socio.getN_socio() + ", Nombre: " + socio.getNombre() + ", NIF: " + socio.getNif() + ", Federación: " + socio.getFederacion().getNombre());

                }
            } else {
                System.out.println("No hay socios federados registrados.");
            }

            // Confirmar la transacción
            session.getTransaction().commit();
        } catch (Exception e) {
            // En caso de error, hacer rollback de la transacción
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void mostrarSociosEstandar() {
        List<SociosModelo> sociosEstandar = sociosDAO.obtenerSociosEst();
        if (!sociosEstandar.isEmpty()) {
            System.out.println("Lista de Socios Estándar:");
            for (SociosModelo socio : sociosEstandar) {
                System.out.println("Número de socio: " + socio.getN_socio() + ", Nombre: " + socio.getNombre() + ", NIF: " + socio.getNif());
            }
        } else {
            System.out.println("No hay socios estándar registrados.");
        }
    }


    public void mostrarFactura(int numSocio, double importe) {
        System.out.println("Factura mensual para el socio " + numSocio + ":");
        System.out.println("Importe: $" + importe);
    }
    public int solicitarFederacion() {
        System.out.print("Ingrese el código de la federación: ");
        int fed = scanner.nextInt();
        return fed;
    }

    public void mostrarFederacionesDisponibles(List<FederacionesModelo> federaciones) {
        System.out.println("Federaciones Disponibles:");
        for (int i = 0; i < federaciones.size(); i++) {
            System.out.println((i + 1) + ". " + federaciones.get(i).getNombre());
        }
    }

}
