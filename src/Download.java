import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.ProgressBar;
import model.ItemSell;
import model.LinkImage;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Download {
    private JPanel panel1;
    private JButton btnClear;
    private JTextArea textArea1;
    private JButton btnDownLoad;
    private JPanel jpanel2;
    private String link = "https://www.amazon.com/Stanco-Hotpoint-Electric-Reflector-Locking/dp/B001TH7H04?th=1&fbclid=IwAR1tLre7gYcki56g5EtL4ystmyEvV_RhuJ_XtbYH-JTy3LP1lLDIflXuFeI";
    List<ItemSell> listAllItem = new ArrayList<>();

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
                    List<ItemSell> itemSellList = GetURLContent(link);
                    listAllItem.addAll(itemSellList);
                }
                System.out.println("Result");
                Gson gson = new Gson();
                System.out.println(gson.toJson(listAllItem));
                textArea1.setText(textArea1.getText()+"\n FINISH");

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
        centreWindow(frame);

    }
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, 0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
    private List<ItemSell> GetURLContent(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ItemSell> itemSellList = new ArrayList<>();
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select("span#productTitle");
        String productTitle = newsHeadlines.get(0).childNode(0).outerHtml();

        for (Element script : doc.getElementsByTag("script")) {
            String type = script.attr("type");
            if (type.contentEquals("text/javascript") && script.outerHtml().contains("hiRes") &&
                script.outerHtml().contains("colorImages") && script.outerHtml().contains("dataInJson")) {
                String scriptData = script.data(); // your text from the script
                String[] arrayJavaS = scriptData.split("\n");

                for (String jsItem : arrayJavaS){
                    if(jsItem.contains("jQuery.parseJSON('")){
                        System.out.println("=");
                        String jsonString = StringUtils.substringBetween(jsItem, "jQuery.parseJSON('", "');");

                        LinkedTreeMap<?, ?> map = getLinkedTreeMap(jsonString);

                        LinkedTreeMap<String, Object> colorImages = (LinkedTreeMap<String, Object>) map.get("colorImages");
                        for (Object key : colorImages.keySet()) {
                            ItemSell itemSell = new ItemSell();
                            itemSell.setProductTitle(productTitle);
                            itemSell.setVariantTitle((String) key);
                            System.out.println("key "+key);
                            itemSell.setProductTitle((String) key);
                            List<LinkImage> listImageLinks = new ArrayList<>();

                            List<LinkedTreeMap<?, ?>> listLinks = (List<LinkedTreeMap<?, ?>>) colorImages.get(key);
                            for (LinkedTreeMap<?, ?> item : listLinks) {
                                for (Object keyLink : item.keySet()) {
                                    if(keyLink.equals("hiRes")){
                                        String linkHires = (String) item.get(keyLink);
                                        System.out.println("link HIRES "+linkHires);
                                        String hiresDemen = StringUtils.substringBetween((String) item.get(keyLink),"._AC_","_.");
                                        System.out.println("demension Hires "+hiresDemen);

                                        LinkImage linkImageHiRes = new LinkImage();
                                        linkImageHiRes.setHiresDemen(hiresDemen);
                                        linkImageHiRes.setLink(linkHires);
                                        linkImageHiRes.setLinkType(LinkImage.LinkType.HIRES);
                                        listImageLinks.add(linkImageHiRes);
                                    }
                                    String linkLarge = (String) item.get("large");
                                    if(keyLink.equals("large")){
                                        System.out.println("link large "+linkLarge);
                                        LinkImage linkImageLarge = new LinkImage();
                                        linkImageLarge.setLink(linkLarge);
                                        linkImageLarge.setLinkType(LinkImage.LinkType.LARGER);
                                        listImageLinks.add(linkImageLarge);
                                    }
                                }
                            }
                            itemSell.setListImageLinks(listImageLinks);
                            itemSellList.add(itemSell);

                        }
                    }

                }

            }
        }
        return itemSellList;
    }
    public static LinkedTreeMap<?, ?> getLinkedTreeMap(final String body) {
        Gson gson = new Gson();
        final LinkedTreeMap<?, ?> map = gson.fromJson(body, new TypeToken<LinkedTreeMap<?, ?>>() {}.getType());
        if (map.get("d") instanceof LinkedTreeMap<?, ?>) {
            return (LinkedTreeMap<?, ?>) map.get("d");
        } else {
            return map;
        }
    }

}
