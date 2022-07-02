package lab_05;

public class Cake {
    private Shape shape;
    private String cakeLayer;
    private int numCakeLayers = 1;
    private Cream midLayerCream;
    private Cream topLayerCream;
    private Topping topping;
    private String message;

    public void SetShape(Shape shape) {
        this.shape = shape;
    }

    public void setLayers(int num) {
        numCakeLayers = num;
    }

    public void setCakeLayer(String layer) {
        cakeLayer = layer;
    }

    public void setTopping(Topping top) {
        topping = top;
    }

    public void setMidCream(Cream mid) {
        midLayerCream = mid;
    }

    public void setTopCream(Cream top) {
        topLayerCream = top;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Shape getShape() {
        return shape;
    }

    public String getCakeLayer() {
        return cakeLayer;
    }

    public int getNumCakeLayers() {
        return numCakeLayers;
    }

    public Cream getMidLayerCream() {
        return midLayerCream;
    }

    public Cream getTopLayerCream() {
        return topLayerCream;
    }

    public Topping getTopping() {
        return topping;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return cakeLayer + " with " + numCakeLayers + ", topped with " + topLayerCream + ". Message says: " + message;
    }
}

enum Cream {
    Whipped_Cream, Cream, Vanilla_Cream
}

enum Topping {
    Chocolate, Whipped_Cream, Caramel
}
