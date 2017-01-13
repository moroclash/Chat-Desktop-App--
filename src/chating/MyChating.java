
package chating;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyChating {
    
    
    public static class server extends JFrame implements ActionListener
    {
        JTextArea txa;
        private JTextField txf;
        private JButton send;
        private static ServerSocket s;
        static Socket con;
        private JLabel l,k,c;
        public server() 
        {
            ready(); 
        }
    
        private void ready()
        {
            this.setLayout(null);
            this.setSize(700, 500);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            
            txf = new JTextField();
            txf.setBackground(Color.LIGHT_GRAY);
            txf.setForeground(Color.black);
            txf.setBounds(5, 5, 500, 50);
            this.add(txf);
            
            send = new JButton("Send");
            send.setBackground(Color.black);
            send.setForeground(Color.white);
            send.setBounds(txf.getBounds().x+txf.getBounds().width + 10,txf.getBounds().y ,100,50);
            send.addActionListener(this);
            this.add(send);
            
            l = new JLabel("Steate : ");
            l.setBounds(txf.getBounds().x , txf.getBounds().height +txf.getBounds().y+5,60,40);
            this.add(l);
            
            k = new JLabel();
            k.setBounds(l.getBounds().x+60 , txf.getBounds().height +txf.getBounds().y+5,140,40);
            k.setForeground(Color.green);
            this.add(k);
            
            c = new JLabel();
            c.setBounds(l.getBounds().x+200 , txf.getBounds().height +txf.getBounds().y+5,140,40);
            c.setForeground(Color.BLUE);
            this.add(c);
            
            txa  = new JTextArea();
            JScrollPane c = new JScrollPane(txa);
            txa.setBackground(Color.PINK);
            txa.setForeground(Color.white);
            c.setBounds(5, 100, 600, 350);
            this.add(c);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!txf.getText().equals(""))
            {
            try {
                txa.setText(txa.getText()+"\n" + "ME :" + txf.getText());
                ObjectOutputStream sen = new ObjectOutputStream(con.getOutputStream());
                sen.writeObject(txf.getText());
                txf.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "can't send check your internet");
            }
            this.setTitle("server");
            }
        }
    
    }
    

    public static void main(String[] args) {
        server s = new server();
        s.setVisible(true);
        try {
                s.s = new ServerSocket(5000);
                if(s.s.isClosed())
                {
                    s.k.setForeground(Color.red);
                    s.k.setText("no connection");
                }
                else
                {
                    s.k.setText("whiting for Connection");                    
                }
                s.con = s.s.accept();
                s.k.setText("Connect");
                s.c.setText("clint found !");
                while(true)
                {
                     ObjectInputStream see = new ObjectInputStream(s.con.getInputStream());
                     String massage = (String) see.readObject();
                     s.txa.setText(s.txa.getText() + "\n" +"Clinet : " + massage);
                }
            } catch (IOException|ClassNotFoundException ex) {
               /* try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex1) {
                    JOptionPane.showMessageDialog(s, "error in thread");
                }*/
                s.k.setForeground(Color.red);
                //s.l.setText("disConnect");
                s.k.setText("disConnect");
                s.c.setText("");
            }
        
        
        
        
    }
    
}
