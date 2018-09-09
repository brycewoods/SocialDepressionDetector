package com.example.bryce.twitter_api_app;
/*
*
* Description: Binary tree node.
*
* */
public class Node {

    private int m_value;
    private Node m_right;
    private Node m_left;


    public Node()
    {

    }

    public Node(int value , Node right , Node left)
    {
        m_value = value;
        m_right = right;
        m_left = left;
    }

    public void SetLeftNode(Node left)
    {
        m_left = left;
    }

    public void SetRightNode(Node right)
    {
        m_right = right;
    }

    public Node GetRightNode()
    {
        return m_right;
    }

    public Node GetLeftNode()
    {
        return m_left;
    }
}
