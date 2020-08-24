package Assignment_01;

public class Apple extends Fruit{
    private static float WEIGHT = 1.0f;
    private static final String NAME = "apples";

    public float getWeight() {
        return WEIGHT;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
