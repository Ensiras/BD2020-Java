package CDI;

import javax.enterprise.inject.Alternative;

@Alternative
public class Vacuum implements CleaningTool {

    @Override
    public void clean() {
        System.out.println("Zuuuuuuuuuuuuuu");
    }
}
