package com.company;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class Front extends JFrame{

    JPanel jp = new JPanel();
    JLabel jl = new JLabel();
    JTextField jt = new JTextField();
    JButton jb = new JButton("Enter");

    public Front(){
        setTitle("Inserir Aluno");
        setVisible(true);
        setSize(400,200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jp.add(jt);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jl.setText("Nome do aluno");
                String input = jt.getText();
            }
        });
        jp.add(jb);
        jp.add(jl);
        add(jp);
    }

}
