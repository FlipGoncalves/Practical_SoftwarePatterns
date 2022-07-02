/**

 * Reservation for FlightManager.java
 * code from pds group 207 developed by:
 * Henrique Sousa, 98324 & Vítor Dias, 98396

 */

 //package lab03;

 class Reservation{
    private Classe classe;
    private int numPassageiros;

    public static Reservation createReservation(Classe classe, int numPassageiros){
        return new Reservation(classe,numPassageiros);
    }

    private Reservation(Classe classe, int numPassageiros){
        this.classe = classe;
        this.numPassageiros = numPassageiros;
    }

    public Classe getClasse(){
        return this.classe;
    }

    public void setClasse(Classe classe){
        this.classe = classe;
    }

    public int getNumPassageiros(){
        return this.numPassageiros;
    }

    public void setNumPassageiros(int numPassageiros){
        this.numPassageiros = numPassageiros;
    }

    public String toString(){
        return this.classe.toString().charAt(0) + " " + this.numPassageiros;
    }
}