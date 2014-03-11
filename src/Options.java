import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;

import tools.Regex;
import configs.Configs;


class Options extends JPanel implements ActionListener
{
	private JButton[] buttons;
	private Applet parent;
	public Options(Applet parent)
	{
		this.parent = parent;
		
		buttons = new JButton[3];
		setLayout(new GridLayout(6, 1, 0, 10));
		setBackground(new Color(0,0,0));
		try 
		{
			buttons[0]=new JButton(new ImageIcon(new URL(Configs.codeBase,"icons/"+Configs.startIcon)));
			buttons[1]=new JButton(new ImageIcon(new URL(Configs.codeBase,"icons/"+Configs.resetIcon)));
			buttons[2]=new JButton(new ImageIcon(new URL(Configs.codeBase,"icons/"+Configs.randomIcon)));
		} 
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		buttons[0].setBackground(new Color(0,0,0));
		buttons[0].setToolTipText("Starts algorithm with user's input");
		buttons[0].addActionListener(this);
		buttons[1].setBackground(Color.black);
		buttons[1].setToolTipText("Reset algorithm");
		buttons[1].addActionListener(this);
		buttons[2].setBackground(Color.black);
		buttons[2].setToolTipText("Starts algorithm with random input");
		buttons[2].addActionListener(this);
		add(buttons[0]);
		add(buttons[1]);
		add(buttons[2]);
	}
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Object ob=arg0.getSource();
		
		if(ob==buttons[0])
		{
			if(Regex.matches(parent.itb.getInput()))
			{
				if(parent.itb.getSelection().trim().equals(""))
				{
					JOptionPane.showMessageDialog(null,
						    "You must select your search target.",
						    "ERROR",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if(Integer.parseInt(parent.itb.getSelection().trim())==0)
				{
					JOptionPane.showMessageDialog(null,
						    "You must select a valid k-th element with k>0",
						    "ERROR",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if(parent.itb.getInput().split(",").length<Integer.parseInt(parent.itb.getSelection().trim()))
				{
					JOptionPane.showMessageDialog(null,
						    "You cannot select an element position that doesn't exist.",
						    "ERROR",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				lockButtons(4);
				parent.itb.lockTextField(true);
				new Thread(new Runnable() 
				{
			       public void run() 
			       {
			    	   parent.gc.start(parent.itb.getInput().split(","),Integer.parseInt(parent.itb.getSelection().trim()));
			        }
				}).start();
			}
			else
			{
				JOptionPane.showMessageDialog(null,
					    "You cannot start the algorithm with an invalid input!",
					    "ERROR",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(ob==buttons[1])
		{
			this.parent.itb.lockTextField(false);
			this.parent.itb.replaceInput("");
			this.parent.itb.vl.cancelTask();
			this.parent.gc.clear();
			this.parent.itb.replaceSelection("001");
			this.unlockButtons(4);
		}
		else if(ob==buttons[2])
		{
			String random=Regex.randomInput();
			this.parent.itb.replaceInput(random);
			this.parent.itb.replaceSelection(String.valueOf(Regex.getRandomSelection(random.split(",").length)));
			if(Regex.matches(parent.itb.getInput()))
			{
				lockButtons(4);
				parent.itb.lockTextField(true);
				new Thread(new Runnable() 
				{
			       public void run() 
			       {
			    	   parent.gc.start(parent.itb.getInput().split(","),Integer.parseInt(parent.itb.getSelection().trim()));
			        }
				}).start();
			}
			else
			{
				JOptionPane.showMessageDialog(null,
					    "You cannot start the algorithm with an invalid input!",
					    "ERROR",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void lockButtons(int button)
	{
		switch(button)
		{
			case 1:
				buttons[0].setEnabled(false);
				break;
			case 2:
				buttons[1].setEnabled(false);
				break;
			case 3:
				buttons[2].setEnabled(false);
				break;
			case 4:
				for(JButton b:buttons)
					b.setEnabled(false);
				break;
		}
	}
	
	public void unlockButtons(int button)
	{
		switch(button)
		{
			case 1:
				buttons[0].setEnabled(true);
				break;
			case 2:
				buttons[1].setEnabled(true);
				break;
			case 3:
				buttons[2].setEnabled(true);
				break;
			case 4:
				for(JButton b:buttons)
					b.setEnabled(true);
				break;
		}
	}
}

