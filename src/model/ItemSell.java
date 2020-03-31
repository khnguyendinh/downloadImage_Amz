package model;

import java.util.List;

public class ItemSell {
    private String productTitle;
    private String variantTitle;
    private List<LinkImage> listImageLinks;

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public List<LinkImage> getListImageLinks() {
        return listImageLinks;
    }

    public void setListImageLinks(List<LinkImage> listImageLinks) {
        this.listImageLinks = listImageLinks;
    }

    public String getVariantTitle() {
        return variantTitle;
    }

    public void setVariantTitle(String variantTitle) {
        this.variantTitle = variantTitle;
    }
}
