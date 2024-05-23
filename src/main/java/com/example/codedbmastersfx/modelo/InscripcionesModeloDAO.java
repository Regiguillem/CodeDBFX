package com.example.codedbmastersfx.modelo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class InscripcionesModeloDAO {

    private Session session;

    public InscripcionesModeloDAO(Session session) {
        this.session = session;
    }


    // METODO PARA AGREGAR INSCRIPCIONES
    public void agregarInscripcion(InscripcionesModelo inscripcion) {
        try {
            // Iniciar una transacción
            Transaction tx = session.beginTransaction();
            // Guardar la inscripción utilizando Hibernate
            session.save(inscripcion);
            // Commit de la transacción
            tx.commit();
        } catch (HibernateException e) {
            // Manejar cualquier excepción
            e.printStackTrace();
        }
    }

    // METODO PARA OBTENER INSCRIPCIONES POR SOCIO
    public List<InscripcionesModelo> obtenerInscripcionesPorSocio(int n_socio) {
        try {
            Transaction tx = session.beginTransaction();

            // Crear consulta HQL para obtener inscripciones por socio
            //la lletra i  s'utilitza per fer referencia a la taula inscripcions
            // la i podriem dir que és un alias
            String hql = "FROM InscripcionesModelo i WHERE i.socio.n_socio = :n_socio";
            Query<InscripcionesModelo> query = session.createQuery(hql, InscripcionesModelo.class);
            query.setParameter("n_socio", n_socio);

            // Ejecutar la consulta y obtener los resultados
            List<InscripcionesModelo> inscripciones = query.list();

            tx.commit(); // Comprometer la transacción

            return inscripciones;
        } catch (HibernateException e) {
            // Se utiliza para manejar cualquier excepción
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Método para buscar una inscripción por su código
    public InscripcionesModelo buscarInscripcionPorCodigo(int codigo) {
        try {
            // Obtener la inscripción por su código utilizando Hibernate
            return session.get(InscripcionesModelo.class, codigo);
        } catch (HibernateException e) {
            // Manejar cualquier excepción
            e.printStackTrace();
            return null;
        }
    }

    // Método para eliminar una inscripción
    public void eliminarInscripcion(InscripcionesModelo inscripcion) {
        try {
            // Iniciar una transacción
            Transaction tx = session.beginTransaction();
            // Eliminar la inscripción utilizando Hibernate
            session.delete(inscripcion);
            // Commit de la transacción
            tx.commit();
        } catch (HibernateException e) {
            // Manejar cualquier excepción
            e.printStackTrace();
        }
    }
}