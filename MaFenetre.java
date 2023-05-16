import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;


public class MaFenetre implements ActionListener {
  private JButton bouton;

  public MaFenetre() {
    JFrame frame = new JFrame("Un bouton");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    bouton = new JButton("Sauvegarder");
    bouton.addActionListener(this); // Ajout de l'écouteur d'événements
    frame.add(bouton);

    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == bouton) {
      sauvegarderPartie();
    }
  }

  private void sauvegarderPartie() {
    String message = "Palu Menfou";
    try {
      FileWriter writer = new FileWriter("save.txt");
      writer.write(message);
      writer.close();
      System.out.println("Fichier save.txt cree avec succes.");
    } catch (IOException ex) {
      System.out.println("Erreur lors de la création du fichier save.txt : " + ex.getMessage());
    }
  }
}
