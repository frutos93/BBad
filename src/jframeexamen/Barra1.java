package jframeexamen;


import java.awt.Image;
import java.awt.Toolkit;

      import java.awt.Image;
      import java.awt.Toolkit;

    public class Barra1 extends Base{
        
    public Barra1(int posX,int posY){
	super(posX,posY);	
        Image malo1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("barra/barra.png"));
        animacion = new Animacion();
        animacion.sumaCuadro(malo1, 100);

	}
    
    private static final String PAUSADO = "PAUSADO";
    private static final String DESAPARECE = "DESAPARECE";
    
    public static String getPausado(){
        return PAUSADO;
    }
    
    public static String getDesaparece(){
        return DESAPARECE;
    }
    
}