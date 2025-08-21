package com.devmania.lg.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Labyrinthes implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Case caseArray[][];
    private Integer cells, cellsVisited = 0;
    private int startX, startY;
    private Integer endX = null, endY = null;
    private Stack<Coordinates> solvedPath;
    private boolean isSolved = false;

    public Labyrinthes(int height, int width) {
        try {
            caseArray = new Case[height][width];
            for (int a = 0; a < height; a++) {
                for (int b = 0; b < width; b++) {
                    caseArray[a][b] = new Case(a, b);
                }
            }
            cells = height * width;
            this.generate();
        } catch (Exception e) {
            System.out.println("Unknown Error !");
        }

    }

    void generate() {
        try {
            Random randomGenerator = new Random();

            int firstRand = randomGenerator.nextInt(4);
            if (firstRand == 0) {
                this.startY = 0;
                this.startX = randomGenerator.nextInt(caseArray.length);
                caseArray[startX][startY].breakTopWall();
            }
            if (firstRand == 1) {
                this.startY = randomGenerator.nextInt(caseArray[0].length);
                this.startX = caseArray.length - 1;
                caseArray[startX][startY].breakRightWall();
            }
            if (firstRand == 2) {
                this.startY = caseArray[0].length - 1;
                this.startX = randomGenerator.nextInt(caseArray.length);
                caseArray[startX][startY].breakBottomWall();
            }
            if (firstRand == 3) {
                this.startY = randomGenerator.nextInt(caseArray[0].length);
                this.startX = 0;
                caseArray[startX][startY].breakLeftWall();
            }


            caseArray[startX][startY].setState(true);
            Coordinates start = new Coordinates(startX, startY);
            Coordinates actual = start;
            
            Stack<Coordinates> cellStack = new Stack<Coordinates>();
            int longerPathSize = 0;

            while (cellsVisited < cells) {
                if (this.getNeighboursFree(actual) != null) {
                    Coordinates[] neighbours = this.getNeighboursFree(actual);
                    int nbr = neighbours.length;
                    int rand = randomGenerator.nextInt(nbr);
                    Coordinates target = new Coordinates(neighbours[rand].getX(), neighbours[rand].getY());

                    caseArray[neighbours[rand].getX()][neighbours[rand].getY()].setState(true);
                    this.breakWall(actual, target);
                    cellStack.push(actual);

                    actual = new Coordinates(target.getX(), target.getY());
                    cellsVisited++;
                } else {
                    if (cellStack.size() > 0) {
                        if (cellStack.size() > longerPathSize) {
                            longerPathSize = cellStack.size();
                            this.endX = actual.getX();
                            this.endY = actual.getY();
                        }

                        Coordinates temp = cellStack.pop();

                        actual = new Coordinates(temp.getX(), temp.getY());
                    } else {
                        break;
                    }

                }

            }
        } catch (Exception e) {
            System.out.println("Unknown Error !");
        }
    }

    void breakWall(Coordinates actual, Coordinates target) {
        try {
            if ((target.getX() == actual.getX()) && (target.getY() <= actual.getY())) {
                caseArray[actual.getX()][actual.getY()].breakTopWall();
                caseArray[target.getX()][target.getY()].breakBottomWall();
            }

            if ((target.getX() >= actual.getX()) && (target.getY() == actual.getY())) {
                caseArray[actual.getX()][actual.getY()].breakRightWall();
                caseArray[target.getX()][target.getY()].breakLeftWall();
            }

            if ((target.getX() == actual.getX()) && (target.getY() >= actual.getY())) {
                caseArray[actual.getX()][actual.getY()].breakBottomWall();
                caseArray[target.getX()][target.getY()].breakTopWall();
            }

            if ((target.getX() <= actual.getX()) && (target.getY() == actual.getY())) {
                caseArray[actual.getX()][actual.getY()].breakLeftWall();
                caseArray[target.getX()][target.getY()].breakRightWall();
            }
        } catch (Exception e) {
            System.out.println("Unknown Error !");
        }
    }

    
    Coordinates[] getNeighboursFree(Coordinates coord_check) {
        try {
            int x = coord_check.getX();
            int y = coord_check.getY();
            Coordinates coord[] = new Coordinates[4];
            int a = 0;
            if (((x >= 0) && (x < caseArray.length)) && (((y - 1) >= 0) && ((y - 1) < caseArray[0].length))) {
                if (caseArray[x][y - 1].has4Walls() && !caseArray[x][y - 1].getState()) {
                    coord[a] = new Coordinates(x, y - 1);
                    a++;
                }
            }
            if ((((x + 1) >= 0) && ((x + 1) < caseArray.length)) && ((y >= 0) && (y < caseArray[0].length))) {
                if (caseArray[x + 1][y].has4Walls() && !caseArray[x + 1][y].getState()) {
                    coord[a] = new Coordinates(x + 1, y);
                    a++;
                }
            }
            if (((x >= 0) && (x < caseArray.length)) && (((y + 1) > 0) && ((y + 1) < caseArray[0].length))) {
                if (caseArray[x][y + 1].has4Walls() && !caseArray[x][y + 1].getState()) {
                    coord[a] = new Coordinates(x, y + 1);
                    a++;
                }
            }
            if ((((x - 1) >= 0) && ((x - 1) < caseArray.length)) && ((y >= 0) && (y < caseArray[0].length))) {
                if (caseArray[x - 1][y].has4Walls() && !caseArray[x - 1][y].getState()) {
                    coord[a] = new Coordinates(x - 1, y);
                    a++;
                }
            }

            Coordinates[] returnCoord;

            if (a != 0) {
                returnCoord = new Coordinates[a];
                for (int b = 0; b < a; b++) {
                    returnCoord[b] = new Coordinates(coord[b].getX(), coord[b].getY());
                }
            } else {
                returnCoord = null;
            }

            return returnCoord;
        } catch (Exception e) {
            System.out.println("Unknown Error !");
            return null;
        }
    }

    
    Coordinates[] getNeighboursOpen(Coordinates coord_check) {
        try {
            int x = coord_check.getX();
            int y = coord_check.getY();
            Coordinates coord[] = new Coordinates[4];
            int a = 0;

            if (((x >= 0) && (x < caseArray.length)) && (((y - 1) >= 0) && ((y - 1) < caseArray[0].length))) {
                if (!caseArray[x][y - 1].getBottomWall() && !caseArray[x][y - 1].getSolverVisited()) {
                    coord[a] = new Coordinates(x, y - 1);
                    a++;
                }
            }
            if ((((x + 1) >= 0) && ((x + 1) < caseArray.length)) && ((y >= 0) && (y < caseArray[0].length))) {
                if (!caseArray[x + 1][y].getLeftWall() && !caseArray[x + 1][y].getSolverVisited()) {
                    coord[a] = new Coordinates(x + 1, y);
                    a++;
                }
            }
            if (((x >= 0) && (x < caseArray.length)) && (((y + 1) > 0) && ((y + 1) < caseArray[0].length))) {
                if (!caseArray[x][y + 1].getTopWall() && !caseArray[x][y + 1].getSolverVisited()) {
                    coord[a] = new Coordinates(x, y + 1);
                    a++;
                }
            }
            if ((((x - 1) >= 0) && ((x - 1) < caseArray.length)) && ((y >= 0) && (y < caseArray[0].length))) {
                if (!caseArray[x - 1][y].getRightWall() && !caseArray[x - 1][y].getSolverVisited()) {
                    coord[a] = new Coordinates(x - 1, y);
                    a++;
                }
            }

            Coordinates[] returnCoord;

            if (a != 0) {
                returnCoord = new Coordinates[a];
                for (int b = 0; b < a; b++) {
                    returnCoord[b] = new Coordinates(coord[b].getX(), coord[b].getY());
                }
            } else {
                returnCoord = null;
            }

            return returnCoord;
        } catch (Exception e) {
            System.out.println("Unknown Error !");
            return null;
        }
    }

    @SuppressWarnings("unchecked")
	public void resolve() {
        try {
            Stack<Coordinates> actualStack = new Stack<Coordinates>();
            Stack<Coordinates> shortestStack = new Stack<Coordinates>();

            Random randomGenerator = new Random();
            Coordinates actual = new Coordinates(startX, startY);
            cellsVisited = 0;
            while (cellsVisited < cells) {
                if (this.getNeighboursOpen(actual) != null) {
                    Coordinates[] neighbours = this.getNeighboursOpen(actual);
                    int nbr = neighbours.length;
                    int rand = randomGenerator.nextInt(nbr);
                    Coordinates target = new Coordinates(neighbours[rand].getX(), neighbours[rand].getY());
                    caseArray[neighbours[rand].getX()][neighbours[rand].getY()].setSolverVisited(true);

                    actualStack.push(actual);
                    actual = new Coordinates(target.getX(), target.getY());

                    cellsVisited++;
                } else {
                    if (actual.getX() == this.getEndX() && actual.getY() == this.getEndY()) {
                        shortestStack = (Stack<Coordinates>) actualStack.clone();
                        Coordinates temp = actualStack.pop();
                        actual = new Coordinates(temp.getX(), temp.getY());
                    } else {
                        Coordinates temp = actualStack.pop();
                        actual = new Coordinates(temp.getX(), temp.getY());
                    }
                }
            }
            solvedPath = (Stack<Coordinates>) shortestStack.clone();
            int i = shortestStack.size();
            for (int j = 0; j < i; j++) {

                Coordinates temp = shortestStack.pop();
                caseArray[temp.getX()][temp.getY()].setFinalPath(true);
            }
            this.isSolved = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unknown Error !");
        }
    }

    
    public Lab getLabyrinthe() {
    	
    	List<List<String>> list = new ArrayList<List<String>>();
    	
    	List<String> l = new ArrayList<String>();
        try {
            //System.out.print('#');
        	l.add("#");
            for (int b = 0; b < caseArray.length * 2; b++) {
                if (b % 2 == 0) {
                    if (caseArray[b / 2][0].getTopWall()) {
                    	l.add("#");
                        //System.out.print('#');
                    } else {
                    	l.add(" ");
                        //System.out.print(' ');
                    }
                } else {
                	l.add("#");
                    //System.out.print('#');
                }
            }
            
            list.add(l);
            System.out.println();

            for (int a = 0; a < (caseArray[0].length * 2); a++) {
            	List<String> l2 = new ArrayList<String>();
            	
            	
                if (a % 2 == 0) {
                    if (caseArray[0][a / 2].getLeftWall()) {
                    	l2.add("#");
                        //System.out.print('#');
                    } else {
                    	l2.add(" ");
                        //System.out.print(' ');
                    }
                    for (int b = 0; b < (caseArray.length * 2); b++) {
                        if (b % 2 == 0) {
                            if ((b / 2) == this.getEndX() && (a / 2) == this.getEndY()) {
                            	l2.add("E");
                                //System.out.print('E');
                            } else if ((b / 2) == this.getStartX() && (a / 2) == this.getStartY()) {
                            	l2.add("S");
                                //System.out.print('S');
                            } else {
                                if (caseArray[b / 2][a / 2].getFinalPath()) {
                                	l2.add(".");
                                    //System.out.print('.');
                                } else {
                                	l2.add(" ");
                                    //System.out.print(' ');
                                }
                            }
                        } else {
                            if (caseArray[b / 2][a / 2].getRightWall()) {
                            	l2.add("#");
                                //System.out.print('#');
                            } else {
                            	l2.add(" ");
                                //System.out.print(' ');
                            }
                        }
                    }
                } else {
                	l2.add("#");
                    //System.out.print('#');
                    for (int b = 0; b < (caseArray.length * 2); b++) {
                        if (b % 2 == 0) {
                            if (caseArray[b / 2][a / 2].getBottomWall()) {
                            	l2.add("#");
                                //System.out.print('#');
                            } else {
                            	l2.add(" ");
                                //System.out.print(' ');
                            }
                        } else {
                        	l2.add("#");
                            //System.out.print('#');
                        }
                    }
                }
                list.add(l2);
                //System.out.println();
            }
//            System.out.println();
//            System.out.println();
        } catch (Exception e) {
            System.out.println("Unknown Error !");
        }
        
        return new Lab(list);
    }

    public Case[][] getCaseArray() {
        return caseArray;
    }

    public int getStartY() {
        return startY;
    }

    public int getStartX() {
        return startX;
    }

    public Integer getEndY() {
        return endY;
    }

    public Integer getEndX() {
        return endX;
    }

    public Stack<Coordinates> getSolvedPath() {
        return solvedPath;
    }

    public boolean isSolved() {
        return isSolved;
    }
}