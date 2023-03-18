import javax.swing.*;

public class frame extends JFrame {

    frame()
    {
        //Adds panel to frame-->function of javax.swing
        //panel class we are creating
        this.add(new panel());
        //adds title to the game
        this.setTitle("Snake Game");
        //user cannot resize the window, the time for snake to reach food should me minimum
        //so default size for all users
        this.setResizable(false);
        //the frame is visible to user
        this.setVisible(true);
        //takes size from panel
        this.pack();
    }
}
