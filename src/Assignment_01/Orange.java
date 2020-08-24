package Assignment_01;

public class Orange extends Fruit{
    private static float WEIGHT = 1.5f;
    private static final String NAME = "oranges";

    public float getWeight() {
        return WEIGHT;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
