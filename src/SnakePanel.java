import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Random;

public class SnakePanel extends JPanel implements ActionListener {


    // Dimensions
    static final int Width = 1200;
    static final int Height = 720;
    static final int Square_Size = 30;

    static final int Objects = (Width * Height) / (Square_Size * Square_Size);
    static final int SleepTimer = 75;


    final int x[] = new int[Objects];
    final int y[] = new int[Objects];

    int StartingBody = 1;
    public String HighScore = "0";

    int Score;
    int SmallAppleX;
    int SmallAppleY;

    int BigAppleX;
    int BigAppleY;

    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
//    private String HighScoreName;

    SnakePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(Width, Height));
        this.setBackground(new Color(250, 250, 170));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        add(SnakeFrame.restartGame);

        startGame();
    }

    public void startGame() {
        SmallApple();
        BigApple();

        running = true;
        timer = new Timer(SleepTimer, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {

            g.setColor(new Color(250, 60, 20));
            g.fillRect(SmallAppleX, SmallAppleY, Square_Size, Square_Size);

            g.setColor(new Color(255, 120, 50));
            g.fillRect(BigAppleX, BigAppleY, Square_Size, Square_Size);

            if (HighScore.equals("0")) {
                HighScore = this.GetHighScore();
            }
//            if (HighScore.equals("null")) {
//                HighScore = this.GetHighScore();
//            }

            for (int i = 0; i < StartingBody; i++) {
                if (i == 0) {
                    g.setColor(new Color(60, 220, 120));
                    g.fillRect(x[i], y[i], Square_Size, Square_Size);
                } else {
                    g.setColor(new Color(60, 150, 120));
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], Square_Size, Square_Size);
                }
            }
            g.setColor(new Color(20, 120, 200));
            g.setFont(new Font("Quicksand", Font.ITALIC, 20));
            g.drawString("Score: " + Score, 0, Height - 30);

            g.setColor(new Color(20, 120, 200));
            g.setFont(new Font("Quicksand", Font.ITALIC, 20));
            g.drawString("HighScore: " + HighScore, 0, Height - 10);


        } else {

            GameOver(g);
        }

    }

    public void SmallApple() {
        SmallAppleX = random.nextInt((int) (Width / Square_Size)) * Square_Size;
        SmallAppleY = random.nextInt((int) (Height / Square_Size)) * Square_Size;
    }

    public void BigApple() {
        BigAppleX = random.nextInt((int) (Width / Square_Size)) * Square_Size;
        BigAppleY = random.nextInt((int) (Height / Square_Size)) * Square_Size;
    }

    public void move() {
        for (int i = StartingBody; i > 0; i--) {
            x[i] = x[i - 1]; // 0,1,2
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - Square_Size;
                break;
            case 'D':
                y[0] = y[0] + Square_Size;
                break;
            case 'L':
                x[0] = x[0] - Square_Size;
                break;
            case 'R':
                x[0] = x[0] + Square_Size;
                break;
        }

    }

    public void checkSmallApple() {
        if ((x[0] == SmallAppleX) && (y[0] == SmallAppleY)) {
            StartingBody += 2;
            Score += 20;
            SmallApple();

        }
    }

    public void checkBigApple() {
        if ((x[0] == BigAppleX) && (y[0] == BigAppleY)) {
            StartingBody += 1;
            Score += 10;
            BigApple();

        }
    }
    public void Collisions() {
        for (int i = StartingBody; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                CheckScore();
            }
        }
        if (x[0] < 0) {
            running = false;
            CheckScore();
        }
        if (x[0] > Width) {
            running = false;
            CheckScore();
        }
        if (y[0] < 0) {
            running = false;
            CheckScore();
        }
        if (y[0] > Height) {
            running = false;
            CheckScore();
        }

        if (!running) {
            timer.stop();
        }
    }

    public String GetHighScore() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader("HighScore.txt");
            bufferedReader = new BufferedReader(fileReader);
            return bufferedReader.readLine();
        } catch (Exception e) {
            return "Nobody:0";
        }
        finally {
            try {
                if (bufferedReader != null)
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void CheckScore() {

        if (Score > Integer.parseInt((HighScore.split(":")[1]))) {
            String Name = JOptionPane.showInputDialog("You've Beaten The Previous High Score! Tell Us Your Name!");
            HighScore = Name +  ":" + Score;

            File scoreFile = new File("HighScore.txt");

            if (!scoreFile.exists()) {
                try {
                    scoreFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;

            try {
                fileWriter = new FileWriter(scoreFile);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(this.HighScore);
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void GameOver(Graphics g) {
        //Score
        this.setBackground(new Color(22,2,22));

//        this.setBackground(new Color());
        g.setColor(new Color(21, 120, 200));
//        g.setColor(Color.RED);
        g.setFont(new Font("Quicksand", Font.ITALIC, 50));
//        g.drawString("Your Final Score: " + Score, Width , Height - 10);
        g.drawString("Your Final Score: " + Score, 0, 50);

        g.setColor(new Color(21, 120, 200));
        g.setFont(new Font("Quicksand", Font.ITALIC, 60));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("You've Done Well But It Is Game Over", (Width - metrics2.stringWidth("You've Done Well But It Is Game Over")) / 2, Height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkSmallApple();
            Collisions();
            checkBigApple();

        }
        repaint();
    }

    public void reset() {
        running = true;
        direction = 'R';
        x[0] = 0;
        y[0] = 0;
        StartingBody = 1;
        Score = 0;
        startGame();
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP, KeyEvent.VK_W:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_DOWN, KeyEvent.VK_S:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;

            }
            if (e.getKeyCode() == KeyEvent.VK_R) {
                reset();
            }
        }
    }
}

