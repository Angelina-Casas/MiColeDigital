package Modelos;

import javax.swing.*;
import java.awt.Component;

/**
 * Clase utilitaria para evaluar respuestas de formularios/prácticas.
 */
public class Evaluador {

    private static final int PUNTAJE_POR_RESPUESTA = 5;

    /**
     * Calcula la nota total basándose en las respuestas correctas seleccionadas.
     *
     * @param grupos             Arreglo de grupos de botones (uno por pregunta)
     * @param respuestasCorrectas Arreglo de radio buttons que representan las respuestas correctas
     * @return Nota total del estudiante
     */
    public static int calcularNota(ButtonGroup[] grupos, JRadioButton[] respuestasCorrectas) {
        int nota = 0;
        for (int i = 0; i < grupos.length; i++) {
            if (respuestasCorrectas[i] != null && respuestasCorrectas[i].isSelected()) {
                nota += PUNTAJE_POR_RESPUESTA;
            }
        }
        return nota;
    }

    /**
     * Muestra la nota al usuario mediante un JOptionPane.
     *
     * @param parent Componente padre para el cuadro de diálogo
     * @param nota   Nota obtenida
     */
    public static void mostrarNota(Component parent, int nota) {
        JOptionPane.showMessageDialog(parent, "Tu nota es: " + nota + " puntos",
                "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Verifica si todos los grupos de botones tienen una opción seleccionada.
     *
     * @param grupos Arreglo de grupos de botones
     * @return true si todos tienen selección; false en caso contrario
     */
    public static boolean todosLosGruposRespondidos(ButtonGroup[] grupos) {
        for (ButtonGroup grupo : grupos) {
            if (grupo.getSelection() == null) {
                return false;
            }
        }
        return true;
    }
}
