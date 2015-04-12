package filosofos;

import java.util.Random;
import java.util.concurrent.Semaphore;
 
/**
 * Hilo fil�sofo. Su m�todo run() realiza un bucle infinito que consite en
 * invocar a los m�todos pensar() y comer(): ... -&gt; pensar -&gt; comer ...
 */
public class filosofos extends Thread {
 
    /**
     * �ndice que identifica al fil�sofo (un entero del 0 al 4)
     */
    private final int idfilosofos;
 
    /**
     * Array de sem�foros. Cada sem�faro es un palillo.
     */
    private final Semaphore[] palillos_semaforo;
 
    /**
     * Array de enteros 2 dimensiones. Por cada valor de su primer �ndice
     * (filas) almacena los palillos que necesita el fil�sofo de ese �ndice para
     * comer. Por ejemplo: el fil�sofo de �ndice 0 necesita los palillos de
     * �ndices {0,4}, el de �ndice 1 los de �ndices {1,0}, etc...
     */
    private final int[][] palillosFilosofo;
 
    /**
     * El �ndice del palillo a la izquierda del fil�sofo. Se obtiene en el
     * constructor a partir del array de enteros palillosFil�sofo.
     */
    private final int palilloIzquierdo;
 
    /**
     * El �ndice del palillo a la derecha del fil�sofo. Se obtiene en el
     * constructor a partir del array de enteros palillosFil�sofo.
     */
    private final int palilloDerecho;
 
    /**
     * N�mero aleatorio para simular el tiempo que tardan los fil�sofos en comer
     * y pensar.
     */
    private final Random tiempoAleatorio = new Random();
 
    /**
     * Constructor de tres par�metros, cada uno de los cuales se guardar� en una
     * variable local para usarla cuando sea necesario.
     *
     * @param idFilosofo int: �ndice que identifica a cada fil�sofo (entero del
     * 0 al 4).
     * @param palillos_semaforo Semaphore[]: array de sem�foros, uno por cada
     * palillo.
     * @param palillosFilosofo int[][]: array de enteros 2 dimensiones. Por cada
     * valor de su primer �ndice (filas) almacena los palillos que necesita el
     * fil�sofo de ese �ndice para comer. Por ejemplo: el fil�sofo de �ndice 0
     * necesita los palillos de �ndices {0,4}, el de �ndice 1 los de �ndices
     * {1,0}, etc...
     */
    public filosofos(int idFilosofo, Semaphore[] palillos_semaforo, int[][] palillosFilosofo) {
        this.idfilosofos = idFilosofo;
        this.palillos_semaforo = palillos_semaforo;
        this.palillosFilosofo = palillosFilosofo;
        this.palilloIzquierdo = palillosFilosofo[idFilosofo][0];
        this.palilloDerecho = palillosFilosofo[idFilosofo][1];
    }
 
    /**
     * Imprimir un mensaje "Filos�fo n est� hambriento" mientras trata de
     * conseguir los 2 palillos que necesita para comer. Una vez conseguidos,
     * mostrar� un mensaje "Fil�sofo n est� comiendo".&lt;br&gt; Para simular esta
     * actividad, dormir� el hilo un tiempo aleatorio. Cuando termine, mostrar�
     * un mensaje "El fil�sofo n ha terminado de comer", indicando los palillos
     * que quedan libres.
     */
    protected void comer() {
        // El fil�sofo tratar� de coger primero el pallillo de su izquierda.
        // Si lo consigue, tratar� entonces de coger el palillo de su derecha
        // El m�todo tryAcquire() adquiere un permiso del sem�foro si es
        // posible y devuelve true en ese caso. Devuelve false si no da permiso.
        if (palillos_semaforo[palilloIzquierdo].tryAcquire()) {
            if (palillos_semaforo[palilloDerecho].tryAcquire()) {
                System.out.println("FIL�SOFO " + idfilosofos + " EST� COMIENDO.");
                try {
                    // Simular el tiempo que tarda el fil�sofo en comer,
                    // entre 0.5 y 1 segundos:
                    sleep(tiempoAleatorio.nextInt(1000) + 500);
                } catch (InterruptedException ex) {
                    System.out.println("Error : " + ex.toString());
                }
                System.out.println("Fil�sofo " + idfilosofos + " ha terminado de comer.Libera los palillos " + palilloIzquierdo + " y " + palilloDerecho);
                // Ya que ha terminado de comer libera el sem�foro-palillo de su derecha:
                palillos_semaforo[palilloDerecho].release();
            }
            // Y libera tambi�n el sem�foro palillo de su izuierda.
            palillos_semaforo[palilloIzquierdo].release();
        } else { // el fil�sofo no ha podido coger el pallillo, no puede comer.
            System.out.println("Fil�sofo " + idfilosofos + " est� hambriento.");
        }
    }
 
    /**
     * Imprimir un mensaje "Fil�sofo n est� pensando". Para simular esta
     * actividad, dormir� el hilo un tiempo aleatorio.
     */
    protected void pensar() {
        System.out.println("Fil�sofo " + idfilosofos + " est� pensando.");
        try {
            // El tiempo que tarda el fil�sofo en pensar, entre 100 y 1000 milisegundos:
            filosofos.sleep(tiempoAleatorio.nextInt(1000) + 100);
        } catch (InterruptedException ex) {
            System.out.println("Error en el m�todo pensar(): " + ex.toString());
        }
    }
 
    /**
     * Bucle infinito: ... -&gt; llamar al m�todo pensar() -&gt; Llamar al m�todo comer() ...
     */
    @Override
    public void run() {
        while (true) {
            pensar();
            comer();
        }
    }
}