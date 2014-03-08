package jframeexamen;

      import java.awt.Image;
      import java.awt.Toolkit;

    public class Bloque extends Base{
        
    public Bloque(int posX,int posY){
	super(posX,posY);	
        Image bueno1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pill/bluepill.png"));

               
        animacion = new Animacion();
        animacion.sumaCuadro(bueno1, 100);

        
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