import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Gui extends JFrame implements ActionListener, Runnable {

	//// TODO: GUI ////
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	private String filename = "./Character.txt";

	JLabel char_name_1;
	JLabel char_name_2;
	JLabel char_name_3;
	JLabel char_name_4;

	JLabel char_money_1;
	JLabel char_money_2;
	JLabel char_money_3;
	JLabel char_money_4;

	JLabel nothing;
	JLabel character;
	JLabel round;

	boolean flag = true;
	static int count = 1;
	static int count_reset = 1;
	static int count_Round = 1;

	static JPanel map;
	static JPanel map_in;
	static Image image;
	JButton dice_button;

	static double[] char_pos = new double[8];
	static int count_pos = 0;
	static int count_move = 0;
	static int temp_pos = 0;
	static int gui_count = 0;
	static int gui_status = 0;

	static Graphics g;

	JLabel position_11;
	JLabel position_12;
	JLabel position_13;
	JLabel position_14;

	JLabel position_16;
	JLabel position_17;
	JLabel position_18;
	JLabel position_19;

	JLabel position_9;
	JLabel position_8;
	JLabel position_7;
	JLabel position_6;

	JLabel position_4;
	JLabel position_3;
	JLabel position_2;
	JLabel position_1;

	JButton startButton;
	JButton loadButton;
	JButton exitButton;

	// JFrame newFrame;

	public Gui() {
		setBounds(400, 200, 200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
		setResizable(false);

		startButton = new JButton("Start");
		startButton.setPreferredSize(new Dimension(150, 30));
		startButton.addActionListener(this);
		loadButton = new JButton("Load");
		loadButton.setPreferredSize(new Dimension(150, 30));
		loadButton.addActionListener(this);
		exitButton = new JButton("Exit");
		exitButton.setPreferredSize(new Dimension(150, 30));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		add(startButton);
		add(loadButton);
		add(exitButton);
	}

	public void generateWindow() {
		startButton.setVisible(false);
		loadButton.setVisible(false);
		exitButton.setVisible(false);

		setTitle("GUI");
		setSize(WIDTH, HEIGHT);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 0));
		setResizable(false);

		JPanel header = new JPanel();
		header.setPreferredSize(new Dimension(700, 50));
		header.setLayout(new FlowLayout(0, 30, 0));
		header.setBackground(Color.WHITE);

		JPanel header_left = new JPanel();
		header_left.setLayout(new GridLayout(1, 2, 40, 0));
		header_left.setBackground(Color.WHITE);

		JButton save = new JButton("Save");
		save.addActionListener(this);
		header_left.add(save);
		JButton load = new JButton("Load");
		load.setPreferredSize(new Dimension(100, 30));
		load.addActionListener(this);
		header_left.add(load);

		JPanel header_right = new JPanel();
		header_right.setLayout(new BorderLayout());
		header_right.setBackground(Color.WHITE);
		JPanel char_info = new JPanel();
		char_info.setLayout(new GridLayout(1, 4, 20, 0));
		char_info.setBackground(Color.WHITE);

		JPanel character1 = new JPanel();
		character1.setLayout(new GridLayout(2, 1));
		character1.setBackground(Color.WHITE);
		// Character1 name
		char_name_1 = new JLabel("Character ", SwingConstants.CENTER);
		char_name_1.setFont(new Font("Arial", Font.BOLD, 12));
		character1.add(char_name_1);
		// Character1 money
		char_money_1 = new JLabel("0", SwingConstants.CENTER);
		char_money_1.setFont(new Font("Arial", Font.PLAIN, 12));
		character1.add(char_money_1);

		JPanel character2 = new JPanel();
		character2.setLayout(new GridLayout(2, 1));
		character2.setBackground(Color.WHITE);
		// Character2 name
		char_name_2 = new JLabel("Character ", SwingConstants.CENTER);
		char_name_2.setFont(new Font("Arial", Font.BOLD, 12));
		character2.add(char_name_2);
		// Character2 money
		char_money_2 = new JLabel("0", SwingConstants.CENTER);
		char_money_2.setFont(new Font("Arial", Font.PLAIN, 12));
		character2.add(char_money_2);

		JPanel character3 = new JPanel();
		character3.setLayout(new GridLayout(2, 1));
		character3.setBackground(Color.WHITE);
		// Character3 name
		char_name_3 = new JLabel("Character ", SwingConstants.CENTER);
		char_name_3.setFont(new Font("Arial", Font.BOLD, 12));
		character3.add(char_name_3);
		// Character3 money
		char_money_3 = new JLabel("0", SwingConstants.CENTER);
		char_money_3.setFont(new Font("Arial", Font.PLAIN, 12));
		character3.add(char_money_3);

		JPanel character4 = new JPanel();
		character4.setLayout(new GridLayout(2, 1));
		character4.setBackground(Color.WHITE);
		// Character4 name
		char_name_4 = new JLabel("Character ", SwingConstants.CENTER);
		char_name_4.setFont(new Font("Arial", Font.BOLD, 12));
		character4.add(char_name_4);
		// Character4 money
		char_money_4 = new JLabel("0", SwingConstants.CENTER);
		char_money_4.setFont(new Font("Arial", Font.PLAIN, 12));
		character4.add(char_money_4);

		char_info.add(character1);
		char_info.add(character2);
		char_info.add(character3);
		char_info.add(character4);

		header_right.add(char_info);

		header.add(header_left);
		header.add(header_right);

		add(header, BorderLayout.NORTH);

		// Making map
		map = new JPanel();
		map.setLayout(new BorderLayout(32, 10));
		map.setBackground(Color.WHITE);
		JPanel outer_left = new JPanel();
		outer_left.setBackground(Color.WHITE);
		map.add(outer_left, BorderLayout.WEST);
		JPanel outer_right = new JPanel();
		outer_right.setBackground(Color.WHITE);
		map.add(outer_right, BorderLayout.EAST);

		map_in = new JPanel();
		map_in.setLayout(new BorderLayout(0, 0));
		map_in.setBackground(Color.WHITE);

		JPanel map_header = new JPanel();
		map_header.setLayout(new FlowLayout(6, 0, 0));
		map_header.setBackground(Color.WHITE);

		JLabel position_10 = new JLabel(new ImageIcon("./img/10.png"));
		position_11 = new JLabel(new ImageIcon("./img/11.png"));
		position_11.setHorizontalTextPosition(JLabel.CENTER);
		position_11.setFont(new Font("Arial", Font.BOLD, 40));
		position_12 = new JLabel(new ImageIcon("./img/12.png"));
		position_12.setHorizontalTextPosition(JLabel.CENTER);
		position_12.setFont(new Font("Arial", Font.BOLD, 40));
		position_13 = new JLabel(new ImageIcon("./img/13.png"));
		position_13.setHorizontalTextPosition(JLabel.CENTER);
		position_13.setFont(new Font("Arial", Font.BOLD, 40));
		position_14 = new JLabel(new ImageIcon("./img/14.png"));
		position_14.setHorizontalTextPosition(JLabel.CENTER);
		position_14.setFont(new Font("Arial", Font.BOLD, 40));
		JLabel position_15 = new JLabel(new ImageIcon("./img/15.png"));
		map_header.add(position_10);
		map_header.add(position_11);
		map_header.add(position_12);
		map_header.add(position_13);
		map_header.add(position_14);
		map_header.add(position_15);

		JPanel map_right = new JPanel();
		map_right.setLayout(new GridLayout(4, 1, 0, 1));
		map_right.setBackground(Color.WHITE);
		position_16 = new JLabel(new ImageIcon("./img/16.png"));
		position_16.setHorizontalTextPosition(JLabel.CENTER);
		position_16.setFont(new Font("Arial", Font.BOLD, 40));
		position_17 = new JLabel(new ImageIcon("./img/17.png"));
		position_17.setHorizontalTextPosition(JLabel.CENTER);
		position_17.setFont(new Font("Arial", Font.BOLD, 40));
		position_18 = new JLabel(new ImageIcon("./img/18.png"));
		position_18.setHorizontalTextPosition(JLabel.CENTER);
		position_18.setFont(new Font("Arial", Font.BOLD, 40));
		position_19 = new JLabel(new ImageIcon("./img/19.png"));
		position_19.setHorizontalTextPosition(JLabel.CENTER);
		position_19.setFont(new Font("Arial", Font.BOLD, 40));
		map_right.add(position_16);
		map_right.add(position_17);
		map_right.add(position_18);
		map_right.add(position_19);

		JPanel map_center = new JPanel();
		map_center.setLayout(new GridLayout(3, 1, 0, -35));
		map_center.setBackground(Color.WHITE);
		JLabel title_label = new JLabel(new ImageIcon("./img/title.png"), JLabel.LEFT);
		title_label.setPreferredSize(new Dimension(150, 20));
		map_center.add(title_label);

		JPanel center_info = new JPanel();
		center_info.setLayout(new GridLayout(1, 2));
		center_info.setBackground(Color.WHITE);
		dice_button = new JButton(new ImageIcon("./img/Dice.png"));
		dice_button.setBackground(Color.WHITE);
		dice_button.addActionListener(this);
		dice_button.setActionCommand("dice");
		dice_button.setBorderPainted(false);
		center_info.add(dice_button);
		JPanel move = new JPanel();
		move.setLayout(new BorderLayout(0, 10));
		move.setBackground(Color.WHITE);
		JLabel blank_1 = new JLabel(" ");
		move.add(blank_1, BorderLayout.SOUTH);
		nothing = new JLabel("0", new ImageIcon("./img/display_dicenum.png"), SwingConstants.CENTER);
		nothing.setHorizontalTextPosition(JLabel.CENTER);
		nothing.setBackground(Color.WHITE);
		nothing.setFont(new Font("Arial", Font.BOLD, 40));
		move.add(nothing, BorderLayout.CENTER);
		center_info.add(move);
		map_center.add(center_info);

		JPanel center_bottom = new JPanel();
		center_bottom.setLayout(new GridLayout(2, 2, 0, -40));
		center_bottom.setBackground(Color.WHITE);
		JLabel blank_2 = new JLabel("");
		center_bottom.add(blank_2);
		round = new JLabel("Round 1", SwingConstants.CENTER);
		round.setFont(new Font("Arial", Font.BOLD, 20));
		center_bottom.add(round);
		JLabel blank_3 = new JLabel("");
		center_bottom.add(blank_3);
		character = new JLabel("Turn   Character 1", SwingConstants.LEFT);
		character.setFont(new Font("Arial", Font.BOLD, 16));
		center_bottom.add(character);
		map_center.add(center_bottom);

		JPanel map_left = new JPanel();
		map_left.setLayout(new GridLayout(4, 1, 0, 1));
		map_left.setBackground(Color.WHITE);
		position_9 = new JLabel(new ImageIcon("./img/9.png"));
		position_9.setHorizontalTextPosition(JLabel.CENTER);
		position_9.setFont(new Font("Arial", Font.BOLD, 40));
		position_8 = new JLabel(new ImageIcon("./img/8.png"));
		position_8.setHorizontalTextPosition(JLabel.CENTER);
		position_8.setFont(new Font("Arial", Font.BOLD, 40));
		position_7 = new JLabel(new ImageIcon("./img/7.png"));
		position_7.setHorizontalTextPosition(JLabel.CENTER);
		position_7.setFont(new Font("Arial", Font.BOLD, 40));
		position_6 = new JLabel(new ImageIcon("./img/6.png"));
		position_6.setHorizontalTextPosition(JLabel.CENTER);
		position_6.setFont(new Font("Arial", Font.BOLD, 40));
		map_left.add(position_9);
		map_left.add(position_8);
		map_left.add(position_7);
		map_left.add(position_6);

		JPanel map_footer = new JPanel();
		map_footer.setLayout(new FlowLayout(6, 0, 0));
		map_footer.setBackground(Color.WHITE);
		JLabel position_5 = new JLabel(new ImageIcon("./img/5.png"));
		position_4 = new JLabel(new ImageIcon("./img/4.png"));
		position_4.setHorizontalTextPosition(JLabel.CENTER);
		position_4.setFont(new Font("Arial", Font.BOLD, 40));
		position_3 = new JLabel(new ImageIcon("./img/3.png"));
		position_3.setHorizontalTextPosition(JLabel.CENTER);
		position_3.setFont(new Font("Arial", Font.BOLD, 40));
		position_2 = new JLabel(new ImageIcon("./img/2.png"));
		position_2.setHorizontalTextPosition(JLabel.CENTER);
		position_2.setFont(new Font("Arial", Font.BOLD, 40));
		position_1 = new JLabel(new ImageIcon("./img/1.png"));
		position_1.setHorizontalTextPosition(JLabel.CENTER);
		position_1.setFont(new Font("Arial", Font.BOLD, 40));
		position_1.setText(" ");
		JLabel position_0 = new JLabel(new ImageIcon("./img/0.png"));
		map_footer.add(position_5);
		map_footer.add(position_4);
		map_footer.add(position_3);
		map_footer.add(position_2);
		map_footer.add(position_1);
		map_footer.add(position_0);

		map_in.add(map_header, BorderLayout.NORTH);
		map_in.add(map_right, BorderLayout.EAST);
		map_in.add(map_left, BorderLayout.WEST);
		map_in.add(map_footer, BorderLayout.SOUTH);
		map_in.add(map_center, BorderLayout.CENTER);

		map.add(map_in, BorderLayout.CENTER);
		add(map, BorderLayout.CENTER);

		// footer
		JPanel footer = new JPanel();
		BorderLayout footer_layout = new BorderLayout();
		footer_layout.setHgap(100);
		footer_layout.setVgap(100);
		footer.setLayout(footer_layout);
		footer.setBackground(Color.WHITE);
		JButton exit = new JButton("Exit");
		exit.setPreferredSize(new Dimension(100, 30));
		exit.setMargin(new Insets(5, 10, 5, 10));
		exit.addActionListener(this);
		footer.add(exit, BorderLayout.EAST);
		add(footer, BorderLayout.SOUTH);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();

		try {
			if (s.equals("Start")) {
				dispose();
				Checkpoint6.createFile();
				Checkpoint6.Load(filename);
			} else if (s.equals("Load")) {
				dispose();

				File charfile = new File("./", "Character.txt");
				File landfile = new File("./", "Land.txt");
				boolean charfile_exists = charfile.createNewFile();
				boolean land_exists = landfile.createNewFile();

				if (charfile_exists && land_exists) {
					JFrame confirmFrame = new JFrame();
					confirmFrame.setSize(150, 100);
					confirmFrame.setResizable(false);
					confirmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					JOptionPane.showMessageDialog(confirmFrame, "Without record", "Error message",
							JOptionPane.PLAIN_MESSAGE);
					charfile.delete();
					landfile.delete();
					System.exit(0);
				} else {
					Checkpoint6.Load(filename);
				}
			} else if (s.equals("dice")) {
				Checkpoint6.Random();
				this.startThread();
			} else if (s.equals("Save")) {
				Checkpoint6.Save(filename);
			} else if (s.equals("Exit")) {
				System.exit(0);
			}
		} catch (Exception exception) {
			// TODO: handle exception
			System.out.println(exception);
		}
	}

	public void startThread() {
		Thread t1 = new Thread(this);
		t1.start();
	}

	public void run() {
		dice_button.setEnabled(false);
		double x_1_3_move = 92;
		double x_2_4_move = 98;
		double y_1_2_move = 92;
		double y_3_4_move = 100;

		for (int i = 0; i < Checkpoint6.move * 50; i++) {
			if (gui_count == 1 && gui_status != 0) {
				if (char_pos[0] > 90 && char_pos[1] == 580) {
					char_pos[0] -= x_1_3_move / 50;
					repaint();
				} else if (char_pos[1] > 120 && char_pos[0] == 90) {
					char_pos[1] -= y_1_2_move / 50;
					repaint();
				} else if (char_pos[0] < 550 && char_pos[1] == 120) {
					char_pos[0] += x_1_3_move / 50;
					repaint();
				} else if (char_pos[1] < 580 && char_pos[0] == 550) {
					char_pos[1] += y_1_2_move / 50;
					repaint();
				}

				if (char_pos[0] <= 90) {
					char_pos[0] = 90;
					repaint();
				}
				if (char_pos[1] <= 120) {
					char_pos[1] = 120;
					repaint();
				}
				if (char_pos[0] >= 550) {
					char_pos[0] = 550;
					repaint();
				}
				if (char_pos[1] >= 580) {
					char_pos[1] = 580;
					repaint();
				}
			}
			if (gui_count == 2 && gui_status != 0) {
				if (char_pos[2] > 90 && char_pos[3] == 580) {
					char_pos[2] -= x_2_4_move / 50;
					repaint();
				} else if (char_pos[3] > 120 && char_pos[2] == 90) {
					char_pos[3] -= y_1_2_move / 50;
					repaint();
				} else if (char_pos[2] < 580 && char_pos[3] == 120) {
					char_pos[2] += x_2_4_move / 50;
					repaint();
				} else if (char_pos[3] < 580 && char_pos[2] == 580) {
					char_pos[3] += y_1_2_move / 50;
					repaint();
				}

				if (char_pos[2] <= 90) {
					char_pos[2] = 90;
					repaint();
				}
				if (char_pos[3] <= 120) {
					char_pos[3] = 120;
					repaint();
				}
				if (char_pos[2] >= 580) {
					char_pos[2] = 580;
					repaint();
				}
				if (char_pos[3] >= 580) {
					char_pos[3] = 580;
					repaint();
				}
			}
			if (gui_count == 3 && gui_status != 0) {
				if (char_pos[4] > 90 && char_pos[5] == 630) {
					char_pos[4] -= (x_1_3_move + 1.5) / 50;
					repaint();
				} else if (char_pos[5] > 120 && char_pos[4] == 90) {
					char_pos[5] -= (y_3_4_move + 1.5) / 50;
					repaint();
				} else if (char_pos[4] < 550 && char_pos[5] == 120) {
					char_pos[4] += x_1_3_move / 50;
					repaint();
				} else if (char_pos[5] < 630 && char_pos[4] == 550) {
					char_pos[5] += y_3_4_move / 50;
					repaint();
				}

				if (char_pos[4] <= 90) {
					char_pos[4] = 90;
					repaint();
				}
				if (char_pos[5] <= 120) {
					char_pos[5] = 120;
					repaint();
				}
				if (char_pos[4] >= 550) {
					char_pos[4] = 550;
					repaint();
				}
				if (char_pos[5] >= 630) {
					char_pos[5] = 630;
					repaint();
				}
			}
			if (gui_count == 4 && gui_status != 0) {
				if (char_pos[6] > 90 && char_pos[7] == 630) {
					char_pos[6] -= (x_2_4_move + 1.5) / 50;
					repaint();
				} else if (char_pos[7] > 120 && char_pos[6] == 90) {
					char_pos[7] -= (y_3_4_move + 1.5) / 50;
					repaint();
				} else if (char_pos[6] < 580 && char_pos[7] == 120) {
					char_pos[6] += x_2_4_move / 50;
					repaint();
				} else if (char_pos[7] < 630 && char_pos[6] == 580) {
					char_pos[7] += y_3_4_move / 50;
					repaint();
				}

				if (char_pos[6] <= 90) {
					char_pos[6] = 90;
					repaint();
				}
				if (char_pos[7] <= 120) {
					char_pos[7] = 120;
					repaint();
				}
				if (char_pos[6] >= 580) {
					char_pos[6] = 580;
					repaint();
				}
				if (char_pos[7] >= 630) {
					char_pos[7] = 630;
					repaint();
				}
			} else if (gui_count == 4 && Checkpoint6.charlist.size() == 3 && gui_status != 0) {
				if (char_pos[4] > 90 && char_pos[5] == 630) {
					char_pos[4] -= x_1_3_move / 50;
					repaint();
				} else if (char_pos[5] > 120 && char_pos[4] == 90) {
					char_pos[5] -= y_3_4_move / 50;
					repaint();
				} else if (char_pos[4] < 550 && char_pos[5] == 120) {
					char_pos[4] += x_1_3_move / 50;
					repaint();
				} else if (char_pos[5] < 630 && char_pos[4] == 550) {
					char_pos[5] += y_3_4_move / 50;
					repaint();
				}

				if (char_pos[4] <= 90) {
					char_pos[4] = 90;
					repaint();
				}
				if (char_pos[5] <= 120) {
					char_pos[5] = 120;
					repaint();
				}
				if (char_pos[4] >= 550) {
					char_pos[4] = 550;
					repaint();
				}
				if (char_pos[5] >= 630) {
					char_pos[5] = 630;
					repaint();
				}
			} else if (gui_count == 4 && Checkpoint6.charlist.size() == 2 && gui_status != 0) {
				if (char_pos[2] > 90 && char_pos[3] == 580) {
					char_pos[2] -= x_2_4_move / 50;
					repaint();
				} else if (char_pos[3] > 120 && char_pos[2] == 90) {
					char_pos[3] -= y_1_2_move / 50;
					repaint();
				} else if (char_pos[2] < 580 && char_pos[3] == 120) {
					char_pos[2] += x_2_4_move / 50;
					repaint();
				} else if (char_pos[3] < 580 && char_pos[2] == 580) {
					char_pos[3] += y_1_2_move / 50;
					repaint();
				}

				if (char_pos[2] <= 90) {
					char_pos[2] = 90;
					repaint();
				}
				if (char_pos[3] <= 120) {
					char_pos[3] = 120;
					repaint();
				}
				if (char_pos[2] >= 580) {
					char_pos[2] = 580;
					repaint();
				}
				if (char_pos[3] >= 580) {
					char_pos[3] = 580;
					repaint();
				}
			}

			try {
				if (Checkpoint6.move == 1)
					Thread.sleep(40);
				else if (Checkpoint6.move == 2)
					Thread.sleep(20);
				else if (Checkpoint6.move == 3)
					Thread.sleep(13);
				else if (Checkpoint6.move == 4)
					Thread.sleep(15);
				else if (Checkpoint6.move == 5)
					Thread.sleep(12);
				else if (Checkpoint6.move == 6)
					Thread.sleep(10);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		// Handle land events
		buyLand();

		// Reset character number and round number
		Checkpoint6.window.character.setText("Turn   Character " + Integer.toString(Checkpoint6.count));
		round.setText("Round " + Checkpoint6.count_Round);

		// Let dice button accessible again
		this.dice_button.setEnabled(true);
	}

	public void setOwner() {
		int temp_location = 0;

		for (Land l : Checkpoint6.landlist) {
			temp_location = l.PLACE_NUMBER;

			if (l.owner != 0) {
				if (temp_location == 1) {
					position_1.setText(Integer.toString(l.owner));
				} else if (temp_location == 2) {
					position_2.setText(Integer.toString(l.owner));
				} else if (temp_location == 3) {
					position_3.setText(Integer.toString(l.owner));
				} else if (temp_location == 4) {
					position_4.setText(Integer.toString(l.owner));
				} else if (temp_location == 6) {
					position_6.setText(Integer.toString(l.owner));
				} else if (temp_location == 7) {
					position_7.setText(Integer.toString(l.owner));
				} else if (temp_location == 8) {
					position_8.setText(Integer.toString(l.owner));
				} else if (temp_location == 9) {
					position_9.setText(Integer.toString(l.owner));
				} else if (temp_location == 11) {
					position_11.setText(Integer.toString(l.owner));
				} else if (temp_location == 12) {
					position_12.setText(Integer.toString(l.owner));
				} else if (temp_location == 13) {
					position_13.setText(Integer.toString(l.owner));
				} else if (temp_location == 14) {
					position_14.setText(Integer.toString(l.owner));
				} else if (temp_location == 16) {
					position_16.setText(Integer.toString(l.owner));
				} else if (temp_location == 17) {
					position_17.setText(Integer.toString(l.owner));
				} else if (temp_location == 18) {
					position_18.setText(Integer.toString(l.owner));
				} else if (temp_location == 19) {
					position_19.setText(Integer.toString(l.owner));
				}
			}

			if (l.owner == 0) {
				if (temp_location == 1) {
					position_1.setText(" ");
				} else if (temp_location == 2) {
					position_2.setText(" ");
				} else if (temp_location == 3) {
					position_3.setText(" ");
				} else if (temp_location == 4) {
					position_4.setText(" ");
				} else if (temp_location == 6) {
					position_6.setText(" ");
				} else if (temp_location == 7) {
					position_7.setText(" ");
				} else if (temp_location == 8) {
					position_8.setText(" ");
				} else if (temp_location == 9) {
					position_9.setText(" ");
				} else if (temp_location == 11) {
					position_11.setText(" ");
				} else if (temp_location == 12) {
					position_12.setText(" ");
				} else if (temp_location == 13) {
					position_13.setText(" ");
				} else if (temp_location == 14) {
					position_14.setText(" ");
				} else if (temp_location == 16) {
					position_16.setText(" ");
				} else if (temp_location == 17) {
					position_17.setText(" ");
				} else if (temp_location == 18) {
					position_18.setText(" ");
				} else if (temp_location == 19) {
					position_19.setText(" ");
				}
			}
			repaint();
		}

	}

	public void setMoney() {
		int temp_charnum = 0;

		for (Character c : Checkpoint6.charlist) {
			temp_charnum = c.CHARACTER_NUMBER;
			if (temp_charnum == 1)
				char_money_1.setText(Integer.toString(c.money));
			else if (temp_charnum == 2)
				char_money_2.setText(Integer.toString(c.money));
			else if (temp_charnum == 3)
				char_money_3.setText(Integer.toString(c.money));
			else if (temp_charnum == 4)
				char_money_4.setText(Integer.toString(c.money));
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		BufferedImage bi;
		g = getGraphics();

		try {
			if (Checkpoint6.charlist.size() == 4) {
				bi = ImageIO.read(new File("./img/Character_1.png"));
				g.drawImage(bi, (int) char_pos[0], (int) char_pos[1], this);
				bi = ImageIO.read(new File("./img/Character_2.png"));
				g.drawImage(bi, (int) char_pos[2], (int) char_pos[3], this);
				bi = ImageIO.read(new File("./img/Character_3.png"));
				g.drawImage(bi, (int) char_pos[4], (int) char_pos[5], this);
				bi = ImageIO.read(new File("./img/Character_4.png"));
				g.drawImage(bi, (int) char_pos[6], (int) char_pos[7], this);
			} else if (Checkpoint6.charlist.size() == 3) {
				bi = ImageIO.read(new File("./img/Character_1.png"));
				g.drawImage(bi, (int) char_pos[0], (int) char_pos[1], this);
				bi = ImageIO.read(new File("./img/Character_2.png"));
				g.drawImage(bi, (int) char_pos[2], (int) char_pos[3], this);
				bi = ImageIO.read(new File("./img/Character_3.png"));
				g.drawImage(bi, (int) char_pos[4], (int) char_pos[5], this);
			} else if (Checkpoint6.charlist.size() == 2) {
				bi = ImageIO.read(new File("./img/Character_1.png"));
				g.drawImage(bi, (int) char_pos[0], (int) char_pos[1], this);
				bi = ImageIO.read(new File("./img/Character_2.png"));
				g.drawImage(bi, (int) char_pos[2], (int) char_pos[3], this);
			} else if (Checkpoint6.charlist.size() == 1) {
				bi = ImageIO.read(new File("./img/Character_1.png"));
				g.drawImage(bi, (int) char_pos[0], (int) char_pos[1], this);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void buyLand() {
		JFrame confirmFrame = new JFrame();
		confirmFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		int position = 0;
		int input = 0;

		if (Checkpoint6.count != 1)
			position = Checkpoint6.charlist.get(Checkpoint6.count - 2).location;
		if (Checkpoint6.count == 1)
			position = Checkpoint6.charlist.get(Checkpoint6.charlist.size() - 1).location;

		if (Checkpoint6.count != 1 && position != 5 && position != 10 && position != 15 && position != 0) {
			if (Checkpoint6.landlist.get(position).owner == 0 && (Checkpoint6.charlist
					.get(Checkpoint6.count - 2).money > Checkpoint6.landlist.get(position).LAND_PRICE)) {
				input = JOptionPane.showConfirmDialog(confirmFrame,
						"Do you want to buy land " + position + "?\n$" + Checkpoint6.landlist.get(position).LAND_PRICE,
						"Buy??", JOptionPane.YES_NO_OPTION);
				if (input == JOptionPane.YES_OPTION) {
					Checkpoint6.landlist.get(position).owner = Checkpoint6.count - 1;
					Checkpoint6.charlist.get(Checkpoint6.count - 2).money -= Checkpoint6.landlist
							.get(position).LAND_PRICE;
				}
			}
			if (Checkpoint6.landlist.get(position).owner != 0
					&& Checkpoint6.landlist.get(position).owner != (Checkpoint6.count - 1)) {
				JOptionPane.showMessageDialog(confirmFrame,
						"The owner of this land is Character" + Checkpoint6.landlist.get(position).owner + ", Character"
								+ (Checkpoint6.count - 1) + " have to pay $" + Checkpoint6.landlist.get(position).TOLLS,
						"Panel!!", JOptionPane.PLAIN_MESSAGE);

				int tolls = 0;
				tolls = Checkpoint6.landlist.get(position).TOLLS;
				Checkpoint6.charlist.get(Checkpoint6.count - 2).money -= tolls;
				Checkpoint6.charlist.get(Checkpoint6.landlist.get(position).owner - 1).money += tolls;
			}

			this.setOwner();
			this.setMoney();
		}

		if (Checkpoint6.count == 1 && position != 5 && position != 10 && position != 15 && position != 0) {
			if (Checkpoint6.landlist.get(position).owner == 0
					&& (Checkpoint6.charlist.get(3).money > Checkpoint6.landlist.get(position).LAND_PRICE)) {
				input = JOptionPane.showConfirmDialog(confirmFrame,
						"Do you want to buy land " + position + "?\n$" + Checkpoint6.landlist.get(position).LAND_PRICE,
						"Buy??", JOptionPane.YES_NO_OPTION);

				if (input == JOptionPane.YES_OPTION) {
					Checkpoint6.landlist.get(position).owner = (Checkpoint6.charlist.size());
					Checkpoint6.charlist.get(Checkpoint6.charlist.size() - 1).money -= Checkpoint6.landlist
							.get(position).LAND_PRICE;
				}
			}
			if (Checkpoint6.landlist.get(position).owner != 0) {
				if (Checkpoint6.landlist.get(position).owner != (Checkpoint6.charlist.size())) {
					JOptionPane.showMessageDialog(confirmFrame,
							"The owner of this land is Character" + Checkpoint6.landlist.get(position).owner
									+ ", Character4 have to pay $" + Checkpoint6.landlist.get(position).TOLLS,
							"Panel!!", JOptionPane.OK_OPTION);

					int tolls = 0;
					tolls = Checkpoint6.landlist.get(position).TOLLS;
					Checkpoint6.charlist.get(Checkpoint6.charlist.size() - 1).money -= tolls;
					Checkpoint6.charlist.get(Checkpoint6.landlist.get(position).owner - 1).money += tolls;
				}
			}

			this.setOwner();
			this.setMoney();
		}

	}
}