package lab_05;

public class CakeMaster implements CakeBuilder{
    protected CakeBuilder cake;

    public void setCakeBuilder(CakeBuilder cake) {
        cake.createCake();
    }

    public void createCake() {
        if (cake == null)
            cake = new CakeBuilder();
    }

    public void createCake(String message) {
        cake.addMessage(message);
    }

    public void createCake(int num, String message) {
        cake.addMessage(message);
        cake.numCakeLayers(num);
    }

    public void createCake(Shape shape, int num, String message) {
        this.addMessage(message);
        cake.setLayers(num);
        this.setCakeShape(shape);
    }

    public void addMessage(String message) {
        cake.setMessage(message);
    }

    public void addTopping() {
        cake.setTopping(Topping.Caramel);
    }

    public void addTopLayer() {
        cake.setTopCream(Cream.Cream);
    }

    public void addCreamLayer() {
        cake.setMidCream(Cream.Whipped_Cream);
    }

    public void addCakeLayer() {
    }

    public void setCakeShape(Shape shape) {
        cake.SetShape(shape);
    }

    public Cake getCake() {
        return cake;
    }
}
