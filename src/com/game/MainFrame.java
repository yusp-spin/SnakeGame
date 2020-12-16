package com.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame extends JFrame {

    private Snake snake;

    private JPanel jPanel;

    //定时器,在指定时间调用蛇移动的方法
    private Timer timer;

    //食物
    private Node food;

    public MainFrame() throws HeadlessException {
        //初始化窗体参数
        initFrame();
        //初始化游戏棋盘
        initGamePanel();
        //初始化蛇
        initSnake();
        //初始化食物
        initFood();
        //初始化定时器
        initTimer();
        //设置键盘监听
        setKeyListener();
    }

    private void initFood() {
        food = new Node();
        food.random();
    }

    private void setKeyListener() {
        addKeyListener(new KeyAdapter() {
            //当键盘按下时，会调用此方法
            @Override
            public void keyPressed(KeyEvent e) {
                //键盘中每一个键都有编号
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP :
                        if(snake.getDirection() != Direction.DOWN)
                            snake.setDirection(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        if(snake.getDirection() != Direction.UP)
                            snake.setDirection(Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        if(snake.getDirection() != Direction.RIGHT)
                            snake.setDirection(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(snake.getDirection() != Direction.LEFT)
                            snake.setDirection(Direction.RIGHT);
                        break;
                }
            }
        });
    }

    private void initTimer() {
        //创建定时器对象
        timer = new Timer();
        //初始化定时任务
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                snake.move();
                //判断蛇头和食物是否重合
                Node first = snake.getBody().getFirst();
                if(first.getX() == food.getX() && first.getY() == food.getY()) {
                    snake.eat();
                    food.random();
                }
                //重绘游戏棋盘
                jPanel.repaint();
            }
        };

        //每100ms执行一次定时任务
        timer.scheduleAtFixedRate(task, 0, 100);
    }

    private void initSnake() {
        snake = new Snake();
    }

    private void initGamePanel() {
        jPanel = new JPanel(){
            //绘制游戏棋盘内容
            @Override
            public void paint(Graphics g) {
                //清空棋盘
                g.clearRect(0,0,600,600);
                //Graphics是一个画笔,提供很多方法可以用，比如直线，矩形，圆形等
                //绘制40条横线
                for (int i = 0; i <= 40; i++) {
                    g.drawLine(0,i*15,600,i*15);
                }
                //绘制竖线
                for (int i = 0; i <= 40; i++) {
                    g.drawLine(i*15,0,i*15,600);
                }
                //绘制蛇
                LinkedList<Node> body = snake.getBody();
                g.setColor(Color.blue);

                for (Node node : body) {
                    g.fillRect(node.getX()*15,node.getY()*15,15,15);
                }

                //绘制食物
                g.setColor(Color.yellow);
                g.fillRect(food.getX()*15, food.getY()*15,15,15);
            }
        };
        //把棋盘添加到窗体中
        add(jPanel);
    }

    private void initFrame() {
        //设置窗体宽高
        setSize(610,640);
        //设置窗体位置
        setLocation(400,400);
        //设置关闭按钮的作用,退出
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗体大小固定
        setResizable(false);
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
