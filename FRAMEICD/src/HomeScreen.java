import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen {

    public HomeScreen() {

        // main frame andback ground from here ---->
        JFrame mainFrame = new JFrame();
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create a custom JPanel to paint the background image
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bgImageIcon = new ImageIcon("FRAMEICD/src/IMGES/medical_building_800x600.gif");
                Image bgImage = bgImageIcon.getImage();
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        // Set the layout manager of the JPanel to null for absolute positioning
        bgPanel.setLayout(null);
        // Set the layout manager of the content pane to BorderLayout
        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.getContentPane().add(bgPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);// <---- to here



        // Imge import section
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("FRAMEICD/src/IMGES/Rectangle 2.png").getImage().getScaledInstance(100,51,Image.SCALE_DEFAULT));
        imageLabel.setIcon(imageIcon);
        imageLabel.setBounds(670, 350,100,50);
        bgPanel.add(imageLabel);

        JLabel imageLabelx = new JLabel();
        ImageIcon imageIconx = new ImageIcon(new ImageIcon("FRAMEICD/src/IMGES/Rectangle 2.png").getImage().getScaledInstance(100,51,Image.SCALE_DEFAULT));
        imageLabelx.setIcon(imageIcon);
        imageLabelx.setBounds(670, 420,100,50);
        bgPanel.add(imageLabelx);

        JLabel imageLabelM = new JLabel();
        ImageIcon imageIconM = new ImageIcon(new ImageIcon("FRAMEICD/src/IMGES/Rectangle 2.png").getImage().getScaledInstance(100,51,Image.SCALE_DEFAULT));
        imageLabelM.setIcon(imageIcon);
        imageLabelM.setBounds(670, 490,100,50);
        bgPanel.add(imageLabelM);

        JLabel imageLabelP = new JLabel();
        ImageIcon imageIconP = new ImageIcon(new ImageIcon("FRAMEICD/src/IMGES/Rectangle 2.png").getImage().getScaledInstance(100,51,Image.SCALE_DEFAULT));
        imageLabelP.setIcon(imageIcon);
        imageLabelP.setBounds(670, 560,100,50);
        bgPanel.add(imageLabelP);

        JLabel Rectangle = new JLabel();
        ImageIcon Rectangleimage = new ImageIcon(new ImageIcon("FRAMEICD/src/IMGES/Rectangle 3.png").getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT));
        Rectangle.setIcon(Rectangleimage);
        Rectangle.setBounds(620,225,500,500);
        bgPanel.add(Rectangle);

        //butten section

        JButton ICDcode = new JButton("ICD-CODE");
        ICDcode.setBounds(670, 350, 100, 50);
        bgPanel.add(ICDcode);

        JButton Symptoms = new JButton("Symptoms");
        Symptoms.setBounds(670, 420, 100, 50);
        bgPanel.add(Symptoms);

        JButton Medication  = new JButton("Medication");
        Medication .setBounds(670, 490, 100, 50);
        bgPanel.add(Medication );

        JButton procedures  = new JButton("procedures");
        procedures .setBounds(670, 560, 100, 50);
        bgPanel.add(procedures );




        mainFrame.pack();

        //Action listner aka functionality

        ICDcode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current JFrame (HomeScreen)
                mainFrame.dispose();

                // Create and display a new instance of ICDPG
                SwingUtilities.invokeLater(() -> {
                    new ICDPG();
                });
            }
        });


        Symptoms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current JFrame (HomeScreen)
                mainFrame.dispose();

                // Create and display a new instance of ICDPG
                SwingUtilities.invokeLater(() -> {
                    new Symptoms();
                });
            }
        });


        Medication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current JFrame (HomeScreen)
                mainFrame.dispose();

                // Create and display a new instance of ICDPG
                SwingUtilities.invokeLater(() -> {
                    new Medication();
                });
            }
        });

        procedures.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current JFrame (HomeScreen)
                mainFrame.dispose();

                // Create and display a new instance of ICDPG
                SwingUtilities.invokeLater(() -> {
                    new procedures();
                });
            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomeScreen();
        });
    }
}
