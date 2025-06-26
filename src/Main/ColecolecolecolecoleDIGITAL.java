package Main;

import Admin.MenuAdm;
import Estudiante.PerfilEstudiante;
import Docente.PerfilDocente;


public class ColecolecolecolecoleDIGITAL {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                //para ver los frame de estudiante
                //PerfilEstudiante ventanaprincipal = new PerfilEstudiante(usuario);
                //ventanaprincipal.setVisible(true);
                
                //para ver los frame de profesor
                //PerfilProfesor ventanaProfe = new PerfilDocente();
                //ventanaProfe.setVisible(true);
                
                //para ver el frame de admin
                //MenuAdm ventanaAdmin = new MenuAdm();
                //ventanaAdmin.setVisible(true);
                
                LoginGeneral ventanaprincipal= new LoginGeneral();
                ventanaprincipal.setVisible(true);
            }
        });
    }
    
}
