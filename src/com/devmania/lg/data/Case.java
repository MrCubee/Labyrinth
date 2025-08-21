package com.devmania.lg.data;
import java.io.Serializable;

public class Case implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private boolean state;
    private Boolean[] walls = new Boolean[4];
    private Boolean finalPath = false;
    private Boolean SolverVisited = false;


    @SuppressWarnings("unused")
	public Case(int x, int y) {
        Coordinates coord = new Coordinates(x, y);
        state = false;
        for (int a = 0; a < walls.length; a++) {
            walls[a] = true;
        }
    }

    public Boolean has4Walls() {
        boolean ok = true;
        for (Boolean wall : walls) {
            if (!wall) {
                ok = false;
            }
        }

        return ok;
    }


    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getTopWall() {
        return walls[0];
    }

    public boolean getRightWall() {
        return walls[1];
    }

    public boolean getBottomWall() {
        return walls[2];
    }

    public boolean getLeftWall() {
        return walls[3];
    }

    public void breakTopWall() {
        walls[0] = false;
    }

    public void breakRightWall() {
        walls[1] = false;
    }

    public void breakBottomWall() {
        walls[2] = false;
    }

    public void breakLeftWall() {
        walls[3] = false;
    }

    public Boolean getFinalPath() {
        return finalPath;
    }

    public void setFinalPath(Boolean finalPath) {
        this.finalPath = finalPath;
    }

    public Boolean getSolverVisited() {
        return SolverVisited;
    }

    public void setSolverVisited(Boolean solverVisited) {
        SolverVisited = solverVisited;
    }

}
