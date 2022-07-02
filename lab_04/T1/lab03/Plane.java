/**

 * Plane for FlightManager.java
 * code from pds group 207 developed by:
 * Henrique Sousa, 98324 & Vítor Dias, 98396

 */

 //package lab03;

 class Plane{
    private int[] seatsTur;
    private int[] seatsExec;

    public static Plane createPlane(String seatsInfo){
        String[] seats = seatsInfo.split(" ");
        if (seats.length > 1){
            return new Plane(Integer.parseInt(seats[1].split("x")[1]),Integer.parseInt(seats[0].split("x")[1]),
            Integer.parseInt(seats[1].split("x")[0]),Integer.parseInt(seats[0].split("x")[0]));
        }
        else {
            return new Plane(Integer.parseInt(seatsInfo.split("x")[1]),Integer.parseInt(seatsInfo.split("x")[0]));}
    }

    private Plane(int seatsTur, int seatsExec, int rowsTur, int rowsExec){
        this.seatsTur = new int[2];
        this.seatsExec = new int[2];
        this.seatsTur[0] = seatsTur;
        this.seatsExec[0] = seatsExec;
        this.seatsTur[1] = rowsTur;
        this.seatsExec[1] = rowsExec;
    }
     
    private Plane(int seatsTur, int rowsTur){
        this.seatsTur = new int[2];
        this.seatsTur[0] = seatsTur;
        this.seatsTur[1] = rowsTur;
    }

    public int[] getSeatsTur(){
        return this.seatsTur;
    }

    public int[] getSeatsExec(){
        return this.seatsExec;
    }

    public boolean hasExecutive(){
        if(this.seatsExec != null) return true;
        return false;
    }
}