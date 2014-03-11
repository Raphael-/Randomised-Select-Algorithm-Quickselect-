package tools;

import java.util.Random;
import java.util.regex.Pattern;

public final class Regex 
{
	private static final String REGEX="^([0-9]{1,3}+,)+[0-9]{1,3}+$";
	
	public static boolean matches(String str)
	{
		return Pattern.matches(REGEX, str);
	}
	
	public static String randomInput()
	{
		Random r = new Random();
		int inputLength=r.nextInt(81)+20;
		String randInput="";
		for(int i=1;i<=inputLength;i++)
		{
			randInput+=r.nextInt(1000);
			if(i==inputLength)
				break;
			randInput+=",";
		}
		return randInput;
	}
	
	public static int getRandomSelection(int length)
	{
		return (new Random()).nextInt(length)+1;
	}
}
