package CDI;

public class Broom implements CleaningTool {

    @Override
    public void clean() {
        System.out.println("Sweeping!");
    }
}
