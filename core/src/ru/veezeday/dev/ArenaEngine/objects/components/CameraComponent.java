package ru.veezeday.dev.ArenaEngine.objects.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Camera;
import ru.veezeday.dev.ArenaEngine.physic.components.BodyComponent;

public class CameraComponent implements Component {
    public Camera camera;
    public boolean isFollowing = false;
    public BodyComponent followingBody;
}
