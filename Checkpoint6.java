import java.io.*;
import java.util.*;
import java.sql.*;

public class Checkpoint6 {
	public static ArrayList<Character> charlist = new ArrayList<>();
	public static ArrayList<Land> landlist = new ArrayList<>();
	public static String filename = "./Character.txt";
	public static String land_file = "./Land.txt";
	public static boolean switch1 = true;
	public static boolean switch2 = false;
	public static boolean loadflag = true;

	static Gui window;

	static int count = 1;
	static int count_Round = 1;
	static int move = 0;

	static int distance = 50;

	public static void main(String[] args) throws IOException {
		//// TODO: Announce your Gui object to make the Gui ////
		//// TODO: This time we won't give you a function to realize our demands (Please
		//// look for demands on the document).
		//// TODO: So, you can make it in anyway whatever you like.

		window = new Gui();
		window.setVisible(true);
	}

	public static void Load(String filename) throws IOException {
		//// TODO: You should load the variables from the file. ////

		window.generateWindow();

		BufferedReader br = new BufferedReader(new FileReader(filename));
		String str = br.readLine();
		String[] count_countRound = str.split(":");
		String[] count_countRound2 = count_countRound[1].split(",");
		count = Integer.parseInt(count_countRound[2]);
		count_Round = Integer.parseInt(count_countRound2[0]);

		int location = 0;
		int CHARACTER_NUMBER = 0;
		int money = 0;
		int status = 0;
		int i = 0;

		while ((str = br.readLine()) != null) {
			String[] temp = str.split(",");

			location = Integer.parseInt(temp[0]);
			CHARACTER_NUMBER = Integer.parseInt(temp[1]);
			money = Integer.parseInt(temp[2]);
			status = Integer.parseInt(temp[3]);

			if (loadflag) {
				Character ch = new Character(location, CHARACTER_NUMBER, money, status, temp[4]);
				charlist.add(ch);
			} else {
				charlist.get(i).location = location;
				charlist.get(i).CHARACTER_NUMBER = CHARACTER_NUMBER;
				charlist.get(i).money = money;
				charlist.get(i).status = status;
				charlist.get(i).IMAGE_FILENAME = temp[4];
				i++;
			}
		}
		br.close();

		// Connect to MySQL database
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		try {
			// String username = "checkpoint";
			// String password = "ckppwd";
			// Connection conn = DriverManager.getConnection(
			// "jdbc:mysql://140.127.220.220:3306/CHECKPOINT?serverTimezone=UTC", username,
			// password);
			String username = "root";
			String password = "12345678";
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/CHECKPOINT", username, password);
			System.out.println("Connected successfully...");
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM LAND");
			System.out.println("Creating tables...");
			System.out.println("Complete!");

			int place_number = 0;
			int owner = 0;
			int land_price = 0;
			int tolls = 0;
			int count_null = 0;
			String[] temp;

			BufferedReader land_br = new BufferedReader(new FileReader(land_file));
			String land_str = land_br.readLine();
			while ((land_str = land_br.readLine()) != null) {
				temp = land_str.split(",");
				owner = Integer.parseInt(temp[1]);

				rs.next();
				place_number = rs.getInt("PLACE_NUMBER");
				land_price = rs.getInt("LAND_PRICE");
				tolls = rs.getInt("TOLLS");

				if (loadflag) {
					if (count_null == 0 || count_null == 4 || count_null == 8 || count_null == 12) {
						landlist.add(new Land(0, 0, 0, 0));
					}
					Land land = new Land(place_number, owner, land_price, tolls);
					landlist.add(land);
				} else {
					landlist.get(Integer.parseInt(temp[0])).PLACE_NUMBER = place_number;
					landlist.get(Integer.parseInt(temp[0])).owner = owner;
					landlist.get(Integer.parseInt(temp[0])).LAND_PRICE = land_price;
					landlist.get(Integer.parseInt(temp[0])).TOLLS = tolls;
				}
				count_null++;
			}
			conn.close();
			land_br.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		loadflag = false;

		for (int j = 0; j < charlist.size(); j++) {
			if (j == 3 && charlist.get(j).status == 0)
				count_Round++;
			if (charlist.get(j).status > 0) {
				count = j + 1;
				break;
			}
		}

		// Load character name and money
		if (charlist.size() >= 1) {
			window.char_name_1.setText("Character " + charlist.get(0).CHARACTER_NUMBER);
			window.char_money_1.setText(Integer.toString(charlist.get(0).money));

			if (charlist.size() >= 2) {
				window.char_name_2.setText("Character " + charlist.get(1).CHARACTER_NUMBER);
				window.char_money_2.setText(Integer.toString(charlist.get(1).money));
			}

			if (charlist.size() >= 3) {
				window.char_name_3.setText("Character " + charlist.get(2).CHARACTER_NUMBER);
				window.char_money_3.setText(Integer.toString(charlist.get(2).money));
			}

			if (charlist.size() >= 4) {
				window.char_name_4.setText("Character " + charlist.get(3).CHARACTER_NUMBER);
				window.char_money_4.setText(Integer.toString(charlist.get(3).money));
			}
		}

		window.character.setText("Turn   Character " + count);
		window.round.setText("Round " + count_Round);

		for (int j = 0; j < charlist.size(); j++) {
			if (charlist.get(j).location == 0) {
				if (j == 0) {
					Gui.char_pos[0] = 550;
					Gui.char_pos[1] = 580;
				} else if (j == 1) {
					Gui.char_pos[2] = 580;
					Gui.char_pos[3] = 580;
				} else if (j == 2) {
					Gui.char_pos[4] = 550;
					Gui.char_pos[5] = 630;
				} else if (j == 3) {
					Gui.char_pos[6] = 580;
					Gui.char_pos[7] = 630;
				}
			}
			if (charlist.get(j).location == 1) {
				if (j == 0) {
					Gui.char_pos[0] = 450;
					Gui.char_pos[1] = 580;
				} else if (j == 1) {
					Gui.char_pos[2] = 480;
					Gui.char_pos[3] = 580;
				} else if (j == 2) {
					Gui.char_pos[4] = 450;
					Gui.char_pos[5] = 630;
				} else if (j == 3) {
					Gui.char_pos[6] = 480;
					Gui.char_pos[7] = 630;
				}
			}
			if (charlist.get(j).location == 2) {
				if (j == 0) {
					Gui.char_pos[0] = 360;
					Gui.char_pos[1] = 580;
				} else if (j == 1) {
					Gui.char_pos[2] = 390;
					Gui.char_pos[3] = 580;
				} else if (j == 2) {
					Gui.char_pos[4] = 360;
					Gui.char_pos[5] = 630;
				} else if (j == 3) {
					Gui.char_pos[6] = 390;
					Gui.char_pos[7] = 630;
				}
			}
			if (charlist.get(j).location == 3) {
				if (j == 0) {
					Gui.char_pos[0] = 270;
					Gui.char_pos[1] = 580;
				} else if (j == 1) {
					Gui.char_pos[2] = 300;
					Gui.char_pos[3] = 580;
				} else if (j == 2) {
					Gui.char_pos[4] = 270;
					Gui.char_pos[5] = 630;
				} else if (j == 3) {
					Gui.char_pos[6] = 300;
					Gui.char_pos[7] = 630;
				}
			}
			if (charlist.get(j).location == 4) {
				if (j == 0) {
					Gui.char_pos[0] = 180;
					Gui.char_pos[1] = 580;
				} else if (j == 1) {
					Gui.char_pos[2] = 210;
					Gui.char_pos[3] = 580;
				} else if (j == 2) {
					Gui.char_pos[4] = 180;
					Gui.char_pos[5] = 630;
				} else if (j == 3) {
					Gui.char_pos[6] = 210;
					Gui.char_pos[7] = 630;
				}
			}
			if (charlist.get(j).location == 5) {
				if (j == 0) {
					Gui.char_pos[0] = 60;
					Gui.char_pos[1] = 580;
				} else if (j == 1) {
					Gui.char_pos[2] = 110;
					Gui.char_pos[3] = 580;
				} else if (j == 2) {
					Gui.char_pos[4] = 60;
					Gui.char_pos[5] = 630;
				} else if (j == 3) {
					Gui.char_pos[6] = 110;
					Gui.char_pos[7] = 630;
				}
			}
			if (charlist.get(j).location == 6) {
				if (j == 0) {
					Gui.char_pos[0] = 60;
					Gui.char_pos[1] = 470;
				} else if (j == 1) {
					Gui.char_pos[2] = 110;
					Gui.char_pos[3] = 470;
				} else if (j == 2) {
					Gui.char_pos[4] = 60;
					Gui.char_pos[5] = 500;
				} else if (j == 3) {
					Gui.char_pos[6] = 110;
					Gui.char_pos[7] = 500;
				}
			}
			if (charlist.get(j).location == 7) {
				if (j == 0) {
					Gui.char_pos[0] = 60;
					Gui.char_pos[1] = 380;
				} else if (j == 1) {
					Gui.char_pos[2] = 110;
					Gui.char_pos[3] = 380;
				} else if (j == 2) {
					Gui.char_pos[4] = 60;
					Gui.char_pos[5] = 410;
				} else if (j == 3) {
					Gui.char_pos[6] = 110;
					Gui.char_pos[7] = 410;
				}
			}
			if (charlist.get(j).location == 8) {
				if (j == 0) {
					Gui.char_pos[0] = 60;
					Gui.char_pos[1] = 290;
				} else if (j == 1) {
					Gui.char_pos[2] = 110;
					Gui.char_pos[3] = 290;
				} else if (j == 2) {
					Gui.char_pos[4] = 60;
					Gui.char_pos[5] = 320;
				} else if (j == 3) {
					Gui.char_pos[6] = 110;
					Gui.char_pos[7] = 320;
				}
			}
			if (charlist.get(j).location == 9) {
				if (j == 0) {
					Gui.char_pos[0] = 60;
					Gui.char_pos[1] = 200;
				} else if (j == 1) {
					Gui.char_pos[2] = 110;
					Gui.char_pos[3] = 200;
				} else if (j == 2) {
					Gui.char_pos[4] = 60;
					Gui.char_pos[5] = 230;
				} else if (j == 3) {
					Gui.char_pos[6] = 110;
					Gui.char_pos[7] = 230;
				}
			}
			if (charlist.get(j).location == 10) {
				if (j == 0) {
					Gui.char_pos[0] = 60;
					Gui.char_pos[1] = 90;
				} else if (j == 1) {
					Gui.char_pos[2] = 110;
					Gui.char_pos[3] = 90;
				} else if (j == 2) {
					Gui.char_pos[4] = 60;
					Gui.char_pos[5] = 130;
				} else if (j == 3) {
					Gui.char_pos[6] = 110;
					Gui.char_pos[7] = 130;
				}
			}
			if (charlist.get(j).location == 11) {
				if (j == 0) {
					Gui.char_pos[0] = 180;
					Gui.char_pos[1] = 90;
				} else if (j == 1) {
					Gui.char_pos[2] = 220;
					Gui.char_pos[3] = 90;
				} else if (j == 2) {
					Gui.char_pos[4] = 180;
					Gui.char_pos[5] = 130;
				} else if (j == 3) {
					Gui.char_pos[6] = 220;
					Gui.char_pos[7] = 130;
				}
			}
			if (charlist.get(j).location == 12) {
				if (j == 0) {
					Gui.char_pos[0] = 270;
					Gui.char_pos[1] = 90;
				} else if (j == 1) {
					Gui.char_pos[2] = 310;
					Gui.char_pos[3] = 90;
				} else if (j == 2) {
					Gui.char_pos[4] = 270;
					Gui.char_pos[5] = 130;
				} else if (j == 3) {
					Gui.char_pos[6] = 310;
					Gui.char_pos[7] = 130;
				}
			}
			if (charlist.get(j).location == 13) {
				if (j == 0) {
					Gui.char_pos[0] = 360;
					Gui.char_pos[1] = 90;
				} else if (j == 1) {
					Gui.char_pos[2] = 400;
					Gui.char_pos[3] = 90;
				} else if (j == 2) {
					Gui.char_pos[4] = 360;
					Gui.char_pos[5] = 130;
				} else if (j == 3) {
					Gui.char_pos[6] = 400;
					Gui.char_pos[7] = 130;
				}
			}
			if (charlist.get(j).location == 14) {
				if (j == 0) {
					Gui.char_pos[0] = 450;
					Gui.char_pos[1] = 90;
				} else if (j == 1) {
					Gui.char_pos[2] = 490;
					Gui.char_pos[3] = 90;
				} else if (j == 2) {
					Gui.char_pos[4] = 450;
					Gui.char_pos[5] = 130;
				} else if (j == 3) {
					Gui.char_pos[6] = 490;
					Gui.char_pos[7] = 130;
				}
			}
			if (charlist.get(j).location == 15) {
				if (j == 0) {
					Gui.char_pos[0] = 570;
					Gui.char_pos[1] = 90;
				} else if (j == 1) {
					Gui.char_pos[2] = 610;
					Gui.char_pos[3] = 90;
				} else if (j == 2) {
					Gui.char_pos[4] = 570;
					Gui.char_pos[5] = 130;
				} else if (j == 3) {
					Gui.char_pos[6] = 610;
					Gui.char_pos[7] = 130;
				}
			}
			if (charlist.get(j).location == 16) {
				if (j == 0) {
					Gui.char_pos[0] = 570;
					Gui.char_pos[1] = 210;
				} else if (j == 1) {
					Gui.char_pos[2] = 610;
					Gui.char_pos[3] = 210;
				} else if (j == 2) {
					Gui.char_pos[4] = 570;
					Gui.char_pos[5] = 250;
				} else if (j == 3) {
					Gui.char_pos[6] = 610;
					Gui.char_pos[7] = 250;
				}
			}
			if (charlist.get(j).location == 17) {
				if (j == 0) {
					Gui.char_pos[0] = 570;
					Gui.char_pos[1] = 290;
				} else if (j == 1) {
					Gui.char_pos[2] = 610;
					Gui.char_pos[3] = 290;
				} else if (j == 2) {
					Gui.char_pos[4] = 570;
					Gui.char_pos[5] = 330;
				} else if (j == 3) {
					Gui.char_pos[6] = 610;
					Gui.char_pos[7] = 330;
				}
			}
			if (charlist.get(j).location == 18) {
				if (j == 0) {
					Gui.char_pos[0] = 570;
					Gui.char_pos[1] = 380;
				} else if (j == 1) {
					Gui.char_pos[2] = 610;
					Gui.char_pos[3] = 380;
				} else if (j == 2) {
					Gui.char_pos[4] = 570;
					Gui.char_pos[5] = 420;
				} else if (j == 3) {
					Gui.char_pos[6] = 610;
					Gui.char_pos[7] = 420;
				}
			}
			if (charlist.get(j).location == 19) {
				if (j == 0) {
					Gui.char_pos[0] = 570;
					Gui.char_pos[1] = 470;
				} else if (j == 1) {
					Gui.char_pos[2] = 610;
					Gui.char_pos[3] = 470;
				} else if (j == 2) {
					Gui.char_pos[4] = 570;
					Gui.char_pos[5] = 510;
				} else if (j == 3) {
					Gui.char_pos[6] = 610;
					Gui.char_pos[7] = 510;
				}
			}
		}

		// Set owner and money on the map
		window.setMoney();
		window.setOwner();
		window.repaint();
	}

	public static void Save(String filename) throws IOException {
		//// TODO: You should save the changed variables into original data (filename).
		//// ////

		int i = 0;

		FileWriter writer = new FileWriter(new File(filename), false);
		writer.write("Round:" + count_Round + ",Turn:" + count + "\n");

		for (Character c : charlist) {
			writer.write(c.location + ",");
			writer.write(c.CHARACTER_NUMBER + ",");
			writer.write(c.money + ",");
			writer.write(c.status + ",");
			writer.write(c.IMAGE_FILENAME);
			i++;
			if (i != charlist.size())
				writer.write("\n");
		}
		writer.flush();

		int j = 0;
		writer = new FileWriter(new File(land_file), false);
		writer.write("LOCATION_NUMBER, owner\n");

		for (Land l : landlist) {
			if (j != 0 && j != 5 && j != 10 && j != 15) {
				writer.write(l.PLACE_NUMBER + ",");
				writer.write(Integer.toString(l.owner));
				if (j != landlist.size() - 1)
					writer.write("\n");
			}
			j++;
		}

		writer.flush();
		writer.close();

	}

	public static void Random() {
		//// TODO: while calling the Random function, Character.location should plus the
		//// random value, and Character.status should minus one.
		//// TODO: While Character.status more than zero(not include zero), Character
		//// can move(plus the random value).

		if (count <= 4 && charlist.size() == 4) {
			Gui.gui_count = count;
			Gui.gui_status = charlist.get(count - 1).status;

			move = (int) (Math.random() * 6) + 1;
			window.nothing.setText(Integer.toString(move));

			charlist.get(count - 1).location += move;
			if (charlist.get(count - 1).location >= 20) {
				Gui.temp_pos = charlist.get(count - 1).location;
				charlist.get(count - 1).location -= 20;
				charlist.get(count - 1).money += 2000;
				window.setMoney();
			}
			charlist.get(count - 1).status -= 1;

			if (count < 2 && charlist.get(count).status == 0 && charlist.get(count + 1).status == 0
					&& charlist.get(count + 2).status == 0) {
				count++;
				if (count < 3 && charlist.get(count).status == 0 && charlist.get(count + 1).status == 0) {
					count++;
					if (count < 4 && charlist.get(count).status == 0) {
						count++;
					}
				}
			} else if (count < 3 && charlist.get(count).status == 0 && charlist.get(count + 1).status == 0) {
				count++;
				if (count < 4 && charlist.get(count).status == 0) {
					count++;
				}
			} else if (count < 4 && charlist.get(count).status == 0) {
				count++;
			}
		}
		if (count <= 3 && charlist.size() == 3) {
			Gui.gui_count = count;
			Gui.gui_status = charlist.get(count - 1).status;

			move = (int) (Math.random() * 6) + 1;
			window.nothing.setText(Integer.toString(move));

			charlist.get(count - 1).location += move;
			if (charlist.get(count - 1).location >= 20) {
				Gui.temp_pos = charlist.get(count - 1).location;
				charlist.get(count - 1).location -= 20;
			}
			charlist.get(count - 1).status -= 1;

			if (count < 2 && charlist.get(count).status == 0 && charlist.get(count + 1).status == 0) {
				count++;
				if (count < 3 && charlist.get(count).status == 0) {
					count++;
				}
			} else if (count < 3 && charlist.get(count).status == 0) {
				count++;
			}
		}
		if (count <= 2 && charlist.size() == 2) {
			Gui.gui_count = count;
			Gui.gui_status = charlist.get(count - 1).status;

			move = (int) (Math.random() * 6) + 1;
			window.nothing.setText(Integer.toString(move));

			charlist.get(count - 1).location += move;
			if (charlist.get(count - 1).location >= 20) {
				Gui.temp_pos = charlist.get(count - 1).location;
				charlist.get(count - 1).location -= 20;
			}
			charlist.get(count - 1).status -= 1;

			if (count < 2 && charlist.get(count).status == 0) {
				count++;
			}
		}
		if (count <= 1 && charlist.size() == 1) {
			Gui.gui_count = count;
			Gui.gui_status = charlist.get(count - 1).status;

			move = (int) (Math.random() * 6) + 1;
			window.nothing.setText(Integer.toString(move));

			charlist.get(count - 1).location += move;
			if (charlist.get(count - 1).location >= 20) {
				Gui.temp_pos = charlist.get(count - 1).location;
				charlist.get(count - 1).location -= 20;
			}
			charlist.get(count - 1).status -= 1;

			if (count < 2 && charlist.get(count).status == 0) {
				count++;
			}
		}
		count++;

		if (charlist.size() == 4) {
			if (count > 4) {
				count = 1;
				Add_status();
				count_Round++;
			}
		}
		if (charlist.size() == 3) {
			if (count > 3) {
				count = 1;
				Add_status();
				count_Round++;
			}
		}
		if (charlist.size() == 2) {
			if (count > 2) {
				count = 1;
				Add_status();
				count_Round++;
			}
		}
	}

	static void Add_status() {
		for (Character c : charlist) {
			c.status++;
		}
	}

	static void createFile() {
		try {
			File file = new File("./", "Character.txt");
			file.createNewFile();
			System.out.println("File create successful...");
			File file2 = new File("./", "Land.txt");
			file2.createNewFile();

			FileWriter createCharacter = new FileWriter(new File("Character.txt"));
			createCharacter.write(
					"Round:1,Turn:1\n0,1,2000,1,Character_1.png\n0,2,2000,1,Character_2.png\n0,3,2000,1,Character_3.png\n0,4,2000,1,Character_4.png");
			createCharacter.flush();

			createCharacter = new FileWriter(new File("Land.txt"));
			createCharacter.write(
					"LOCATION_NUMBER, owner\n1,0\n2,0\n3,0\n4,0\n6,0\n7,0\n8,0\n9,0\n11,0\n12,0\n13,0\n14,0\n16,0\n17,0\n18,0\n19,0");
			createCharacter.flush();

			createCharacter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}