public class Main {
    public static void main(String args[]) {
        int[] luggage;
        Vehicle v;

        // Get vehicle for 1 passanger without luggage
        v = VehicleFactory.getVehicle(1);   // Scooter 

        // Get vehicle for 1 passanger with two items of luggage
        luggage = new int[]{100, 140};  // Two bags with a total volume of 240
        v = VehicleFactory.getVehicle(1, luggage);  // Micro car

        // Get vehicle for 3 passangers with three items of luggage
        luggage = new int[]{50, 200, 240};  // Three bags with a total volume of 490
        v = VehicleFactory.getVehicle(3, luggage);  // Family car
        
        // Print maxPassangers and maxVolume of vehicle (Family car)
        if (v != null) {
            System.out.println("MaxPassangers: " + v.getMaxPassangers());
            System.out.println("MaxVolume: " + v.getMaxVolume());
        }

        // Get vehicle for 2 passangers with wheelchair
        v = VehicleFactory.getVehicle(2, true); // Van

        // Get vehicle for 2 passangers without wheelchair
        v = VehicleFactory.getVehicle(2, false); // City car

        // Get vehicle for 8 passangers with two items of luggage
        v = VehicleFactory.getVehicle(8, luggage);  // Undefined
        
        if (v != null) {
            v.getMaxPassangers();
            v.getMaxVolume();
        }

    }
    
}
