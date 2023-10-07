package ru.veezeday.dev.ArenaEngine.objects;

public class LevelGenerator {
    private static LevelGenerator instance;
    private EntityFactory entityFactory;

    private LevelGenerator(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public static LevelGenerator getInstance(EntityFactory objectFactory) {
        if (instance == null) instance = new LevelGenerator(objectFactory);
        return instance;
    }

    public void generateLevel(String levelName) {
        switch (levelName) {
            case "plains" :
                generatePlains();
                break;
            default:
                System.out.println("Missing level: " + levelName);
        }
    }

    private void generatePlains() {
        //objectFactory.createBG("plains_bg");
        entityFactory.createPlayer(5, 2);
        entityFactory.createEnemy(10,10);
        entityFactory.createWall(0, 0);
        entityFactory.createWall(1, 0);
        entityFactory.createWall(0, 1);
        entityFactory.createWall(3, 0);
        //entityFactory.createSwordSpellItem(1,8);
        entityFactory.createFireballCast(2,8);
    }
}
