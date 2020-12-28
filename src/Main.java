
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import rbt.RedBlackTree;

public class Main extends JPanel {

	// Object from red black tree class
	private RedBlackTree tree = new RedBlackTree();
	// Object from the main window of the program
	private MainPanel mainTreePanel = new MainPanel(tree);

	public Main() {
		mainTreePanel.setBackground(new Color(255, 255, 255));
		initViews();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		JFrame mainFrame = new JFrame();
		try {
			mainFrame.setIconImage(ImageIO.read(Main.class.getResource("/res/rbt_icon.png")));//set icon for the window
		} catch (IOException e) {
			e.printStackTrace();
		}

		mainFrame.setTitle("Red Black Tree GUI - The Team 2020");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.add(new Main());// Start initializing the GUI
		mainFrame.pack();
		mainFrame.setVisible(true);// Calling GUI to the screen

	}

	private void initViews() {
		super.setLayout(new BorderLayout());
		setScrollPanel();
		setBottomPanel();
	}

	private void setScrollPanel() {
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

	// This function to initialize the button with icons and its places
	private void buttonsInit(JButton button, String imgSrc) {
		try {
			Image buttonIcon = ImageIO.read(getClass().getResource("/res/" + imgSrc + ".png"));
			button.setIcon(new ImageIcon(buttonIcon));
			button.setBorderPainted(false);
			button.setFocusPainted(false);
			button.setContentAreaFilled(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setBottomPanel() {

		// Initializing the views like button and text fields
		final JTextField numberTextEdit = new JTextField(15);
		final JButton insertNodeButton = new JButton();
		buttonsInit(insertNodeButton, "rbt_icon_insert");
		final JButton removeNodeButton = new JButton();
		buttonsInit(removeNodeButton, "rbt_icon_remove");
		final JButton clearNodeButton = new JButton();
		buttonsInit(clearNodeButton, "rbt_icon_clear");

		// Add views to the panel
		JPanel panel = new JPanel();
		panel.add(insertNodeButton);
		panel.add(numberTextEdit);
		panel.add(removeNodeButton);
		panel.add(clearNodeButton);
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.SOUTH);

		// The text field which user can enter the number
		numberTextEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				insertNodeButton.doClick();
			}

		});

		// The clear button action which calling clear function from tree
		clearNodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				tree.clear();
				mainTreePanel.repaint();
			}
		});

		// The insert button action which calling insert function from tree
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

		// The remove button action which calling remove function from tree
		removeNodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (numberTextEdit.getText().equals(""))
					return;

				Integer number = Integer.parseInt(numberTextEdit.getText());
				tree.removeNode(number);
				mainTreePanel.repaint();
				numberTextEdit.requestFocus();
				numberTextEdit.selectAll();
			}

		});

	}

}
