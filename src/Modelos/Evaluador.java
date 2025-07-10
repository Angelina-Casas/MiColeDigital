package Modelos;

import javax.swing.*;
import java.awt.Component;

public class Evaluador {

    private static final int PUNTAJE_POR_RESPUESTA = 5;

    public static int calcularNota(ButtonGroup[] grupos, JRadioButton[] respuestasCorrectas) {
        int nota = 0;
        for (int i = 0; i < grupos.length; i++) {
            if (respuestasCorrectas[i] != null && respuestasCorrectas[i].isSelected()) {
                nota += PUNTAJE_POR_RESPUESTA;
            }
        }
        return nota;
    }

    public static void mostrarNota(Component parent, int nota) {
        JOptionPane.showMessageDialog(parent, "Tu nota es: " + nota + " puntos",
                "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean todosLosGruposRespondidos(ButtonGroup[] grupos) {
        for (ButtonGroup grupo : grupos) {
            if (grupo.getSelection() == null) {
                return false;
            }
        }
        return true;
    }
}
