package com.mycompany.ecos_de_la_catedral_ocura;

import javax.swing.JFrame;

public class Ecos_de_la_catedral_ocura {

    public static void main(String[] args) {
        //inicializamo ventana
        JFrame ventana = new JFrame();
        //evitamos que el juego siga corriendo al cerrar la ventana}
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //el jugador no puede cambiar el tamaaño de la ventana
        ventana.setResizable(false);
        ventana.setTitle("Minerio en la penumbra");
        // Instanciamos nuestro lienzo y lo agregamos a la ventana
        PanelJuego panel = new PanelJuego();
        ventana.add(panel);
        // Esta función hace que la ventana se ajuste al tamaño exacto de nuestro JPanel
        ventana.pack();
        //centramos
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}
