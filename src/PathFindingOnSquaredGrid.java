/***************************************************************************
 *
 *Created by w1530819 Hadi Elmekawi
 *
 ****************************************************************************/


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;




public class PathFindingOnSquaredGrid {
	
	public static int SIZE_OF_THE_GRID = 30;
	public static int BLOCKED_CELL = 1;
	
    public static int costManhattan = 0 ;


    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top
    public static boolean[][] flow(boolean[][] open) {
        int N = open.length;

        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }

        return full;
    }

    // determine set of open/blocked cells using depth first search
    public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
        int N = open.length;

        // base cases
        if (i < 0 || i >= N) return;    // invalid row
        if (j < 0 || j >= N) return;    // invalid column
        if (!open[i][j]) return;        // not an open cell
        if (full[i][j]) return;         // already marked as open

        full[i][j] = true;

        flow(open, full, i + 1, j);   // down
        flow(open, full, i, j + 1);   // right
        flow(open, full, i, j - 1);   // left
        flow(open, full, i - 1, j);   // up
    }

    // does the system percolate?
    public static boolean percolates(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        int y = N - 1;
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j])

                return true;

        }

        return false;
    }

    // does the system percolate vertically in a direct way?
    public static boolean percolatesDirect(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) {
                //StdOut.println("Hello");
                directPerc = 1;
                int rowabove = N - 2;
                for (int i = rowabove; i >= 0; i--) {
                    if (full[i][j]) {
                        // StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
                        directPerc++;
                    } else break;
                }
            }
        }

        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) return true;
        else return false;
    }

    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        ;
        StdDraw.setYscale(-1, N);


        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    StdDraw.square(j, N - i - 1, .5);
                else StdDraw.filledSquare(j, N - i - 1, .5);
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2, int x3, int y3) {

        int N = a.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);


        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    if ((i == x1 && j == y1) || (i == x2 && j == y2) || (i == x3 && j == y3)) {
                        StdDraw.circle(j, N - i - 1, .5);
                    } else StdDraw.square(j, N - i - 1, .5);
                else StdDraw.filledSquare(j, N - i - 1, .5);
    }

    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }


    // AStarTest client
    @SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {


        
        /********************************************************************************
         *  The following will generate a 50x50 squared grid with 0 obstacles  in it
         * The lower the second parameter, the more obstacles (black cells) are generated
         *********************************************************************************/
        System.out.println("The Size of your grid is: "+ SIZE_OF_THE_GRID);
        boolean[][] emptyGrid = random(SIZE_OF_THE_GRID, BLOCKED_CELL);
        StdArrayIO.print(emptyGrid);
        show(emptyGrid, true);



        //Uncomment Randon and comment Scanner if you want coordinates to be random

        // Reading the coordinates for points A and B on the input squared grid.
        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
        // Start the clock ticking in order to capture the time being spent on inputting the coordinates
        // You should position this command accordingly in order to perform the algorithmic analysis

        ArrayList<Point> coordinates = new ArrayList<Point>();
        ArrayList<Point> Blocked = new ArrayList<Point>();

        int N = emptyGrid.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);

        /* add all empty coordinates to array list of points */
        int counterBlocked = 0;
        for (int i = 0; i < emptyGrid.length; i++) {
            for (int j = 0; j < emptyGrid.length; j++) {
            	/*Checked my empty cells*/
            	if (emptyGrid[i][j]) {
                    coordinates.add(new Point(i, j));

                } /*Checked my blocked cells*/
            	else {
                	
                    counterBlocked++;                    
                    Blocked.add(new Point(i,j));
                 


                }
            }

        }
        int tag1x = 0;
        int tag1y = 0;
        
        int tag2x = 0;
        int tag2y = 29;
        
        int tag3x = 29;
        int tag3y = 0;
        
        


         show(emptyGrid, true, tag1x, tag1y, tag2x, tag2y, tag3x, tag3y);

        int[][] BlockedPoints = new int[counterBlocked][2];
        for(int i=0; i<counterBlocked; i++){
            //x points
            BlockedPoints[i][0] = ((int)Blocked.get(i).getX());
            //y point
            BlockedPoints[i][1] =  ((int)Blocked.get(i).getY());
        }




        /*
        Parameters :
        x, y = my grid dimension
        tagx, tagy = start location's x and y coordinates
        anchorx, anchory = end location's x and y coordinates
        int[][] BlockedPoints = array containing inaccessible cell coordinates
        */

        //Manhattan
        AStar.AStarTest(emptyGrid.length, emptyGrid.length, 29, 29, tag2x, tag2y, BlockedPoints);
        //GET THE SHORTEST PATH
        System.out.println("================");
        System.out.println("Shortest Path: ");
        System.out.println();
        for(int i = 0; i < ResutlPath.arraylist.size()-1;i++){
            //costManhattan++;
            System.out.println("x: "+ ResutlPath.arraylist.get(i).j+" y: "+ ResutlPath.arraylist.get(i).i);

            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledSquare(ResutlPath.arraylist.get(i).j, N - ResutlPath.arraylist.get(i).i - 1, .5);
            //Use a thread in order to see the line drawing
           // Thread.sleep(200);
        }

        ResutlPath.arraylist.removeAll(ResutlPath.arraylist);
        
        //Manhattan
        AStar.AStarTest(emptyGrid.length, emptyGrid.length, tag2x, tag2y, tag1x, tag1y, BlockedPoints);
        //GET THE SHORTEST PATH
        System.out.println("================");
        System.out.println("Shortest Path: ");
        System.out.println();
        for(int i = 0; i < ResutlPath.arraylist.size()-1;i++){
            //costManhattan++;
            System.out.println("x: "+ ResutlPath.arraylist.get(i).j+" y: "+ ResutlPath.arraylist.get(i).i);

            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledSquare(ResutlPath.arraylist.get(i).j, N - ResutlPath.arraylist.get(i).i - 1, .5);
            //Use a thread in order to see the line drawing
           // Thread.sleep(200);
        }

        
        ResutlPath.arraylist.removeAll(ResutlPath.arraylist);
        
        //Manhattan
        AStar.AStarTest(emptyGrid.length, emptyGrid.length, tag1x, tag1y, tag3x, tag3y, BlockedPoints);
        //GET THE SHORTEST PATH
        System.out.println("================");
        System.out.println("Shortest Path: ");
        System.out.println();
        for(int i = 0; i < ResutlPath.arraylist.size()-1;i++){
            //costManhattan++;
            System.out.println("x: "+ ResutlPath.arraylist.get(i).j+" y: "+ ResutlPath.arraylist.get(i).i);

            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledSquare(ResutlPath.arraylist.get(i).j, N - ResutlPath.arraylist.get(i).i - 1, .5);
            //Use a thread in order to see the line drawing
           // Thread.sleep(200);
        }

        ResutlPath.arraylist.removeAll(ResutlPath.arraylist);
        
        //Manhattan
        AStar.AStarTest(emptyGrid.length, emptyGrid.length,  tag3x, tag3y, 29, 29, BlockedPoints);
        //GET THE SHORTEST PATH
        System.out.println("================");
        System.out.println("Shortest Path: ");
        System.out.println();
        for(int i = 0; i < ResutlPath.arraylist.size()-1;i++){
            //costManhattan++;
            System.out.println("x: "+ ResutlPath.arraylist.get(i).j+" y: "+ ResutlPath.arraylist.get(i).i);

            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledSquare(ResutlPath.arraylist.get(i).j, N - ResutlPath.arraylist.get(i).i - 1, .5);
            //Use a thread in order to see the line drawing
           // Thread.sleep(200);
        }

        

        //Draw Cost on the Grid
//        Font font = new Font("Arial", Font.BOLD, 13);
//        StdDraw.setFont(font);
//        StdDraw.setPenColor(StdDraw.BLUE);
//        StdDraw.text(0, hundredByOneGrid.length - -0.8 -1, "Cost: "+(costManhattan ));
//
//        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
//        // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
//        // You should position this command accordingly in order to perform the algorithmic analysis
//        StdOut.println("*****************************************");
//        StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
//        StdOut.println("TOT COST MANHATTAN: "+costManhattan);
//        StdOut.println("*****************************************");




        //euclidian
//        Stopwatch timerFlow1 = new Stopwatch();
//        timerFlow1.elapsedTime();
//        AStar.AStarTest(hundredByOneGrid.length, hundredByOneGrid.length, BiR, BjR,AiR, AjR, BlockedPoints);
//        //GET THE SHORTEST PATH
//        System.out.println("================");
//        System.out.println("Shortest Path: ");
//        System.out.println();
//
//        for(int i = 0; i < ResutlPath.arraylist1.size()-1;i++){
//            costEuclidian += 1.4;
//            System.out.println("x: "+ ResutlPath.arraylist1.get(i).j+" y: "+ ResutlPath.arraylist1.get(i).i);
//
//            StdDraw.setPenColor(StdDraw.ORANGE);
//            StdDraw.filledSquare(ResutlPath.arraylist1.get(i).j, N - ResutlPath.arraylist1.get(i).i - 1, .3);
//            //Use a thread in order to see the line drawing
//            //Thread.sleep(500);
//        }

//        //Draw Cost on the Grid
//        StdDraw.setFont(font);
//        StdDraw.setPenColor(StdDraw.ORANGE);
//        StdDraw.text(4, hundredByOneGrid.length - -0.8 -1, "Cost: "+(costEuclidian +1));

        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
        // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
        // You should position this command accordingly in order to perform the algorithmic analysis
//        StdOut.println("*****************************************");
//        StdOut.println("Elapsed time = " + timerFlow1.elapsedTime());
//        StdOut.println("TOT COST EUCLIDIAN: "+(costEuclidian+1));
//        StdOut.println("*****************************************");
//

//        StdOut.println("1   Manhattan ");
//        StdOut.println("2   Euclidian ");
//        StdOut.println("3   Chebyshev ");


        //Chebyshev
//        Stopwatch timerFlow2 = new Stopwatch();
//        timerFlow2.elapsedTime();
//        AStar.AStarTest(hundredByOneGrid.length, hundredByOneGrid.length, BiR, BjR,AiR, AjR, BlockedPoints);
//        //GET THE SHORTEST PATH
//        System.out.println("================");
//        System.out.println("Shortest Path: ");
//        System.out.println();
//
//        for(int i = 0; i < ResutlPath.arraylist2.size()-1;i++){
//            costChebishi++;
//            System.out.println("x: "+ ResutlPath.arraylist2.get(i).j+" y: "+ ResutlPath.arraylist2.get(i).i);
//
//            StdDraw.setPenColor(StdDraw.GREEN);
//            StdDraw.filledSquare(ResutlPath.arraylist2.get(i).j, N - ResutlPath.arraylist2.get(i).i - 1, .2);
//            //Use a thread in order to see the line drawing
//            //Thread.sleep(500);
//        }
//
//        //Draw Cost on the Grid
//        StdDraw.setFont(font);
//        StdDraw.setPenColor(StdDraw.GREEN);
//        StdDraw.text(8, hundredByOneGrid.length - -0.8 -1, "Cost: "+(costChebishi +1));
//        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
//        // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
//        // You should position this command accordingly in order to perform the algorithmic analysis
//        StdOut.println("*****************************************");
//        StdOut.println("Elapsed time = " + timerFlow2.elapsedTime());
//        StdOut.println("TOT COST CHEBISHA: "+(costChebishi+1));
//        StdOut.println("*****************************************");



    }



}









