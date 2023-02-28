package maze;

import java.util.Objects;
import java.util.concurrent.Callable;

public class Command {
    private String name;
    private Callable action;
    public Command (String name, Callable action) {
        this.name = name;
        this.action = action;
    }

    public void callAction() throws Exception {
        if (!Objects.equals(this.action, null)) {
            this.action.call();
        }
    }

    public String getName() {
        return name;
    }

    public Callable getAction() {
        return action;
    }
}
