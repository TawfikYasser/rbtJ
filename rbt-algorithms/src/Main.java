
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import rbt.RedBlackTree;

public class Main extends JPanel {

	private static final long serialVersionUID = 1500L;
	private RedBlackTree tree = new RedBlackTree();
	private MainPanel mainTreePanel = new MainPanel(tree);

	public Main() {
		mainTreePanel.setBackground(new Color(255, 255, 255));
		initViews();
	}

	private void initViews() {
		super.setLayout(new BorderLayout());
		setScrollPane();
		setBottomPanel();
	}

	private void setScrollPane() {
		mainTreePanel.setPreferredSize(new Dimension(9000, 4096));
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(mainTreePanel);
		scroll.setPreferredSize(new Dimension(750, 500));
		setMidPoint(scroll);
		add(scroll, BorderLayout.CENTER);
	}

	private void setMidPoint(JScrollPane scrollPane) {
		scrollPane.getViewport().setViewPosition(new Point(4100, 0));

	}

	private void setupButton(JButton button, String imgSrc) {
		try {
			Image icon = ImageIO.read(getClass().getResource("/res/" + imgSrc + ".png"));
			button.setIcon(new ImageIcon(icon));
			button.setBorderPainted(false);
			button.setFocusPainted(false);
			button.setContentAreaFilled(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setBottomPanel() {
		final JTextField numberTextEdit = new JTextField(15);
		final JButton insertNodeButton = new JButton();
		setupButton(insertNodeButton, "rbt_icon_insert");
		final JButton removeNodeButton = new JButton();
		setupButton(removeNodeButton, "rbt_icon_remove");
		final JButton clearNodeButton = new JButton();
		setupButton(clearNodeButton, "rbt_icon_clear");

		JPanel panel = new JPanel();
		panel.add(insertNodeButton);
		panel.add(numberTextEdit);
		panel.add(removeNodeButton);
		panel.add(clearNodeButton);
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.SOUTH);

		insertNodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (numberTextEdit.getText().equals(""))
					return;
				Integer number = Integer.parseInt(numberTextEdit.getText());
				tree.insert(number);
				mainTreePanel.repaint();
				numberTextEdit.requestFocus();
				numberTextEdit.selectAll();

			}

		});

		removeNodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (numberTextEdit.getText().equals(""))
					return;

				Integer number = Integer.parseInt(numberTextEdit.getText());
				tree.deleteNode(number);
				mainTreePanel.repaint();
				numberTextEdit.requestFocus();
				numberTextEdit.selectAll();
			}

		});

		clearNodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				tree.clear();
				mainTreePanel.setSearch(0);
				mainTreePanel.repaint();
			}
		});

		numberTextEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				insertNodeButton.doClick();
			}

		});

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		RedBlackTree blackTree = new RedBlackTree();
//		blackTree.insert(5);
//		blackTree.insert(8);
//		blackTree.insert(6);
//		blackTree.insert(1);
//		blackTree.post();
//		blackTree.remove(8);
//		blackTree.post();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		JFrame j = new JFrame();
		j.setTitle("Red Black Tree GUI - The Team 2020");
		try {
			j.setIconImage(ImageIO.read(Main.class.getResource("/res/rbt_icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.add(new Main());
		j.pack();
		j.setVisible(true);

	}

}
