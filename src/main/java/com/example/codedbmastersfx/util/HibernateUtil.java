package com.example.codedbmastersfx.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Configura el registro de servicios estándar
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml") // Carga la configuración desde hibernate.cfg.xml en el directorio de recursos
                    .build();

            // Crea el objeto MetadataSources
            MetadataSources metadataSources = new MetadataSources(standardRegistry);

            // Crea el objeto Metadata a partir de las fuentes de metadatos
            Metadata metadata = metadataSources.getMetadataBuilder().build();

            // Devuelve la sesión de fábrica construida a partir de los metadatos
            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            // Maneja el error adecuadamente
            System.err.println("Error al inicializar la SessionFactory de Hibernate: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Cierra la sesión de Hibernate, si es necesario
        getSessionFactory().close();
    }
}