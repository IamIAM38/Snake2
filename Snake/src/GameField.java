import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by infuntis on 15/01/17.
 */
public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 960;
    private final int DOT_SIZE = 48;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;

    private Image danger;

    private int dangerX;

    private int dangerY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;


    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 144 - i*DOT_SIZE;
            y[i] = 144;
        }
        timer = new Timer(200,this);
        timer.start();
        createApple();
        createDanger();
    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;


    }
    public void createDanger() {

        dangerX = new Random().nextInt(20) * DOT_SIZE;
        dangerY = new Random().nextInt(20) * DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iic = new ImageIcon("danger.png");
        danger = iic.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < dots; i++)
                g.drawImage(danger,dangerX,dangerY,this);
            for (int i = 0; i < dots; i++){
                g.drawImage(dot,x[i],y[i],this);
            }
        } else{
            String str = "???? ????????????????";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.white);
           // g.setFont(f);
            g.drawString(str,450,SIZE/2);

        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        } if(up){
            y[0] -= DOT_SIZE;
        } if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
        if(x[0] == dangerX && y[0] ==dangerY){
            inGame = false;
        }

    }

    public void checkCollisions(){
        for (int i = dots; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if(x[0]>SIZE){
            // inGame = false;
            x[0] = 0;
        }
        if(x[0]<0){
           // inGame = false;
            x[0] = SIZE;
        }
        if(y[0]>SIZE){
          //  inGame = false;
            y[0]= 0;
        }
        if(y[0]<0){
           // inGame = false;
            y[0]= SIZE;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();

        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
            if (key == KeyEvent.VK_R){
                inGame = false;
                new GameField();
                inGame = true;
            }
            if (key == KeyEvent.VK_E){
                inGame = true;
                new GameField();
            }
            if (key == KeyEvent.VK_M){
                dots++;

            }
        }
    }


}
