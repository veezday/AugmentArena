package ru.veezeday.dev.ArenaEngine.objects.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

public class CastComponent implements Component {
    // Must be declared
    /** spell division */
    public SpellType type;
    /** damage */
    public float damage;
    /** animation speed (seconds) */
    public float speed;
    /** range limit, -1 is no limit (meters) */
    public float range;
    /** spell spread, 0 is full accuracy (degrees) */
    public float angle;
    /** time between attacks (seconds) */
    public float chargeTime;
    /** can pierce multiple targets */
    public boolean hasPiercing;
    /** how spell should move */
    public TravelWay travelWay;
    /** how to cast spell */
    public UseWay useWay;
    /** effect on contact */
    public ParticleEffectPool effectOnContactPool;

    // Must not be changed
    /** is spell used right now */
    public boolean isActive = false;
    /** has cast used for charge mod */
    public boolean hasCast = false;
    /** charge progress */
    public float charge = 0;

    /** see each field comment in class */
    public void setupSpell(SpellType type, UseWay useWay, TravelWay travelWay, ParticleEffectPool effectOnContact,
                          float damage, float speed, float range, float chargeTime, float angle, boolean hasPiercing) {
        this.type = type;
        this.useWay = useWay;
        this.travelWay = travelWay;
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        this.chargeTime = chargeTime;
        this.angle = angle;
        this.hasPiercing = hasPiercing;
        this.effectOnContactPool = effectOnContact;
    }

    public enum SpellType {
        ATTACK,
        HELP,
        MOVE,
        DEFENCE;
    }

    public enum TravelWay {
        HITSCAN,
        PROJECTILE,
        SWING,
        STAB
    }

    public enum UseWay {
        CHARGE, // also == semi-auto
        HOLD // also == full-auto
    }
}
