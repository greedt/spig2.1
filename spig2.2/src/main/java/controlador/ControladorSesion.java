package controlador;

import is.lab.mapita.modelo.Rol;
import is.lab.mapita.modelo.Usuario;
import is.lab.mapita.modelo.UsuarioDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author n-droid
 */
@ManagedBean
@SessionScoped
public class ControladorSesion implements Serializable{
    private String correo;
    private String passwd;
    private String rol;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String login(){
        FacesContext context = FacesContext.getCurrentInstance();
        Informador informador = null;
        Comentarista comentarista = null;
        UserLogged u = null;
        if(rol.equals("informador") || rol.equals("comentarista") || rol.equals("administrador")){
            Mensajes.error("Rol inválido");
            return "";
        }
        if(rol.equals("informador")){
            InformadorDAO udb = new InformadorDAO();
            informador = udb.buscaPorCorreoContrasenia(correo, passwd);
        } else if(rol.equals("comentarista")){
            ComentaristaDAO udb = new ComentaristaDAO();
            comentarista = udb.buscaPorCorreoContrasenia(correo, passwd);
        } else if (rol.equals("administrador") && correo.equals("spig.mapas@gmail.com") && passwd.equals("qwerty")){
            u = new UserLogged("admin","spig.mapas@gmail.com",rol);
            context.getExternalContext().getSessionMap().put("user", u);
            return "/admin/index_admin.xhtml?faces-redirect=true";
        }

        if(informador != null){
            u = new UserLogged(informador.getNombre(),informador.getCorreo(),rol);
            context.getExternalContext().getSessionMap().put("user", u);
            return "/user/perfiluser?faces-redirect=true";
        }
        if(comentarista != null){
            u = new UserLogged(comentarista.getNombre(),comentarista.getCorreo(),rol);
            context.getExternalContext().getSessionMap().put("user", u);
            return "/superuser/perfilsuperuser?faces-redirect=true";
        }
        Mensajes.error("Fallo en el inicio de sesión");
        return "";
    }

    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }

    public class UserLogged implements Serializable{
        private String nombre;
        private String correo;
        private Rol rol;

        public UserLogged(String nombre, String correo, Rol rol) {
            this.nombre = nombre;
            this.correo = correo;
            this.rol = rol;
        }



        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public Rol getRol() {
            return rol;
        }

        public void setRol(Rol rol) {
            this.rol = rol;
        }


    }
}
