import javax.swing.*;
import javax.swing.plaf.ActionMapUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeFrame extends JFrame implements ActionListener {
//    SnakeFrame Snake;

    static JButton restartGame = new JButton("Restart");

    SnakeFrame(){
//        restartGame
        restartGame.setLocation(200, 100);
        restartGame.setFocusable(true);
        restartGame.addActionListener(this);

        this.add(new SnakePanel());
        this.setTitle("Wonsz The Snake");
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartGame)
//            this.remove(Snake);
            this.dispose();
            new SnakeFrame();
    }
}