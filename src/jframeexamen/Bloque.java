package jframeexamen;

import java.awt.Image;
import java.awt.Toolkit;

public class Bloque extends Base {

    private int golpes;
    private boolean destruido;
    private boolean choca;

    public Bloque(int posX, int posY) {
        super(posX, posY);
        Image bueno1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pill/green pill 1.png"));
        animacion = new Animacion();
        animacion.sumaCuadro(bueno1, 100);
        destruido = false;
        golpes = 0;
        choca = false;
    }

    public boolean getChoca() {
        return choca;
    }

    public void setChoca(boolean c) {
        choca = c;
    }

    private static final String PAUSADO = "PAUSADO";
    private static final String DESAPARECE = "DESAPARECE";

    public static String getPausado() {
        return PAUSADO;
    }

    public static String getDesaparece() {
        return DESAPARECE;
    }

    public void setdestruido(boolean rompe) {
        this.destruido = rompe;
    }

    public int getGolpes() {
        return golpes;
    }

    public void addGolpe() {
        golpes++;
        if (golpes == 3) {
            destruido = true;
        }
    }
}
