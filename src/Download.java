import model.ItemSell;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Download {
    private JPanel panel1;
    private JButton btnClear;
    private JTextArea textArea1;
    private JButton btnDownLoad;
    private JPanel jpanel2;
    private String link = "https://www.amazon.com/Stanco-Hotpoint-Electric-Reflector-Locking/dp/B001TH7H04?th=1&fbclid=IwAR1tLre7gYcki56g5EtL4ystmyEvV_RhuJ_XtbYH-JTy3LP1lLDIflXuFeI";
    public Download() {
        textArea1.setText(link+"\n");
        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                textArea1.setText("");

            }
        });
        btnDownLoad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String links = textArea1.getText();
                String[] linksArray = links.split("\n");

                for (String link : linksArray){
                    GetURLContent(link);
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
    private ItemSell GetURLContent(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select("span#productTitle");
//        System.out.println( "TITLE"+newsHeadlines.get(0).childNode(0).outerHtml());
        ItemSell itemSell = new ItemSell();
        itemSell.setProductTitle(newsHeadlines.get(0).childNode(0).outerHtml());
//        Pattern pattern = Pattern.compile("<%=(.*?)%>", Pattern.DOTALL);
//        Pattern pattern = Pattern.compile("var obj = jQuery.parseJSON('=(.*?)');\n"
////                +"data"
//                , Pattern.DOTALL);
//        Matcher matcher = pattern.matcher(doc.outerHtml());
//        while (matcher.find()) {
//            System.out.println(matcher.group(1));
//        }
        for (Element script : doc.getElementsByTag("script")) {
            String type = script.attr("type");
            if (type.contentEquals("text/javascript") && script.outerHtml().contains("hiRes") &&
                    script.outerHtml().contains("colorImages") && script.outerHtml().contains("dataInJson")) {
                String scriptData = script.data(); // your text from the script
                String[] arrayJavaS = scriptData.split("\n");

                for (String jsItem : arrayJavaS){
                    System.out.println("=");
                    System.out.println(jsItem);
                }

//                break;
            }
//            System.out.println(script.outerHtml());
        }
        return itemSell;
    }
}
