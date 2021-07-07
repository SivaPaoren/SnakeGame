import javax.swing.JPanel;
import javax.swing.Timer;



import java.awt.event.*;
import java.awt.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

   static final int screenWidth = 600;
   static final int screenHeight = 600;
   static final int unitSize = 25;
   static final int gameUnit = (screenHeight*screenWidth)/unitSize;
   static final int delay = 75;
   final int X[] = new int[gameUnit];
   final int Y[] = new int[gameUnit];
   int bodyParts = 6;
   int appleEaten;
   int appleX;
   int appleY;
   Random random;
   char direction = 'R';
   boolean running = false;
   Timer timer;

    GamePanel(){
       random = new Random();
       this.setPreferredSize(new Dimension(screenWidth,screenHeight));
       this.setBackground(Color.black);
       this.setFocusable(true); 
       this.addKeyListener(new myKeyAdaptor());
       startGame();
    }
    public void startGame(){
      newApple();
      running = true;
      timer = new Timer(delay,this);
      timer.start();
    }

    public void paintComponent(Graphics g){
       super.paintComponent(g);
       draw(g);
    }

    public void draw(Graphics g){
          
        if(running){
            /*
            for(int i=0;i<screenHeight/unitSize;i++){
                g.drawLine(i*unitSize, 0, i*unitSize, screenHeight);
                g.drawLine(0, i*unitSize, screenWidth, i*unitSize);
              } */
              g.setColor(Color.red);
              g.fillOval(appleX, appleY, unitSize, unitSize);
      
              for(int i=0;i<bodyParts;i++){
                  if(i == 0){
                      g.setColor(Color.green);
                      g.fillRect(X[i], Y[i], unitSize, unitSize);
                  }else{
                      g.setColor(Color.green);
                      //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                      g.fillRect(X[i], Y[i], unitSize, unitSize);
                  }
              }
              g.setColor(Color.red);
              g.setFont(new Font("Ink Free",Font.BOLD,40));
              FontMetrics metrics = getFontMetrics(g.getFont());
              g.drawString("Score : "+appleEaten, (screenWidth-metrics.stringWidth("Score : "+appleEaten))/2, g.getFont().getSize());
        }else{
            gameOver(g);
        }
    }

    public void newApple(){
        appleX = random.nextInt((int)screenWidth/unitSize)*unitSize;
        appleY = random.nextInt((int)screenHeight/unitSize)*unitSize;

    }

    public void move(){
        for (int i = bodyParts; i > 0; i--) {
            X[i] = X[i-1];
            Y[i] = Y[i-1];
        }

        switch(direction){
          case 'U':
           Y[0] = Y[0]-unitSize;
           break;
          case 'D':
           Y[0] = Y[0]+unitSize;
           break;
          case 'L':
           X[0] = X[0]-unitSize;
           break;
           case 'R' :
           X[0] = X[0]+unitSize;
           break;
        }
    }

    public void checkApple(){
        if((X[0] == appleX) && (Y[0] == appleY)){
          bodyParts++;
          appleEaten++;
          newApple();
        }
    }

    public void checkCollisions(){
        for(int i=bodyParts;i>0;i--){
           if(X[0] == X[i] && Y[0] == Y[i]){
               running = false;
           }
        }

        //check if thead touches left border
        if(X[0] < 0){
            running = false;
        }

        //if head touches right border
        if(X[0] >  screenWidth){
            running = false;
        }

        //if head touches top boirde
        if(Y[0] < 0){
            running = false;
        }

        
        //if head touches right border
        if(Y[0] >  screenHeight){
            running = false;
        }

        if(!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score : "+appleEaten, (screenWidth-metrics1.stringWidth("Score : "+appleEaten))/2, g.getFont().getSize());
       //game over text
       g.setColor(Color.red);
       g.setFont(new Font("Ink Free",Font.BOLD,75));
       FontMetrics metrics = getFontMetrics(g.getFont());
       g.drawString("Game Over", (screenWidth-metrics.stringWidth("Game Over"))/2, screenHeight/2);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
       if(running){
           move();
           checkApple();
           checkCollisions();
       }
        repaint();
    }

    public class myKeyAdaptor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
              case KeyEvent.VK_LEFT:
                 if(direction != 'R'){
                     direction = 'L';
                 } 
                 break;

                 case KeyEvent.VK_RIGHT:
                 if(direction != 'L'){
                     direction = 'R';
                 } 
                 break;

                 case KeyEvent.VK_UP:
                 if(direction != 'D'){
                     direction = 'U';
                 } 
                 break;

                 case KeyEvent.VK_DOWN:
                 if(direction != 'U'){
                     direction = 'D';
                 } 
                 break;
            }
        }
    }
    
}
