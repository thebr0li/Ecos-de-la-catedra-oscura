package com.mycompany.ecos_de_la_catedral_ocura;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Jugador {
    public int x, y;
    private int ancho = 30;
    private int alto = 30;
    public double cordura = 100.0;
    public int dirX = 1, dirY = 0; // Direccion a la que mira (1 = derecha/abajo, -1 = izquierda/arriba, 0 = quieto)
    private Image spriteJugador;

    public Jugador(int xInicial, int yInicial) {
        this.x = xInicial;
        this.y = yInicial;
        try {
            spriteJugador = ImageIO.read(getClass().getResourceAsStream("/heroe.png"));
        } catch (Exception e) {
            spriteJugador = null;
        }
    }
    
    public void dibujar(Graphics g){
        if (spriteJugador != null) {
            g.drawImage(spriteJugador, x, y, ancho, alto, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, ancho, alto);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x + 4, y + 4, 6, 6);
            g.fillRect(x + 20, y + 4, 6, 6);
            g.setColor(Color.GRAY);
            g.fillRect(x + 10, y + 18, 10, 4);
        }
        //BARRA DE CORDURA
        g.setColor(Color.GREEN);
        if(cordura < 50) g.setColor(Color.RED);
        g.fillRect(x, y - 10, (int)(ancho * cordura / 100.0), 5);
    }
    
    public void mover(int cantidadX, int cantidadY, ArrayList<Muro> muros){
        
        if(cantidadX != 0){
            dirX = (cantidadX > 0 ? 1 : -1);
            dirY = 0;
        }
        if(cantidadY !=0){
            dirY = (cantidadY > 0 ? 1 : -1);
            dirX = 0;
        }
        
        Rectangle futuro = new Rectangle(x + cantidadX, y + cantidadY, ancho, alto);
        boolean choca = false;
        
        for (Muro m : muros) {
            if(futuro.intersects(m.obtenerBordes())){
                choca = true;
            }
        }
        
        if(!choca){
            this.x += cantidadX;
            this.y += cantidadY;
        }
    }
    public Rectangle obtenerBordes(){
        return new Rectangle(x, y, ancho, alto);
    }
}
