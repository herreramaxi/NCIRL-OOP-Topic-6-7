package christmasgifts.christmasgifts;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Maximiliano Herrera
 */
public class Validation {

    public boolean successful() {
        return successful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private boolean successful;
    private String errorMessage;

    public Validation() {
        successful = true;
    }

    public Validation setAsFailed(String errorMessage) {
        this.successful = false;
        this.errorMessage = errorMessage;

        return this;
    }
}
