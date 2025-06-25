package Modelos;

import java.awt.Component;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class Evaluador {
    public static int calcularNota(ButtonGroup[] grupos, JRadioButton[] respuestasCorrectas) {
        int nota = 0;
        for (int i = 0; i < grupos.length; i++) {
            if (respuestasCorrectas[i].isSelected()) {
                nota += 5;
            }
        }
        return nota;
    }

    public static void mostrarNota(Component parent, int nota) {
        JOptionPane.showMessageDialog(parent, "Tu nota es: " + nota + " puntos");
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
