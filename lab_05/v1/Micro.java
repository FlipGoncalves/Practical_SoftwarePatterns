class Micro implements Vehicle {

    protected Micro() {
        System.out.println("Micro car");
    }

    public int getMaxVolume() {
        return 250;
    }
    public int getMaxPassangers() {
        return 1;
    }
}