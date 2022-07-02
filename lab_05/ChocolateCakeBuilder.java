package lab_05;

public class ChocolateCakeBuilder extends CakeMaster{

    public void createCake(String message) {
        super.cake.setMessage(message);
    }

    public void createCake(int num, String message) {
        super.cake.setMessage(message);
        super.cake.setLayers(num);
    }

    public void createCake(Shape shape, int num, String message) {
        super.cake.setMessage(message);
        super.cake.setLayers(num);
        super.cake.SetShape(shape);
        super.cake.setTopping(Topping.Whipped_Cream);
    }
}
