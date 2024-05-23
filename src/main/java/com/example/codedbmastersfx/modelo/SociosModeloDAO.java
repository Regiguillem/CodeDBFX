package com.example.codedbmastersfx.modelo;

import com.example.codedbmastersfx.vista.SociosVista;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class SociosModeloDAO {
    private Session session;
    private SociosVista vistaSocios;

    public SociosModeloDAO(Session session) {
        this.session = session;
        this.vistaSocios = new SociosVista(session);
    }

    public void eliminarSocio(int numeroSocio) {
        try {
            session.beginTransaction();
            eliminarInscripciones(numeroSocio);
            SociosModelo socio = session.get(SociosModelo.class, numeroSocio);
            if (socio != null) {
                session.delete(socio);
                session.getTransaction().commit();
                vistaSocios.mostrarMensaje("Socio eliminado correctamente.");
            } else {
                vistaSocios.mostrarMensaje("El socio no existe.");
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            vistaSocios.mostrarMensaje("Error al eliminar el socio.");
            e.printStackTrace();
        }
    }

    private void eliminarInscripciones(int numeroSocio) {
        Query query = session.createQuery("DELETE FROM InscripcionesModelo WHERE socio.n_socio = :numSocio");
        query.setParameter("numSocio", numeroSocio);
        query.executeUpdate();
    }


    /* public double calcularFacturaMensual(int numSocio) {
        try {
            session.beginTransaction();
            SociosModelo socio = session.get(SociosModelo.class, numSocio);
            double cuotaMensual = socio.getCuotaMensual();
            double totalExcursiones = 0.0;
            // Asegúrate de que el atributo inscripciones esté mapeado correctamente
            for (InscripcionesModelo inscripcion : socio.getInscripciones()) {
                totalExcursiones += inscripcion.getExcursion().getPrecio();
            }
            session.getTransaction().commit();
            return cuotaMensual + totalExcursiones;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return -1;
        }
    } */

    public boolean actualizarTipoSeguro(int numeroSocio, int idSeguro) {
        try {
            session.beginTransaction();
            SociosModelo socio = session.get(SociosModelo.class, numeroSocio);
            session.update(socio);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public SociosModelo obtenerSocioPorNumero(int numeroSocio) {
        try {
            session.beginTransaction();
            SociosModelo socio = session.get(SociosModelo.class, numeroSocio);
            session.getTransaction().commit();
            return socio;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }

    public void agregarSocioEst(String nombre, String nif, int seguroId) {
        try {
            session.beginTransaction();

            // Obtener el seguro correspondiente al ID proporcionado
            SeguroModelo seguro = session.get(SeguroModelo.class, seguroId);

            // Crear un nuevo socio estándar con los datos proporcionados y el seguro obtenido
            SocioEstandarModelo socio = new SocioEstandarModelo(nombre, nif, seguro, 0, "Estándar");

            // Guardar el socio en la base de datos
            session.save(socio);

            // Confirmar la transacción
            session.getTransaction().commit();

            System.out.println("Socio Estándar agregado correctamente.");
        } catch (Exception e) {
            // En caso de error, hacer rollback de la transacción
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public boolean agregarSocioFederado(String nombre, String nif, String opcionFederacion) {
        try {
            session.beginTransaction();

            // Obtener el seguro correspondiente al ID proporcionado
            // SeguroModelo seguro;
            // seguro = session.get(SeguroModelo.class);

            // Obtener la federación correspondiente a la opción seleccionada
            FederacionesModelo federacion = session.get(FederacionesModelo.class, opcionFederacion);

            // Crear un nuevo socio federado con los datos proporcionados, la federación y el seguro obtenidos
            SociosFederadosModelo socio = new SociosFederadosModelo(0, nombre, nif, "Federado", federacion);

            // Guardar el socio en la base de datos
            session.save(socio);

            // Confirmar la transacción
            session.getTransaction().commit();

            vistaSocios.mostrarMensaje("Socio Federado agregado correctamente.");
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            vistaSocios.mostrarMensaje("Error al agregar el socio Federado.");
            return false;
        }
    }

    public boolean agregarSocioInfantil(String nombre, String nif, int numSocioPadreMadre) {
        try {
            session.beginTransaction();

            // Obtener el seguro correspondiente al ID proporcionado
            // SeguroModelo seguro = session.get(SeguroModelo.class, seguroId);

            // Obtener el socio padre o madre correspondiente al número de socio proporcionado
            SociosModelo socioPadreMadre = session.get(SociosModelo.class, numSocioPadreMadre);

            // Crear un nuevo socio infantil con los datos proporcionados, el socio padre o madre y el seguro obtenidos
            SocioInfantilModelo socio = new SocioInfantilModelo(0, nombre, nif, "Infantil", socioPadreMadre);

            // Guardar el socio en la base de datos
            session.save(socio);

            // Confirmar la transacción
            session.getTransaction().commit();

            vistaSocios.mostrarMensaje("Socio Infantil agregado correctamente.");
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            vistaSocios.mostrarMensaje("Error al agregar el socio Infantil.");
            return false;
        }
    }

    public List<SociosModelo> obtenerSociosEst() {
        List<SociosModelo> sociosEst = new ArrayList<>();
        try {
            session.beginTransaction();
            sociosEst = session.createQuery("FROM SociosModelo WHERE tipo_Socio = 'Estándar'").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return sociosEst;
    }

    public List<SociosModelo> obtenerSociosFed() {
        List<SociosModelo> sociosFed = new ArrayList<>();
        try {
            session.beginTransaction();
            sociosFed = session.createQuery("FROM SociosModelo WHERE tipo_Socio = 'Federado'").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return sociosFed;
    }

    public List<SociosModelo> obtenerSociosInf() {
        List<SociosModelo> sociosInf = new ArrayList<>();
        try {
            session.beginTransaction();
            sociosInf = session.createQuery("FROM SociosModelo WHERE tipo_Socio = 'Infantil'").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return sociosInf;
    }

    public SociosModelo obtenerSocioPorCodigo(int codigo) {
        try {
            // Iniciar la transacción
            session.beginTransaction();

            // Obtener el socio por su código
            SociosModelo socio = session.get(SociosModelo.class, codigo);

            // Confirmar la transacción
            session.getTransaction().commit();

            return socio;
        } catch (Exception e) {
            // En caso de error, hacer rollback de la transacción
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
}
/*
    public FederacionesModelo obtenerFederacionPorCodigo(String codigoFederacion) {
        try {
            session.beginTransaction();
            FederacionesModelo federacion = session.get(FederacionesModelo.class, codigoFederacion);
            session.getTransaction().commit();
            return federacion;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }

    public SeguroModelo obtenerSeguroPorId(int idSeguro) {
        try {
            session.beginTransaction();
            SeguroModelo seguro = session.get(SeguroModelo.class, idSeguro);
            session.getTransaction().commit();
            return seguro;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
}

    public List<SociosModelo> obtenerTodosLosSocios() {
        List<SociosModelo> listaSocios = new ArrayList<>();
        try {
            session.beginTransaction();
            listaSocios = session.createQuery("FROM SociosModelo").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return listaSocios;
    }

    public double obtenerCuotaMensual(int numSocio) {
        try {
            session.beginTransaction();
            SociosModelo socio = session.get(SociosModelo.class, numSocio);
            double cuotaMensual = socio.getCuotaMensual();
            session.getTransaction().commit();
            return cuotaMensual;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return -1;
        }
    }

    public double obtenerTotalExcursiones(int numSocio) {
        try {
            session.beginTransaction();
            SociosModelo socio = session.get(SociosModelo.class, numSocio);
            double totalExcursiones = 0.0;
            for (InscripcionesModelo inscripcion : socio.getInscripciones()) {
                totalExcursiones += inscripcion.getExcursion().getPrecio();
            }
            session.getTransaction().commit();
            return totalExcursiones;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return -1;
        }
    }
     */