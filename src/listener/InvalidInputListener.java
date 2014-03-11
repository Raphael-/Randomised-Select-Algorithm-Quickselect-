package listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InvalidInputListener extends KeyAdapter
{ 
    
	public void keyTyped(KeyEvent e) 
	{  
		char c = Character.toLowerCase(e.getKeyChar()); 
		if(!Character.isDigit(c) && c!=',' && c!='\b')
		{
			java.awt.Toolkit.getDefaultToolkit().beep();  
			e.consume();
			return;
		}
	}  
}

