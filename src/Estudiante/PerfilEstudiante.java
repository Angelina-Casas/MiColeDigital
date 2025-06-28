package Estudiante;

import Complementos.ComplementosFrameEstudiante;
import Modelos.Usuario;
import javax.swing.*;
import java.awt.*;

public class PerfilEstudiante extends ComplementosFrameEstudiante{
    private Usuario usuario;
    private JLabel lblFoto;
    private JLabel lblInfoBasica;
    private JLabel lblInfoAdicional;
    private JLabel lblNombre;
    private JLabel lblNombreValor;
    private JLabel lblCurso;
    private JLabel lblCursoValor;
    private JLabel lblCorreo;
    private JLabel lblCorreoValor;
    private JLabel lblGrado;
    private JLabel lblGradoValor;
    private JLabel lblSeccion;
    private JLabel lblSeccionValor;
    
    public PerfilEstudiante(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;
        
        add(crearPanelIzquierdo());

        add(crearPanelDerecho("BIENVENIDO A MICOLEDIGITAL"));
        

        lblFoto = new JLabel();
        lblFoto.setBounds(460, 170, 180, 180);
        lblFoto.setIcon(new ImageIcon(getClass().getResource("/Img/estudiantefoto.png")));
        panelDerecho.add(lblFoto);

        lblInfoBasica = new JLabel("Información básica");
        lblInfoBasica.setFont(new Font("Serif", Font.BOLD, 18));
        lblInfoBasica.setBounds(280, 380, 200, 30);
        panelDerecho.add(lblInfoBasica);

        lblInfoAdicional = new JLabel("Información adicional");
        lblInfoAdicional.setFont(new Font("Serif", Font.BOLD, 18));
        lblInfoAdicional.setBounds(700, 380, 220, 30);
        panelDerecho.add(lblInfoAdicional);

        lblNombre = new JLabel("NOMBRE:");
        lblNombre.setFont(new Font("Serif", Font.BOLD, 14));
        lblNombre.setBounds(280, 415, 100, 30);
        panelDerecho.add(lblNombre);

        lblNombreValor = new JLabel(usuario.getNombre());
        lblNombreValor.setFont(new Font("Serif", Font.BOLD, 14));
        lblNombreValor.setBounds(380, 415, 300, 30);
        panelDerecho.add(lblNombreValor);
        
        
        lblCorreo = new JLabel("CORREO:");
        lblCorreo.setFont(new Font("Serif", Font.BOLD, 14));
        lblCorreo.setBounds(280, 450, 100, 30);
        panelDerecho.add(lblCorreo);

        lblCorreoValor = new JLabel(usuario.getCorreo());
        lblCorreoValor.setFont(new Font("Serif", Font.BOLD, 14));
        lblCorreoValor.setBounds(380, 450, 300, 30);
        panelDerecho.add(lblCorreoValor);

        lblCurso = new JLabel("CURSO:");
        lblCurso.setFont(new Font("Serif", Font.BOLD, 14));
        lblCurso.setBounds(280, 485, 100, 30);
        panelDerecho.add(lblCurso);

        lblCursoValor = new JLabel("Matematica");
        lblCursoValor.setFont(new Font("Serif", Font.BOLD, 14));
        lblCursoValor.setBounds(380, 485, 200, 30);
        panelDerecho.add(lblCursoValor);

        lblGrado = new JLabel("GRADO:");
        lblGrado.setFont(new Font("Serif", Font.BOLD, 14));
        lblGrado.setBounds(700, 415, 100, 30);
        panelDerecho.add(lblGrado);

        lblGradoValor = new JLabel("6");
        lblGradoValor.setBounds(800, 415, 100, 30);
        lblGradoValor.setFont(new Font("Serif", Font.BOLD, 14));
        panelDerecho.add(lblGradoValor);

        lblSeccion = new JLabel("SECCIÓN:");
        lblSeccion.setFont(new Font("Serif", Font.BOLD, 14));
        lblSeccion.setBounds(700, 450, 100, 30);
        panelDerecho.add(lblSeccion);

        lblSeccionValor = new JLabel("A");
        lblSeccionValor.setBounds(800, 450, 100, 30);
        lblSeccionValor.setFont(new Font("Serif", Font.BOLD, 14));
        panelDerecho.add(lblSeccionValor);

        setVisible(true);
    }
 
}
