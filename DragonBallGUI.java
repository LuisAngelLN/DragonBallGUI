import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import org.json.*;

public class DragonBallGUI extends JFrame implements ActionListener {
    private JTextField idField;
    private JButton searchButton;
    private JTextArea resultArea;

    public DragonBallGUI() {
        setTitle("Dragon Ball Character Info");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        idField = new JTextField(10);
        add(new JLabel("Enter Character ID:"));
        add(idField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        add(searchButton);

        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == searchButton) {
            String characterID = idField.getText();
            try {
                URL url = new URL("https://dragonball-api.com/api/characters/" + characterID);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JSONObject character = new JSONObject(content.toString());
                String name = character.getString("name");
                String ki = character.getString("ki");
                String race = character.getString("race");
                String gender = character.getString("gender");
                String description = character.getString("description");

                resultArea.setText("Name: " + name + "\n"
                                    + "Ki: " + ki + "\n"
                                    + "Race: " + race + "\n"
                                    + "Gender: " + gender + "\n"
                                    + "Description: " + description);
            } catch (Exception e) {
                resultArea.setText("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DragonBallGUI().setVisible(true);
            }
        });
    }
}
