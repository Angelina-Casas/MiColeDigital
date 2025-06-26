/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    private JLabel lblGrado;
    private JLabel lblGradoValor;
    private JLabel lblSeccion;
    private JLabel lblSeccionValor;
    
    public PerfilEstudiante(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;
        
        //LLAMANDO A PANEL IZQUIERDO
        add(crearPanelIzquierdo());

        // === PANEL DERECHO ===
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
        lblNombre.setBounds(280, 410, 100, 30);
        panelDerecho.add(lblNombre);

        lblNombreValor = new JLabel(usuario.getNombre());
        lblNombreValor.setFont(new Font("Serif", Font.BOLD, 14));
        lblNombreValor.setBounds(380, 410, 300, 30);
        panelDerecho.add(lblNombreValor);

        lblCurso = new JLabel("CURSO:");
        lblCurso.setFont(new Font("Serif", Font.BOLD, 14));
        lblCurso.setBounds(280, 440, 100, 30);
        panelDerecho.add(lblCurso);

        lblCursoValor = new JLabel("Matematica");
        lblCursoValor.setFont(new Font("Serif", Font.BOLD, 14));
        lblCursoValor.setBounds(380, 440, 200, 30);
        panelDerecho.add(lblCursoValor);

        lblGrado = new JLabel("GRADO:");
        lblGrado.setFont(new Font("Serif", Font.BOLD, 14));
        lblGrado.setBounds(700, 410, 100, 30);
        panelDerecho.add(lblGrado);

        lblGradoValor = new JLabel("6");
        lblGradoValor.setBounds(800, 410, 100, 30);
        lblGradoValor.setFont(new Font("Serif", Font.BOLD, 14));
        panelDerecho.add(lblGradoValor);

        lblSeccion = new JLabel("SECCIÓN:");
        lblSeccion.setFont(new Font("Serif", Font.BOLD, 14));
        lblSeccion.setBounds(700, 440, 100, 30);
        panelDerecho.add(lblSeccion);

        lblSeccionValor = new JLabel("A");
        lblSeccionValor.setBounds(800, 440, 100, 30);
        lblSeccionValor.setFont(new Font("Serif", Font.BOLD, 14));
        panelDerecho.add(lblSeccionValor);

        
        

        setVisible(true);
    }
 
}
