import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {

    static int width=1200;
    static int height=600;
    static int unit=50;
    //to specify time in which new frame is displayed
    Timer timer;
    //deciding food location randomly
    Random random;
    int foodx,foody;
    int score;
    //length of snake
    int length=3;
    char dir='R';
    //false -->when user on game-over screen
    //true-->when user on snake playing screen
    boolean flag=false;

    static  int delay=160;
    //create array for storing values of snake in x and y co-ordinate
    //as taking x-cordinate 1200 and y-co-ordinate 600 , each pixel of 50 unit
    //x-->1200/50=24
    //y-->600/50=12
    //(x,y)-->12*24=288
    int xsnake[]=new int[288];
    int ysnake[]=new int[288];
    panel()
    {
        //setPreferedSize is called in frame by function this.pack
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
        //setFocusable -->allows keyboard input from user
        this.setFocusable(true);
        random=new Random();
        //creating abstract class
        //addkeylistner will call mykey
        //takes input from up,down from keyboard
        this.addKeyListener(new myKey());

        gameStart();
    }

    public  void gameStart()
    {
        spawnFood();
        //game is started
        flag=true;

        timer=new Timer(delay,this);
        //actionlistner---->calls-->actionperformed-->at every 160ms delay
        timer.start();
    }

    public void spawnFood()
    {
        //width-->1200/50=24-->choose randomly from(0,24)
        foodx= random.nextInt(width/unit)*50;
        //height-->600/50=12-->choose randomly from(0,12)
        foody= random.nextInt(height/unit)*50;
    }

    //helps to draw
    public void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);
        draw(graphic);
    }

    //draw
    public void draw(Graphics graphic)
    {
        //draw food particle
        //flag-->true-->game is running
        if(flag==true)
        {
            //food particle
            graphic.setColor(Color.RED);
            graphic.fillOval(foodx,foody,unit,unit);

            //snake
            for(int i=0;i<length;i++)
            {
                //head of snake
                if(i==0)
                {
                    graphic.setColor(Color.ORANGE);
                }
                else
                {
                    //body of snake
                    graphic.setColor(Color.green);
                }

                graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
            }

            //score
            graphic.setColor(Color.cyan);
            graphic.setFont(new Font("comic sans",Font.BOLD,40));
            //size of string
            FontMetrics f=getFontMetrics(graphic.getFont());
            graphic.drawString("score:"+score,(width-f.stringWidth("score:"+score))/2,graphic.getFont().getSize());

        }
        else
        {
            gameover(graphic);
        }
    }


    public void gameover(Graphics graphic)
    {
        //to display the score
        graphic.setColor(Color.cyan);
        graphic.setFont(new Font("comic sans",Font.BOLD,40));
        //size of string
        FontMetrics f=getFontMetrics(graphic.getFont());
        graphic.drawString("score:"+score,(width-f.stringWidth("score:"+score))/2,graphic.getFont().getSize());

        //to display the gameover text
        graphic.setColor(Color.red);
        graphic.setFont(new Font("comic sans",Font.BOLD,80));
        //size of string
        FontMetrics f2=getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER!",(width-f2.stringWidth("GAME OVER!"))/2,height/2);

        //to display the replay prompt
        graphic.setColor(Color.green);
        graphic.setFont(new Font("comic sans",Font.BOLD,40));
        graphic.drawString("Press R to Replay",(width-f.stringWidth("Press R to Replay"))/2,height/2 + 150);

    }

    public void checkhit()
    {
        //snake head hits walls
        if(xsnake[0]<0)
        {
            //leftside
            flag=false;
        }
        else if (xsnake[0]>1200)//width
        {
            //rightside
            flag=false;
        }
        else if(ysnake[0]<0)
        {
            //upside
            flag=false;
        }
        else if(ysnake[0]>600)//height
        {
            //downside
            flag=false;
        }

        //snake head hits its own body
        for(int i=length;i>0;i--)
        {
            //snake head co-ordinates hits body co-ordinates
            if(xsnake[0]==xsnake[i] && ysnake[0]==ysnake[i])
            {
                flag=false;
            }
        }

        //if flag is false
        if(!flag)
        {
            timer.stop();
        }
    }

    //if snake eats food particle
    public void eat()
    {
        //if snake head coordiates== cordinates of food particle
        if((xsnake[0]==foodx)&& (ysnake[0]==foody))
        {
           length++;
            score++;
            spawnFood();//will again reassign food particle at random location
        }

    }


    public void move()
    {
        //body moves behind head-->
        //head will be affected and body will follow

        //updating body parts
        for(int i=length;i>0;i--)
        {
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }

        //updating head to move in direction
        switch (dir)
        {
            case 'R':
                     xsnake[0]=xsnake[0]+unit; //unit taken as 50 (single coordinate is 50
                     break;
            case 'L':
                    xsnake[0]=xsnake[0]-unit;
                    break;
            case 'D':
                    ysnake[0]=ysnake[0]+unit;
                    break;

            case 'U':
                    ysnake[0]=ysnake[0]-unit;
                    break;

        }
    }

    //when key is pressed obj of mykey class is created and keypressed method is called
    public class myKey extends KeyAdapter
    {
        public void keyPressed(KeyEvent evt)
        {
            //for any key pressed on keyboard
            switch (evt.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    //if direction is not equal to down then only up
                    if(dir!='D')
                    {
                        dir='U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    //if dir is not up then only go down
                    if(dir!='U')
                    {
                        dir='D';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    //if dir is not left then only go right
                    if(dir!='L')
                    {
                        dir='R';
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    //if dir is not right then go to left
                    if(dir!='R')
                    {
                        dir='L';
                    }
                    break;

                case KeyEvent.VK_R:
                    //replay
                    //if we are having high score and click r then it will replay
                    if(!flag)
                    {
                        score=0;
                        length=3;
                        dir='R';
                        Arrays.fill(xsnake,0);//x and y co-ordinate will again start with (0,0)
                        Arrays.fill(ysnake,0);

                        gameStart();
                    }
                    break;
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if(flag)
        {
            //if game is running
            move();
            eat();
            checkhit();
        }
        repaint();//after updating using move(),eat(),checkhit()-->it will repaint according to coordinate

    }


}
