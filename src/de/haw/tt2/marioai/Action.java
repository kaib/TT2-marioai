package de.haw.tt2.marioai;

import ch.idsia.benchmark.mario.engine.sprites.Mario;

/**
 * Created by dude on 02.06.2014.
 */
public enum Action {
    //TODO add DOWN ACTION!!
    //Nothing
    NOTHING(0),
    //Single Actions
    LEFT(1, Mario.KEY_LEFT),
    RIGHT(2, Mario.KEY_RIGHT),
    JUMP(3, Mario.KEY_JUMP),
    RUN_AND_FIRE(4, Mario.KEY_SPEED),
    //two Actions Combined
    JUMP_LEFT(5, Mario.KEY_JUMP, Mario.KEY_LEFT),
    JUMP_RIGHT(6, Mario.KEY_JUMP, Mario.KEY_RIGHT),
    RUN_AND_FIRE_LEFT(7, Mario.KEY_SPEED, Mario.KEY_LEFT),
    RUN_AND_FIRE_RIGHT(8, Mario.KEY_SPEED, Mario.KEY_RIGHT),
    RUN_AND_FIRE_JUMP(9, Mario.KEY_SPEED, Mario.KEY_JUMP),
    //three Actions Combined
    RUN_AND_FIRE_JUMP_LEFT(10,Mario.KEY_SPEED, Mario.KEY_JUMP, Mario.KEY_LEFT),
    RUN_AND_FIRE_JUMP_RIGHT(11,Mario.KEY_SPEED, Mario.KEY_JUMP, Mario.KEY_RIGHT);




    private int number;
    private final boolean[] action;

    Action(int number, int... used_keys) {
        this.action = new boolean[6];
        this.number = number;
       for(int key: used_keys) {
           this.action[key] = true;
       }
    }
}
