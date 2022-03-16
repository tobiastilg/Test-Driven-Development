package at.itkolleg.ase.tdd.kino;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Vorstellung {

    private final KinoSaal saal;

    private final Zeitfenster zeitfenster;

    private final LocalDate datum;

    private final String film;

    private final float preis;

    private final List<Ticket> tickets = new LinkedList<>();

    public Vorstellung(KinoSaal saal, Zeitfenster zeitfenster, LocalDate datum, String film, float preis) {
        this.saal = saal;
        this.zeitfenster = zeitfenster;
        this.datum = datum;
        this.film = film;
        this.preis = preis;
    }

    public String getFilm() {
        return film;
    }

    public KinoSaal getSaal() {
        return saal;
    }

    public Zeitfenster getZeitfenster() {
        return zeitfenster;
    }

    public LocalDate getDatum() {
        return datum;
    }

    Ticket kaufeTicket(char reihe, int platz, float geld) {
        if (geld < preis) {
            throw new IllegalArgumentException("Nicht ausreichend Geld.");
        }
        if (!saal.pruefePlatz(reihe, platz)) {
            throw new IllegalArgumentException("Der Platz " + reihe + platz + " existiert nicht.");
        }
        if (tickets.stream().anyMatch(t -> t.getReihe() == reihe && t.getPlatz() == platz)) {
            throw new IllegalStateException("Der Platz " + reihe + platz + " ist bereits belegt.");
        }
        Ticket ticket = new Ticket(saal.getName(), zeitfenster, datum, reihe, platz);
        tickets.add(ticket);
        return ticket;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vorstellung)) {
            return false;
        }
        Vorstellung otherV = (Vorstellung) obj;
        return saal.equals(otherV.getSaal()) && zeitfenster == otherV.getZeitfenster() && datum.isEqual(otherV.getDatum());
    }

}
