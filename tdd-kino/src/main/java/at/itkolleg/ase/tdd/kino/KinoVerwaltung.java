package at.itkolleg.ase.tdd.kino;

import java.util.LinkedList;
import java.util.List;

public class KinoVerwaltung {

    private final List<Vorstellung> vorstellungen = new LinkedList<>();

    public void einplanenVorstellung(Vorstellung vorstellung) {
        if (vorstellungen.contains(vorstellung)) {
            throw new IllegalArgumentException("Die Vorstellung ist bereits eingeplant");
        }
        vorstellungen.add(vorstellung);
    }

    public List<Vorstellung> getVorstellungen() {
        return vorstellungen;
    }

    public Ticket kaufeTicket(Vorstellung vorstellung, char reihe, int platz, float geld) {
        //Hier sollte man eigentlich noch prüfen, ob die Vorstellung überhaupt eingeplant ist (Businesslogik?)
        return vorstellung.kaufeTicket(reihe, platz, geld);
    }


}
