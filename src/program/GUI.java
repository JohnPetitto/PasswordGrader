package program;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/*
 * This class is entirely responsible for the GUI. It makes a call
 * to the Score class each time the user enters or deletes a character
 * in the password box. It also makes a call to the GeneratePassword
 * class when the user clicks on the Generate Random Password button.
 */

public class GUI extends JFrame implements ActionListener, DocumentListener {
	private static final long serialVersionUID = 1L; // make compiler happy
	private static final char ECHO_OFF = '\u0000'; // turns off password masking
	private static final int MAX_CHARS = 50;
	
	private JPasswordField passwordField; // user enters password into passwordField
	private JCheckBox hidePassword; // user clicks hidePassword to hide/show password
	private char passwordDot; // turns on password masking
	private JLabel charsRemaining; // notifies user of character limit for passwordField
	private JSlider passwordMeter; // displays password strength graphically
	private JLabel passwordStrength; // displays password strength with text category
	private JButton generatePassword; // randomly generates a password with 100% strength
	
	// the main window (frame) that holds all of the components of the GUI
	public GUI() {
		// initialize basic frame properties
		super("Password Grader");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new GridBagLayout());
		
		// set window icon
		URL iconURL = GUI.class.getResource("images/icon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());
		
		// position logo header at top of window
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		
		// create and add logo header to window
		URL logoURL = GUI.class.getResource("images/logo.png");
		ImageIcon logoIcon = new ImageIcon(logoURL);
		JLabel logo = new JLabel(logoIcon);
		this.add(logo, c);
		
		// position passwordField below logo header and to the left
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 65, 0, 0);
		
		// create and add passwordField to window
		passwordField = new JPasswordField(15);
		passwordDot = passwordField.getEchoChar(); // save echo char to enable/disable password masking
		passwordField.getDocument().addDocumentListener(this); // responds to input after every character
		PasswordFieldFilter filter = new PasswordFieldFilter(); // filter out spaces and impose character limit in passwordField
		((AbstractDocument) passwordField.getDocument()).setDocumentFilter(filter);
		this.add(passwordField, c);
		
		// position hidePassword check box to the right of passwordField
		c.insets = new Insets(10, 0, 0, 0);
		c.gridx = 1;
		
		// create and add hidePassword to window
		hidePassword = new JCheckBox();
		hidePassword.setSelected(true);
		hidePassword.addActionListener(this);
		this.add(hidePassword, c);
		
		// position hidePasswordText to the right of hidePassword
		c.gridx = 2;
		
		// create and add hidePasswordText
		JLabel hidePasswordText = new JLabel("hide password");
		this.add(hidePasswordText, c);
		
		// position charsRemaining text below previous components
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(-3, -87, 15, 0);
		
		// initialize and add charsRemaining to window
		charsRemaining = new JLabel(MAX_CHARS + " characters remaining");
		charsRemaining.setFont(new Font("Dialog", 1, 10));
		this.add(charsRemaining, c);
		
		// position generatePassword button below charsRemaining
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0, 40, 20, 0);
		
		// create and add generatePassword to window
		generatePassword = new JButton("Generate Random Password");
		generatePassword.setFont(new Font("Dialog", 1, 11));
		generatePassword.setFocusable(false);
		generatePassword.addActionListener(this);
		this.add(generatePassword, c);
		
		// position passwordStrength text below generatePassword
		c.gridy = 4;
		c.insets = new Insets(0, 45, 10, 0);
		
		// initialize (blank) and add passwordStrength to window
		passwordStrength = new JLabel("None");
		this.add(passwordStrength, c);
		
		// position passwordMeter below passwordStrength
		c.gridy = 5;
		c.insets = new Insets(0, 50, 10, 0);
		
		// create and add passwordMeter to window
		passwordMeter = new JSlider(0, Score.MAX_SCORE, 0);
		passwordMeter.setEnabled(false);
		passwordMeter.setPaintTicks(true);
		passwordMeter.setMajorTickSpacing(25);
		passwordMeter.setMinorTickSpacing(5);
		passwordMeter.setPaintLabels(true);
		this.add(passwordMeter, c);
		
		this.pack(); // resizes window to hold components
		
		// position window in center of user's screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		this.setLocation((screenWidth / 2) - (this.getWidth() / 2), (screenHeight / 2) - (this.getHeight() / 2));
	}
	
	// calls Score class to evaluate password and then updates display
	public void evalPassword(String password) {
		if (password.length() > 0) {
			// get password strength and update display accordingly
			int strength = Score.calcStrength(password);
			passwordMeter.setValue(strength);
			
			if (strength < 20) {
				passwordStrength.setText("Very Weak");
				passwordStrength.setForeground(new Color(0xD11515));
			} else if (strength >= 20 && strength < 40) {
				passwordStrength.setText("Weak");
				passwordStrength.setForeground(new Color(0xDE8304));
			} else if (strength >= 40 && strength < 60) {
				passwordStrength.setText("Moderate");
				passwordStrength.setForeground(new Color(0xB8B527));
			} else if (strength >= 60 && strength < 79) {
				passwordStrength.setText("Strong");
				passwordStrength.setForeground(new Color(0x5BD63C));
			} else {
				passwordStrength.setText("Very Strong");
				passwordStrength.setForeground(new Color(0x278C0E));
			}
		} else {
			// user has not entered any input
			passwordMeter.setValue(0);
			passwordStrength.setText("None");
			passwordStrength.setForeground(Color.BLACK);
		}
		
		// update characters remaining text
		charsRemaining.setText(MAX_CHARS - password.length() + " characters remaining");
	}
	
	// listen for user actions
	public void actionPerformed(ActionEvent e) {
		// user clicks generatePassword button
		if (e.getSource() == generatePassword) {
			passwordField.setText(GeneratePassword.generate());
			passwordField.setEchoChar(ECHO_OFF); // show password
			hidePassword.setSelected(false); // uncheck hidePassword check box
		}
		
		// user clicks hidePassword check box
		if (e.getSource() == hidePassword) {
			if (hidePassword.isSelected())
				passwordField.setEchoChar(passwordDot); // hide password
			else
				passwordField.setEchoChar(ECHO_OFF); // show password
		}
	}
	
	// evaluate password after user enters a character
	public void insertUpdate(DocumentEvent e) {
		evalPassword(new String(passwordField.getPassword()));
	}
	
	// evaluate password after user deletes a character
	public void removeUpdate(DocumentEvent e) {
		evalPassword(new String(passwordField.getPassword()));
	}
	
	// not used with plain text
	public void changedUpdate(DocumentEvent e) {
		// not implemented
	}
	
	// nested class that filters out spaces entered by user and imposes a character limit
	class PasswordFieldFilter extends DocumentFilter {
		public void replace(FilterBypass fb, int offset, int length, 
				String text, AttributeSet attr) throws BadLocationException {
			
			if (offset < MAX_CHARS) {
				if (text.contains(" ")) {
					Toolkit.getDefaultToolkit().beep(); // spaces not allowed (warn user)
					fb.replace(offset, length, text.replaceAll(" ", ""), attr); // filter out spaces
				} else {
					fb.replace(offset, length, text, attr); // does not exceed character limit (allow)
				}
			} else
				Toolkit.getDefaultToolkit().beep(); // exceeds character limit (warn user)
		}
	}
	
	// create and show GUI
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setVisible(true);
	}
}
