package jframeexamen;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.Toolkit;

public class Ball extends Base {

    private int velX;
    private int velY;

    public Ball(int posX, int posY) {
        super(posX, posY);
        Image malo1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pill/BolaDos.png")).getScaledInstance(30, 30, 1);
        animacion = new Animacion();
        animacion.sumaCuadro(malo1, 100);
        velX = 0;
        while (velX == 0) {
            velX = (int) (Math.random() * 10) - 5;
        }
        velY = -4;
    }

    public int getVelX() {
        return velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelX(int v) {
        velX = v;
    }

    public void setVelY(int v) {
        velY = v;
    }
    private static final String PAUSADO = "PAUSADO";
    private static final String DESAPARECE = "DESAPARECE";

    public static String getPausado() {
        return PAUSADO;
    }

    public static String getDesaparece() {
        return DESAPARECE;
    }

}
