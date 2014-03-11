package configs;

import java.io.IOException;
import java.net.URL;

public class Configs 
{
	public static URL codeBase;
	public static String exitIcon; 
	public static String startIcon;
	public static String resetIcon;
	public static String helpIcon;
	public static String randomIcon;
	
	public static void load() throws IOException
	{
		AppletProperties appletSettings = new AppletProperties(codeBase);
		exitIcon = appletSettings.getProperty("ExitIconFilename","exit.jpg");
		startIcon = appletSettings.getProperty("StartIconFilename","start.png");
		resetIcon = appletSettings.getProperty("ResetIconFilename","reset.png");
		randomIcon = appletSettings.getProperty("RandomIcon","random.png");
	}
}
