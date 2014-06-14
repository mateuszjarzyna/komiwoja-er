import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic {

	private int population;
	private int[][] matrix;
	private ArrayList<Point> vertexList;
	ArrayList<Phenotype> pheno;
	int min = Integer.MAX_VALUE;
	Phenotype best;

	public Genetic(int p, int[][] m, ArrayList<Point> vl) {
		population = p;
		matrix = m;
		vertexList = vl;
	}

	public ArrayList<Phenotype> generateRandomPopulation() {
		pheno = new ArrayList<>();

		for (int i = 0; i < population; i++) {
			Phenotype ph = new Phenotype(null);
			ph.rand(vertexList.size());
			ph.setMatrix(matrix);

			pheno.add(ph);
		}

		return pheno;
	}

	public Phenotype getBest() {
		for (Phenotype p : pheno) {
			int dist = p.evaluate();

			if (dist < min) {
				min = dist;
				best = p;
			}
		}

		return best;
	}

	public ArrayList<Phenotype> linearRanking(int n) {
		ArrayList<Phenotype> sorted = (ArrayList<Phenotype>) pheno.clone();
		Collections.sort(sorted);

		ArrayList<Phenotype> selected = new ArrayList<Phenotype>();

		Random ran = new Random();
		double r = ran.nextDouble();

		for (int j = 0; j < n; j++) {
			double from = 0;
			double sum = (sorted.size() * (sorted.size() + 1)) / 2.0;
			int i = 0;
			
			for (; i < sorted.size(); i++) {
				if (r >= from && r <= from + i / sum)
					break;

				from += i / sum;
			}
			
			if (i == sorted.size())
				i--;
			
			selected.add(sorted.get(i));
			sorted.remove(i);
		}
		
		return selected;
	}
	
	public ArrayList<Phenotype> cross(ArrayList<Phenotype> tc) {
		ArrayList<Phenotype> childs = new ArrayList<Phenotype>();
		Random ran = new Random();
		
		int r = ran.nextInt(tc.size());
		Phenotype p = tc.get(r);
		tc.remove(r);
		
		while (tc.size() > 0) {
			int r2 = ran.nextInt(tc.size());
			Phenotype p2 = tc.get(r2);
			tc.remove(r2);
			
			childs.add(p.OX(p2));
			
			r = r2;
		}
		
		return childs;
	}
	
	public void mutation(ArrayList<Phenotype> cd, double p) {
		Random ran = new Random();
		
		for (Phenotype ph : cd) {
			double r = ran.nextDouble();
			
			if (r <= p)
				ph.mutation();
		}
	}
	
	public void toPopulation(ArrayList<Phenotype> c) {
		ArrayList<Phenotype> tmpPopulation = (ArrayList<Phenotype>) pheno.clone();
		ArrayList<Phenotype> newPopulation = new ArrayList<Phenotype>();
		
		for(Phenotype p : c)
			tmpPopulation.add(p);
		
		for (int i = 0; i < population; i++) {
			Phenotype lbest = tmpPopulation.get(0);
			
			for (Phenotype p : tmpPopulation) {
				int dist = p.evaluate();

				if (dist < lbest.evaluate())
					lbest = p;
			}
			
			newPopulation.add(lbest);
			tmpPopulation.remove(lbest);
		}
		
		pheno = newPopulation;
	}
}
