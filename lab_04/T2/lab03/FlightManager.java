import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class FlightManager {
	private static final String ALPHANUMREGEX = "^[a-zA-Z0-9]+$"; // alphanumeric
	private static final String INTXINTREGEX = "\\d+x\\d+";	      // {n}x{m} (no brackets)
	private static final String INTREGEX = "\\d+";				  // integer (includes 0)
	private static final String NATURALREGEX = "\\d*[1-9]\\d*";	  // natural numbers
	private static final String SPACEREGEX = "\\s+";		      // whitespace
	public static void main(String[] args) {
		Map<String, Flight> flightMap = new HashMap<>(); // hashmap of flights <code (String), Flight>
		Scanner sc; // can be either terminal input or a command file
		if (args.length == 0) { // if no argument is given, read from terminal input (System.in)
			sc = new Scanner(System.in);
		} else { // if an argument (file name) is given, read from the file
			try {
				sc = new Scanner(new File(args[0]));
			} catch (FileNotFoundException e) { // handle possible non-existence of file
				System.err.println("Ficheiro " + args[0] + " não encontrado.");
				return;
			}
		}
		String input, command;
		List<String> inputWords = new ArrayList<String>();
		do {
			System.out.println("Escolha uma opção: (H para ajuda)");
			input = askForCommand(sc); // asks for a command with valid syntax
			inputWords = Arrays.asList(input.split(SPACEREGEX));
			command = inputWords.get(0); // first word
			switch (command) {
			// H: show usage
			case "H":
				printUsage();
				break;
			// I filename: read from file
			case "I":
				readFlightFile(inputWords.get(1), flightMap);
				break;
			// M flight_code: print reservations
			case "M":
				printBookings(inputWords.get(1), flightMap);
				break;
			// F flight_code num_seats_exec num_seats_tour: add flight
			case "F":
				if (inputWords.size() == 3) // if only touristic seat layout was specified
					flightMap.put(inputWords.get(1), createFlight(inputWords.get(1), null, inputWords.get(2)));
				else // if executive seat layout was specified
					flightMap.put(inputWords.get(1),
							createFlight(inputWords.get(1), inputWords.get(2), inputWords.get(3)));
				break;
			// R flight_code class number_seats: add booking
			case "R":
				if (!addReservation(flightMap, inputWords.get(1), inputWords.get(2), inputWords.get(3)))
					System.out.println("Erro ao adicionar a reserva.");
				break;
			// C reservation_code: cancel a booking
			case "C":
				if (!cancelReservation(flightMap, inputWords.get(1)))
					System.out.println("Erro ao cancelar a reserva.");
				break;
			// Q: quit
			case "Q":
				System.out.println("Terminando o programa.");
				break;
			default:
				System.out.println("Opção inválida.");
			}
		} while (!command.equals("Q"));
		sc.close();
	}

	public static String askForCommand(Scanner sc) {
		String input;
		while (true) {
			input = sc.nextLine(); // get line entered by user
			if (validateInput(input)) // check if the syntax is valid
				break;				  // if so, break
			System.out.println("Opção inválida."); // else, keep asking
			printUsage(); // and print the usage to remind the user
		}
		return input;
	}

	public static boolean validateInput(String input) {
		List<String> inputWords = Arrays.asList(input.split(SPACEREGEX));
		int numWords = inputWords.size();
		if (numWords == 0)
			return false;
		String command = inputWords.get(0);

		switch (command) {
		case "H": // H
			return (numWords == 1);
		case "I": // I filename
			return (numWords == 2);
		case "M": // M flight_code
			return (numWords == 2); // flight_code isn't worth validating, since invalid codes won't be in the HashMap 
		case "F": // F flight_code num_seats_executive num_seats_tourist or F flight_code num_seats_tourist
			if (!inputWords.get(1).matches(ALPHANUMREGEX)) {
				System.err.println("flight_code inválido (tem de ser alfanumérico)");
				return false;
			}
			if (!inputWords.get(2).matches(INTXINTREGEX)) { // validate 1st seat layout input
				System.err.println("Input de lugares inválido.");
				return false;
			}
			if (numWords == 4) {
				if (!inputWords.get(3).matches(INTXINTREGEX)) { // validate 1st seat layout input
					System.err.println("Input de lugares inválido.");
					return false;
				}
			}
			return (numWords == 3 || numWords == 4);
		case "R": // R flight_code class number_seats
			String fClass = inputWords.get(2);
			String numberSeats = inputWords.get(3);
			if (!(fClass.equals("T") || fClass.equals("E"))) { // validate class
				System.err.println("Classe tem de ser T (Turística) ou E (Executiva).");
				return false;
			}
			if (!numberSeats.matches(NATURALREGEX)) { // validate number_seats
				System.err.println("Número de lugares inválido (tem de ser um número natural).");
				return false;
			}
			return (numWords == 4);
		case "C": // C reservation_code
			// reservation_code = flight_code:sequential_reservation_number
			if (numWords != 2)
				return false;
			String resCode = inputWords.get(1); // validate reservation_code
			String[] resCodeSplit = resCode.split(":");
			if (!resCodeSplit[1].matches(NATURALREGEX)) {
				System.err.println("Número sequencial de reserva inválido (tem de ser um número natural).");
				return false;
			}
			return (resCodeSplit.length == 2);
		case "Q": // Q
			return (numWords == 1);
		default: // unexistent option
			return false;
		}
	}

	// print the usage
	public static void printUsage() {
		System.out.println(
				"Opções:\n" + "H: Ajuda\n" + "I filename:\n" + " - Ler ficheiro de texto com informação sobre um voo\n"
						+ "M flight_code:\n" + " - Exibir o mapa das reservas de um voo\n"
						+ "F flight_code num_seats_executive num_seats_tourist:\n" + " - Acrescentar um novo voo\n"
						+ "R flight_code class number_seats:\n" + " - Acrescentar uma nova reserva a um voo\n"
						+ "C reservation_code:\n" + " - Cancelar uma reserva\n" + "Q: Terminar o programa");
	}

	// create a flight
	public static Flight createFlight(String code, String executiveSeats, String touristicSeats) {
		if (executiveSeats == null) {
			String[] tSeats = touristicSeats.split("x");
			return new Flight(code, Integer.parseInt(tSeats[0]), Integer.parseInt(tSeats[1]));
		} else {
			String[] eSeats = executiveSeats.split("x");
			String[] tSeats = touristicSeats.split("x");
			return new Flight(code, Integer.parseInt(eSeats[0]), Integer.parseInt(eSeats[1]),
					Integer.parseInt(tSeats[0]), Integer.parseInt(tSeats[1]));
		}
	}

	// add a reservation to a flight
	public static boolean addReservation(Map<String, Flight> flightMap, String flightCode, String fClass,
			String numberSeats) {
		if (!flightMap.containsKey(flightCode)) {
			System.err.println("Não existe nenhum voo com o código " + flightCode + ".");
			return false;
		}
		int seats = Integer.parseInt(numberSeats);
		return flightMap.get(flightCode).addReservation(fClass.charAt(0), seats);
	}

	// cancel a flight's reservation
	public static boolean cancelReservation(Map<String, Flight> flightMap, String resCode) {
		String[] resCodeSplit = resCode.split(":");
		String flightCode = resCodeSplit[0];
		if (!flightMap.containsKey(flightCode)) {
			System.err.println("Não existe nenhum voo com o código " + flightCode + ".");
			return false;
		}
		int resNumber = Integer.parseInt(resCodeSplit[1]);
		return flightMap.get(flightCode).cancelReservation(resNumber);
	}

	// create a flight by reading from a file
	public static void readFlightFile(String fileName, Map<String, Flight> flightMap) {
		try {
			Scanner sc = new Scanner(new File(fileName));
			Flight flight;
			String flightCode = "";
			String out = "";

			boolean firstLine = true;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();

				if (firstLine) {
					if (line.charAt(0) != '>') {
						System.out.println("Ficheiro inválido.");
						return;
					}

					line = line.substring(1);
					String[] flightInfo = line.split(SPACEREGEX);
					flightCode = flightInfo[0];
					out += "Código de voo " + flightCode + ". ";

					if (flightInfo.length == 2) {
						flight = createFlight(flightCode, null, flightInfo[1]);
						out += "Lugares disponíveis: " + (flight.getRows('T') * flight.getSeatsPerRow('T'))
								+ " lugares em classe Turística\n" + "Classe executiva não disponível neste voo.";
					} else if (flightInfo.length == 3) {
						flight = createFlight(flightCode, flightInfo[1], flightInfo[2]);
						out += "Lugares disponíveis: " + (flight.getRows('E') * flight.getSeatsPerRow('E'))
								+ " lugares em classe Executiva; " + (flight.getRows('T') * flight.getSeatsPerRow('T'))
								+ " lugares em classe Turística.";
					} else {
						System.out.println("Ficheiro inválido.");
						return;
					}
					flightMap.put(flightCode, flight);
					firstLine = false;
				} else {
					String[] resInfo = line.split(SPACEREGEX);
					if (!addReservation(flightMap, flightCode, resInfo[0], resInfo[1])) {
						out += "\nNão foi possível obter lugares para a reserva: " + line;
					}
				}
			}
			System.out.println(out);
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ficheiro " + fileName + " não encontrado.");
			return;
		}
	}

	// print a flight's reservations
	public static void printBookings(String flightCode, Map<String, Flight> flightMap) {
		if (flightMap.containsKey(flightCode)) {
			Flight flight = flightMap.get(flightCode);
			flight.printBookings();
		} else {
			System.out.println("Código de voo " + flightCode + " não registado");
			return;
		}
	}
}