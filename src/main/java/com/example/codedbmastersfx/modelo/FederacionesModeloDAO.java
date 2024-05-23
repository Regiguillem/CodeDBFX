package com.example.codedbmastersfx.modelo;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class FederacionesModeloDAO {
    private Session session;
    public FederacionesModeloDAO(Session session) {
        this.session = session;
    }

    public List<FederacionesModelo> obtenerTodasLasFederaciones() {
        List<FederacionesModelo> federaciones = new ArrayList<>();
        try {
            session.beginTransaction();
            String hql = "FROM FederacionesModelo";
            Query<FederacionesModelo> query = session.createQuery(hql, FederacionesModelo.class);
            federaciones = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return federaciones;
    }
}