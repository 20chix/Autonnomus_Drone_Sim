/***************************************************************************
 *
 *Created by w1530819 Hadi Elmekawi
 *
 ****************************************************************************/

import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Reference of A* algorithm:
 * https://en.wikipedia.org/wiki/A*_search_algorithm
 * http://stackoverflow.com/questions/6158321/stuck-implementing-wikipedias-a-a-star-algorithm
 * https://www.youtube.com/watch?v=-L-WgKMFuhE
 * https://www.youtube.com/watch?v=EaZxUCWAjb0
 * http://www.codebytes.in/2015/02/a-shortest-path-finding-algorithm.html
 *
 */
public class AStar {

    public static  int FINAL_COST_MANHATTAN=0;
    public static int FINAL_COST_EUCLIDIAN=0;
    public static int FINAL_COST_CHEBIJA= 0;


    static int cost_counter= 0;


    static class Cell {
        int heuristicCost = 0; //Heuristic
        int finalCost = 0; //G+H
        int i, j;
        Cell parent;

        Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }

    }

    //Blocked cells are just null Cell values in grid
    static Cell[][] grid = new Cell[5][5];
    static PriorityQueue<Cell> open;

    static boolean closed[][];
    static int startI, startJ;
    static int endI, endJ;

    public static void setBlocked(int i, int j) {
        grid[i][j] = null;
    }

    public static void setStartCell(int i, int j) {
        startI = i;
        startJ = j;
    }

    public static void setEndCell(int i, int j) {
        endI = i;
        endJ = j;
    }

    static void checkAndUpdateCost(Cell current, Cell t, int cost) {
        if (t == null || closed[t.i][t.j]) return;
        int t_final_cost = t.heuristicCost + cost;

        boolean inOpen = open.contains(t);
        if (!inOpen || t_final_cost < t.finalCost) {
            t.finalCost = t_final_cost;
            t.parent = current;
            if (!inOpen) open.add(t);
        }
    }

    public static void AStarChebyshev() {
        final int DIAGONAL_COST_C = 1;
        final int VERTICAL_HORIZONTAL_COST_C = 1;

        //add the start location to open list.
        open.add(grid[startI][startJ]);

        Cell current;

        while (true) {
            current = open.poll();
            if (current == null) break;
            closed[current.i][current.j] = true;

            if (current.equals(grid[endI][endJ])) {
                return;
            }
            FINAL_COST_CHEBIJA += current.finalCost+DIAGONAL_COST_C;
            FINAL_COST_CHEBIJA += current.finalCost+VERTICAL_HORIZONTAL_COST_C;


            Cell myCell;
            if (current.i - 1 >= 0) {
                myCell = grid[current.i - 1][current.j];
                checkAndUpdateCost(current, myCell, current.finalCost + VERTICAL_HORIZONTAL_COST_C);


                if (current.j - 1 >= 0) {
                    myCell = grid[current.i - 1][current.j - 1];
                    checkAndUpdateCost(current, myCell, current.finalCost + DIAGONAL_COST_C);

                }

                if (current.j + 1 < grid[0].length) {
                    myCell = grid[current.i - 1][current.j + 1];
                    checkAndUpdateCost(current, myCell, current.finalCost + DIAGONAL_COST_C);
                }
            }

            if (current.j - 1 >= 0) {
                myCell = grid[current.i][current.j - 1];
                checkAndUpdateCost(current, myCell, current.finalCost + VERTICAL_HORIZONTAL_COST_C);
            }

            if (current.j + 1 < grid[0].length) {
                myCell = grid[current.i][current.j + 1];
                checkAndUpdateCost(current, myCell, current.finalCost + VERTICAL_HORIZONTAL_COST_C);
            }

            if (current.i + 1 < grid.length) {
                myCell = grid[current.i + 1][current.j];
                checkAndUpdateCost(current, myCell, current.finalCost + VERTICAL_HORIZONTAL_COST_C);

                if (current.j - 1 >= 0) {
                    myCell = grid[current.i + 1][current.j - 1];
                    checkAndUpdateCost(current, myCell, current.finalCost + DIAGONAL_COST_C);
                }

                if (current.j + 1 < grid[0].length) {
                    myCell = grid[current.i + 1][current.j + 1];
                    checkAndUpdateCost(current, myCell, current.finalCost + DIAGONAL_COST_C);

                }

            }


        }

    }


    public static void AStarMahattan() {



        final int VERTICAL_HORIZONTAL_COST = 1;
        final int DIAGONAL_COST = 2;


        //add the start location to open list.
        open.add(grid[startI][startJ]);

        Cell current;

        while (true) {
            current = open.poll();
            if (current == null) break;
            closed[current.i][current.j] = true;

            if (current.equals(grid[endI][endJ])) {
                return;
            }

            FINAL_COST_MANHATTAN += current.finalCost+VERTICAL_HORIZONTAL_COST;
            Cell t;
            if (current.i - 1 >= 0) {
                t = grid[current.i - 1][current.j];
                checkAndUpdateCost(current, t, current.finalCost + VERTICAL_HORIZONTAL_COST);
               // PathFindingOnSquaredGrid.costManhattan += VERTICAL_HORIZONTAL_COST;



                if (current.j - 1 >= 0) {
                t = grid[current.i-1][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                    //PathFindingOnSquaredGrid.costManhattan += DIAGONAL_COST;
                }

                if (current.j + 1 < grid[0].length) {
               t = grid[current.i-1][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                  //  PathFindingOnSquaredGrid.costManhattan += DIAGONAL_COST;
                }
            }

            if (current.j - 1 >= 0) {
                t = grid[current.i][current.j - 1];
                checkAndUpdateCost(current, t, current.finalCost + VERTICAL_HORIZONTAL_COST);
               // PathFindingOnSquaredGrid.costManhattan += VERTICAL_HORIZONTAL_COST;


            }

            if (current.j + 1 < grid[0].length) {
                t = grid[current.i][current.j + 1];
                checkAndUpdateCost(current, t, current.finalCost + VERTICAL_HORIZONTAL_COST);
                //PathFindingOnSquaredGrid.costManhattan += VERTICAL_HORIZONTAL_COST;

            }

            if (current.i + 1 < grid.length) {
                t = grid[current.i + 1][current.j];
                checkAndUpdateCost(current, t, current.finalCost + VERTICAL_HORIZONTAL_COST);
                //PathFindingOnSquaredGrid.costManhattan += VERTICAL_HORIZONTAL_COST;

                if (current.j - 1 >= 0) {
                t = grid[current.i+1][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                   // PathFindingOnSquaredGrid.costManhattan += VERTICAL_HORIZONTAL_COST;
                }

                if (current.j + 1 < grid[0].length) {
                t = grid[current.i+1][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                    //PathFindingOnSquaredGrid.costManhattan += VERTICAL_HORIZONTAL_COST;
            }
                }
            }


        }





    public static void AStarEuclidian() {
        final int DIAGONAL_COST = 1;
        final int VERTICAL_HORIZONTAL_COST = 1;

        //add the start location to open list.
        open.add(grid[startI][startJ]);

        Cell current;

        while (true) {
            current = open.poll();
            if (current == null) break;
            closed[current.i][current.j] = true;

            if (current.equals(grid[endI][endJ])) {
                return;
            }
            FINAL_COST_EUCLIDIAN += current.finalCost+DIAGONAL_COST;
            Cell t;
            if (current.i - 1 >= 0) {
//                t = grid[current.i-1][current.j];
//                checkAndUpdateCost(current, t, current.finalCost+ VERTICAL_HORIZONTAL_COST);

                if (current.j - 1 >= 0) {
                    t = grid[current.i - 1][current.j - 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
                }

                if (current.j + 1 < grid[0].length) {
                    t = grid[current.i - 1][current.j + 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
                }
            }


//           if(current.j-1>=0){
////                t = grid[current.i][current.j-1];
////               // checkAndUpdateCost(current, t, current.finalCost+ VERTICAL_HORIZONTAL_COST);
////            }
////
////            if(current.j+1<grid[0].length){
////                t = grid[current.i][current.j+1];
////               // checkAndUpdateCost(current, t, current.finalCost+ VERTICAL_HORIZONTAL_COST);
//            }

            if (current.i + 1 < grid.length) {
                //  t = grid[current.i+1][current.j];
                //  checkAndUpdateCost(current, t, current.finalCost+ VERTICAL_HORIZONTAL_COST);
                if (current.j - 1 >= 0) {
                    t = grid[current.i + 1][current.j - 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
                }

                if (current.j + 1 < grid[0].length) {
                    t = grid[current.i + 1][current.j + 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
                }
            }



        }


    }

    /*
    Params :
    x, y = Board's dimensions
    si, sj = start location's x and y coordinates
    ei, ej = end location's x and y coordinates
    int[][] blocked = array containing inaccessible cell coordinates
    */
    public static void AStarTest(int x, int y, int ei, int ej, int si, int sj, int[][] blocked) {
        // System.out.println("\n\nTest Case #"+tCase);
        //Reset
        grid = new Cell[x][y];
        closed = new boolean[x][y];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell) o1;
            Cell c2 = (Cell) o2;

            return c1.finalCost < c2.finalCost ? -1 :
                    c1.finalCost > c2.finalCost ? 1 : 0;
        });
        //Set start position
        setStartCell(si, sj);

        //Set End Location
        setEndCell(ei, ej);
//        System.out.println("Heuristic Cost:      ");
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < y; ++j) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j - endJ);
                //System.out.print(grid[i][j].heuristicCost+" ");
                cost_counter = grid[i][j].heuristicCost;
            }
            // System.out.println();
        }
        PathFindingOnSquaredGrid.costManhattan = cost_counter;
        grid[si][sj].finalCost = 0;

           /*
             Set blocked cells.
           */
        for (int i = 0; i < blocked.length; ++i) {
            setBlocked(blocked[i][0], blocked[i][1]);
        }

        Scanner in = new Scanner(System.in);
        StdOut.println();
        StdOut.println();
        StdOut.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        StdOut.println("Please entter Algorithm you would like to use: ");
        StdOut.println("1   Manhattan ");
        StdOut.println("2   Euclidian ");
        StdOut.println("3   Chebyshev ");
        boolean manhattan = false;
        boolean euclidian = false;
        boolean chebija = false;

        //Always use Manhattan
        String msg = "1";
        int month = Integer.valueOf(msg);
        StdOut.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        StdOut.println();
        StdOut.println();
        String monthString;
        switch (month) {
            case 1:
                AStarMahattan();
                manhattan = true;
                break;
            case 2:
                AStarEuclidian();
                euclidian = true;
                break;
            case 3:
                AStarChebyshev();
                chebija = true;
                break;
            default:
                monthString = "Invalid choice";
                break;
        }



        if (closed[endI][endJ]) {
            //Trace back the path
            //System.out.println("Shortest Path: ");
            Cell current = grid[endI][endJ];
            int i = 0;
            int j = 0;
            int g = 0;
            // System.out.print(current);
            while (current.parent != null) {
                i++;
                if (manhattan) {
                    i++;
                    ResutlPath.arraylist.add(current.parent);

                }
                else if (euclidian){
                    j++;
                    ResutlPath.arraylist1.add(current.parent);

                }
                else if (chebija) {
                    g++;
                    ResutlPath.arraylist2.add(current.parent);

                }
                // System.out.print(" -> "+current.parent);

                current = current.parent;
                //this is just a AStarTest

            }
            ResutlPath.arraylistCost.add(i);
            ResutlPath.arraylist1Cost.add(j);
            ResutlPath.arraylist2Cost.add(g);
            manhattan = false;
            euclidian = false;
            chebija = false;
            System.out.println();
        } else {
        	System.out.println("No possible path");
        }
    }

}

