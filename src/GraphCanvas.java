import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

public class GraphCanvas extends Canvas
{
	private Applet parent;
	private int[] arrayA;
	private int[] arrayB;
	private int[] arrayC;
	private int[] arrayD;
	private static int startPosX=50;
	private static int startPosY1=30;
	private static int step=1;
	private static int randomElementIndex=-1;
	private static int k=-1;
	private static boolean instantSol=false;
	
	public GraphCanvas(Applet parent)
	{
		this.setIgnoreRepaint(true);
		this.parent=parent;
		this.setBackground(Color.white);
	}
	
	private void doRepaint()
	{
		Runnable task = new Runnable() 
		{
			public void run() 
			{
				paint(getGraphics());
			}
		};
			try 
			{
				SwingUtilities.invokeAndWait(task);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
	}
	
	public void start(String [] input,int sel)
	{
		arrayA=new int[input.length];
		for(int i=0;i<input.length;i++)
			arrayA[i]=Integer.parseInt(input[i]);
		k=sel;
		
		if(input.length<15)
			instantSol=false;
		else
			instantSol=true;
		if(!instantSol)
			graphSol();
		else
			instantSol();
		parent.options.unlockButtons(2);
	}
	
	private void graphSol()
	{
		while(true)
		{
			erase();
			this.parent.c.write("Draw array A", true);
			doRepaint();
			this.parent.c.write("Select a random element", true);
			step++;
			doRepaint();
			this.parent.c.write("Every element in array A that is < random element,put it in array B", true);
			step++;
			sleep(2000);
			doRepaint();
			this.parent.c.write("Every element in array A that is = random element,put it in array C", true);
			step++;
			sleep(2000);
			doRepaint();
			this.parent.c.write("Every element in array A that is > random element,put it in array D", true);
			step++;
			sleep(2000);
			doRepaint();
			this.parent.c.write("Check if k="+k+"<=|B| where |B|="+arrayB.length, true);
			if(k<=arrayB.length)
			{
				this.parent.c.write("Condition is true,calling select(B,k)",true);
				arrayA=arrayB;
				step=1;
				continue;
			}
			this.parent.c.write("Condition is false", true);
			this.parent.c.write("Check if k="+k+"<=|B|+|C| where |B|="+arrayB.length+" and |C|="+arrayC.length, true);
			if(k<=arrayB.length+arrayC.length)
			{
				this.parent.c.write("Condition is true,element is "+arrayA[randomElementIndex],true);
				return;
			}
			else
			{
				this.parent.c.write("Condition is false,calling select(D,k-|B|-|C|)", true);
				arrayA=arrayD;
				step=1;
				k-=arrayB.length+arrayC.length;
			}
		}
	}
	
	private void instantSol()
	{
		while(true)
		{
			randomElementIndex=selectRandom();
			constructArrayB();
			constructArrayC();
			constructArrayD();
			if(k<=arrayB.length)
			{
				arrayA=arrayB;
				step=1;
				continue;
			}
			else if(k<=arrayB.length+arrayC.length)
			{
				this.parent.c.write("Requested element is "+arrayA[randomElementIndex],true);
				return;
			}
			else
			{
				arrayA=arrayD;
				step=1;
				k-=arrayB.length+arrayC.length;
			}
		}
	}
	
	private void drawArray(int [] s,String arrayName)
	{
		Graphics g=getGraphics();
		g.setColor(Color.black);
		Font font = new Font("Arial", Font.BOLD, 22);
		g.setFont(font);
		if(arrayName.equals("A:"))
		{
			g.drawString(arrayName, startPosX-25, startPosY1+20);
			for(int i=0;i<s.length;i++)
			{
				g.drawRect(startPosX+startPosX*i, startPosY1, startPosX, startPosY1);
				g.drawString(String.valueOf(s[i]), startPosX+startPosX*i+5, startPosY1+25);
			}
		}
		else if(arrayName.equals("B:"))
		{
			g.drawString(arrayName, startPosX-25, 3*startPosY1+20);
			for(int i=0;i<s.length;i++)
			{
				highlightElement(s[i],Color.green,false);
				sleep(700);
				g.drawRect(startPosX+startPosX*i, 3*startPosY1, startPosX, startPosY1);
				g.drawString(String.valueOf(s[i]), startPosX+startPosX*i+5, 3*startPosY1+25);
			}
		}
		else if(arrayName.equals("C:"))
		{
			g.drawString(arrayName, startPosX-25, 5*startPosY1+20);
			for(int i=0;i<s.length;i++)
			{
				highlightElement(s[i],Color.cyan,false);
				sleep(700);
				g.drawRect(startPosX+startPosX*i, 5*startPosY1, startPosX, startPosY1);
				g.drawString(String.valueOf(s[i]), startPosX+startPosX*i+5, 5*startPosY1+25);
			}
		}
		else if(arrayName.equals("D:"))
		{
			g.drawString(arrayName, startPosX-25, 7*startPosY1+20);
			for(int i=0;i<s.length;i++)
			{
				highlightElement(s[i],Color.red,false);
				sleep(700);
				g.drawRect(startPosX+startPosX*i, 7*startPosY1, startPosX, startPosY1);
				g.drawString(String.valueOf(s[i]), startPosX+startPosX*i+5, 7*startPosY1+25);
			}
		}
	}
	
	private int selectRandom()
	{
		int rand=arrayA.length>1?(new Random()).nextInt(arrayA.length):0;
		if(!instantSol)
			highlightElement(arrayA[rand],Color.orange,true);
		return rand;
	}
	
	private void highlightElement(int element,Color c,boolean isRandomHighlight)
	{
			for(int i=0;i<arrayA.length;i++)
			{
				if(arrayA[i]==element)
				{
					Graphics g=getGraphics();
					g.setColor(c);
					g.fillRect(startPosX+startPosX*i+2, startPosY1+2, startPosX-3, startPosY1-3);
					Font font = new Font("Arial", Font.BOLD, 22);
					g.setFont(font);
					g.setColor(Color.black);
					g.drawString(String.valueOf(arrayA[i]), startPosX+startPosX*i+5, startPosY1+25);
					if(isRandomHighlight)
						break;
				}
			}
	}
	
	private void constructArrayB()
	{
		List<Integer> l = new ArrayList<Integer>();
		for(int i=0;i<arrayA.length;i++)
		{
			if(arrayA[i]<arrayA[randomElementIndex])
			{
				l.add(arrayA[i]);
			}
		}
		arrayB=new int[l.size()];
		for(int i=0;i<l.size();i++)
			arrayB[i]=l.get(i);
	}
	
	private void constructArrayC()
	{
		List<Integer> l = new ArrayList<Integer>();
		for(int i=0;i<arrayA.length;i++)
		{
			if(arrayA[i]==arrayA[randomElementIndex])
				l.add(arrayA[i]);
		}
		arrayC=new int[l.size()];
		for(int i=0;i<l.size();i++)
			arrayC[i]=l.get(i);
	}
	
	private void constructArrayD()
	{
		List<Integer> l = new ArrayList<Integer>();
		for(int i=0;i<arrayA.length;i++)
		{
			if(arrayA[i]>arrayA[randomElementIndex])
				l.add(arrayA[i]);
		}
		arrayD=new int[l.size()];
		for(int i=0;i<l.size();i++)
			arrayD[i]=l.get(i);
	}
	
	private void erase()
	{
		Graphics g = this.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void clear()
	{
		erase();
		step=1;
		this.parent.c.clear();
	}
	
	private void sleep(int ms)
	{
		try 
		{
			Thread.sleep(ms);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g)
	{
		switch(step)
		{
			case 1:
				drawArray(this.arrayA,"A:");
				break;
			case 2:
				randomElementIndex=selectRandom();
				break;
			case 3:
				constructArrayB();
				drawArray(arrayB,"B:");
				break;
			case 4:
				constructArrayC();
				drawArray(arrayC,"C:");
				break;
			case 5:
				constructArrayD();
				drawArray(arrayD,"D:");
				break;
		}
	}
	
}