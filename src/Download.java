import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.text.html.parser.Parser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Download {
    private JPanel panel1;
    private JButton btnClear;
    private JTextArea textArea1;
    private JButton btnDownLoad;
    private JPanel jpanel2;
    private String link = "https://www.amazon.com/Stanco-Hotpoint-Electric-Reflector-Locking/dp/B001TH7H04?th=1&fbclid=IwAR1tLre7gYcki56g5EtL4ystmyEvV_RhuJ_XtbYH-JTy3LP1lLDIflXuFeI";
    public Download() {
        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                textArea1.setText("");
                GetURLContent(link);
            }
        });
        btnDownLoad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String links = textArea1.getText();
                String[] linksArray = links.split("\n");

                for (String link : linksArray){
                    System.out.println(link);
                }
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Download");
        frame.setContentPane(new Download().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    private void GetURLContent(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select("span#productTitle");
        for (Element headline : newsHeadlines) {
            System.out.println( "TITLE"+headline.childNode(0).outerHtml());
        }

    }
}
