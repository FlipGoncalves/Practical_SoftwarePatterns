public class Flight {
	private String code; 			// flight code
	private int[][] touristicSeats; // number of touristic seats
	private int[][] executiveSeats; // number of executive seats
	private int reservationNumber;  // number of the reservation

	// constructor with touristic and executive seats
	public Flight(String code, int executiveRows, int executiveSeatsPerRow, int touristicRows,
			int touristicSeatsPerRow) {
		this.code = code;
		this.executiveSeats = new int[executiveSeatsPerRow][executiveRows];
		this.touristicSeats = new int[touristicSeatsPerRow][touristicRows];
		this.reservationNumber = 1;
	}

	// constructor with only touristic seats
	public Flight(String code, int touristicRows, int touristicSeatsPerRow) {
		this.code = code;
		this.executiveSeats = new int[0][0];
		this.touristicSeats = new int[touristicSeatsPerRow][touristicRows];
		this.reservationNumber = 1;
	}

	// print the map of current reservations
	public void printBookings() {
		int tourRows = getRows('T'); // number of touristic rows
		int execRows = getRows('E'); // number of executive rows
		int tourSPR = getSeatsPerRow('T'); // number of touristic SPR (seats per row)
		int execSPR = getSeatsPerRow('E'); // number of executive SPR
		int rows = tourRows + execRows; // total number of rows
		int m = Math.max(tourSPR, execSPR); // maximum between both classes' SPR

		// print header
		System.out.print(" "); // space because next lines have a letter first
		for (int i = 1; i <= rows; i++)  // print the row numbers
			System.out.printf("%3d", i);
		System.out.println(); // print newline

		// print every row of the 2D arrays (not row as in plane rows)
		for (int i = 0; i < m; i++) { // we'll print m (max between both classes' SPR) rows
			char rowLetter = (char) (i + 'A');    // get the letter and print it
			System.out.printf("%-1c", rowLetter); // (will bork if >26 SPR, but you'd need a super wide plane)
			if (i < execSPR) // if there are executive seats in this array-row
				for (int col = 0; col < execRows; col++)
					System.out.printf("%3d", this.executiveSeats[i][col]);
			else // if there are no executive seats in this array-row, print some whitespace
				System.out.printf("   ".repeat(execRows)); // padding because of following touristic seats
			if (i < tourSPR) // if there are touristic seats in this array-row
				for (int col = 0; col < tourRows; col++)
					System.out.printf("%3d", this.touristicSeats[i][col]);
			else // if there are no touristic seats in this array-row, print some whitespace
				System.out.printf("   ".repeat(tourRows));
			System.out.println(); // print newline
		}
	}

	// add a reservation
	public boolean addReservation(char fClass, int numberSeats) {
		String output = this.code + ":" + reservationNumber + " ="; // we'll append the positions to this String
		StringBuilder positions = new StringBuilder();
		int[][] seats = (fClass == 'T') ? this.touristicSeats : this.executiveSeats;
		// if there aren't enough seats or the user tries to make a reservation of 0, return false (invalid)
		if (numberSeats > availableSeats(fClass) || numberSeats <= 0)
			return false;
		int toAdd = numberSeats; // number of seats left to assign
		int firstFreeRow = firstFreeRow(fClass); // first free row in the class, -1 if there are none

		int m; // minimum between seats left to assign and the number of seats per row
		// while there are free rows
		while (firstFreeRow != -1) { // while there are free rows left
			// minimum between the seats left to assign and the number of SPR
			m = Math.min(toAdd, getSeatsPerRow(fClass));
			for (int i = 0; i < m; i++) { // for every seat in the row to assign
				seats[i][firstFreeRow] = this.reservationNumber; // assign it
				char letter = (char) (i + 'A'); // get the letter
				int number = firstFreeRow + 1;  // get the number (1-indexed)
				if (fClass == 'T') // exec rows come first, so add the number of exec if we're dealing with a touristic one
					number += this.getRows('E');
				positions.append(String.format("| %d%c ", number, letter)); // append the position code to positions
			}
			toAdd -= m; // decrement number of seats left to assign
			if (toAdd == 0) { // if finished
				this.reservationNumber++; // increment the reservation number for the next time
				break; // leave the while loop
			}
			firstFreeRow = firstFreeRow(fClass); // find the next free row and continue the loop
		}

		// when there are no more free rows
		if (toAdd > 0) { // if we're not done yet
			outerloop: // label so we can break out of the nested for
			for (int i = 0; i < seats[0].length; i++) {   // for every seat (sequentially)
				for (int j = 0; j < seats.length; j++) {
					if (seats[j][i] == 0) { 			  // if it's available
						seats[j][i] = this.reservationNumber; // assing it
						char letter = (char) (j + 'A'); // get the letter
						int number = i + 1;				// get the number (1-indexed)
						if (fClass == 'T') // exec rows come first, so add the number of exec if we're dealing with a touristic one
							number += this.getRows('E');
						positions.append(String.format("| %d%c ", number, letter)); // append the position code to positions
						if (--toAdd == 0) { // decrement toAdd and check if we're done
							this.reservationNumber++; // if so, increment the reservation number for the next time
							break outerloop; // break out of the nested for
						}
					}
				}
			}
		}
		
		// if this doesn't happen, something went wrong
		if (toAdd == 0) {
			positions.deleteCharAt(0); // delete '|' at the beginning
			output += positions; // add positions to output
			System.out.println(output); // print it
			return true; // success
		}

		// should have returned by now!
		System.err.println("addReservation returning false at the end! This shouldn't happen!");
		return false;
	}

	// cancel a reservation with reservation number resNumber
	public boolean cancelReservation(int resNumber) {
		boolean canceled = false; // indicates if it was canceled
		for (int i = 0; i < this.touristicSeats.length; i++) {		  // for every tour seat
			for (int j = 0; j < this.touristicSeats[0].length; j++) {
				if (this.touristicSeats[i][j] == resNumber) { // if it matches the reservation number
					this.touristicSeats[i][j] = 0;		      // cancel it by setting it to 0
					canceled = true; 						  // update canceled
				}
			}
		}
		// copy-paste because it's impossible to make a list of int[][]
		for (int i = 0; i < this.executiveSeats.length; i++) {
			for (int j = 0; j < this.executiveSeats[0].length; j++) {
				if (this.executiveSeats[i][j] == resNumber) {
					this.executiveSeats[i][j] = 0;
					canceled = true;
				}
			}
		}
		return canceled;
	}

	// find the first free row of seats in class fClass (T/E)
	private int firstFreeRow(char fClass) {
		int[][] seats = (fClass == 'T') ? this.touristicSeats : this.executiveSeats;
		boolean emptyRow;
		int i, j;
		for (i = 0; i < seats[0].length; i++) { // seats[0].length: 15 (2d arr columns)
			emptyRow = true;
			for (j = 0; j < seats.length; j++) // seats.length: 3 (2d arr rows)
				if (seats[j][i] != 0)
					emptyRow = false;
			if (emptyRow)
				return i;
		}
		return -1;
	}

	// count the number of available seats in class fClass (T/E)
	public int availableSeats(char fClass) {
		// get seats according to class
		int[][] seats = (fClass == 'T') ? this.touristicSeats : this.executiveSeats;
		int count = 0; // number of seats available
		for (int i = 0; i < seats.length; i++)			// for every seat
			for (int j = 0; j < seats[0].length; j++)
				if (seats[i][j] == 0)				    // if it's available
					count++;							// increment count
		return count;
	}

	// get number of rows of class fClass (T/E)
	public int getRows(char fClass) {
		int[][] seats = (fClass == 'T') ? this.touristicSeats : this.executiveSeats;
		if (seats.length == 0) // avoid index out of bounds in seats[0]
			return 0;
		return seats[0].length;
	}

	// get number of seats per row of class fClass (T/E)
	public int getSeatsPerRow(char fClass) {
		int[][] seats = (fClass == 'T') ? this.touristicSeats : this.executiveSeats;
		return seats.length;
	}
}