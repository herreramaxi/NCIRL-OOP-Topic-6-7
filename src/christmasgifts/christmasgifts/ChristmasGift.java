package christmasgifts.christmasgifts;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.common.base.Strings;
import java.io.Serializable;

/**
 *
 * @author Maximiliano Herrera
 */
public class ChristmasGift implements Serializable {

    private String recipient;
    private String idea;
    private String url;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getIdea() {
        return idea;
    }

    public String getUrl() {
        return url;
    }

    public ChristmasGift(String recipient, String idea, String url) {
        this.recipient = recipient;
        this.idea = idea;
        this.url = url;
    }

    public Validation Validate() {
        Validation validation = new Validation();

        return Strings.isNullOrEmpty(recipient)
                || Strings.isNullOrEmpty(idea)
                || Strings.isNullOrEmpty(url)
                ? validation.setAsFailed("Please fill mandatory fields: Recipient, Idea and URL.")
                : validation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipient: ").append(recipient).append("\n");
        sb.append("Idea: ").append(idea).append("\n");
        sb.append("URL: ").append(url);

        return sb.toString();
    }
}
