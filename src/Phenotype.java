import java.util.ArrayList;
import java.util.Random;


public class Phenotype implements Comparable<Phenotype> {

	private ArrayList<Integer> path;
	int[][] matrix = null;
	int distance = -1;
	
	public Phenotype(ArrayList<Integer> p) {
		path = p;
	}
	
	public int evaluate() {
		if (distance > -1)
			return distance;
		
		int ev = 0;
		
		for (int i = 0; i < path.size() - 1; i++)
			ev += matrix[path.get(i)][path.get(i + 1)];
		
		ev += matrix[path.get(path.size()-1)][path.get(0)];
		
		distance = ev;
		
		return ev;
	}
	
	public void rand(int v) {
		path = new ArrayList<>();
		
		ArrayList<Integer> removeList = new ArrayList<>();
		
		for (int i = 0; i < v; i++)
			removeList.add(i);
		
		Random ran = new Random();
		
		for (int i = 0; i < v; i++) {
			int r = ran.nextInt(removeList.size());
			
			path.add(removeList.get(r));
			
			removeList.remove(r);
		}
	}
	
	public Phenotype OX(Phenotype parent) {
		Random ran = new Random();
		
		int start = ran.nextInt(path.size() - 1);
		int end = ran.nextInt(path.size() - start) + start;
		
		ArrayList<Integer> child = new ArrayList<Integer>();
		for (int i = 0; i < path.size(); i++)
			child.add(-3);
		
		for (int i = start; i <= end; i++)
			child.set(i, parent.getPath().get(i));
		
		for (int i = end + 1, j = i; j % path.size() != start; i++) {
			if (!child.contains(path.get(i % path.size()))) {
				child.set(j % path.size(), path.get(i % path.size()));
				j++;
			}
		}
		
		Phenotype p = new Phenotype(child);
		p.setMatrix(matrix);
		
		return p;
	}
	
	public void mutation() {
		Random ran = new Random();
		
		int a = ran.nextInt(path.size());
		int b = ran.nextInt(path.size());
		
		int tmp = path.get(a);
		
		path.set(a, path.get(b));
		path.set(b, tmp);
	}
	
	public ArrayList<Integer> getPath() {
		return path;
	}
	
	public void setMatrix(int[][] m) {
		matrix = m;
	}
	
	@Override
	public String toString() {
		String strPath = "";
		
		for (int i = 0; i <path.size() - 1; i++)
			strPath += path.get(i) + "->";
		
		strPath += path.get(path.size() - 1);
		
		return "d³ugoœæ: " + evaluate() + ";   " + strPath;
	}

	@Override
	public int compareTo(Phenotype o) {
		return -new Integer(evaluate()).compareTo(new Integer(o.evaluate()));
	}
	
}
