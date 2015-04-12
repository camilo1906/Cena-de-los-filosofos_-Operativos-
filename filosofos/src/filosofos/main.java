package filosofos;

/* Pueden estar comiendo 2 fil�sofos a la vez:
	 * El fil�sofo 0 puede comer con el fil�sofo 2 o con el 3.
	 * El fil�sofo 1 puede comer con el fil�sofo 3 o con el 4.
	 * El fil�sofo 2 puede comer con el fil�sofo 4 o con el 0.
	 * El fil�sofo 3 puede comer con el fil�sofo 0 o con el 1.
	 * El fil�sofo 4 puede comer con el fil�sofo 1 o con el 2.
	 */

	 
	import java.util.concurrent.Semaphore;
	 
	/**
	 * Clase donde reside el m�todo main() que inicia la aplicaci�n
	 *
	 * @author netsis.es
	 */
	
	public class main  {
	 
	    /**
	     * La cantidad de fil�sofos
	     */
	    final static int numeroFilosofos = 5;
	 
	    /**
	     * Array de 2 dimensiones que tiene tantas filas como fil�sofos. Cada fila
	     * es un un array de 2 enteros que representan los 2 palillos del fil�sofo:
	     * el de su izquierda y el de su derecha, por este orden.
	     */
	    final static int[][] palillosFilosofo = {
	        {0, 4}, // filosofo 0
	        {1, 0}, // filosofo 1
	        {2, 1}, // filosofo 2
	        {3, 2}, // filosofo 3
	        {4, 3} // filosofo 4
	    };
	 
	    /**
	     * Array de objetos Semaphore que representan a los palillos. Hay tantos
	     * palillos como fil�sofos.
	     */
	    final static Semaphore[] palillos_semaforo = new Semaphore[numeroFilosofos];
	 
	    /**
	     * Define los par�metros necesarios para construir hilos de la clase
	     * Fil�sofo. Se crean los 5 fil�sofos y se inician.
	     */
	    public static void main(String[] args) {
	        // Crear sem�foros. A cada uno se le da un �nico permiso:
	        for (int i = 0; i < numeroFilosofos; i++) {
	            // S�lo 1 permiso porque cada palillo-semaforo solo puede tenerlo un fil�sofo a la vez.
	            palillos_semaforo[i] = new Semaphore(1);
	        }
	 
	        // Crear los objetos de tipo Filosofo que extienden Thread, e iniciarlos.
	        // Al constructor se le pasa un id, el array de los semaforos
	        // y el array de sus 2 palillos:
	        for (int idFilosofo = 0; idFilosofo < numeroFilosofos; idFilosofo++) {
	            new filosofos(idFilosofo, palillos_semaforo, palillosFilosofo).start();
	        }
	    }
	}