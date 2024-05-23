package com.example.codedbmastersfx.modelo;

import com.example.codedbmastersfx.vista.ExcursionesVista;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class ExcursionesModeloDAO {
    //private Connection connection;
    private Session session;

    private ExcursionesVista vistaEx;

    public ExcursionesModeloDAO(Session session) {
        //this.connection = connection;
        this.session = session;
        this.vistaEx = new ExcursionesVista();
    }

    public ExcursionesModeloDAO() {

    }

    //Query con Hibernate
    public void agregarExcursion(ExcursionesModelo excursion){
        try {
            session.beginTransaction();
            session.save(excursion);
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    /*public void agregarExcursion(ExcursionesModelo excursion) throws SQLException {
        String sql = "INSERT INTO Excursiones (codigo, descripcion, fecha, n_dias, precio) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, excursion.getCodigo());
            statement.setString(2, excursion.getDescripcion());
            statement.setDate(3, java.sql.Date.valueOf(excursion.getFecha()));
            statement.setInt(4, excursion.getN_dias());
            statement.setDouble(5, excursion.getPrecio());
            statement.executeUpdate();
        }
    }*/
    //Query con Hibernate
    public List<ExcursionesModelo> filtrarExcursion(LocalDate fechaInicio, LocalDate fechaFin) {
        List<ExcursionesModelo> excursiones = null;
        try {
            Query<ExcursionesModelo> query = session.createQuery("FROM ExcursionesModelo WHERE fecha BETWEEN :fechaInicio AND :fechaFin", ExcursionesModelo.class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
            excursiones = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return excursiones != null ? excursiones : List.of(); // Devolver una lista vacía en lugar de null
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
    /*public void filtrarExcursion(LocalDate fechaIni, LocalDate fechaFin) throws SQLException {
        String sql = "SELECT * FROM Excursiones WHERE fecha BETWEEN ? AND ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(fechaIni));
            statement.setDate(2, java.sql.Date.valueOf(fechaFin));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String codigo = resultSet.getString("codigo");
                    String descripcion = resultSet.getString("descripcion");
                    LocalDate fecha = resultSet.getDate("fecha").toLocalDate();
                    int n_dias = resultSet.getInt("n_dias");
                    double precio = resultSet.getDouble("precio");
                    ExcursionesModelo excursion = new ExcursionesModelo(codigo, descripcion, fecha, n_dias, precio);
                    vistaEx.mostrarExcursion(excursion);
                }
            }
        }
    }*/
}
