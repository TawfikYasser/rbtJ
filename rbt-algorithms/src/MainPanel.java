import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import rbt.Node;
import rbt.RedBlackTree;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1250L;

	private RedBlackTree tree;
	private int radius = 30;
	private int yOffset = 55;
	private Color textColor = new Color(230, 230, 230);
	private int toSearch;

	public MainPanel(RedBlackTree tree) {
		this.tree = tree;
	}

	public void setSearch(int sea) {
		toSearch = sea;
	}

	@Override
	protected void paintComponent(Graphics graphics) {

		super.paintComponent(graphics);

		if (tree.getRoot() != null) {
			Graphics2D graphics2d = (Graphics2D) graphics;
			graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			paintTree(graphics2d, (Node) tree.getRoot(), getWidth() / 2, 30, getGap());
		}

	}

	private void paintTree(Graphics2D g, Node root, int x, int y, int xOffset) {

		/* Update the view if unbound layout */
		if (x < 0)
			setPreferredSize(new Dimension(2 * getWidth(), getHeight()));

		// drawHalo(g, x, y);

		drawNode(g, root, x, y);

		if (root.getLeftChild() != null) {
			join(g, x - xOffset, y + yOffset, x, y);
			paintTree(g, (Node) root.getLeftChild(), x - xOffset, y + yOffset, xOffset / 2);
		}

		if (root.getRightChild() != null) {
			join(g, x + xOffset, y + yOffset, x, y);
			paintTree(g, (Node) root.getRightChild(), x + xOffset, y + yOffset, xOffset / 2);
		}
	}

	private void drawHalo(Graphics2D g, int x, int y) {
		g.setColor(new Color(160, 100, 250));
		radius += 5;
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
		radius -= 5;
	}

	private void drawNode(Graphics2D g, Node node, int x, int y) {
		if (node != null) {
			g.setColor(node.getColorCode());
			g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
			g.setColor(textColor);

			String text = node.toString();
			drawCentreText(g, text, x, y);
			g.setColor(Color.GRAY);
		}

	}

	private void drawCentreText(Graphics2D g, String text, int x, int y) {
		FontMetrics fm = g.getFontMetrics();
		double t_width = fm.getStringBounds(text, g).getWidth();
		g.drawString(text, (int) (x - t_width / 2), (int) (y + fm.getMaxAscent() / 2));
	}

	private void join(Graphics2D g, int x1, int y1, int x2, int y2) {
		double hypot = Math.hypot(yOffset, x2 - x1);
		int x11 = (int) (x1 + radius * (x2 - x1) / hypot);
		int y11 = (int) (y1 - radius * yOffset / hypot);
		int x21 = (int) (x2 - radius * (x2 - x1) / hypot);
		int y21 = (int) (y2 + radius * yOffset / hypot);
		g.drawLine(x11, y11, x21, y21);
	}

	private int getGap() {
		int depth = tree.getDepth();
		int multiplier = 30;
		float exponent = (float) 1.4;

		if (depth > 6) {
			multiplier += depth * 3;
			exponent += .1;
		}

		return (int) Math.pow(depth, exponent) * multiplier;
	}

}
