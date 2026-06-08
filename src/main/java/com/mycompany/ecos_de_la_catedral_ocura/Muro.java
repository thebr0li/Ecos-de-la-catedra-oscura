package com.mycompany.ecos_de_la_catedral_ocura;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Muro {
    private int x, y, ancho, alto;

    public Muro(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }
    
    public void dibujar(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, alto, ancho);
    }
    
    public Rectangle obtenerBordes(){
        return new Rectangle(x, y, ancho, alto);
    }
}
