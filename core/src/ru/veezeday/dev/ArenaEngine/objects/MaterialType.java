package ru.veezeday.dev.ArenaEngine.objects;

public enum MaterialType {
    STEEL(1f, 0.3f, 0.1f),
    WOOD(0.5f, 0.7f, 0.3f),
    RUBBER(1f, 0.9f, 1f),
    STONE(1f, 0.8f, 0.05f),
    MEAT(1f, 0.6f, 0.01f),
    NONE(0,0,0);

    private final float density;
    private final float friction;
    private final float restitution;

    MaterialType(float density, float friction, float restitution) {
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
    }

    public float getDensity() {
        return density;
    }

    public float getFriction() {
        return friction;
    }

    public float getRestitution() {
        return restitution;
    }
}
