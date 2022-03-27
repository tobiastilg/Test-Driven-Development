package at.itkolleg.ase.tdd.kino;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

/**
 * Testklasse für die Klasse Vorstellung
 */
@DisplayName("Kinoverwaltung Tests")
@ExtendWith(MockitoExtension.class) //macht automatische Initialisierung möglich (es wird auch kein AutoCloseable mehr benötigt)
public class TestKinoverwaltung {

    @Mock //automatische Initialisierung
    private Vorstellung vorstellung;
    private List<Vorstellung> vorstellungen;
    private KinoVerwaltung kinoVerwaltung;

    @BeforeEach
    void setUp() {
        //Given
        kinoVerwaltung = new KinoVerwaltung();
        vorstellungen = new LinkedList<>();
    }

    /**
     * Aufgabe 7 - Übung 1
     */
    @Test
    @DisplayName("Erfolgreiches Einplanen einer Vorstellung")
    void vorstellungEinplanen_korrekteInputParameter_vorstellungHinzugefuegt() {
        //When
        kinoVerwaltung.einplanenVorstellung(vorstellung);

        //Then
        Assertions.assertEquals(kinoVerwaltung.getVorstellungen().size(), 1, "Falsche Anzahl an Vorstellungen eingeplant.");
        Assertions.assertEquals(kinoVerwaltung.getVorstellungen().get(0), vorstellung, "Vorstellung konnte nicht eingeplant werden.");
    }

    /**
     * Aufgabe 7 - Übung 2
     */
    @Test
    @DisplayName("Erfolgreiches Einplanen mehrerer Vorstellungen")
    void vorstellungenEinplanen_korrekteInputParameter_vorstellungenHinzugefuegt() {
        //Given - manuelles initialisieren lokaler Mocks
        Vorstellung vorstellung2 = Mockito.mock(Vorstellung.class);
        Vorstellung vorstellung3 = Mockito.mock(Vorstellung.class);

        //When
        kinoVerwaltung.einplanenVorstellung(vorstellung);
        kinoVerwaltung.einplanenVorstellung(vorstellung2);
        kinoVerwaltung.einplanenVorstellung(vorstellung3);

        //Then
        Assertions.assertAll("Vorstellungen einplanen",
                ()-> Assertions.assertEquals(kinoVerwaltung.getVorstellungen().size(), 3, "Falsche Anzahl an Vorstellungen eingeplant."),
                ()-> Assertions.assertEquals(kinoVerwaltung.getVorstellungen().get(0), vorstellung, "Erste Vorstellung konnte nicht eingeplant werden."),
                ()-> Assertions.assertEquals(kinoVerwaltung.getVorstellungen().get(1), vorstellung2, "Zweite Vorstellung konnte nicht eingeplant werden."),
                ()-> Assertions.assertEquals(kinoVerwaltung.getVorstellungen().get(2), vorstellung3, "Dritte Vorstellung konnte nicht eingeplant werden.")
        );

        Assertions.assertEquals(kinoVerwaltung.getVorstellungen().size(), 3, "Falsche Anzahl an Vorstellungen eingeplant.");
        Assertions.assertEquals(kinoVerwaltung.getVorstellungen().get(0), vorstellung, "Vorstellung nicht eingeplant.");
    }

    /**
     * Aufgabe 7 - Übung 3
     */
    @Test
    @DisplayName("Vorstellung bereits eingeplant")
    void vorstellungEinplanen_fehlerhafteInputParameter_exceptionVorstellungBereitsEingeplant() {
        //When
        kinoVerwaltung.einplanenVorstellung(vorstellung);

        //Then
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, ()-> kinoVerwaltung.einplanenVorstellung(vorstellung), "Die Vorstellung ist noch nicht eingeplant");
        Assertions.assertEquals(exception.getMessage(), "Die Vorstellung ist bereits eingeplant");
    }

    /**
     * Getter-Tests an sich nicht nötig bzw. sinnvoll, sofern keine weitere Logik darin existiert
     * (die Getter-Methode wird nämlich zB in anderen Tests benötigt)
     */
    @Test
    @DisplayName("Erfolgreiches holen der Vorstellungen")
    void getVorstellungenErfolgreich() {
        //When
        List<Vorstellung> vorstellungenGet = kinoVerwaltung.getVorstellungen();

        //Then
        Assertions.assertEquals(vorstellungen, vorstellungenGet);
    }

    /**
     * Eigentlich nicht nötig bzw. richtig, da die Methode keine wirkliche Logik enthält,
     * und schon auf Logik innerhalb der Vorstellungsklasse geprüft wird (return)
     */
    @Test
    @DisplayName("Ticket kaufen erfolgreich")
    void kaufeTicket_korrekteInputParameter_ticket() {
        //Given
        Ticket ticket = Mockito.mock(Ticket.class);

        //When
        Mockito.when(vorstellung.kaufeTicket(
                Mockito.any(Character.class),
                Mockito.any(Integer.class),
                Mockito.any(Float.class))).thenReturn(ticket);

        //When & Then
        Assertions.assertInstanceOf(Ticket.class, kinoVerwaltung.kaufeTicket(vorstellung, 'C', 10, 12));
    }

    /**
     * Eigentlich nicht nötig bzw. richtig, da die Methode keine wirkliche Logik enthält,
     * und schon auf Logik innerhalb der Vorstellungsklasse geprüft wird (Exception)
     */
    @Test
    @DisplayName("Ticket kaufen fehlerhaft")
    void kaufeTicket_fehlerhafteInputParameter_exception() {
        //Given
        Ticket ticket = Mockito.mock(Ticket.class);

        //When
        Mockito.when(vorstellung.kaufeTicket(
                Mockito.any(Character.class),
                Mockito.any(Integer.class),
                Mockito.any(Float.class))).thenThrow(RuntimeException.class);

        //When & Then
        Assertions.assertThrows(RuntimeException.class, ()-> kinoVerwaltung.kaufeTicket(vorstellung, 'C', 10, 12));
    }
}
