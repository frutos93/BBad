package jframeexamen;

import JFrameExamen.SoundClip;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedList;

public class JFrameExamen extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables. 
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private SoundClip payaso;
    private SoundClip snake;
    private SoundClip waka;
    private Ball bola;
    private Bloque pill;
    private BloqueR pillR;// Objeto de la clase Elefante
    private Barra1 bar;   //Objeto de la clase Raton
    private boolean musicafondo;
    private int vidas;
    private int contVidas;
    private Image game_over;        //Imagen de Game-over
    private int direccion;          //Variable para la dirección del personaje
    private int score;
    private boolean move;
    private boolean pausa;
    private long tiempoActual;
    private double tiempo;
    private boolean puedoDisparar;
    private int angulo;
    private boolean instrucciones;
    private final String nombreArchivo = "savedState.txt";
    private boolean puedoGrabar;
    private int valordemapa;
    private LinkedList<Bloque> lista;
    private LinkedList<BloqueR> lista2;
    private LinkedList<Bloque> lista3;
    private LinkedList<BloqueR> lista4;
    private LinkedList<Bloque> lista5;
    private Image fondo;

    /**
     * Constructor vacio de la clase <code>JFrameExamen</code>.
     */
    public JFrameExamen() {
        init();
        start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        valordemapa = (int) (Math.random() * ((3 - 1)));
        lista = new LinkedList();
        lista2 = new LinkedList();
        lista3 = new LinkedList();
        lista4 = new LinkedList();
        lista5 = new LinkedList();

        setSize(800, 500);
        pausa = false;
        move = false;
        musicafondo = false;
        direccion = 0;
        score = 0;                    //puntaje inicial
        vidas = 5;                    //vidaas iniciales
        contVidas = 0;                    //contador de vidas, cada 3 puntos se restará una vida
        payaso = new SoundClip("sounds/pashaso.wav");
        snake = new SoundClip("sounds/snake.wav");
        waka = new SoundClip("sounds/waka.wav");
        bar = new Barra1(getWidth() / 2, getHeight() - 30);
        setBackground(Color.black);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        bola = new Ball(bar.getPosX() + 20, bar.getPosY() - 30);
        for (int i = 1; i < 15; i++) {
            if (i == 1) {
                pill = new Bloque(40, 70);
                lista.add(pill);
            } else {
                Bloque pillaux = (Bloque) lista.get(i - 2);
                pill = new Bloque(pillaux.getPosX() + 50, pillaux.getPosY());
                lista.add(pill);
            }

        }
        for (int i = 1; i < 15; i++) {
            if (i == 1) {
                pillR = new BloqueR(40, 120);
                lista2.add(pillR);
            } else {
                BloqueR pillaux = (BloqueR) lista2.get(i - 2);
                pillR = new BloqueR(pillaux.getPosX() + 50, pillaux.getPosY());
                lista2.add(pillR);
            }

        }
        for (int i = 1; i < 15; i++) {
            if (i == 1) {
                pill = new Bloque(40, 170);
                lista3.add(pill);
            } else {
                Bloque pillaux = (Bloque) lista3.get(i - 2);
                pill = new Bloque(pillaux.getPosX() + 50, pillaux.getPosY());
                lista3.add(pill);
            }

        }
        for (int i = 1; i < 15; i++) {
            if (i == 1) {
                pillR = new BloqueR(40, 220);
                lista4.add(pillR);
            } else {
                BloqueR pillaux = (BloqueR) lista4.get(i - 2);
                pillR = new BloqueR(pillaux.getPosX() + 50, pillaux.getPosY());
                lista4.add(pillR);
            }

        }

        URL goURL = this.getClass().getResource("pill/creditos.jpg");
        game_over = Toolkit.getDefaultToolkit().getImage(goURL);
        URL fURL = this.getClass().getResource("Fondo/FondoDos.jpg");
        fondo = Toolkit.getDefaultToolkit().getImage(fURL).getScaledInstance(getWidth(), getHeight(), 1);
        instrucciones = false;
        puedoGrabar = true;
    }

    /**
     * Metodo <I>Start</I> sobrescrito de la clase <code>Thread</code>.<P>
     * Este metodo comienza la ejecucion del hilo. Esto llama al metodo
     * <code>run</code>
     */
    public void start() {
        // Declaras un hilo
        payaso.setLooping(true);
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        tiempoActual = System.currentTimeMillis();
        while (vidas > 0) {
            if (musicafondo) {
                payaso.stop();
                payaso.setLooping(false);
            } else {
                if (!payaso.getLooping()) {
                    payaso.setLooping(true);
                    payaso.play();
                }
            }
            if (!pausa) {
                actualiza();
                checaColision();
            }
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo <I>actualiza</I>.
     * <P>
     * En este metodo se actualizan las posiciones de link como de la armadura,
     * ya sea por presionar una tecla o por moverlos con el mouse.
     */
    public void actualiza() {

        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        tiempoActual += tiempoTranscurrido;
        pill.actualiza(tiempoTranscurrido);
        bar.actualiza(tiempoTranscurrido);
        double incrementoTiempo = 0.05;
        tiempo += incrementoTiempo;    //actualizar el tiempo y la nueva posicion.

        if (move) {
            bar.setMoviendose(true);
            switch (direccion) {
                case 3: {
                    bar.setPosX(bar.getPosX() - 3);
                    break; //se mueve hacia la izquierda
                }
                case 4: {

                    bar.setPosX(bar.getPosX() + 3);
                    break; //se mueve hacia la derecha
                }
            }
        }

    }

    /**
     * Metodo usado para checar las colisiones del objeto link con el objeto
     * armadura y además con las orillas del <code>Applet</code>.
     */
    public void checaColision() {
        if (bar.getPosX() + bar.getAncho() > getWidth()) {
            bar.setPosX(getWidth() - bar.getAncho());
        }
        if (bar.getPosX() < 0) {
            bar.setPosX(0);
        }
        for (Bloque i : lista) {
            if (bola.intersec(i)) {
                if (i.getPosY() < bola.getPosY() + bola.getAlto() || i.getPosY() + i.getAlto() > bola.getPosY()) { //por arriba o por abajo
                    bola.setVelY(-bola.getVelY());
                } else {           //por la izquierda o la derecha
                    bola.setVelX(-bola.getVelX());
                }
            }
        }

    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);

    }

    /**
     * Metodo <I>keypPressed</I> sobrescrito de la clase
     * <code>KeyEvent</code>.<P>
     * En este método se actualiza la variable de dirección dependiendo de la
     * tecla que haya sido precionado El parámetro e se usará cpara obtener la
     * acción de la tecla que fue presionada.
     *
     */
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            direccion = 3;
            move = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            direccion = 4;
            move = true;
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            pausa = !pausa;
        } else if (e.getKeyCode() == KeyEvent.VK_S && !pausa) {
            musicafondo = !musicafondo;

        } else if (e.getKeyCode() == KeyEvent.VK_G && puedoGrabar && !instrucciones) {
            grabaArchivo();

        } else if (e.getKeyCode() == KeyEvent.VK_I) {
            instrucciones = !instrucciones;
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
            cargarArchivo();
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la clase
     * <code>KeyEvent</code>.<P>
     * En este método se verifica si alguna tecla que haya sido presionada es
     * liberada. Si es liberada la booleana que controla el movimiento se
     * convierte en falsa.
     */
    public void keyReleased(KeyEvent e) {
        move = false;
        bar.setMoviendose(false);
        if (e.getKeyCode() == KeyEvent.VK_G) {
            puedoGrabar = true;
        }
    }

    /**
     * Metodo <I>mouseClicked</I> sobrescrito de la clase
     * <code>MouseEvent</code>.
     * <P>
     * Este metodo es invocado cuando se ha presionado un boton del mouse en un
     * componente.
     *
     * @param e es el evento generado al ocurrir lo descrito.
     */
    public void mouseClicked(MouseEvent e) {
        if (!puedoDisparar) {
            if (pill.contiene(e.getX(), e.getY())) {
                puedoDisparar = true;
            }
        }

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    /**
     * Metodo <I>MousePressed</I> sobrescrito de la clase
     * <code>mouseEvent</code>.<P>
     * En este metodo se verifica si el mouse ha dado click sobre la imágen. Al
     * verificar que haya dado un click se actualizan las coordenadas de 'x' y
     * 'y' para ajustar el desfase que puede tener la imagen con el click
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Metodo <I>MouseReleased</I> sobrescrito de la clase
     * <code>MouseEvent</code>.<P>
     * En este método se verifica si el click del mouse ha sido liberado, si sí
     * entonces la booleana que l ocontrola se hace falsa, para marcar que ya no
     * está siendo presionadao.
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Metodo <I>mouseReleased</I> sobrescrito de la clase
     * <code>MouseEvent</code>.<P>
     * Este metodo es invocado cuando el cursor es movido dentro de un
     * componente sin presionar ningun boton.
     *
     * @param e es el evento generado al ocurrir lo descrito.
     */
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * Metodo <I>mouseDragged</I> sobrescrito de la clase
     * <code>MouseEvent</code>.<P>
     * Este metodo es invocado cuando se presiona un boton en un componente, y
     * luego este es arrastrado.
     *
     * @param e es el evento generado al ocurrir lo descrito.
     */
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Metodo <I>paint1</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @paramg es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (vidas > 0) {
            g.drawImage(fondo, 0, 0, this);
            if (lista != null && bar != null) {
                for (Bloque i : lista) {

                    g.drawImage(i.getImagenI(), i.getPosX(), i.getPosY(), this);
                }
                for (BloqueR i : lista2) {

                    g.drawImage(i.getImagenI(), i.getPosX(), i.getPosY(), this);
                }
                for (Bloque i : lista3) {

                    g.drawImage(i.getImagenI(), i.getPosX(), i.getPosY(), this);
                }
                for (BloqueR i : lista4) {

                    g.drawImage(i.getImagenI(), i.getPosX(), i.getPosY(), this);
                }

                g.drawImage(bola.getImagenI(), bola.getPosX(), bola.getPosY(), this);
                g.drawImage(bar.getImagenI(), bar.getPosX(), bar.getPosY(), this);

                g.setColor(Color.white);
                g.drawString("Puntos = " + score, 20, 50);
                g.drawString("Vidas = " + vidas, 20, 70);
                g.drawString("Presiona I para ver instrucciones.", getWidth() - 200, 50);
                //    if (pausa) {
                //        g.setColor(Color.white);
                //        g.drawString(pill.getPausado(), pill.getPosX() + pill.getAncho() / 3, pill.getPosY() + pill.getAlto() / 2);
                //    }
                if (instrucciones) {
                    g.drawString("Instrucciones:", 20, 90);
                    g.drawString("Haz click en el personaje para lanzarlo. Tu objetivo es atraparlo con la mano. Si lo haces, obtendras puntos.", 20, 110);
                    g.drawString("Para mover la mano, presiona las teclas de flecha izquierda o derecha.", 20, 130);
                    g.drawString("Presiona G para guardar tu partida, C para cargar, P para pausar y S para detener la musica.", 20, 150);
                    g.drawString("Si el personaje cae tres veces, perderas una vida y la dificultad aumentara.", 20, 170);

                }
            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }

        } else {
            g.drawImage(game_over, getWidth() / 5, 0, this);
        }
    }

    /**
     * Metodo <I>grabaArchivo</I> utilizado para grabar los datos del juego en
     * un archivo con extension <I>.txt</I>
     */
    public void grabaArchivo() {

        try {
            File file = new File(nombreArchivo);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            Vector datos = new Vector();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                datos.add(pill.getPosX());
                datos.add(pill.getPosY());
                if (pill.getMoviendose()) {
                    datos.add(1);
                } else {
                    datos.add(0);
                }
                datos.add(bar.getPosX());
                datos.add(bar.getPosY());
                datos.add(contVidas);
                datos.add(vidas);
                datos.add(score);
                if (move) {
                    datos.add(1);
                } else {
                    datos.add(0);
                }
                datos.add(tiempo);
                datos.add(angulo);
                if (puedoDisparar) {
                    datos.add(1);
                } else {
                    datos.add(0);
                }

                for (Object i : datos) {
                    bw.write("" + i + "\n");
                }
                puedoGrabar = false;
            }

        } catch (IOException ioe) {
            System.out.println(" Se obtuvo error al grabar archivo : " + ioe.toString());
        }
    }

    /**
     * Metodo <I>cargarArchivo</I> utilizado para cargar los datos que fueron
     * grabados la ultima vez que se jugo. El archivo tiene extension
     * <I>.txt</I>
     */
    public void cargarArchivo() {
        BufferedReader br;
        try {
            File file = new File(nombreArchivo);
            if (!file.exists()) {
                return;
            }
            br = new BufferedReader(new FileReader(file));
            Vector datos = new Vector();
            for (String line; (line = br.readLine()) != null;) {
                if (line.isEmpty()) {
                    break;
                }
                datos.add(Double.valueOf(line).intValue());
            }

            pill.setPosX((int) datos.get(0));
            pill.setPosY((int) datos.get(1));
            pill.setMoviendose((int) datos.get(2) == 1);
            bar.setPosX((int) datos.get(3));
            bar.setPosY((int) datos.get(4));
            contVidas = (int) datos.get(5);
            vidas = (int) datos.get(6);
            score = (int) datos.get(7);
            move = ((int) datos.get(8) == 1);
            tiempo = (int) datos.get(9);
            angulo = (int) datos.get(10);
            puedoDisparar = ((int) datos.get(11) == 1);
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JFrameExamen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JFrameExamen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
