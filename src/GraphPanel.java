import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GraphPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Point> vertexList = new ArrayList<Point>();
	private ArrayList<Integer> minimum = null;
	
	
	public GraphPanel(ArrayList<Point> vl) {
		vertexList = vl;
	}
	
	public void setMinimumPath(ArrayList<Integer> m) {
		minimum = m;
		
		repaint();
	}
	
	@Override
	public void paint (Graphics g) {
		g.setColor(Color.MAGENTA);
		
		for (Point p : vertexList)
			g.fillOval((int) p.getX() - 3, (int) p.getY() - 3, 6, 6);
		
		
		g.setColor(Color.GRAY);
		
		for (int i = 0; i < vertexList.size(); i++)
			for (int j = i + 1; j < vertexList.size(); j++)
				g.drawLine((int) vertexList.get(i).getX(), (int) vertexList.get(i).getY(), (int) vertexList.get(j).getX(), (int) vertexList.get(j).getY());
		
		
		if (minimum != null) {
			g.setColor(Color.RED);
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.RED);
	        g2.setStroke(new BasicStroke(3));
			
			for (int i = 0; i < minimum.size() - 1; i++)
				g2.drawLine((int) vertexList.get(minimum.get(i)).getX(), (int) vertexList.get(minimum.get(i)).getY(), (int) vertexList.get(minimum.get(i + 1)).getX(), (int) vertexList.get(minimum.get(i + 1)).getY());
			
			g2.drawLine((int) vertexList.get(minimum.get(0)).getX(), (int) vertexList.get(minimum.get(0)).getY(), (int) vertexList.get(minimum.get(minimum.size() - 1)).getX(), (int) vertexList.get(minimum.get(minimum.size() - 1)).getY());
		}
		
		
		for (int i = 0; i < vertexList.size(); i++) {
			String number = Integer.toString(i);
			int x = (int) vertexList.get(i).getX() - 6;
			int y = (int) vertexList.get(i).getY() - 6;
			FontMetrics fm = g.getFontMetrics();
            Rectangle2D rect = fm.getStringBounds(number, g);
            
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y - fm.getAscent(), (int) rect.getWidth() + 2, (int) rect.getHeight() + 2);
			
            g.setColor(Color.GREEN);
			g.drawString(number, x + 1, y + 1);
		}
	}

}
