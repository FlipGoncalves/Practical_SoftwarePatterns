/**

 * JGaloInterface for JGalo.java
 * code from pds group 207 developed by:
 * Henrique Sousa, 98324 & Vítor Dias, 98396

 */
package lab3;
public interface JGaloInterface {
	public abstract char getActualPlayer();
	public abstract boolean setJogada(int lin, int col);
	public abstract boolean isFinished();
	public abstract char checkResult();
}