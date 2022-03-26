package at.itkolleg.ase.tdd.kino;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

/**
 * Testklasse für die Klasse Vorstellung
 */
@DisplayName("Vorstellung Tests")
public class TestVorstellung {

    private KinoSaal saal;
    private Zeitfenster zeitfenster;
    private LocalDate datum;
    private String film;
    private float preis;
    private List<Ticket> tickets;
    private Vorstellung vorstellung;

    /**
     * Initialisieren der Datenfelder - "Given" auslagern
     * inkl. manuelles initialisieren der Mocks
     */
    @BeforeEach
    void setUp() {
        //Given
        saal = Mockito.mock(KinoSaal.class); //manuelle Initialisierung
        zeitfenster = Zeitfenster.ABEND;
        datum = LocalDate.of(2022, 3, 26);
        film = "Batman";
        preis = 10.5F;

        /* Nicht benötigt
        tickets = new LinkedList<>();
        tickets.add(Mockito.mock(Ticket.class));*/

        vorstellung = new Vorstellung(saal, zeitfenster, datum, film, preis);
    }

    @Test
    @DisplayName("Anlegen einer Vorstellung mit korrekten Parametern")
    void vorstellungAnlegen_korrekteInputParameter_vorstellungAngelegt() {
        //When
        vorstellung = new Vorstellung(saal, zeitfenster, datum, film, preis);
        KinoSaal tempSaal = saal;

        //Then
        Assertions.assertEquals(tempSaal, saal);
        Assertions.assertEquals(zeitfenster, Zeitfenster.ABEND);
        Assertions.assertEquals(datum, LocalDate.of(2022, 3, 26));
        Assertions.assertEquals(film, "Batman");
        Assertions.assertEquals(preis, 10.5);


        /* Eigentlich nicht ganz richtig, da es sich hier um Operationen der Klasse Kinosaal handelt
        //When
        Mockito.when(saal.getName()).thenReturn("Saal 4"); //Bedingung bei einem Mocks-Aufruf festlegen
        Mockito.when(saal.pruefePlatz(Mockito.any(Character.class), Mockito.any(Integer.class))).thenReturn(true);

        //Then
        Assertions.assertEquals(saal.getName(), "Saal 4");
        Assertions.assertTrue(saal.pruefePlatz('F', 20));*/


        /* Nicht benötigt, da es nichts mit dem Erstellen vom Objekt zu tun hat
        //When
        Mockito.when(tickets.get(0).getPlatz()).thenReturn(6); //Mockito.any(Integer.class)
        Mockito.when(tickets.get(0).getReihe()).thenReturn('E');

        //Then
        Assertions.assertEquals(tickets.get(0).getPlatz(), 6);
        Assertions.assertEquals(tickets.get(0).getReihe(), 'E');*/
    }

    @Test
    @DisplayName("Erfolgreiches holen des Films")
    void getFilmErfolgreich() {
        //When
        film = vorstellung.getFilm();

        //Then
        Assertions.assertEquals("Batman", film);
    }

    @Test
    @DisplayName("Erfolgreiches holen des Saals")
    void getSaalErfolgreich() {
        //When
        KinoSaal saalGet = vorstellung.getSaal();

        //Then
        Assertions.assertEquals(saal, saalGet);
    }

    @Test
    @DisplayName("Erfolgreiches holen des Zeitfensters")
    void getZeitfensterErfolgreich() {
        //When
        zeitfenster = vorstellung.getZeitfenster();

        //Then
        Assertions.assertEquals(zeitfenster, Zeitfenster.ABEND);
    }

    @Test
    @DisplayName("Erfolgreiches holen des Datums")
    void getDatumErfolgreich() {
        //When
        datum = vorstellung.getDatum();

        //Then
        Assertions.assertEquals(datum, LocalDate.of(2022, 3, 26));
    }

    @Test
    @DisplayName("Ticket kaufen erfolgreich")
    void kaufeTicket_korrekteInputParameter_ticket() {
        //When
        Mockito.when(saal.pruefePlatz(Mockito.any(Character.class), Mockito.any(Integer.class))).thenReturn(true);

        //When & Then
        Assertions.assertInstanceOf(Ticket.class, vorstellung.kaufeTicket('E', 12, 15));
        Assertions.assertEquals(vorstellung.getTickets().size(), 1);
    }

    @Test
    @DisplayName("Ticket kaufen nicht erfolgreich, zu wenig Geld")
    void kaufeTicket_fehlerhafteInputParameter_exceptionZuWenigGeld() {
        //When
        Mockito.when(saal.pruefePlatz(Mockito.any(Character.class), Mockito.any(Integer.class))).thenReturn(true);

        //Then
        Assertions.assertThrows(IllegalArgumentException.class, ()-> vorstellung.kaufeTicket('E', 12, 5), "Nicht ausreichend Geld.");
    }

    @Test
    @DisplayName("Ticket kaufen nicht erfolgreich, Platz existiert nicht")
    void kaufeTicket_fehlerhafteInputParameter_exceptionPlatzExistiertNicht() {
        //When
        Mockito.when(saal.pruefePlatz(Mockito.any(Character.class), Mockito.any(Integer.class))).thenReturn(false);

        //Then
        Assertions.assertThrows(IllegalArgumentException.class, ()-> vorstellung.kaufeTicket('E', 12, 15));
    }

    @Test
    @DisplayName("Ticket kaufen nicht erfolgreich, Platz bereits belegt")
    void kaufeTicket_fehlerhafteInputParameter_exceptionPlatzBereitsBelegt() {
        //When
        Mockito.when(saal.pruefePlatz(Mockito.any(Character.class), Mockito.any(Integer.class))).thenReturn(true);
        vorstellung.kaufeTicket('E', 12, 15);

        //Then
        Assertions.assertThrows(IllegalStateException.class, ()-> vorstellung.kaufeTicket('E', 12, 15));
    }
}
