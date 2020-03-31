package model;

public class LinkImage {
    public enum LinkType {
        HIRES("hires"),
        LARGER("larger");

        private final String formattedValue;

        LinkType(String formattedValue) {
            this.formattedValue = formattedValue;
        }

        public String getFormattedValue() {
            return this.formattedValue;
        }
    }
    private String link;
    private LinkType linkType;
    private String hiresDemen;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
    }

    public String getHiresDemen() {
        return hiresDemen;
    }

    public void setHiresDemen(String hiresDemen) {
        this.hiresDemen = hiresDemen;
    }
}
