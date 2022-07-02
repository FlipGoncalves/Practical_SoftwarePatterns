/**

 * FlightManager
 * code from pds group 207 developed by:
 * Henrique Sousa, 98324 & Vítor Dias, 98396

 */

//package lab03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FlightManager {
    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<Flight> flights = new ArrayList<>();
    public static void main(String args[]) {
        if (args.length == 0) menu("nofile");
        else if (args.length == 1)	menu(args[0]);	
        else ErrorFound('E');
	}
    public static void menu(String filename) {
        if (filename.equals("nofile")){ //If the user doens't specify a command file
            System.out.println("\nEscolha uma opção: (H para ajuda)");
            handleMenu(sc.nextLine());
            menu("nofile");
        }
        else{ //if there is a command file
            try {
                File myObj = new File(filename);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                  handleMenu(myReader.nextLine());
                }
                myReader.close();
              } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
        }
        
    }

    static void handleMenu(String op){//handle menu options
        String[] arguments = op.split(" "); 
        switch (Character.toUpperCase(op.charAt(0))) {
            case 'H': //help the user showing the options avaiable
                System.out.println("Possíveis Opções:");
                System.out.println("I {filename} - Indica informações do voo");
                System.out.println("M {flight_code} - Exibe o mapa de reservas de um voor");
                System.out.println("F {flight_code num_seats_executive num_seats_tourist} - Acrescenta um novo voo");
                System.out.println("R {flight_code class number_seats} - Acrescenta uma nova reserva a um voo");
                System.out.println("C {reservation_code} - Cancela uma reserva");
                System.out.println("Q - Termina o programa");
                break;
            
            case 'I': //read info from file
                if (arguments.length > 1) flightInfo(arguments[1]);
                else ErrorFound('A');
                break;
            
            case 'M': //print flight map
                if (arguments.length >= 2){
                    for(Flight flight : flights){
                        if (flight.getCode().equals(arguments[1])){
                            flight.printFlightMap();
                            break;
                        }
                    }
                } 
                else ErrorFound('A');
                break;
            case 'F': //crete a new flight
                if (arguments.length >= 3 ){
                    boolean found = false;
                    for(Flight flight : flights){
                        if (flight.getCode().equals(arguments[1])){
                            found = true;
                            break;
                        }
                    }
                    if (!found){
                        String seats = arguments[2];
                        if (arguments.length == 4) seats = seats + " " + arguments[3];
                        flights.add(Flight.createFlight(arguments[1], Plane.createPlane(seats)));
                    }
                    else System.out.println("There is already a flight with that code");
                }
                else ErrorFound('A');
                break;
            case 'R': //add a reservation to a specific flight
                if (arguments.length >= 4){
                    boolean found = false;
                    for(Flight flight : flights){
                        if (flight.getCode().equals(arguments[1])){
                            found = true;
                            System.out.println(flight.addReservation(Reservation.createReservation((arguments[2].equals("T") ? Classe.TURISTICO : Classe.EXECUTIVO), Integer.parseInt(arguments[3]))));
                            break;
                        }
                    }
                    if (!found) System.out.println("There is no flight with that code");
                } 
                else ErrorFound('A');
                break;
            case 'C': //remove a certain reservation for a specific flight
                if (arguments.length >= 2){
                    boolean found = false;
                    for(Flight flight : flights){
                        if (flight.getCode().equals(arguments[1].split(":")[0])){
                            found = true;
                            flight.removeReservation(arguments[1].split(":")[0], arguments[1].split(":")[1]);
                            break;
                        }
                    }
                    if (!found) System.out.println("There is no flight with that code");
                } 
                else ErrorFound('A');
                break;

            case 'Q': //exit the program
                System.out.println("A Terminar Programa...");
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public static void flightInfo(String filename){ //Read and print flight info from file without using external classes
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            int line = 0, seatsT = 0, seatsE = 0, numberOfT = 0, numberOfE = 0;;
            boolean turSeats = false, exeSeats = false;
            ArrayList<String> reserves = new ArrayList<String>();
            ArrayList<String> notPossible = new ArrayList<String>();
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              if (line == 0){
                  line++;
                  String[] splitData = data.split(" ");
                  if (splitData.length == 2){
                    turSeats = true;
                    seatsT = Integer.parseInt(splitData[1].split("x")[0]) * Integer.parseInt(splitData[1].split("x")[1]);
                    System.out.printf("\nCódigo de voo%s. Lugares disponíveis: %d lugares em classe Turística.\nClasse executiva não disponível neste voo.\n", splitData[0].replace('>', ' '), seatsT);
                  }
                  else{
                    turSeats = true; exeSeats = true;
                    seatsE = Integer.parseInt(splitData[1].split("x")[0]) * Integer.parseInt(splitData[1].split("x")[1]);
                    seatsT = Integer.parseInt(splitData[2].split("x")[0]) * Integer.parseInt(splitData[2].split("x")[1]);
                    System.out.printf("\nCódigo de voo%s. Lugares disponíveis: %d lugares em classe Executiva; %d lugares em classe Turística.\n", splitData[0].replace('>', ' '), seatsE, seatsT);
                  }
                } 
              else{
                String[] splitData = data.split(" ");
                if (reserves.contains(data)){
                    notPossible.add(data);
                }
                else{
                    reserves.add(data);
                    if (splitData[0] == "T") {
                        if (turSeats && numberOfT < seatsT) numberOfT++;
                        else notPossible.add(data);
                    }
                    else if (splitData[0] == "E") {
                        if (exeSeats && numberOfE < seatsE) numberOfE++;
                        else notPossible.add(data);
                    }
                }

              }  
            }
            if (notPossible.size() > 0) System.out.printf("Não foi possível obter lugares para a reserva: %s\n", notPossible.toArray());
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    
    
    static void ErrorFound(char error) { //handle errors
        switch (error) {
        case 'A':
            System.out.println("Argumentos mal passados.");
            break;  
        case 'E':
            System.out.println("Programa mal executado. \nTem de ser do tipo {java FlightManager ficheiro_de_comandos(opcional)}");
            System.exit(0);
            break;            	
        default:
            System.out.println("There was an error Error");
            break;
        }
    }  
}
