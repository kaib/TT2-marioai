package de.haw.tt2.marioai;

import junit.framework.TestCase;

public class QTableTest extends TestCase {

    public void testGetQValues() throws Exception {
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

        QTable qTable = new QTable(Params.NUMBER_OF_ACTIONS);
        qTable.updateQTable(1f,state1);

        assertTrue(qTable.getqTable().containsKey(state1));
        assertTrue(qTable.getqTable().containsKey(state2));
        assertFalse(qTable.getqTable().containsKey(state3));
        assertEquals(2,qTable.getqTable().size());

        qTable.updateQTable(1f,state1);

        assertEquals(2,qTable.getqTable().size());
        qTable.updateQTable(1f,state2);
        assertEquals(2,qTable.getqTable().size());
        qTable.updateQTable(1f,state3);
        assertEquals(3,qTable.getqTable().size());
        state1.getEnemies()[2] =true;
        qTable.updateQTable(1f,state1);
        assertEquals(4,qTable.getqTable().size());
    }
}