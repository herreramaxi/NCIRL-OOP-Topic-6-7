package christmasgifts.UI;

import christmasgifts.christmasgifts.ChristmasGift;
import christmasgifts.christmasgifts.GiftList;
import christmasgifts.christmasgifts.UIComponentObserverPull;
import christmasgifts.christmasgifts.UIComponentObserverPush;
import christmasgifts.christmasgifts.Validation;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Maximiliano Herrera
 */
public class UIMediator {

    private final MainJFrame mainFrame;
    private GiftList gifts;
    private final JTable jTable;

    public UIMediator(MainJFrame mainFrame, JTable table) {
        this.mainFrame = mainFrame;
        jTable = table;
        gifts = new GiftList();
        gifts.attach(new UIComponentObserverPush((aGiftList) -> this.updateTableModel(aGiftList)));
        gifts.attach(new UIComponentObserverPull(() -> this.updateTableModel(this.gifts)));
    }

    public void addGift() {
        String recipient = this.mainFrame.getRecipient();
        String idea = this.mainFrame.getIdea();
        String url = this.mainFrame.getUrl();

        ChristmasGift gift = new ChristmasGift(recipient, idea, url);

        Validation validation = gift.Validate();

        if (!validation.successful()) {
            this.mainFrame.showErrorMessageDialog(validation.getErrorMessage());
            return;
        }

        ChristmasGift aGift = gifts.stream().max(Comparator.comparing(ChristmasGift::getId)).orElse(null);
        int id = aGift == null ? 1 : aGift.getId() + 1;

        gift.setId(id);
        gifts.add(gift);

        this.mainFrame.initializeControlValues();
        this.mainFrame.cleanSearchFilter();
//        this.updateTableModel(gifts);

        System.out.println("Gift added: ");
        System.out.println(gifts);
    }

    public void displayGift(int id) {
        ChristmasGift gift = gifts.stream()
                .filter((q) -> q.getId() == id)
                .findFirst()
                .orElse(null);

        if (gift != null) {
            mainFrame.showMessageDialog(gift.toString());
        } else {
            mainFrame.showErrorMessageDialog("Error: gift not found");
        }
    }

    public void deleteGift(int id) {
        ChristmasGift gift = gifts.stream()
                .filter((q) -> q.getId() == id)
                .findFirst()
                .orElse(null);

        if (gift != null) {
            gifts.remove(gift);

            String searchText = this.mainFrame.getSearchFilter();
            gifts.searchByRecipient(searchText);
//            ArrayList<ChristmasGift> filteredGifts = this.FilterGiftsByRecipient(searchText);

//            this.updateTableModel(filteredGifts);
        }
    }

    public void initializeFrame() {
        this.mainFrame.setEnabledDeleteButton(false);
        this.mainFrame.setEnabledDisplayButton(false);
    }

    public void load() {
        try {
            this.gifts.load();
        } catch (Exception ex) {
            Logger.getLogger(UIMediator.class.getName()).log(Level.SEVERE, null, ex);
            mainFrame.showErrorMessageDialog("Error when loading gifts: " + ex.getMessage());
        }
    }

    public void save() {
        try {
            this.gifts.save();
        } catch (Exception ex) {
            Logger.getLogger(UIMediator.class.getName()).log(Level.SEVERE, null, ex);
            mainFrame.showErrorMessageDialog("Error when saving gifts: " + ex.getMessage());
        }
    }

    public void searchByRecipient(String recipient) {
        this.gifts.searchByRecipient(recipient);
    }

    private void updateTableModel(ArrayList<ChristmasGift> gifts) {
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        Object[] row = new Object[3];
        model.setRowCount(0);
        for (int i = 0; i < gifts.size(); i++) {
            row[0] = gifts.get(i).getId();
            row[1] = gifts.get(i).getRecipient();
            row[2] = gifts.get(i).getIdea();
            model.addRow(row);
        }
    }

    private ArrayList<ChristmasGift> FilterGiftsByRecipient(String filter) {
        List<ChristmasGift> filteredQuestions = gifts.stream()
                .filter((q) -> q.getRecipient().toLowerCase().contains(filter.toLowerCase()))
                .collect(Collectors.toList());

        return new ArrayList<>(filteredQuestions);
    }
}
