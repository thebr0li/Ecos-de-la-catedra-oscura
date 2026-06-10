package com.mycompany.ecos_de_la_catedral_ocura;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Sombra {
    private int x = 100, y = 100;
    private int velocidadX = 4, velocidadY = 4;
    private int tamaño = 25;
    private Random random = new Random();
    
    public void moverAutomaticamente(ArrayList<Muro> muros){
        Rectangle futuroX = new Rectangle(x + velocidadX, y, tamaño, tamaño);
        for (Muro m : muros) {
            if (futuroX.intersects(m.obtenerBordes())) {
                velocidadX = -velocidadX;
                
                //CAOS: Decide al azar si ir para arriba o para abajo
                velocidadY = random.nextBoolean() ? 4 : -4; 
                
                // 3. COSTURA: Rompemos el ciclo para no evaluar otros bloques contiguos
                break; 
            }
        }
        x += velocidadX;
        Rectangle futuroY = new Rectangle(x, y + velocidadY, tamaño, tamaño);
        for (Muro m : muros) {
            if (futuroY.intersects(m.obtenerBordes())) {
                velocidadY = -velocidadY;
                
                //CAOS: Decide al azar si ir para arriba o para abajo
                velocidadX = random.nextBoolean() ? 4 : -4; 
                    
                break; 
            }
        }
        y += velocidadY;
        
        if (x <= 0 || x >= 760) {
            velocidadX = -velocidadX;
            velocidadY = random.nextBoolean() ? 4 : -4;
        }
        if (y <= 0 || y >= 530) {
            velocidadY = -velocidadY;
            velocidadX = random.nextBoolean() ? 4 : -4;
        }
    }
    
    public void dibujar(Graphics g){
        g.setColor(Color.red);
        g.fillRect(x, y, tamaño, tamaño);
    }
    
    public Rectangle obtenerBordes(){
        return new Rectangle(x, y , tamaño, tamaño);
    }
}
