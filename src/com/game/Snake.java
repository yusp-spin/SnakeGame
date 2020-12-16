package com.game;

import java.util.LinkedList;

//用LinkedList存储节点，刚才出生的时候有三个节点
public class Snake {
    private LinkedList<Node> body;
    //蛇的默认运动方向
    private Direction direction = Direction.LEFT;

    //蛇是否活着
    private boolean isLive = true;

    public Snake() {
        initSnake();
    }

    private void initSnake() {
        body = new LinkedList<>();
        body.add(new Node(20,20));
        body.add(new Node(21,20));
        body.add(new Node(22,20));
    }

    //蛇的移动
    //在蛇头运动方向添加节点，在蛇尾节点删除
    public void move () {
        if(!isLive) {
            return;
        }
        addSnake();
        //判断蛇是否撞墙
        Node head = body.getFirst();
        if(head.getX() < 0 || head.getY() < 0 || head.getX() >= 40 || head.getY()>= 40) {
            isLive = false;
        }

        //判断蛇是否碰到自己身体 用Set更好
        for (int i = 1; i < body.size(); i++) {
            Node node = body.get(i);
            if(node.getX()==head.getX() && node.getY() == head.getY()) {
                isLive = false;
            }
        }

        //删除最后一个节点
        body.removeLast();
    }

    public LinkedList<Node> getBody() {
        return body;
    }

    public void setBody(LinkedList<Node> body) {
        this.body = body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    //吃食物，沿着蛇头方向添加一个节点
    public void eat() {
        addSnake();
    }

    //蛇头增长

    public void addSnake () {
        //获取蛇头
        Node head = body.getFirst();
        switch (direction) {
            case UP:
                //上边添加一个点
                body.addFirst(new Node(head.getX(),head.getY()-1));
                break;
            case DOWN:
                body.addFirst(new Node(head.getX(),head.getY()+1));
                break;
            case LEFT:
                body.addFirst(new Node(head.getX()-1,head.getY()));
                break;
            case RIGHT:
                body.addFirst(new Node(head.getX()+1,head.getY()));
                break;
        }
    }
}
