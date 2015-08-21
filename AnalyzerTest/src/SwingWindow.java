import ru.kartashov.treeanalyzer.TreeAnalyzer;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Shoff
 * Date: 11.08.13
 * Time: 18:02
 * Test frame for analyzer testing
 */
public class SwingWindow {

    public static void main(String... args){
		JFrame frame = new JFrame();
		frame.setVisible(true);
		TreeAnalyzer.doAnalyze(frame);
    }

}
