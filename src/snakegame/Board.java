package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Board extends JPanel implements ActionListener {
    private int B_Height = 300;
    private int B_Width = 300;
    private int Dot_Size = 10;
    private int All_Dots = 900;
    private int Rand_Pos = 29;
    private int Delay = 140;
    private int x[] = new int[All_Dots];
    private int y[] = new int[All_Dots];
    private int dots , apple_x , apple_y;
    private boolean leftDirection , upDirection , downDirection = false;
    private boolean rightDirection , inGame = true;
    private Timer timer;
    private Image ball , apple ,head;
    public Board(){
        initBoard(); }
    private void initBoard(){
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(B_Width , B_Height));
        loadImage();
        initGame(); }
    private void loadImage(){
        ImageIcon imagedot = new ImageIcon("src/assets/dot.png");
        ball = imagedot.getImage();
        ImageIcon imageapple = new ImageIcon("src/assets/apple.png");
        apple = imageapple.getImage();
        ImageIcon imagehead = new ImageIcon("src/assets/head.png");
        head = imagehead.getImage(); }
    private void initGame(){
        dots = 3;
        for (int z = 0 ; z <dots ; z++){
            x[z] = 50 - z * 10 ;
            y[z] = 50; }
        locateApple();
        timer = new Timer(Delay,this);
        timer.start(); }
    @Override
    public void paintComponent(Graphics graphics){
      super.paintComponent(graphics);
      doDrawing(graphics); }
    private void doDrawing(Graphics graphics) {
        if (inGame) {
            graphics.drawImage(apple, apple_x, apple_y, this);
            for (int z = 0 ; z< dots ; z++){
                if (z==0){ graphics.drawImage(head , x[z] , y[z] , this); }
                else {graphics.drawImage(ball , x[z] , y[z] , this ); } }
            Toolkit.getDefaultToolkit().sync();
        }else { gameOver(graphics); } }
    private  void gameOver(Graphics graphics){
        String msg = "Game Over";
        Font small = new Font("Roboto" , Font.BOLD , 14);
        FontMetrics metrics = getFontMetrics(small);
        graphics.drawString(msg , (B_Width - metrics.stringWidth(msg))/2 , B_Height / 2); }
    private void checkApple(){
        if ((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;
            locateApple(); } }
    private void locateApple(){
        int r = (int) (Math.random() * Rand_Pos);
        apple_x = ((r * Dot_Size));
        r = (int)(Math.random() * Rand_Pos);
        apple_y = ((r * Dot_Size)); }
    private void move(){
        for (int z = dots ; z> 0 ; z--){ x[z] = x[(z-1)] ;y[z] = y[(z-1)]; }
        if (leftDirection){ x[0] -= Dot_Size; }
        if (rightDirection){ x[0] += Dot_Size; }
        if (upDirection){ y[0] -= Dot_Size; }
        if (downDirection){ y[0] += Dot_Size; } }
    private void checkCollision(){
        for (int z = dots ; z > 0 ; z--){
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])){ inGame = false; } }
        if (y[0] >= B_Height){ inGame = false; }
        if (y[0] < 0){ inGame = false; }
        if (x[0] >= B_Width){ inGame = false; }
        if (x[0] < 0){ inGame = false; }
        if (!inGame){ timer.stop(); } }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkApple();checkCollision();move();
        }repaint(); }
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)){
                leftDirection = true;upDirection = false;downDirection = false; }
            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)){
                rightDirection = true;upDirection = false;downDirection = false; }
            if ((key == KeyEvent.VK_UP) && (!downDirection)){
                upDirection = true;rightDirection = false;leftDirection = false; }
            if ((key == KeyEvent.VK_DOWN) && (!upDirection)){
                downDirection =true;rightDirection = false;leftDirection = false; } } }}