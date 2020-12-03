/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package christmasgifts.christmasgifts;

import Interfaces.IObserverPush;
import Interfaces.ISubject;
import Interfaces.IObserverPull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Maximiliano Herrera
 */
public class GiftList extends ArrayList<ChristmasGift> implements ISubject {

    private final String FILE_PATH = "giftList";
    private final ArrayList<IObserverPull> observersPull;
    private final ArrayList<IObserverPush> observersPush;

    public GiftList() {
        observersPull = new ArrayList<IObserverPull>();
        observersPush = new ArrayList<IObserverPush>();
    }

    @Override
    public boolean add(ChristmasGift e) {
        boolean returnValue = super.add(e);
        this.Notify();

        return returnValue;
    }

    @Override
    public boolean remove(Object o) {
        boolean returnValue = super.remove(o);
        this.Notify();

        return returnValue;
    }

    @Override
    public boolean addAll(Collection<? extends ChristmasGift> clctn) {
        boolean returnValue = super.addAll(clctn);
        this.Notify();

        return returnValue;
    }

    public void searchByRecipient(String recipient) {
        List<ChristmasGift> filteredQuestions = this.stream()
                .filter((q) -> q.getRecipient().toLowerCase().contains(recipient.toLowerCase()))
                .collect(Collectors.toList());

        this.Notify(new ArrayList<>(filteredQuestions));
    }

    @Override
    public void attach(IObserverPull observer) {
        observersPull.add(observer);
    }

    @Override
    public void attach(IObserverPush observer) {
        observersPush.add(observer);
    }

    @Override
    public void Notify() {
        //Observer pull model
        observersPull.forEach((o) -> o.update());
    }

    public void Notify(ArrayList<ChristmasGift> filteredQuestions) {
        //Observer push model
        observersPush.forEach((o) -> o.update(filteredQuestions));
    }

    public void save() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        ArrayList<ChristmasGift> list = new ArrayList<>(this);
        objectOut.writeObject(list);
        objectOut.close();
    }

    public void load() throws IOException, ClassNotFoundException, FileNotFoundException {

        FileInputStream fileOut = new FileInputStream(FILE_PATH);
        ObjectInputStream object = new ObjectInputStream(fileOut);

        this.clear();
        this.addAll((ArrayList<ChristmasGift>) object.readObject());

        object.close();
    }
}
