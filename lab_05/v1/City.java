class City implements Vehicle {
    
    protected City() {
        System.out.println("City car");
    }

    public int getMaxVolume() {
        return 250;
    }
    public int getMaxPassangers() {
        return 3;
    }
}
