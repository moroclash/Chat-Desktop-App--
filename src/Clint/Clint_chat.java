package Clint;




import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Clint_chat extends JFrame implements ActionListener{
    private  JTextArea txa;
        private JTextField txf;
        private JButton send;
        private static Socket con;
        
        public Clint_chat() 
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
            
            txa  = new JTextArea();
            JScrollPane c = new JScrollPane(txa);
            txa.setBackground(Color.LIGHT_GRAY);
            txa.setForeground(Color.black);
            c.setBounds(5, 70, 600, 380);
            this.add(c);
            
            
            this.setTitle("clint");
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
            }
            
        }
        
        
        public static void main(String[] args) {
        Clint_chat k = new Clint_chat();
        k.setVisible(true);
        try {
                k.con = new Socket("127.0.0.1", 5000);
                while(true)
                {
                     ObjectInputStream see = new ObjectInputStream(k.con.getInputStream());
                     String massage = (String) see.readObject();
                     k.txa.setText(k.txa.getText() + "\n" + "Server : " + massage);
                }
            } catch (IOException|ClassNotFoundException ex) {
               /* try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex1) {
                    JOptionPane.showMessageDialog(k, "error in thread");
                }*/
                JOptionPane.showMessageDialog(k, "sory can't connect with system");
            }
        
        
        
    }
    
}
