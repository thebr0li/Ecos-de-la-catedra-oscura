package com.mycompany.ecos_de_la_catedral_ocura;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.util.ArrayList;

class PanelJuego extends JPanel implements KeyListener, ActionListener {
    
    Jugador minero;
    Recolectable luz;
    ArrayList<Sombra> enemigos;
    ArrayList<Muro> muros;
    ArrayList<Proyectil> proyectiles; // Lista de disparos
    
    int velocidad = 10;
    int puntaje = 0;
    boolean juegoTerminado = false;
    Timer reloj;

    public PanelJuego() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(this);
        
        inicializarJuego();
        reloj = new Timer(16, this);
        reloj.start();
    }

    public void inicializarJuego() {
        minero = new Jugador(385, 285);
        proyectiles = new ArrayList<>();
        
        // Creamos algunos muros para el mapa
        muros = new ArrayList<>();
        int[][] diseñoMapa = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,1,1,0,0,0,1,1,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1},
            {1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1},
            {1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,0,0,0,1,0,0,1,0,0,0,1,1,1,0,0,1},
            {1,0,0,1,0,0,0,1,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,1,1,1,0,1,0,0,1,0,0,0,1,1,1,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        
        int tamañoBloque = 40;
        
        for (int fila = 0; fila < diseñoMapa.length; fila++) {
            for (int col = 0; col < diseñoMapa[fila].length; col++) {
                if (diseñoMapa[fila][col] == 1) {
                    // Multiplicamos el índice por 40 para obtener la coordenada exacta en pantalla
                    muros.add(new Muro(col * tamañoBloque, fila * tamañoBloque, tamañoBloque, tamañoBloque));
                }
            }
        }
        
        puntaje = 0;
        enemigos = new ArrayList<>();
        enemigos.add(new Sombra());
        juegoTerminado = false;
        luz = new Recolectable(muros);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (!juegoTerminado) {
            for (Muro m : muros){
                m.dibujar(g);
            }
            minero.dibujar(g);
            luz.dibujar(g);
            for (Sombra enemigo : enemigos) enemigo.dibujar(g);
            for (Proyectil p : proyectiles) p.dibujar(g);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Luz: " + puntaje, 20, 30);
            g.drawString("Cordura: " + minero.cordura + "%", 20, 60);
        } else {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("¡LA PENUMBRA TE CONSUMIÓ!", 100, 300);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Presiona 'R' para reiniciar", 280, 400);
        }
    }

    public void chequearColisiones() {
        // Colisión Luz
        if (minero.obtenerBordes().intersects(luz.obtenerBordes())) {
            luz.reubicar(muros);
            puntaje++;
            if (puntaje % 3 == 0) enemigos.add(new Sombra());
        }
        
        // Colisión Enemigos vs Jugador (Ahora resta cordura en vez de matar instantáneo)
        for (int i = 0; i < enemigos.size(); i++) {
            if (minero.obtenerBordes().intersects(enemigos.get(i).obtenerBordes())) {
                minero.cordura -= 2; // Resta vida muy rápido porque colisiona 60 veces por segundo
                if (minero.cordura <= 0) {
                    juegoTerminado = true;
                    reloj.stop();
                }
            }
        }

        // Colisión Proyectiles vs Enemigos
        ArrayList<Sombra> sombrasMuertas = new ArrayList<>();
        ArrayList<Proyectil> proyectilesRotos = new ArrayList<>();

        for (Proyectil p : proyectiles) {
            for (Sombra s : enemigos) {
                if (p.obtenerBordes().intersects(s.obtenerBordes())) {
                    sombrasMuertas.add(s);
                    proyectilesRotos.add(p);
                }
            }
            // Si el proyectil choca contra un muro, se destruye
            for (Muro m : muros) {
                if (p.obtenerBordes().intersects(m.obtenerBordes())) proyectilesRotos.add(p);
            }
        }
        
        // Limpiamos las listas
        enemigos.removeAll(sombrasMuertas);
        proyectiles.removeAll(proyectilesRotos);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!juegoTerminado) {
            for (Sombra enemigo : enemigos) enemigo.moverAutomaticamente(muros);
            for (Proyectil p : proyectiles) p.mover();
            chequearColisiones();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();

        if (juegoTerminado && tecla == KeyEvent.VK_R) {
            inicializarJuego();
            reloj.start();
            return;
        }
        if (juegoTerminado) return;

        // Le pasamos la lista de muros al método mover
        if (tecla == KeyEvent.VK_W || tecla == KeyEvent.VK_UP) minero.mover(0, -velocidad, muros);
        if (tecla == KeyEvent.VK_S || tecla == KeyEvent.VK_DOWN) minero.mover(0, velocidad, muros);
        if (tecla == KeyEvent.VK_A || tecla == KeyEvent.VK_LEFT) minero.mover(-velocidad, 0, muros);
        if (tecla == KeyEvent.VK_D || tecla == KeyEvent.VK_RIGHT) minero.mover(velocidad, 0, muros);

        // Disparo con la barra espaciadora
        if (tecla == KeyEvent.VK_SPACE) {
            int velBala = 15;
            proyectiles.add(new Proyectil(minero.x + 10, minero.y + 10, minero.dirX * velBala, minero.dirY * velBala));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
