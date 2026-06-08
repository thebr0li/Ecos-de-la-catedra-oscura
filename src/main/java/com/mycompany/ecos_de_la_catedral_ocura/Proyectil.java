package com.mycompany.ecos_de_la_catedral_ocura;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Proyectil {
    private int x, y;
    private int velocidadX, velocidadY;

    public Proyectil(int x, int y, int velocidadX, int velocidadY) {
        this.x = x;
        this.y = y;
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
    }
    
    public void mover(){
        x += velocidadX;
        y += velocidadY;
    }
    
    public void dibujar(Graphics g){
        g.setColor(Color.CYAN);
        g.fillOval(x, y, 10, 10);
    }
    
    public Rectangle obtenerBordes(){
        return new Rectangle(x, y, 10, 10);
    }
}
