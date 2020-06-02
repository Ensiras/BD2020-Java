package CDI;

import javax.inject.Inject;

public class Cleaner {

    @Inject
    CleaningTool tool;

    public void clean() {
        tool.clean();
    }
}
