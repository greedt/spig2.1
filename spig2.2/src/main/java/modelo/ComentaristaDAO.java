/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author desales
 */
public class ComentaristaDAO extends AbstractDAO<Comentarista>{

     /**
     * Constructor
     * @author desalessr
     */
    public ComentaristaDAO() {
        super();
    }

    /**
     * Método para almacenar un nuevo comentario
     * @param comentarista comentarista a almacenar
     */
    public void save(Comentarista comentarista) {
        super.save(comentarista);
    }


       /**
     * Método para actualizar un comentario
     *  @param comentarista Comentarista a actualizar
     */
    public void update(String email) {
        Comentarista com = find(email);


    }

    public Comentarista find(String correo) {
        Comentarista u = null;
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            String hql = "from Comentarista where correo = :correo ";
            Query query = session.createQuery(hql);
            query.setParameter("correo", correo);
            u = (Comentarista)query.uniqueResult();
            tx.commit();

        }catch(HibernateException e){
            if(tx!=null){
                tx.rollback();
            }
            e.printStackTrace();

        }finally{
            session.close();
        }
        return u;
    }

    /**
     * Método que nos ayidará a mostrar todos los comentaristas
     *@return comentaristas Comentarios que serán mostrados
     */
    public List<Comentarista> findAll(){
        return super.findAll(Comentarista.class);
    }

    public Comentarista buscaPorCorreoContrasenia(String correo,String contrasenia){
        Comentarista u = null;
        Session session = this.sessionFactory.openSession();
        Transaction tx =null;
        try{
            tx = session.beginTransaction();
            String hql = "from Comentarista where correo = :correo and contrasenia = :contrasenia";
            Query query = session.createQuery(hql);
            query.setParameter("correo", correo);
            query.setParameter("contrasenia",contrasenia);
            u = (Comentarista)query.uniqueResult();
            tx.commit();
        }catch(HibernateException e){
            if(tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
        return u;
    }
}
