import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Symptoms {
    private final List<String> autocompleteSuggestions = new ArrayList<>();
    private final Map<String, String> diseaseToCodeMap = new HashMap<>();
    public Symptoms (){



        // main frame andback ground from here ---->
        JFrame Symptoms = new JFrame();
        Symptoms.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Symptoms.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create a custom JPanel to paint the background image
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bgImageIcon = new ImageIcon("/Users/mbp/IdeaProjects/Updated/src/IMGES/orang_batuk.gif");
                Image bgImage = bgImageIcon.getImage();
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        // Set the layout manager of the JPanel to null for absolute positioning
        bgPanel.setLayout(null);
        // Set the layout manager of the content pane to BorderLayout
        Symptoms.getContentPane().setLayout(new BorderLayout());
        Symptoms.getContentPane().add(bgPanel, BorderLayout.CENTER);
        Symptoms.setVisible(true);// <---- to here






      //obj or button text etc

        JTextField Sentences = new JTextField();
        Sentences.setBounds(15,570,500,50);
        Sentences.setVisible(true);
        bgPanel.add(Sentences);

        JButton Converter = new JButton("Show Symptoms");
        Converter.setBounds(520,720,130,50);
        Converter.setVisible(true);
        bgPanel.add(Converter);

        JTextArea Container = new JTextArea();
        Container.setBounds(930, 570, 500, 200);
        Container.setLineWrap(true);
        Container.setWrapStyleWord(true);
        Container.setEditable(false);
        bgPanel.add(Container);

        JScrollPane scrollPane = new JScrollPane(Container);
        scrollPane.setBounds(930, 570, 500, 200);
        scrollPane.setVisible(true);
        bgPanel.add(scrollPane);

        JButton Transfer = new JButton("Transfer"+"-->");
        Transfer.setBounds(830,720,90,50);
        bgPanel.add(Transfer);

        JTextField SymptomsT = new JTextField();
        SymptomsT.setBounds(15,720,500,50);
        SymptomsT.setVisible(true);
        bgPanel.add(SymptomsT);

        JButton clearSentenceButton = new JButton("Clear");
        clearSentenceButton.setBounds(520, 570, 100, 50);
        clearSentenceButton.setVisible(true);
        bgPanel.add(clearSentenceButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(830, 660, 90, 50);
        clearButton.setVisible(true);
        bgPanel.add(clearButton);

        JButton Back = new JButton("<-HomePage");
        Back.setBounds(17,17,100,100);
        Back.setVisible(true);
        bgPanel.add(Back);
























        //file reader(

        loadSuggestionsFromFile("/Users/mbp/IdeaProjects/Updated/src/Data/Diseases.txt");
        loadDiseaseToCodeMapFromFile("/Users/mbp/IdeaProjects/Updated/src/Data/Symptoms.txt");

        JList<String> suggestionList = new JList<>();
        DefaultListModel<String> suggestionListModel = new DefaultListModel<>();
        suggestionList.setModel(suggestionListModel);
        suggestionList.setVisibleRowCount(5);

        //Sentance TextFild Functionalty
        // Create a JScrollPane for the suggestion list
        JScrollPane suggestionScrollPane = new JScrollPane(suggestionList);
        suggestionScrollPane.setBounds(15, 620, 500, 100); // Adjust position and size as needed
        bgPanel.add(suggestionScrollPane);
        suggestionScrollPane.setVisible(false);

        //Sentance TextFild Functionalty
        // Add a KeyAdapter to the Sentences JTextField for autocomplete functionality
        Sentences.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = Sentences.getText().toLowerCase();
                suggestionListModel.clear();

                if (!searchText.isEmpty()) {
                    for (String suggestion : autocompleteSuggestions) {
                        if (suggestion.toLowerCase().contains(searchText)) {
                            suggestionListModel.addElement(suggestion);
                        }
                    }
                    suggestionScrollPane.setVisible(true);
                } else {
                    suggestionScrollPane.setVisible(false);
                }
            }
        });
        //Sentance TextFild Functionalty
        // Add an ActionListener to select a suggestion when clicked
        suggestionList.addListSelectionListener(e -> {
            if (!suggestionList.isSelectionEmpty()) {
                Sentences.setText(suggestionList.getSelectedValue());
                suggestionScrollPane.setVisible(false);
            }
        });
        //filer reader )


        Converter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the text from the Sentences JTextField and preprocess it
                String enteredDiseaseName = Sentences.getText();

                // Look up the corresponding code in the mapping
                String Symp = diseaseToCodeMap.get(enteredDiseaseName);

                // Set the code in the Code JTextField
                SymptomsT.setText(Symp);
            }
        });
        Transfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the text from the Code and Sentences JTextFields
                String Symptom = SymptomsT.getText();
                String diseaseName = Sentences.getText();

                // Format the text as specified and append it to the Container JTextArea with one line break
                String formattedText =  diseaseName + " Symptoms :(" + Symptom + ")\n\n"; // Add an extra newline
                Container.append(formattedText);

                // Clear the Code and Sentences JTextFields after transferring
                SymptomsT.setText("");
                Sentences.setText("");
            }
        });


        // cleare Button Functionality  For Container
        // Add an ActionListener to the Clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the content in the Container JTextArea
                Container.setText("");
            }
        });


        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current JFrame (HomeScreen)
                Symptoms.dispose();

                // Create and display a new instance of ICDPG
                SwingUtilities.invokeLater(() -> {
                    new HomeScreen();
                });
            }
        });









    }

    private void loadDiseaseToCodeMapFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String diseaseName = parts[0];
                    String code = parts[1];
                    diseaseToCodeMap.put(diseaseName, code);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    private String loadSuggestionsFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                autocompleteSuggestions.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }


    public static void main(String[] args) {

        new Symptoms();

    }
}
