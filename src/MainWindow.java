import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainWindow extends JFrame implements ActionListener{
	JTextArea shopDisplay;
	Button button;
	TextField numberInput;
	
	public MainWindow() {
		setLayout(new BorderLayout());
		
		shopDisplay = new JTextArea("output");
		
		shopDisplay.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		add(shopDisplay, BorderLayout.CENTER);
		
		JPanel southbar = new JPanel();
		
		southbar.setLayout(new BoxLayout(southbar, BoxLayout.LINE_AXIS));
		
		numberInput  = new TextField("No. of Shop Items");
		
		southbar.add(numberInput);
		
		button = new Button("Generate!");
		
		button.setPreferredSize(new Dimension(50,25));
		
		button.addActionListener(this);
		
		southbar.add(button);
		
		add(southbar, BorderLayout.SOUTH);
		
		setTitle("Terabix's RP shop inventory generator.");  // "super" Frame sets title
	    setSize(600, 500);
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
	    if(e.getActionCommand().equals("Generate!")) {
	    	String inventory = null;
	    	try {
	    		inventory = Driver.calculate_inventory("StoreInventorySettings.txt", "StoreInventoryTransform.txt", Integer.parseInt(numberInput.getText()));
	    	}catch (NumberFormatException e1){
	    		numberInput.setText("Invalid Input!");
	    	}/*catch (Exception e2) {
	    		StringWriter sw = new StringWriter();
	    		PrintWriter pw = new PrintWriter(sw);
	    		e2.printStackTrace(pw);
	    		String sStackTrace = sw.toString();
	    		shopDisplay.setText(sStackTrace);
	    	}*/
	    	shopDisplay.setText(inventory);
	    	
	    }
	}
}
