package src.Run;
import javax.swing.JFrame;
import src.Interface.MainWindow;


public class MainRun {
	public static void main(String[] args){
		MainWindow tw=new MainWindow();
		tw.setLocation(200,100);
		tw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tw.setSize(700, 500);
		tw.setVisible(true);
	}
}
