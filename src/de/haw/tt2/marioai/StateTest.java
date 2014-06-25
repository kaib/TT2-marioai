package de.haw.tt2.marioai;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public class StateTest extends TestCase {



    public void testGetReward() throws Exception {
        State state1 = new State();
        state1.setCanJump(1);
        state1.setDirection(1);
        state1.setGotHit(1);
        state1.setHeight(1);
        state1.setMarioMode(1);
        state1.setOnGround(1);
        state1.setEnemies(new boolean[25]);
        state1.setObstacles(new boolean[4]);
        state1.setDistance(1);
        state1.setStuck(1);

     //  Reward = distance * Params.REWARDS.DISTANCE + height * Params.REWARDS.HEIGHT + stuck* Params.REWARDS.STUCK;
        assertEquals(1 * Params.REWARDS.DISTANCE + 1 * Params.REWARDS.HEIGHT + 1 * Params.REWARDS.STUCK, state1.getReward(),0.00001);
    }

    public void testEquals() throws Exception {
        //*
        //  if (canJump != state.canJump) return false;
        //  if (direction != state.direction) return false;
        //  if (gotHit != state.gotHit) return false;
        //  if (height != state.height) return false;
        //  if (marioMode != state.marioMode) return false;
        //  if (onGround != state.onGround) return false;
        //  if (!Arrays.equals(enemies, state.enemies)) return false;
        //  if (!Arrays.equals(obstacles, state.obstacles)) return false;
        State state1 = new State();
        state1.setCanJump(1);
        state1.setDirection(1);
        state1.setGotHit(1);
        state1.setHeight(1);
        state1.setMarioMode(1);
        state1.setOnGround(1);
        state1.setEnemies(new boolean[25]);
        state1.setObstacles(new boolean[4]);
        state1.setDistance(1);
        state1.setStuck(1);

        State state2 = new State();
        state2.setCanJump(1);
        state2.setDirection(1);
        state2.setGotHit(1);
        state2.setHeight(1);
        state2.setMarioMode(1);
        state2.setOnGround(1);
        state2.setEnemies(new boolean[25]);
        state2.setObstacles(new boolean[4]);
        state2.setStuck(1);

        State state3 = new State();
        state3.setCanJump(1);
        state3.setDirection(1);
        state3.setGotHit(1);
        state3.setHeight(1);
        state3.setMarioMode(2);
        state3.setOnGround(1);
        state3.setEnemies(new boolean[25]);
        state3.setObstacles(new boolean[4]);
        assertEquals(state1,state2);
        assertTrue(state1.equals(state2));
        assertFalse(state1.equals(state3));
        Set<State> qTable = new HashSet<State>();
        qTable.add(state1);
        qTable.add(state2);
        assertEquals(1,qTable.size());
        qTable.add(state3);
        assertEquals(2,qTable.size());

    }

}