package Modelos;

import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

/**
 * Clase utilitaria para convertir etiquetas JLabel en hipervínculos funcionales.
 */
public class EnlacesContenido {

    /**
     * Convierte una JLabel en un hipervínculo que abre la URL especificada en el navegador.
     *
     * @param label JLabel que se transformará en hipervínculo
     * @param url   Dirección URL que se abrirá al hacer clic
     */
    public static void hacerHipervinculo(JLabel label, String url) {
        if (label == null || url == null || url.isBlank()) return;

        label.setText("<html><a href=''>" + url + "</a></html>");
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirEnlace(url);
            }
        });
    }

    /**
     * Abre una URL en el navegador predeterminado del sistema.
     *
     * @param url La URL a abrir
     */
    private static void abrirEnlace(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.err.println("Desktop no soportado en este sistema.");
            }
        } catch (Exception ex) {
            System.err.println("Error al abrir el enlace: " + url);
            ex.printStackTrace();
        }
    }
}
