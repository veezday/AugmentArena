package ru.veezeday.dev.ArenaEngine.objects.components;

import com.badlogic.ashley.core.Component;
import ru.veezeday.dev.ArenaEngine.objects.State;

public class StateComponent implements Component {
    private State state = State.NORMAL;

    public void set(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
