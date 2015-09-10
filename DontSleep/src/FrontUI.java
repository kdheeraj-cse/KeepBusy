import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.HashMap;

public class FrontUI extends Frame implements ActionListener, ItemListener, WindowListener {
	private static final long serialVersionUID = 1L;
	private static Thread backCounter = null, backlogic = null;
	static Integer applicationMode;
	static Integer timeInMinInteges;
	static Integer time;
	static Integer remaining;
	static HashMap<String, Integer> myhash = new HashMap<>();
	static HashMap<String, String> userValueHash = new HashMap<>();
	Frame about;
	
	
	void initializeHash() {
		myhash.put("Power Operation", 0);
		myhash.put("System Busy", 1);
		myhash.put("Shut down", 2);
		myhash.put("Restart", 3);
		myhash.put("Hibernate", 4);
		myhash.put("No Power Operation", 5);
	}

	static BackLogic objBackLogic = new BackLogic();
	static Label useModeLabel;
	static Label enterTimeLabel;
	static Label powerOptionLabel;
	static Choice useMode;
	static Choice timeInMinutes;
	static Choice powerOptionChoice;
	static Label titleLabel;
	static Button startButton;
	static Button stopButton;
	static Button exitButton;
	static Label emptyLabel;
	static Label finalConfirmation;
	static Label powerStatus;
	static Label thanksLabel;
	static Button aboutAppButton;
	public FrontUI() {
		this.setLayout(null);
	}

	void setupGUI() {
		initializeHash();
		useModeLabel = new Label();
		useModeLabel.setLocation(27, 121);
		useModeLabel.setSize(102, 35);
		useModeLabel.setBackground(new Color(-16737895));
		useModeLabel.setText("Mode of use");
		add(useModeLabel);

		enterTimeLabel = new Label();
		enterTimeLabel.setLocation(27, 179);
		enterTimeLabel.setSize(305, 35);
		enterTimeLabel.setBackground(new Color(-16737895));
		enterTimeLabel.setText("Time in Minutes before Power operation  ");
		add(enterTimeLabel);

		powerOptionLabel = new Label();
		powerOptionLabel.setLocation(27, 238);
		powerOptionLabel.setSize(283, 35);
		powerOptionLabel.setBackground(new Color(-16737895));
		powerOptionLabel.setText("Type of Power operation");
		// powerOptionLabel.setText("    Power operation after this Busy Period");
		add(powerOptionLabel);

		useMode = new Choice();
		useMode.add("Power Operation");
		useMode.add("System Busy");
		useMode.addItemListener(this);
		useMode.setLocation(400, 121);
		useMode.setSize(135, 35);
		add(useMode);

		timeInMinutes = new Choice();
		timeInMinutes.setLocation(400, 179);
		timeInMinutes.setSize(135, 35);
		timeInMinutes.add("1");
		for (Integer i = 5; i <= 240; i += 5) {
			timeInMinutes.add(i.toString());
		}
		add(timeInMinutes);

		powerOptionChoice = new Choice();
		powerOptionChoice.setLocation(400, 238);
		powerOptionChoice.setSize(135, 35);
		powerOptionChoice.add("Shut down");
		powerOptionChoice.add("Restart");
		powerOptionChoice.add("Hibernate");
		add(powerOptionChoice);

		titleLabel = new Label();
		titleLabel.setLocation(7, 53);
		titleLabel.setSize(532, 37);
		titleLabel.setFont(new Font("Calibri", 2, 16));

		// titleLabel.setBackground(new Color(-16724788));
		// titleLabel.setBackground(Color.);
		titleLabel.setText("     Hi " + System.getProperty("user.name") + ", "
				+ " By this App, You can keep your PC Busy");
		add(titleLabel);

		startButton = new Button();
		startButton.setLocation(125, 326);
		startButton.setSize(108, 44);
		startButton.setLabel("Click to Start");
		startButton.addActionListener(this);

		add(startButton);

		exitButton = new Button();
		exitButton.setLocation(280, 326);
		exitButton.setSize(108, 44);
		exitButton.setLabel("Exit App");
		exitButton.addActionListener(this);
		add(exitButton);

		emptyLabel = new Label();
		emptyLabel.setLocation(50, 1);
		emptyLabel.setSize(443, 53);
		emptyLabel.setBackground(new Color(-16737895));
		emptyLabel.setText("");
		add(emptyLabel);

		finalConfirmation = new Label();
		finalConfirmation.setLocation(48, 395);
		finalConfirmation.setSize(500, 34);
		finalConfirmation.setText("");
		add(finalConfirmation);

		powerStatus = new Label();
		powerStatus.setLocation(49, 433);
		powerStatus.setSize(500, 34);
		powerStatus.setText("");
		add(powerStatus);

		thanksLabel = new Label();
		thanksLabel.setLocation(350, 510);
		thanksLabel.setSize(192, 44);
		thanksLabel.setBackground(new Color(-16724941));
		thanksLabel.setText("     - Dheeraj Arya :)");
		add(thanksLabel);

		aboutAppButton = new Button();
		aboutAppButton.setLocation(10, 510);
		aboutAppButton.setSize(100, 50);
		aboutAppButton.setLabel("About");
		aboutAppButton.setBackground(Color.CYAN);
		aboutAppButton.addActionListener(this);
		add(aboutAppButton);

		
		
		setTitle("Keep Moving - Version 1.1");
		setSize(551, 560);
		setBackground(new Color(-16737895));
		setVisible(true);
		setResizable(false);
		setFont(new Font("Calibri", 4, 15));
		addWindowListener(this);

	}

	void grabUserInputValue() {
		userValueHash.put("useMode", useMode.getSelectedItem());
		userValueHash.put("timeInMinutes", timeInMinutes.getSelectedItem());
		userValueHash.put("powerOptionChoice",
				powerOptionChoice.getSelectedItem());
	}

	static void powerOptionAfterTime(int seconds, String options) throws IOException {
		Integer key = myhash.get(options);

		switch (key) {
		case 2:
			for (int i = 0; i < seconds; i++) {
				powerStatus.setText("Shutting down in " + (seconds - i)
						+ " seconds");
				objBackLogic.waitForInputTimeSecs(1);
			}
				objBackLogic.shutDown();
			break;
		case 3:
			for (int i = 0; i < seconds; i++) {
				powerStatus.setText("Restarting in " + (seconds - i)
						+ " seconds");
				objBackLogic.waitForInputTimeSecs(1);
			}
			objBackLogic.restart();
			break;
		case 4:
			for (int i = 0; i < seconds; i++) {
				powerStatus.setText("Doing Hibernate in " + (seconds - i)
						+ " seconds");
				objBackLogic.waitForInputTimeSecs(1);
			}
			objBackLogic.hibernate();
			break;
		case 5:
			powerStatus.setText("No power Options Selected");
			objBackLogic.waitForInputTimeSecs(1);
			break;
		}
	}

	
	void initializeBackLogicThread()
	{
		userValueHash.clear();
		grabUserInputValue();
		if(backlogic == null)
		{
			backlogic = new Thread() {
				public void run() {
					applicationMode = myhash.get(useMode.getSelectedItem());
					timeInMinInteges = Integer.parseInt(userValueHash
							.get("timeInMinutes"));
					switch (applicationMode) {
					case 0:
						finalConfirmation.setText("Application mode is "
								+ userValueHash.get("useMode"));
						objBackLogic.waitForInputTimeSecs(1);
						finalConfirmation.setText("Timer Set for "
								+ timeInMinInteges + " minutes");
						objBackLogic.waitForInputTimeSecs(1);
						finalConfirmation.setText("Starting in one second");
						objBackLogic.waitForInputTimeSecs(1);
						finalConfirmation.setText(" Waiting ");
						objBackLogic.waitForInputTimeSecs(1);
						initializeBackCounterThread();
						backCounter.start(); // starting back end thread for
												// remaining time display
						powerStatus.setText("Power option is : "
								+ userValueHash.get("powerOptionChoice"));

						objBackLogic.waitForInputTimeMins(timeInMinInteges);
						try {
							powerOptionAfterTime(20,
									userValueHash.get("powerOptionChoice"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							finalConfirmation.setText("Some error in " +userValueHash.get("powerOptionChoice"));
						}
						break;
					case 1:
						time = timeInMinInteges * 60;
						remaining = time;
						finalConfirmation.setText("Application mode is "
								+ userValueHash.get("useMode"));
						objBackLogic.waitForInputTimeSecs(1);
						finalConfirmation.setText("Timer Set for "
								+ timeInMinInteges + " minutes");
						objBackLogic.waitForInputTimeSecs(1);
						finalConfirmation.setText("Starting in one second");
						objBackLogic.waitForInputTimeSecs(1);
						finalConfirmation.setText(" Running ");
						powerStatus.setText("Power option is : "
								+ userValueHash.get("powerOptionChoice"));
						while (time != 0) {
							objBackLogic.mouseMotion(0, 0);
							objBackLogic.waitForInputTimeSecs(2);
							objBackLogic.mouseMotion(0, 500);
							objBackLogic.waitForInputTimeSecs(2);
							objBackLogic.mouseMotion(500, 500);
							objBackLogic.waitForInputTimeSecs(2);
							objBackLogic.mouseMotion(500, 0);
							objBackLogic.waitForInputTimeSecs(2);
							time = time - 8;
							finalConfirmation.setText(((remaining - time) / 60)
									+ " Minutes Passed");
						}
						try {
							powerOptionAfterTime(20,
									userValueHash.get("powerOptionChoice"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							finalConfirmation.setText("Some error in "+userValueHash.get("powerOptionChoice"));
						}
						break;
					default:
						break;
					}
				}
			
			
			};
		}
		
	}
	
	void initializeBackCounterThread()
	{

		if(backCounter == null)
		{
			backCounter = new Thread() {
				public void run() {

					// back counter
					timeInMinInteges = Integer.parseInt(userValueHash
							.get("timeInMinutes"));
					time = timeInMinInteges * 60;
					remaining = time;
					finalConfirmation.setText(((remaining - time) / 60)
							+ " Minutes Passed");
					while (time != 0) {
						objBackLogic.waitForInputTimeSecs(60);
						time = time - 60;
						finalConfirmation.setText(((remaining - time) / 60)
								+ " Minutes Passed");
					}
				}
			};
		}
	}
	
	
	
	
	public static void main(String args[]) {
		FrontUI mainFrame = new FrontUI();
		mainFrame.setupGUI();
		mainFrame.grabUserInputValue();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Click to Start")) {
			initializeBackLogicThread();
			backlogic.start();
			startButton.setLabel("Stop App");
		} else if (e.getActionCommand().equals("Stop App")) {
			backlogic.stop();
			backlogic=null;
			backCounter=null;
			finalConfirmation.setText("");
			powerStatus.setText("");
			startButton.setLabel("Click to Start");
		} else if (e.getActionCommand().equals("Exit App")) {
			System.exit(0);
		}
		else if (e.getActionCommand().equals("About"))
		{
			aboutAppButton.disable();
			about = new Frame();
			
			about.setLayout(null);
			Button closebuttoninfo;
			Label header;
			Label footer;
			Label mainline;
			Label thanks;
			Label []features = new Label[4];
			
			
			
			
			closebuttoninfo = new Button();
			closebuttoninfo.setLocation(10, 400);
			closebuttoninfo.setSize(100, 50);
			closebuttoninfo.setLabel("Close");
			closebuttoninfo.setBackground(Color.CYAN);
			closebuttoninfo.addActionListener(this);
			about.add(closebuttoninfo);
			
			header = new Label();
			header.setLocation(7, 53);
			header.setSize(532, 30);
			header.setFont(new Font("Calibri", 2, 16));
			header.setText("  **************************WELCOME*******************************");
			header.setBackground(new Color(-16737895));
			about.add(header);
			
			
			
			mainline = new Label();
			mainline.setLocation(7, 83);
			mainline.setSize(532, 30);
			mainline.setFont(new Font("Calibri", 1, 16));
			mainline.setText("  This version is having the following features");
			mainline.setBackground(new Color(-16737895));
			about.add(mainline);
			
			thanks = new Label();
			thanks.setLocation(110, 350);
			thanks.setSize(532, 30);
			thanks.setFont(new Font("Calibri", 0, 16));
			thanks.setText("  Thanks for using the Application - Dheeraj Arya");
			thanks.setBackground(new Color(-16737895));
			about.add(thanks);
			
			int yloc = 123;
			for (int i = 0; i < 4; i++) {
				features[i]=new Label();
				features[i].setLocation(57, yloc);
				features[i].setSize(532, 30);
				features[i].setFont(new Font("Calibri", 4, 14));
				features[i].setBackground(new Color(-16737895));
				yloc += 40;
			}
			
			features[0].setText("1. Can wait for power options without moving the cursor.");
			features[1].setText("2. More number of power operations(Restart,Hibernate).");
			features[2].setText("3. Operation break and restart capabilities.");
			features[3].setText("4. Full control on UI during the operation.");

			
			
			footer = new Label();
			footer.setLocation(7, yloc);
			footer.setSize(532, 30);
			footer.setFont(new Font("Calibri", 2, 16));
			footer.setText("  ********************************************************************");
			footer.setBackground(new Color(-16737895));
			about.add(footer);
			
			
			about.add(features[0]);
			about.add(features[1]);
			about.add(features[2]);
			about.add(features[3]);
			about.pack();
			about.setTitle("About - Version 1.1");
			about.setSize(450, 450);
			about.setBackground(new Color(-16737895));
			about.setResizable(false);
			about.setFont(new Font("Calibri", 4, 15));
			
			about.setVisible(true);
			}
		else if (e.getActionCommand().equals("Close")) {
			about.dispose();
			aboutAppButton.enable();
		}
		}
	

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if (useMode.getSelectedItem().equals("Power Operation")) {
			powerOptionChoice.remove("No Power Operation");
			powerOptionLabel.setText("Type of Power operation");
			enterTimeLabel
					.setText("Select Time in Minutes before Power operation");
		} else if (useMode.getSelectedItem().equals("System Busy")) {
			powerOptionChoice.add("No Power Operation");
			powerOptionLabel.setText("Power operation after this Busy Period");
			enterTimeLabel.setText("Time in Minutes to keep your System Busy");
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		dispose();
		System.exit(0);
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		dispose();
		System.exit(0);
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}