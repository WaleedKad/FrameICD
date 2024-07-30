import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ICDPG {

    private final List<String> autocompleteSuggestions = new ArrayList<>();
    private final Map<String, String> diseaseToCodeMap = new HashMap<>();

    public ICDPG() {
        // Create and set up the JFrame
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create a custom JPanel to paint the background image
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bgImageIcon = new ImageIcon("FRAMEICD/src/IMGES/medical-icons.gif");
                Image bgImage = bgImageIcon.getImage();
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set the layout manager of the JPanel to null for absolute positioning
        bgPanel.setLayout(null);

        // Add the background panel to the frame
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(bgPanel, BorderLayout.CENTER);

        // Create and add UI components
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(830, 660, 90, 50);
        bgPanel.add(clearButton);

        JTextArea container = new JTextArea();
        container.setBounds(930, 570, 500, 200);
        container.setLineWrap(true);
        container.setWrapStyleWord(true);
        container.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBounds(930, 570, 500, 200);
        bgPanel.add(scrollPane);

        JTextField sentences = new JTextField();
        sentences.setBounds(15, 570, 500, 50);
        bgPanel.add(sentences);

        JTextField code = new JTextField();
        code.setBounds(15, 720, 500, 50);
        bgPanel.add(code);

        JButton converter = new JButton("Convert To ICD");
        converter.setBounds(520, 720, 110, 50);
        bgPanel.add(converter);

        JButton transfer = new JButton("Transfer-->");
        transfer.setBounds(830, 720, 90, 50);
        bgPanel.add(transfer);

        JButton clearSentenceButton = new JButton("Clear");
        clearSentenceButton.setBounds(520, 570, 100, 50);
        bgPanel.add(clearSentenceButton);

        JButton back = new JButton("<-HomePage");
        back.setBounds(17, 17, 100, 100);
        bgPanel.add(back);

        // Load data
        loadSuggestionsFromFile("FRAMEICD/src/Data/Diseases.txt");
        loadDiseaseToCodeMapFromFile("FRAMEICD/src/Data/DiseasesAndCODES.txt");

        // Set up autocomplete suggestion list
        JList<String> suggestionList = new JList<>();
        DefaultListModel<String> suggestionListModel = new DefaultListModel<>();
        suggestionList.setModel(suggestionListModel);
        suggestionList.setVisibleRowCount(5);

        JScrollPane suggestionScrollPane = new JScrollPane(suggestionList);
        suggestionScrollPane.setBounds(15, 620, 500, 100); // Adjust position and size as needed
        bgPanel.add(suggestionScrollPane);
        suggestionScrollPane.setVisible(false);

        // Add a KeyAdapter to the Sentences JTextField for autocomplete functionality
        sentences.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = sentences.getText().toLowerCase();
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

        // Add an ActionListener to select a suggestion when clicked
        suggestionList.addListSelectionListener(e -> {
            if (!suggestionList.isSelectionEmpty()) {
                sentences.setText(suggestionList.getSelectedValue());
                suggestionScrollPane.setVisible(false);
            }
        });

        // Converter functionality
        converter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredDiseaseName = sentences.getText();
                String codeValue = diseaseToCodeMap.get(enteredDiseaseName);
                code.setText(codeValue != null ? codeValue : "Not found");
            }
        });

        // Transfer functionality
        transfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codeText = code.getText();
                String diseaseName = sentences.getText();
                String formattedText = "(" + codeText + ")-->(" + diseaseName + ")\n\n";
                container.append(formattedText);
                code.setText("");
                sentences.setText("");
            }
        });

        // Clear button functionality for Container
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.setText("");
            }
        });

        // Back button functionality
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                SwingUtilities.invokeLater(() -> new HomeScreen());
            }
        });

        // Set the frame visible
        frame.setVisible(true);
    }

    private void loadSuggestionsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                autocompleteSuggestions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDiseaseToCodeMapFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    diseaseToCodeMap.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ICDPG());
    }
}
