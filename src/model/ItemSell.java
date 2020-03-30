package model;

import java.util.List;

public class ItemSell {
    private String productTitle;
    private List<String> listImageLinks;

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public List<String> getListImageLinks() {
        return listImageLinks;
    }

    public void setListImageLinks(List<String> listImageLinks) {
        this.listImageLinks = listImageLinks;
    }
}
