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

    //2-fire 1-big 0-small
    private int marioMode = 2;
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

    //[x][x][x][x][x]
    //[x][x][x][x][x]
    //[x][x][M][x][x]
    //[x][x][M][x][x]
    //[x][x][x][x][x]
    private boolean[] enemies = new boolean[25];
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

        //Update Distance Delta  // TODO Implement with minValue
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

        time = environment.getTimeSpent();
        canJump = environment.isMarioAbleToJump()?1:0;
        onGround = environment.isMarioOnGround()?1:0;

        //Fill enemy Info
        //Reset and new
        enemies = new boolean[25];
        int enemyIndize=0;
        totalEnemyCount = 0;
        for(int x = MARIO_X -2; x <= MARIO_X+2;x++){
            for(int y = MARIO_Y -3; y <= MARIO_Y+1; y++){
                //  System.out.println("[" + x + ":" +y+"]");
               if(scene[y][x] == Sprite.KIND_BULLET_BILL ||
                  scene[y][x] == Sprite.KIND_GOOMBA ||
                  scene[y][x] == Sprite.KIND_ENEMY_FLOWER ||
                  scene[y][x] == Sprite.KIND_GOOMBA_WINGED ||
                  scene[y][x] == Sprite.KIND_GREEN_KOOPA ||
                  scene[y][x] == Sprite.KIND_GREEN_KOOPA_WINGED ||
                  scene[y][x] == Sprite.KIND_RED_KOOPA ||
                  scene[y][x] == Sprite.KIND_RED_KOOPA_WINGED ||
                  scene[y][x] == Sprite.KIND_SPIKY ||
                  scene[y][x] == Sprite.KIND_SPIKY_WINGED) {
                    enemies[enemyIndize] = true;
                    totalEnemyCount++;
               }
               enemyIndize++;
            }
        }
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
    }

    public float getReward(){
        float reward =  distance * Params.REWARDS.DISTANCE +
                height * Params.REWARDS.HEIGHT +

                stuck * Params.REWARDS.STUCK;
      //  time * Params.REWARDS.TIME_SPENT +
               logger.info("D: " + distance + " H:" + height + " R:" +reward);
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
        if (distance != state.distance) return false;
        if (gotHit != state.gotHit) return false;
        if (height != state.height) return false;
        if (marioMode != state.marioMode) return false;
        if (onGround != state.onGround) return false;
        if (!Arrays.equals(enemies, state.enemies)) return false;
        if (!Arrays.equals(obstacles, state.obstacles)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = marioMode;
        result = 31 * result + direction;
        result = 31 * result + onGround;
        result = 31 * result + canJump;
        result = 31 * result + gotHit;
        result = 31 * result + Arrays.hashCode(enemies);
        result = 31 * result + Arrays.hashCode(obstacles);
        result = 31 * result + distance;
        result = 31 * result + height;
        return result;
    }
}
