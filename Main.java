import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame {

    private String path1;
    private String path2;

    public Main() {

        Component component = this;

        setTitle("First app");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 200, 200);
        setLayout(new GridLayout(1,3));

        JButton buttonHandleThis = new JButton("-");
        buttonHandleThis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handlePdf(path1, path2);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton buttonFileExplorer1 = new JButton("Directory");
        buttonFileExplorer1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(component);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    path1 = selectedFile.getAbsolutePath();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });

        JButton buttonFileExplorer2 = new JButton("File 2");
        buttonFileExplorer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(component);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    path2 = selectedFile.getAbsolutePath();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });

        add(buttonHandleThis);
        add(buttonFileExplorer1);
        add(buttonFileExplorer2);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    public static void handlePdf(String path1, String path2) throws IOException {

        File directory = new File(path1);
        File[] pdfFiles = directory.listFiles();

        File file2 = new File(path2);

        for (File file1 : pdfFiles) {
            PDDocument document1 = PDDocument.load(file1);
            PDDocument document2 = PDDocument.load(file2);

            PDPageTree pages = document2.getPages();

            for (PDPage page : pages) {
                document1.addPage(page);
            }
            //Saving the document
            document1.save(file1.getAbsolutePath());
            //Closing the document
            document1.close();
            document2.close();
        }

    }

}
