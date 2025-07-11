/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import javax.swing.*;

public class ComplementosAdmin {
    public static void mostrarMensaje(JFrame frame, String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje);
    }

    public static boolean verificarCamposTexto(JFrame frame, String texto, String campoNombre) {
        if (texto == null || texto.trim().isEmpty()) {
            mostrarMensaje(frame, "El campo '" + campoNombre + "' no puede estar vacío.");
            return false;
        }
        return true;
    }

    public static boolean verificarGrado(JFrame frame, String gradoStr) {
        try {
            int grado = Integer.parseInt(gradoStr);
            if (grado < 1 || grado > 6) {
                mostrarMensaje(frame, "El grado debe estar entre 1 y 6.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarMensaje(frame, "El grado debe ser un número entero.");
            return false;
        }
        return true;
    }

    public static boolean verificarSeccion(JFrame frame, String seccion) {
        if (seccion.length() >= 3) {
            mostrarMensaje(frame, "La sección debe tener máximo 2 caracteres.");
            return false;
        }
        return true;
    }
    
    public static boolean verificarCamposCurso(JFrame frame, String nombre, Object docente, Object aula) {
    if (nombre == null || nombre.trim().isEmpty()) {
        mostrarMensaje(frame, "El nombre del curso no puede estar vacío.");
        return false;
    }
    if (docente == null) {
        mostrarMensaje(frame, "Selecciona un docente.");
        return false;
    }
    if (aula == null) {
        mostrarMensaje(frame, "Selecciona un aula.");
        return false;
    }
    return true;
    }
    
}
