package com.ChattingApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Client implements ActionListener {

    static JTextField text;
    static JPanel a1; //declare globbalyy so used outside constructor
    static Box vertical = Box.createVerticalBox(); //align msg vertically
    static JFrame f = new JFrame(); //make jfram class static so we can use static method in nonstatic main function
    static DataOutputStream dout;
    Client() {

        
        // to create your qwn layout set layout null
        f.setLayout(null);
        // ....................header panel
        // started.......................................................
        // used to create differnt header
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        // set header in below coordinates
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null); // to show the image on panel if null then icon visible
        f.add(p1); // add panel

        // add action on clicking arrow icon
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("./com/icons/3.png")); // arrow img
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);// to scale the image dimesions
        ImageIcon i3 = new ImageIcon(i2); // created this bcz you can't pass scale property directly as label
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back); // add image on panel

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0); // to close the whole frame when click on mouse
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("./com/icons/2.png")); // arrow img
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);// to scale the image dimesions
        ImageIcon i6 = new ImageIcon(i5); // created this bcz you can't pass scale property directly as label
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50); // why 40? => bcz arrow size covers 30(5+25) add gap 10 so 40
        p1.add(profile); // add image on panel

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("./com/icons/video.png")); // arrow img
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);// to scale the image dimesions
        ImageIcon i9 = new ImageIcon(i8); // created this bcz you can't pass scale property directly as label
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30); // why 40? => bcz arrow size covers 30(5+25) add gap 10 so 40
        p1.add(video); // add image on panel

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("./com/icons/phone.png")); // arrow img
        Image i11 = i10.getImage().getScaledInstance(20, 30, Image.SCALE_DEFAULT);// to scale the image dimesions
        ImageIcon i12 = new ImageIcon(i11); // created this bcz you can't pass scale property directly as label
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 30, 30); // why 40? => bcz arrow size covers 30(5+25) add gap 10 so 40
        p1.add(phone); // add image on panel

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("./com/icons/3icon.png")); // arrow img
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);// to scale the image dimesions
        ImageIcon i15 = new ImageIcon(i14); // created this bcz you can't pass scale property directly as label
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25); // why 40? => bcz arrow size covers 30(5+25) add gap 10 so 40
        p1.add(morevert); // add image on panel

        // to write anything on frame use jlabel
        JLabel name = new JLabel("sharmaji");
        name.setBounds(110, 15, 100, 15);
        name.setForeground(Color.WHITE);// set color of text
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 10);
        status.setForeground(Color.WHITE);// set color of text
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 12));
        p1.add(status);
        // .............................................. Header panel
        // ended....................................................

        // .............................................Text area
        // panel.................................................

        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5,655, 310,40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));

        f.add(send);

        // create a swing frame

        f.setSize(450, 700); // size of our frame
        f.setLocation(800, 50);// from where to start
        f.setUndecorated(true);//remove frame header
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);// to show the frame
    }

   

    @Override
    public void actionPerformed(ActionEvent ae) {
        // TODO Auto-generated method stub
        try{
        String out = text.getText(); //by clicking send button get value written in text and store it

        JPanel p2 = formatLabel(out);
        a1.setLayout(new BorderLayout()); //place element top bottom left right center 

        JPanel right = new JPanel(new BorderLayout()); //sender's msg should be placed at right side
        right.add(p2, BorderLayout.LINE_END);  //cant add out bcz of string{only can add panel}
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));//add space 15 between two vertical strut(msg)
        a1.add(vertical, BorderLayout.PAGE_START);

        dout.writeUTF(out);
        
        //setthe text empty after sending msg
        text.setText("");

        // reload the frame- when msg send the frame should repaint(reload the frame)
        //use frame object to load the function in it

        f.repaint();
        f.invalidate();
        f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }



    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true); //background color will be seen
        output.setBorder(new EmptyBorder(15,15,15,15));




        panel.add(output);
//showing time of sended msg
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }
    public static void main(String[] args) {
        new Client();
        try{
            Socket s = new Socket("127.0.0.1",6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true){
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);
                
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);

                // dout.writeUTF(msg);

                // text.setText("");
                
                f.validate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
