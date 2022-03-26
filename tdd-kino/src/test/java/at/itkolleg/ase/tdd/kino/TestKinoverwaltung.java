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

    @Test
    @DisplayName("Erfolgreiches Einplanen einer Vorstellung")
    void vorstellungEinplanen_korrekteInputParameter_vorstellungHinzugefuegt() {
        //When
        kinoVerwaltung.einplanenVorstellung(vorstellung);

        //Then
        Assertions.assertEquals(kinoVerwaltung.getVorstellungen().size(), 1);
    }

    @Test
    @DisplayName("Vorstellung bereits eingeplant")
    void vorstellungEinplanen_fehlerhafteInputParameter_exceptionVorstellungBereitsEingeplant() {
        //When
        kinoVerwaltung.einplanenVorstellung(vorstellung);

        //Then
        Assertions.assertThrows(IllegalArgumentException.class, ()-> kinoVerwaltung.einplanenVorstellung(vorstellung), "Die Vorstellung ist bereits eingeplant");
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
     * und schon auf Logik innerhalb der Vorstellungsklasse prüft (return)
     */
    @Test
    @DisplayName("Ticket kaufen erfolgreich")
    void kaufeTicket_korrekteInputParameter_ticket() {
        //When
        Ticket ticket = Mockito.mock(Ticket.class);
        Mockito.when(vorstellung.kaufeTicket(
                Mockito.any(Character.class),
                Mockito.any(Integer.class),
                Mockito.any(Float.class))).thenReturn(ticket);

        //When & Then
        Assertions.assertInstanceOf(Ticket.class, kinoVerwaltung.kaufeTicket(vorstellung, 'C', 10, 12));
    }

    /**
     * Eigentlich nicht nötig bzw. richtig, da die Methode keine wirkliche Logik enthält,
     * und schon auf Logik innerhalb der Vorstellungsklasse prüft (Exception)
     */
    @Test
    @DisplayName("Ticket kaufen fehlerhaft")
    void kaufeTicket_fehlerhafteInputParameter_exception() {
        //When
        Ticket ticket = Mockito.mock(Ticket.class);
        Mockito.when(vorstellung.kaufeTicket(
                Mockito.any(Character.class),
                Mockito.any(Integer.class),
                Mockito.any(Float.class))).thenThrow(RuntimeException.class);

        //When & Then
        Assertions.assertThrows(RuntimeException.class, ()-> kinoVerwaltung.kaufeTicket(vorstellung, 'C', 10, 12));
    }
}
