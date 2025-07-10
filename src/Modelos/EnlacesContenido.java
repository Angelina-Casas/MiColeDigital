package Modelos;

import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class EnlacesContenido {

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
