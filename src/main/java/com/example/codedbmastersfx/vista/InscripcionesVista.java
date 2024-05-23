package com.example.codedbmastersfx.vista;

import com.example.codedbmastersfx.controlador.*;
import com.example.codedbmastersfx.controlador.ExcursionesControlador;
import com.example.codedbmastersfx.controlador.SociosControlador;
import com.example.codedbmastersfx.modelo.*;
import com.example.codedbmastersfx.modelo.InscripcionesModeloDAO;
import org.hibernate.Session;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;

public class InscripcionesVista {

    private Scanner scanner;
    private ExcursionesControlador excursionesControlador;
    private SociosControlador sociosControlador;
    private SociosVista sociosVista;
    private SociosModeloDAO sociosDAO;

    private Connection connection;
    private Session session;

    public InscripcionesVista(Session session) {
        this.session = session;
        this.sociosDAO = new SociosModeloDAO(session);
        //this.connection = conexion;
        this.scanner = new Scanner(System.in);
        this.excursionesControlador = new ExcursionesControlador(session);
        this.sociosControlador = new SociosControlador(session);
        this.sociosVista = new SociosVista(session);
    }

    // LO HE PUESTO DE MANERA QUE TENGAMOS QUE SELECCIONAR EL NÚMEOR DE INSCRIPCIÓN
    // EN OTRAS PARTES DEL CODIGO LO HEMOS PUESTO AUTOINCREMENTAL
    //QUEDA PENDIENTE DE OPTIMIZAR ESTA PARTE
    public InscripcionesModelo DatosInscripcion() {
        System.out.println("Añadiendo inscripción...");
        System.out.println("Introduzca el número de inscripción:");
        int numeroInscripcion = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Introduzca el código del socio:");
        int codigoSocio = scanner.nextInt();
        scanner.nextLine();
        SociosModelo socio = sociosDAO.obtenerSocioPorCodigo(codigoSocio);

        System.out.println("Introduzca el código de la excursión:");
        String codigoExcursion = scanner.nextLine();
        ExcursionesModelo excursion = buscarExcursionPorCodigo(codigoExcursion);

        System.out.println("Introduzca la fecha de inscripción (YYYY-MM-DD):");
        LocalDate fechaInscripcion = LocalDate.parse(scanner.next());

        // Ahora puedes crear la inscripción con todos los valores necesarios, incluido el número de inscripción
        return new InscripcionesModelo(numeroInscripcion, socio, excursion, fechaInscripcion);
    }


    //A CONTINUACIÓN VAMOS A CREAR LOS DOS MÉTODOS QUE NECESITA PARA FUNCIONAR
    // BUSCARSOCIOSPORCODIGO Y BUSCAREXCURSIONPORCODIGO
/*
    public SociosModelo buscarSocioPorCodigo(String codigo) {
        try {
            // Prepara la consulta SQL para buscar un socio por su código
            String consulta = "SELECT * FROM socios WHERE n_socio = ?";
            PreparedStatement consultaPreparada = connection.prepareStatement(consulta);
            consultaPreparada.setString(1, codigo);

            // Ejecuta la consulta
            ResultSet resultado = consultaPreparada.executeQuery();

            // Verifica si se encontró algún socio con el código proporcionado
            if (resultado.next()) {
                return  new SociosModelo(resultado.getString("n_socio"));
            } else {
                // Si no se encontró ningún socio con el código proporcionado, retorna null
                return null;
            }
        } catch (SQLException e) {
            // Manejo de la excepción
            e.printStackTrace(); // Esto imprimirá información sobre la excepción en la consola
            return null; // Puedes retornar null u otro valor según sea apropiado para tu aplicación
        }
    }
*/

    public ExcursionesModelo buscarExcursionPorCodigo(String codigo) {
        try {
            // Utiliza HQL para buscar una excursión por su código
            String hql = "FROM ExcursionesModelo WHERE codigo = :codigo";
            return session.createQuery(hql, ExcursionesModelo.class)
                    .setParameter("codigo", codigo)
                    .uniqueResult();
        } catch (Exception e) {
            // Manejo de la excepción
            e.printStackTrace(); // Esto imprimirá información sobre la excepción en la consola
            return null; // Puedes retornar null u otro valor según sea apropiado para tu aplicación
        }
    }


//METODO PARA ELIMINAR

    public InscripcionesModelo BorrarInscripcion() {
        System.out.println("Borrando inscripción...");
        System.out.println("Introduzca el código de la inscripción:");
        int codigoInscripcion = scanner.nextInt();
        scanner.nextLine();

        // Crear una instancia de InscripcionesModeloDAO utilizando la fábrica de sesiones
        InscripcionesModeloDAO inscripcionesDAO = new InscripcionesModeloDAO(session);


        // Llamar al método buscarInscripcionPorCodigo en la instancia de InscripcionesModeloDAO
        InscripcionesModelo inscripcion = inscripcionesDAO.buscarInscripcionPorCodigo(codigoInscripcion);

        if (inscripcion == null) {
            mostrarMensaje("No se encontró ninguna inscripción con ese código.");
            return null;
        }
        return inscripcion;
    }

    public int obtenerNumeroSocio() {
        System.out.println("Ingrese el número de socio:");
        int numeroSocio = scanner.nextInt();
        scanner.nextLine();
        return numeroSocio;
    }

    // DESPUES MOSTRAMOS LAS INSCRIPCIONES SELECCIONADAS
    public void mostrarInscripcion(InscripcionesModelo inscripcion) {
        System.out.println("Número de Inscripción: " + inscripcion.getN_inscripcion());
        System.out.println("Fecha de Inscripción: " + inscripcion.getFechaInscripcion());
        System.out.println("--------------------");
    }

    // Método para mostrar el menú de gestión de inscripciones
    public int mostrarMenu() {
        System.out.println("--GESTIÓN INSCRIPCIONES--");
        System.out.println("1. Añadir Inscripción");
        System.out.println("2. Mostrar Inscripciones con filtro por socio");
        System.out.println("3. Eliminar Inscripción");
        System.out.println("0. Volver al menú principal");
        System.out.println("Seleccione una opción: ");
        return scanner.nextInt();
    }

    // Método para solicitar datos de una inscripción al usuario
    public int solicitarNumeroInscripcion() {
        System.out.println("Introduzca el número de inscripción:");
        int n_inscripcion = scanner.nextInt();
        return n_inscripcion;
    }

    public ExcursionesModelo solicitarExcursion() {
        System.out.println("Seleccione la excursión: ");
        excursionesControlador.mostrarExcursiones(); // Mostramos la lista de excursiones
        System.out.println("Ingrese el código de la excursión: ");
        return excursionesControlador.obtenerExcursionPorCodigo(scanner.nextLine().toUpperCase());
    }

    public SociosModelo solicitarSocio(){
        System.out.println("Seleccione el socio que desea inscribirse: ");
        sociosVista.mostrarSocios();
        System.out.println("Íngrese el código del socio que quiere inscribirse: ");
        int n_socio = scanner.nextInt();
        scanner.nextLine();
        return sociosControlador.obtenerSocioPorCodigo(n_socio);
    }

    public int agregarSocioInsc(){
        System.out.println("Añadiendo nuevo socio: ");
        System.out.println("Qué tipo de socio desea añadir?");
        System.out.println("1. Socio Estándar");
        System.out.println("2. Socio Federado");
        System.out.println("3. Socio Infantil");
        System.out.println("0. Salir");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    public SociosModelo listarSocios(){
        System.out.println("Seleccione el socio para mostrar sus inscripciones: ");
        sociosVista.mostrarSocios();
        System.out.println("Íngrese el código del socio: ");
        int n_socio = scanner.nextInt();
        scanner.nextLine();
        return sociosControlador.obtenerSocioPorCodigo(n_socio);
    }
    // Método para mostrar un mensaje
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}