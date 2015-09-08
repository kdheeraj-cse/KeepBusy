import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
public class BackLogic {
	public void mouseMotion(int x, int y){
		try {
			Robot objRobot = new Robot();
			objRobot.mouseMove(x, y);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void waitForInputTimeMins(Integer min){
		try {
			Thread.sleep(min*60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void waitForInputTimeSecs(Integer Secs){
		try {
			Thread.sleep(Secs*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void shutDown() throws IOException {
		Runtime.getRuntime().exec("shutdown.exe -s -t 0");
	}
	public void restart() throws IOException {
		Runtime.getRuntime().exec("shutdown.exe -r -t 0");
	}
	public void hibernate() throws IOException {
		Runtime.getRuntime().exec("shutdown.exe /h");
	}
}
