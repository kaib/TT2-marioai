package de.haw.tt2.marioai;
import ch.idsia.benchmark.mario.engine.GeneralizerLevelScene;
import ch.idsia.benchmark.mario.engine.sprites.Sprite;
import ch.idsia.benchmark.mario.environments.Environment;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by dude on 02.06.2014.
 */
public class State {
    Logger logger = Logger.getLogger("MarioState");

    private static final int MARIO_X = 9;
    private static final int MARIO_Y = 9;

    public int getMarioMode() {
        return marioMode;
    }

    public void setMarioMode(int marioMode) {
        this.marioMode = marioMode;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public float getMarioX() {
        return marioX;
    }

    public void setMarioX(float marioX) {
        this.marioX = marioX;
    }

    public float getMarioY() {
        return marioY;
    }

    public void setMarioY(float marioY) {
        this.marioY = marioY;
    }

    public int getStuckCounter() {
        return stuckCounter;
    }

    public void setStuckCounter(int stuckCounter) {
        this.stuckCounter = stuckCounter;
    }

    public int getOnGround() {
        return onGround;
    }

    public void setOnGround(int onGround) {
        this.onGround = onGround;
    }

    public int getStuck() {
        return stuck;
    }

    public void setStuck(int stuck) {
        this.stuck = stuck;
    }

    public int getCanJump() {
        return canJump;
    }

    public void setCanJump(int canJump) {
        this.canJump = canJump;
    }

    public int getGotHit() {
        return gotHit;
    }

    public void setGotHit(int gotHit) {
        this.gotHit = gotHit;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getEnemiesKilledByStomp() {
        return enemiesKilledByStomp;
    }

    public void setEnemiesKilledByStomp(int enemiesKilledByStomp) {
        this.enemiesKilledByStomp = enemiesKilledByStomp;
    }

    public boolean[] getEnemiesSmall() {
        return this.enemiesSmall;
    }

    public void setEnemiesSmall(boolean[] enemiesSmall) {
        this.enemiesSmall = enemiesSmall;
    }

    public int getEnemiesKilledByFire() {
        return enemiesKilledByFire;
    }

    public void setEnemiesKilledByFire(int enemiesKilledByFire) {
        this.enemiesKilledByFire = enemiesKilledByFire;
    }

    public int getLastEnemiesKilledByStomp() {
        return lastEnemiesKilledByStomp;
    }

    public void setLastEnemiesKilledByStomp(int lastEnemiesKilledByStomp) {
        this.lastEnemiesKilledByStomp = lastEnemiesKilledByStomp;
    }

    public int getLastEnemiesKilledByFire() {
        return lastEnemiesKilledByFire;
    }

    public void setLastEnemiesKilledByFire(int lastEnemiesKilledByFire) {
        this.lastEnemiesKilledByFire = lastEnemiesKilledByFire;
    }

    public int getTotalEnemyCount() {
        return totalEnemyCount;
    }

    public void setTotalEnemyCount(int totalEnemyCount) {
        this.totalEnemyCount = totalEnemyCount;
    }

    public boolean[] getObstacles() {
        return obstacles;
    }

    public void setObstacles(boolean[] obstacles) {
        this.obstacles = obstacles;
    }

    public byte[][] getScene() {
        return scene;
    }

    public void setScene(byte[][] scene) {
        this.scene = scene;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLastDistance() {
        return lastDistance;
    }

    public void setLastDistance(int lastDistance) {
        this.lastDistance = lastDistance;
    }

    public int getLastHeight() {
        return lastHeight;
    }

    public void setLastHeight(int lastHeight) {
        this.lastHeight = lastHeight;
    }
    //direction 0-8
    private int direction = 8;
    private float marioX = 0f;
    private float marioY = 0f;
    private int stuckCounter = 0;
    private int stuck = 0;
    private int onGround = 1;
    private int canJump = 1;
    private int hitCount = 0;
    private int gotHit = 0;
    private int time = 0;
    private int lastTime = 0;
    private int win = 0;
    //2-fire 1-big 0-small
    private int marioMode = 2;
    private boolean[] enemiesSmall = new boolean[3]      ;
    //Remote Enemies 2 Fields Away-> this is an area without the direct Enemies Matrix
    //private boolean[] remoteEnemies = new boolean[18];

    //Killed Enemies in this Frame
    private int enemiesKilledByStomp = 0;
    private int enemiesKilledByFire = 0;
    private int lastEnemiesKilledByFire = 0;
    private int lastEnemiesKilledByStomp = 0;
    private int totalEnemyCount = 0;

    // Obstacles in front of Mario from one field below to 2 above
    private boolean[] obstacles = new boolean[4];

    //TODO CHECK GAPS


    private Environment environment;
    private byte[][] scene;

    private int distance = 0;
    private int height = 0;
    private int lastDistance = 0;
    private int lastHeight = 0;

    public State(){
    }

    public void update(Environment environment) {
        this.environment = environment;
        this.scene = environment.getMergedObservationZZ(1,1);
        if(environment.getEvaluationInfo().distancePassedCells == 256) {
            win = 1;
            System.out.println("WON");
        }
        distance = environment.getEvaluationInfo().distancePassedPhys - lastDistance;
        if(distance < Params.DISTANCE_THRESHOLD) {distance = 0;}
        lastDistance = environment.getEvaluationInfo().distancePassedPhys;
        //todo Height
        height = Math.max(0, Math.max(0,getDistanceToGround(MARIO_X-1)-getDistanceToGround(MARIO_X))-lastHeight);
        lastHeight = Math.max(0,getDistanceToGround(MARIO_X-1)-getDistanceToGround(MARIO_X));

        //Update State Params
        marioMode = environment.getMarioMode();

        float[] pos = environment.getMarioFloatPos();
        direction = getDirection(pos[0] - marioX, pos[1] - marioY);
        marioX = pos[0];
        marioY = pos[1];

        if(distance == 0) {
            stuckCounter++;
        }else {
            stuckCounter = 0;
            stuck = 0;
        }
        if(stuckCounter >= Params.MIN_STUCK_FRAMES){
            stuck = 1;
        }
        //Stuck??

        gotHit = environment.getEvaluationInfo().collisionsWithCreatures - hitCount;
        hitCount = environment.getEvaluationInfo().collisionsWithCreatures;

        time = environment.getTimeSpent() - lastTime;
        lastTime = time;
        canJump = (!environment.isMarioOnGround()||environment.isMarioAbleToJump())?1:0;
        onGround = environment.isMarioOnGround()?1:0;

        enemiesKilledByStomp = environment.getKillsByStomp();
        enemiesKilledByFire = environment.getKillsByFire();

       obstacles = new boolean[4];
       int obstacleIndize = 0;
       for(int y = MARIO_Y-2; y <= MARIO_Y +1; y++){
        //   y = (y < 0)?0:y;
           if(isObstacle(MARIO_X + 1, y)){
               obstacles[obstacleIndize] = true;
           }
           obstacleIndize++;
       }
       enemiesSmall[0] = isEnemy(MARIO_X+1,MARIO_Y);
       enemiesSmall[1] = isEnemy(MARIO_X+2,MARIO_Y);
       enemiesSmall[2] = isEnemy(MARIO_X+3,MARIO_Y);

    }

    private boolean isEnemy(int x, int y) {
        return (scene[y][x] == Sprite.KIND_BULLET_BILL ||
                scene[y][x] == Sprite.KIND_GOOMBA ||
                scene[y][x] == Sprite.KIND_ENEMY_FLOWER ||
                scene[y][x] == Sprite.KIND_GOOMBA_WINGED ||
                scene[y][x] == Sprite.KIND_GREEN_KOOPA ||
                scene[y][x] == Sprite.KIND_GREEN_KOOPA_WINGED ||
                scene[y][x] == Sprite.KIND_RED_KOOPA ||
                scene[y][x] == Sprite.KIND_RED_KOOPA_WINGED ||
                scene[y][x] == Sprite.KIND_SPIKY ||
                scene[y][x] == Sprite.KIND_SPIKY_WINGED);
    }

    public float getReward(){
        float reward =  distance * Params.REWARDS.DISTANCE +
               // time * Params.REWARDS.TIME +
                win * Params.REWARDS.WIN +
                gotHit * Params.REWARDS.COLLISION;
             //   stuck * Params.REWARDS.STUCK;
           //     time * Params.REWARDS.TIME_SPENT;
        if(environment.getEvaluationInfo().distancePassedCells >= 255){

        };
           return reward;
    }

    //-1 for no ground under Mario
    private int getDistanceToGround(int x){
        for(int y = MARIO_Y + 1; y < scene.length;y++){
            if(isGround(x,y)) {
                return Math.min(3,y-MARIO_Y-1);
            }
        }
        return -1;
    }

    private boolean isGround(int x, int y) {
        return isObstacle(x,y) || scene[y][x] == GeneralizerLevelScene.BORDER_HILL;
    }

    private boolean isObstacle(int x, int y) {
       return (scene[y][x]==GeneralizerLevelScene.BRICK
               || scene[y][x]==GeneralizerLevelScene.BORDER_CANNOT_PASS_THROUGH
               || scene[y][x]== GeneralizerLevelScene.FLOWER_POT_OR_CANNON
               || scene[y][x]==GeneralizerLevelScene.LADDER);
    }

    private int getDirection(float dX, float dY) {
        if(Math.abs(dX) < Params.DIRECTION_THRESHOLD) {dX = 0;}
        if(Math.abs(dY) < Params.DIRECTION_THRESHOLD) {dY = 0;}

        if(dX == 0 && dY > 0) {return Direction.UP;}
        else if(dX>0 && dY > 0){return Direction.UP_RIGHT;}
        else if(dX>0 && dY == 0){return Direction.RIGHT;}
        else if(dX>0 && dY < 0){return Direction.DOWN_RIGHT;}
        else if(dX == 0 && dY < 0){return Direction.DOWN;}
        else if(dX < 0 && dY < 0){return Direction.DOWN_LEFT;}
        else if(dX < 0 && dY == 0){return Direction.LEFT;}
        else if(dX < 0 && dY > 0){return Direction.UP_LEFT;}
        return Direction.NONE;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (canJump != state.canJump) return false;
        if (direction != state.direction) return false;
        if (gotHit != state.gotHit) return false;
        if (height != state.height) return false;
        if (marioMode != state.marioMode) return false;
        if (onGround != state.onGround) return false;
        if (stuck != state.stuck) return false;
        if (!Arrays.equals(enemiesSmall, state.enemiesSmall)) return false;
        if (!Arrays.equals(obstacles, state.obstacles)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = direction;
        result = 31 * result + stuck;
        result = 31 * result + onGround;
        result = 31 * result + canJump;
        result = 31 * result + gotHit;
        result = 31 * result + marioMode;
        result = 31 * result + Arrays.hashCode(enemiesSmall);
        result = 31 * result + Arrays.hashCode(obstacles);
        result = 31 * result + height;
        return result;
    }
}
