class Van implements Vehicle {
    
    protected Van() {
        System.out.println("Van");
    }

    public int getMaxVolume() {
        return 1000;
    }
    public int getMaxPassangers() {
        return 4;
    }
}