/**

 * Flight for FlightManager.java
 * code from pds group 207 developed by:
 * Henrique Sousa, 98324 & Vítor Dias, 98396

 */

//package lab03;

import java.util.ArrayList;
import java.lang.Math;

class Flight{
    private String code;
    private Plane plane;
    private ArrayList<Reservation> reservasIndisponíveis;
    private int[][] seatMapExec;
    private int[][] seatMapTur;
    private int noReservation;

    public static Flight createFlight(String code, Plane plane){
        return new Flight(code,plane);
    }

    private Flight(String code, Plane plane){
        this.code = code;
        this.plane = plane;
        this.reservasIndisponíveis = new ArrayList<Reservation>();
        this.noReservation = 1;
        if(plane.hasExecutive()){ 
            this.seatMapExec = new int[plane.getSeatsExec()[0]][plane.getSeatsExec()[1]];
            this.seatMapTur = new int[plane.getSeatsTur()[0]][plane.getSeatsTur()[1]];
        } 
        else this.seatMapTur = new int[plane.getSeatsTur()[0]][plane.getSeatsTur()[1]];
    }

    public String getCode(){
        return this.code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public Plane getPlane(){
        return this.plane;
    }

    public void setPlane(Plane plane){
        this.plane = plane;
    }

    public String addReservation(Reservation reserv){
        int seatCounter = 0, freeSeats = 0, tempPass;
        ArrayList<Integer> possibleRows = new ArrayList<>(); //Arraylist to store the completly avaiable rows
        int[][] tempList;       
        String seatsAssigned = "";
        if(reserv.getClasse().equals(Classe.TURISTICO)) tempList = this.seatMapTur;   //Chose wich array we'll need to change
        else if(this.seatMapExec==null){this.reservasIndisponíveis.add(reserv);return "Reserva indisponível";}
        else tempList = this.seatMapExec;
        int rowsNeeded = (int)Math.ceil((float)reserv.getNumPassageiros()/ tempList.length); //Calculate the lines needed for the reserv

        for(int i = 0; i < tempList[0].length; i++){    //First we calculate all the free seats and lines
            seatCounter = 0;
            for(int j = 0; j < tempList.length; j++){
                if(tempList[j][i] == 0) {seatCounter++; freeSeats++;}
            }
            if(seatCounter == tempList.length) possibleRows.add(i);
        }
        if(rowsNeeded <= possibleRows.size()){        //If there are free lines for the reservation we assign the seats needed
            tempPass = 0;
            for(int i = 0;i < rowsNeeded; i++){
                for(int j = 0;j < tempList.length; j++) {
                    if(tempPass < reserv.getNumPassageiros()){ 
                        tempList[j][possibleRows.get(0)] = this.noReservation;
                        if(reserv.getClasse() == Classe.TURISTICO && this.plane.hasExecutive())
                            seatsAssigned = seatsAssigned + " | " + String.valueOf(possibleRows.get(0) + 1 + this.seatMapExec[0].length) + String.valueOf(Character.toChars(j + 65));
                        else if(reserv.getClasse() == Classe.TURISTICO) seatsAssigned = seatsAssigned + " | " + String.valueOf(possibleRows.get(0) + 1) + String.valueOf(Character.toChars(j + 65));
                        else seatsAssigned = seatsAssigned + " | " + String.valueOf(possibleRows.get(0) + 1) + String.valueOf(Character.toChars(j + 65));
                        tempPass++;
                    }
                    else break;
                }
                possibleRows.remove(0);
            }
            this.noReservation++;
        }
        else if(freeSeats >= reserv.getNumPassageiros()){       //If there are no avaiable lines, we place them in the last empty seats
            for(int i = 0; i < tempList[0].length; i++){
                for(int j = 0; j < tempList.length; j++){
                    if(tempList[j][i] == 0) {
                        tempList[j][i] = this.noReservation;
                        seatsAssigned = seatsAssigned + " | " + String.valueOf(i + 1) + String.valueOf(Character.toChars(i + 65));
                    }
                }
            }
            this.noReservation++;
        }
        else {this.reservasIndisponíveis.add(reserv); return "Não foi possível reservar";}  //If there are no seats
                                                        //, we can't do the reservation
        if(reserv.getClasse().equals(Classe.EXECUTIVO)) this.seatMapExec = tempList;    
        else this.seatMapTur = tempList;

        return this.code + ":" + (this.noReservation-1) + " = " + seatsAssigned.substring(2);
    }

    public void printFlightInfo(){
        if (this.plane.hasExecutive()){
            System.out.printf("Código de voo %s. Lugares disponíveis: %d em classe Executiva; %d em classe Turística.\n",
                this.code, this.plane.getSeatsExec()[0]*this.plane.getSeatsExec()[1],this.plane.getSeatsTur()[0]*this.plane.getSeatsTur()[1]);
        }
        else {
            System.out.printf("Código de voo %s. Lugares disponíveis: %d em classe Turística.\n",
                this.code, this.plane.getSeatsTur()[0]*this.plane.getSeatsTur()[1]);
        }
        if(this.reservasIndisponíveis.size() > 0){
            System.out.print("Não foi possível obter lugares para a reserva: ");
            for(Reservation reserva : this.reservasIndisponíveis) {System.out.print(reserva.toString()); System.out.print(" ");}
            System.out.println("");
        }
    }

    public void printFlightMap(){
        int execLines = (this.plane.hasExecutive()) ? this.seatMapExec.length : 0;
        int execSeats = (this.plane.hasExecutive()) ? this.seatMapExec[0].length : 0;
        for(int j = 0; j <= execLines + (this.seatMapTur.length - execLines); j++){
            if(j == 0){
                for(int i = 0; i <= execSeats + this.seatMapTur[0].length;i++) {
                    if(i == 0) System.out.print(" ");
                    else System.out.printf("%2d",i);
                    System.out.print(" ");
                }
                System.out.println("");
            }
            else{
                System.out.print(Character.toChars(j + 64));
                System.out.print(" ");
                for(int i = 1; i <= execSeats + this.seatMapTur[0].length;i++) {
                    if(this.plane.hasExecutive() && i >= 1 && i <= this.seatMapExec[0].length && j <= this.seatMapExec.length) System.out.printf("%2d",this.seatMapExec[j-1][i - 1]);
                    else if (i > execSeats && j <= this.seatMapTur.length) System.out.printf("%2d",this.seatMapTur[j -1][i - execSeats- 1]);
                    else System.out.print("  ");
                    System.out.print(" ");
                }
                System.out.println("");
            }   
        }
    }
    public void removeReservation(String flight_code, String reservation_code){
        boolean found = false;
        if(this.plane.hasExecutive()){
            for(int j = 0; j < this.seatMapExec.length; j++){
                for(int i = 0; i < this.seatMapExec[0].length; i++){
                    if (this.seatMapExec[j][i] == Integer.parseInt(reservation_code)){this.seatMapExec[j][i] = 0; found = true;}
                }
            }
        }
        for(int j = 0; j < this.seatMapTur.length; j++){
            for(int i = 0; i < this.seatMapTur[0].length; i++){
                if (this.seatMapTur[j][i] == Integer.parseInt(reservation_code)){this.seatMapTur[j][i] = 0; found = true;}
            }
        }
        if (found) System.out.println("Reservation Canceled");
        else System.out.println("There wasn't any reservation with that code");
    }
}