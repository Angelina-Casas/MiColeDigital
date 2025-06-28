package Complementos;

import javax.swing.*;
import java.awt.*;

public abstract class BaseFrame extends JFrame{
    protected JPanel panelFondo;
    protected JPanel panelContenido;
    public static final int ANCHO = 1280;
    public static final int ALTO = 720;

    public BaseFrame() {
        setSize(ANCHO, ALTO);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        panelFondo = new JPanel();
        panelFondo.setBackground(new Color(255, 220, 80));
        panelFondo.setBounds(0, 0, 1280, 720);
        panelFondo.setLayout(null);
        add(panelFondo);

        panelContenido = new JPanel();
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBounds(50, 50, 1160, 580);
        panelContenido.setLayout(null);
        panelFondo.add(panelContenido);

        setVisible(true);
    }
    protected abstract void initContenido(); 
}
