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

public class Medication {

    private final List<String> autocompleteSuggestions = new ArrayList<>();
    private final Map<String, String> Med = new HashMap<>();

    public Medication() {
        // Create and set up the JFrame
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create a custom JPanel to paint the background image
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bgImageIcon = new ImageIcon("FRAMEICD/src/IMGES/31f3d13c0de276a7940879547ef4730a.gif");
                Image bgImage = bgImageIcon.getImage();
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set the layout manager of the JPanel to null for absolute positioning
        bgPanel.setLayout(null);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(bgPanel, BorderLayout.CENTER);

        // Create and add UI components
        JTextField sentences = new JTextField();
        sentences.setBounds(15, 570, 500, 50);
        bgPanel.add(sentences);

        JButton converter = new JButton("Show Medication");
        converter.setBounds(520, 720, 130, 50);
        bgPanel.add(converter);

        JTextArea container = new JTextArea();
        container.setBounds(930, 570, 500, 200);
        container.setLineWrap(true);
        container.setWrapStyleWord(true);
        container.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBounds(930, 570, 500, 200);
        bgPanel.add(scrollPane);

        JButton transfer = new JButton("Transfer-->");
        transfer.setBounds(830, 720, 90, 50);
        bgPanel.add(transfer);

        JTextField medT = new JTextField();
        medT.setBounds(15, 720, 500, 50);
        bgPanel.add(medT);

        JButton clearSentenceButton = new JButton("Clear");
        clearSentenceButton.setBounds(520, 570, 100, 50);
        bgPanel.add(clearSentenceButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(830, 660, 90, 50);
        bgPanel.add(clearButton);

        JButton back = new JButton("<-HomePage");
        back.setBounds(17, 17, 100, 100);
        bgPanel.add(back);

        // Load data
        loadSuggestionsFromFile("FRAMEICD/src/Data/Diseases.txt");
        loadDiseaseToCodeMapFromFile("FRAMEICD/src/Data/Medication.txt");

        // Set up autocomplete suggestion list
        JList<String> suggestionList = new JList<>();
        DefaultListModel<String> suggestionListModel = new DefaultListModel<>();
        suggestionList.setModel(suggestionListModel);
        suggestionList.setVisibleRowCount(5);

        JScrollPane suggestionScrollPane = new JScrollPane(suggestionList);
        suggestionScrollPane.setBounds(15, 620, 500, 100);
        bgPanel.add(suggestionScrollPane);
        suggestionScrollPane.setVisible(false);

        // Add KeyAdapter for autocomplete functionality
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

        // Add ActionListener for selecting a suggestion
        suggestionList.addListSelectionListener(e -> {
            if (!suggestionList.isSelectionEmpty()) {
                sentences.setText(suggestionList.getSelectedValue());
                suggestionScrollPane.setVisible(false);
            }
        });

        // Add ActionListener for the Show Medication button
        converter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredDiseaseName = sentences.getText();
                String medValue = Med.get(enteredDiseaseName);
                medT.setText(medValue != null ? medValue : "Not found");
            }
        });

        // Add ActionListener for the Transfer button
        transfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String medication = medT.getText();
                String diseaseName = sentences.getText();
                String formattedText = diseaseName + " (Medication)-->(" + medication + ")\n\n";
                container.append(formattedText);
                medT.setText("");
                sentences.setText("");
            }
        });

        // Add ActionListener for the Clear button (for container)
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.setText("");
            }
        });

        // Add ActionListener for the Back button
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

    private void loadDiseaseToCodeMapFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String diseaseName = parts[0];
                    String medication = parts[1];
                    Med.put(diseaseName, medication);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Medication());
    }
}
