package at.itkolleg.ase.tdd.kino;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Stream;

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
    //private List<Ticket> tickets;
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

        /* Nicht benötigt - wird nicht über Konstruktor mitgegeben und nur intern benötigt
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
        Assertions.assertThrows(IllegalArgumentException.class, ()-> vorstellung.kaufeTicket('E', 12, 5));
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

    /**
     * Aufgabe 7 - Übung 4
     */
    @ParameterizedTest(name = "reihe: {0}, platz: {1}, geld: {2}")
    @CsvSource({"A, 10, 12", "B, 5, 10.5", "C, 15, 15,", "D, 8, 16"}) //Given
    @DisplayName("Mehrere Tickets kaufen erfolgreich")
    void kaufeTickets_korrekteInputParameter_ticket(char reihe, int platz, float geld) {
        /* Wenn man auf fehlerhafte Werte prüfen möchte, dann funktioniert das so nicht.
        *  Das Geld lässt sich zwar ohne Probleme überprüfen und mittels der Mockito.when() Methode auch die
        *  pruefePlatz() Abfrage des Saals, ob das Ticket aber bereits belegt ist kann nicht korrekt getestet werden,
        *  da die Ticketliste des Saals nicht erhalten bleibt und somit jedes Mal neu erzeugt wird.
        *  Hierbei spielt es keine Rolle, ob es sich um einen gemockten Saal oder um ein richtig erzeugtes Objekt handelt.
        */

        //When
        Mockito.when(saal.pruefePlatz(Mockito.any(Character.class), Mockito.any(Integer.class))).thenReturn(true);
        Ticket ticket = vorstellung.kaufeTicket(reihe, platz, geld);

        //Then
        Assertions.assertInstanceOf(Ticket.class, ticket);
        Assertions.assertEquals(ticket.getReihe(), reihe);
        Assertions.assertEquals(ticket.getPlatz(), platz);
    }

    /**
     * Aufgabe 7 - Übung 5
     */
    @TestFactory
    @DisplayName("Dynamisch - Mehrere Tickets kaufen erfolgreich")
    Stream<DynamicTest> kaufeTickets_korrekteInputParameter_ticket() {
        //Given - Lifecycle-Methode setUp() funktioniert bei einer TestFactory nicht
        saal = Mockito.mock(KinoSaal.class); //manuelle Initialisierung
        zeitfenster = Zeitfenster.ABEND;
        datum = LocalDate.of(2022, 3, 26);
        film = "Batman";
        preis = 10.5F;
        vorstellung = new Vorstellung(saal, zeitfenster, datum, film, preis);

        //When & Then
        return new Random(999).ints(0, 1000).limit(100).mapToObj(
            i -> {
                char reihe = (char) ((i % 50) + 50);
                int platz = i % 50;
                int geld = i % 50;

                if (i % 5 == 0) {
                    Mockito.when(saal.pruefePlatz(Mockito.any(Character.class), Mockito.any(Integer.class))).thenReturn(false);
                } else {
                    Mockito.when(saal.pruefePlatz(Mockito.any(Character.class), Mockito.any(Integer.class))).thenReturn(true);
                }

                return DynamicTest.dynamicTest("Ticket kaufen", () -> {
                    try {
                        Assertions.assertInstanceOf(Ticket.class, vorstellung.kaufeTicket(reihe, platz, geld), "Ticketkauf nicht erfolgreich");
                    } catch (IllegalArgumentException illegalArgumentException) {
                        if (illegalArgumentException.getMessage().equals("Nicht ausreichend Geld.")) {
                            Assertions.assertTrue(true, "Nicht ausreichend Geld.");
                            //Message tritt in diesem Fall (auch bei den anderen Exceptions) bei false ein - also sollte sie eigentlich "Ausreichend Geld" heißen (oder?)
                        } else if ((illegalArgumentException.getMessage().contains("existiert nicht")))  {
                            Assertions.assertTrue(true, "Der Platz existiert nicht.");
                        }
                    } catch (IllegalStateException illegalStateException) {
                        if (illegalStateException.getMessage().contains("bereits belegt"))
                            Assertions.assertTrue(true, "Der Platz ist bereits belegt.");
                    }
                });
            }
        );
    }
}
