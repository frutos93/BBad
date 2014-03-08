package jframeexamen;


import java.awt.Image;
import java.awt.Toolkit;

      import java.awt.Image;
      import java.awt.Toolkit;

    public class Ball extends Base{
        
    public Ball(int posX,int posY){
	super(posX,posY);	
        Image malo1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pill/BolaDos.png"));
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