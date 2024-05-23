package com.example.codedbmastersfx.vista;

import com.example.codedbmastersfx.controlador.ExcursionesControlador;
import com.example.codedbmastersfx.controlador.InscripcionesControlador;
import com.example.codedbmastersfx.controlador.SociosControlador;
import com.example.codedbmastersfx.util.DataBaseConnection;
import com.example.codedbmastersfx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.util.Scanner;


public class Main {
public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Conexión antigua
        Connection connection = null;
        //Nueva conexión
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        try {
            // Establecer la conexión al inicio del programa
            //Conexión antigua
            connection = DataBaseConnection.getConnection();
            // Crear la sesión de Hibernate
            session = sessionFactory.openSession();
            System.out.println("Conexión con Hibernate establecida.");
            System.out.println("Conexión con MySQL establecida.");

            int opcion;

            do {
                // Menú Principal
                System.out.println("Menú Principal");
                System.out.println("Seleccione una opción:");
                System.out.println("1. Controlador Excursiones");
                System.out.println("2. Controlador Socios");
                System.out.println("3. Controlador Inscripciones");
                System.out.println("0. Salir");

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        ExcursionesControlador controladorEx = new ExcursionesControlador(session);
                        if (!controladorEx.iniciar()) {
                            System.out.println("Volviendo al menú principal...");
                        }
                        break;
                    case 2:
                        SociosControlador controladorSoc = new SociosControlador(session);
                        if (!controladorSoc.iniciar()) {
                            System.out.println("Volviendo al menú principal...");
                        }
                        break;
                    case 3:
                        InscripcionesControlador controladorInsc = new InscripcionesControlador(session);
                        if (!controladorInsc.iniciar()) {
                            System.out.println("Volviendo al menú principal...");
                        }
                        break;
                    case 0:
                        System.out.println("Saliendo del programa.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Cerrar la conexión al salir del programa
                if (session != null) {
                    HibernateUtil.shutdown();
                    System.out.println("Conexión cerrada.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }
}