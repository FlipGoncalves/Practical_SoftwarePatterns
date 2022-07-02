abstract class VehicleFactory {
    
    public static Vehicle getVehicle(int passangers) {
        
        System.out.printf("Vehicle for %s passengers: Use a ", passangers);

        if (passangers == 1)
                return new Scooter();
        if (passangers <= 3)
                return new City();
        if (passangers == 4)
                return new Family();

        System.err.println("(undefined)");
        return null;
    }

    public static Vehicle getVehicle(int passangers, int[] luggageArray) {

        System.out.printf("Vehicle for %s passengers with %s items of luggage: Use a ", passangers, luggageArray.length);

        int luggage = 0;
        for (int i = 0; i < luggageArray.length; i++)
            luggage += luggageArray[i];

        if (passangers == 1) {
            if (luggage == 0)
                return new Scooter();
            if (luggage <= 250)
                return new Micro();
        }

        if (passangers <= 3 && luggage <= 250)
            return new City();
        
        if (passangers <= 4) {
            if (luggage <= 600)
                return new Family();
            if (luggage <= 1000)
                return new Van();
        }
    
        System.err.println("(undefined)");
        return null;
    }

    public static Vehicle getVehicle(int passangers, boolean wheelchair) {
        
        if (wheelchair == true) {
            System.out.printf("Vehicle for %s passengers and wheelchair: Use a ", passangers);
            if (passangers <= 4)
                return new Van();
        } else {
            return getVehicle(passangers);
        }

        System.err.println("(undefined)");
        return null;
    }
}

