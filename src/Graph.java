import java.awt.Point;
import java.util.ArrayList;


public class Graph {

	public static int[][] calculateMatrix(ArrayList<Point> vertexList) {
		int size = vertexList.size();
		int[][] matrix = new int[size][size];
		
		for (int i = 0; i < size; i ++)
			for (int j = 0; j < size; j++)
				matrix[i][j] = calculateDistance(vertexList.get(i), vertexList.get(j));
		
		return matrix;
	}
	
	private static int calculateDistance(Point a, Point b) {
		return (int) Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
	}
	
}
