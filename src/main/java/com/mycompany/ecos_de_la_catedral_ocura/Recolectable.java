package com.mycompany.ecos_de_la_catedral_ocura;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Recolectable {
    private int x;
    private int y;
    private int tamaño = 15;
    
    public Recolectable(ArrayList<Muro> muros){
        reubicar(muros);
    }
    public void reubicar(ArrayList<Muro> muros){
        boolean chocaConMuro;
        Random random = new Random();
        
        do{
            chocaConMuro = false;
            this.x = random.nextInt(700);
            this.y = random.nextInt(500);
            Rectangle futuroLugar = new Rectangle(this.x, this.y, tamaño, tamaño);
            for (Muro m : muros) {
                if (futuroLugar.intersects(m.obtenerBordes())) {
                    chocaConMuro = true;
                    break;
                }
            }
        }while(chocaConMuro);
        
    }
    public void dibujar(Graphics g){
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, tamaño, tamaño);
    }
    
    public Rectangle obtenerBordes(){
        return new Rectangle(x, y, tamaño, tamaño);
    }
}
