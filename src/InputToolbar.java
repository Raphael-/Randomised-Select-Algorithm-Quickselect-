import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import listener.InvalidInputListener;
import listener.ValidationListener;
import configs.Configs;


public class InputToolbar extends JPanel
{
	private JTextField ftx;
	private JFormattedTextField ftf;
	private Label label1,label2,label4;
	private JLabel label3;
	private Applet parent;
	public ValidationListener vl;
	
	public InputToolbar(Applet parent)
	{
		this.parent = parent;

		setBackground(Color.black);
	

		ftx = new JTextField();
		ftx.setColumns(40);
		
		label1 = new Label("Input");
		label1.setForeground(Color.red);
		
		label2 = new Label("Search for ");
		label2.setForeground(Color.red);
		
		label4 = new Label("th smallest number");
		label4.setForeground(Color.red);
		
		try 
		{
			ftf = new JFormattedTextField(new MaskFormatter("###"));
		}
		catch (ParseException e1) 
		{
			e1.printStackTrace();
		}
		
		ftf.setPreferredSize(new Dimension(25,25));
		ftf.setFocusLostBehavior(JFormattedTextField.PERSIST);
		
		label3 = new JLabel();
		label3.setPreferredSize(new Dimension(48,48));
		
		try 
		{
			label3.setDisabledIcon(new ImageIcon(new URL(Configs.codeBase,"icons/locked.png")));
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		
		ftx.addKeyListener(new InvalidInputListener());
		
		vl = new ValidationListener(ftx,label3);
		ftx.getDocument().addDocumentListener(vl);
		
		add("Center",label1);
		add("Center",label3);
		add("Center",ftx);
		add("East",label2);
		add("East",ftf);
		add("East",label4);
	}
	
	public void lockTextField(boolean b)
	{
		ftx.setEditable(!b);
		if(ftx.isEditable())
		{
			label3.setEnabled(true);
			label3.setIcon(null);
		}
		else
		{
			label3.setEnabled(false);
		}
	}
	
	public String getInput()
	{
		return ftx.getText();
	}
	
	public String getSelection()
	{
		return ftf.getText();
	}
	
	public void replaceInput(String str)
	{
		ftx.setText(str);
	}
	
	public void replaceSelection(String s)
	{
		ftf.setText(s);
	}
}
