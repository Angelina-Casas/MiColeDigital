package Complementos;

import javax.swing.*;
import java.awt.*;

/**
 * Clase base para todos los frames del sistema.
 * Define una estructura común con fondo y panel de contenido.
 */
public abstract class BaseFrame extends JFrame {

    protected JPanel panelFondo;
    protected JPanel panelContenido;

    public static final int ANCHO = 1280;
    public static final int ALTO = 720;

    public BaseFrame() {
        configurarFrame();
        inicializarPaneles();
        setVisible(true);  // Mostrar solo al final para evitar parpadeos
    }

    /**
     * Configura propiedades generales del JFrame.
     */
    private void configurarFrame() {
        setTitle("MiCole - Sistema");
        setSize(ANCHO, ALTO);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Inicializa el panel de fondo y el panel de contenido.
     */
    private void inicializarPaneles() {
        panelFondo = crearPanel(null, new Color(212, 223, 237), 0, 0, ANCHO, ALTO);
        add(panelFondo);

        panelContenido = crearPanel(null, Color.WHITE, 50, 50, 1160, 580);
        panelFondo.add(panelContenido);
    }

    /**
     * Crea un panel con layout, color y bounds definidos.
     */
    private JPanel crearPanel(LayoutManager layout, Color color, int x, int y, int width, int height) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(color);
        panel.setBounds(x, y, width, height);
        return panel;
    }

    /**
     * Método obligatorio para que las subclases definan su contenido.
     */
    protected abstract void initContenido();
}
