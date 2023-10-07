package ru.veezeday.dev.ArenaEngine.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BodyFactory {
    private static BodyFactory instance;
    private World world;

    private BodyFactory(World world) {
        this.world = world;
    }

    public static BodyFactory getInstance(World world) {
        if (instance == null) {
            instance = new BodyFactory(world);
        }
        return instance;
    }

    static public FixtureDef makeFixture(MaterialType materialType, Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = materialType.getDensity();
        fixtureDef.friction = materialType.getFriction();
        fixtureDef.restitution = materialType.getRestitution();
        return fixtureDef;
    }

    public Body makeCirclePolyBody(float x, float y, float radius, MaterialType materialType, BodyDef.BodyType bodyType, boolean fixedRotation) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = fixedRotation;

        Body body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(radius/2);
        FixtureDef fixtureDef = makeFixture(materialType, shape);
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    public Body makeDynamicCirclePolyBody(float x, float y, float radius, MaterialType materialType) {
        return makeCirclePolyBody(x, y, radius, materialType, BodyDef.BodyType.DynamicBody, false);
    }

    public Body makeStaticCirclePolyBody(float x, float y, float radius, MaterialType material) {
        return makeCirclePolyBody(x, y, radius, material, BodyDef.BodyType.StaticBody, true);
    }

    public Body makeBoxPolyBody(float x, float y, float width, float height, MaterialType materialType, BodyDef.BodyType bodyType, boolean fixedRotation) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = fixedRotation;

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);
        FixtureDef fixtureDef = makeFixture(materialType, shape);
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    public Body makeDynamicBoxPolyBody(float x, float y, float width, float height, MaterialType materialType) {
        return makeBoxPolyBody(x, y, width, height, materialType, BodyDef.BodyType.DynamicBody, false);
    }

    public Body makeStaticBoxPolyBody(float x, float y, float width, float height, MaterialType material) {
        return makeBoxPolyBody(x, y, width, height, material, BodyDef.BodyType.StaticBody, true);
    }

    public Body makePolyShapeBody(float x, float y, Vector2[] vertices, MaterialType materialType, BodyDef.BodyType bodyType, boolean fixedRotation) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = fixedRotation;

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);

        FixtureDef fixtureDef = makeFixture(materialType, shape);
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    public Body makeDynamicPolyShapeBody(float x, float y, Vector2[] vertices, MaterialType materialType) {
        return makePolyShapeBody(x, y, vertices, materialType, BodyDef.BodyType.DynamicBody, false);
    }

    public Body makeStaticPolyShapeBody(float x, float y, Vector2[] vertices, MaterialType material) {
        return makePolyShapeBody(x, y, vertices, material, BodyDef.BodyType.StaticBody, true);
    }

    public Body makeConeSensor(Body body, float size, float degrees) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(0, 0);
        for (int i = 2; i < 6; i++) {
            float angle = (float) (Math.PI * i / 3 / 10 * degrees);
            vertices[i-1] = new Vector2(size * (float)Math.cos(angle), size * (float)Math.sin(angle));
        }
        shape.set(vertices);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }
}
