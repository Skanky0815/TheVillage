package core.engine;

/**
 * Macht Objekte bewegbar
 * @author RICO
 * @version 0.1
 */
public interface Moveable {

	/**
	 * Methode für die Bewegung eines Objektes
	 * @param delta
	 */
	public void move(final long delta);
}
