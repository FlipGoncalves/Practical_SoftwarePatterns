class Family implements Vehicle {
    
    protected Family() {
        System.out.println("Family car");
    }

    public int getMaxVolume() {
        return 600;
    }
    public int getMaxPassangers() {
        return 4;
    }
}