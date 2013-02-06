import javax.swing.*;
import java.awt.*;

/*
 * GUI for Search Application
 */
public class SearchGUI {

    public static void main(String[] args) {
        new SearchGUI();
    }
    
    public SearchGUI() {
        JFrame frame = new JFrame();
        frame.setTitle("Search and ye shall find");
        frame.setSize(500,400);
        
        Container pane = frame.getContentPane();
        pane.setLayout(new GridLayout(3,2));
        
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

