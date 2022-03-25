package at.itkolleg.ase.tdd.kino;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Testklasse für die Klasse Kinosaal
 */
@DisplayName("Kinosaal Tests")
public class TestKinoSaal {

    //Datenfelder deklarieren
    private String name;
    private Map<Character, Integer> reihen;
    private KinoSaal kinoSaal1;

    /**
     * Initialisieren der Datenfelder - "Given" auslagern
     */
    @BeforeEach
    public void setUp() {
        //Given
        name = "Saal 1";
        reihen = new HashMap<>();
        reihen.put('A', 15);
        reihen.put('B', 15);
        reihen.put('C', 15);
        reihen.put('D', 20);
        reihen.put('E', 20);

        kinoSaal1 = new KinoSaal(name, reihen);
    }

    @Test
    @DisplayName("Anlegen eines Kinosaals mit korrekten Parametern")
    public void saalAnlegen_korrekteInputParameter_saalAngelegt() {
        //When
        kinoSaal1 = new KinoSaal(name, reihen);

        //Then
        Assertions.assertEquals("Saal 1", name);
        Assertions.assertEquals(15, reihen.get('A'));
        Assertions.assertEquals(15, reihen.get('B'));
        Assertions.assertEquals(15, reihen.get('C'));
        Assertions.assertEquals(20, reihen.get('D'));
        Assertions.assertEquals(20, reihen.get('E'));
    }

    @Test
    @DisplayName("Anlegen eines Kinosaals mit falschen Parametern")
    public void saalAnlegen_falscheInputParameter_saalNichtAngelegt() {
        //When
        kinoSaal1 = new KinoSaal(name, reihen);

        //Then
        Assertions.assertNotEquals("Saal 2", name);
        Assertions.assertNotEquals(0, reihen.get('A'));
        Assertions.assertNotEquals(5, reihen.get('B'));
        Assertions.assertNotEquals(10, reihen.get('C'));
        Assertions.assertNotEquals(15, reihen.get('D'));
        Assertions.assertNotEquals(0, reihen.get('E'));
    }

    @Test
    @DisplayName("Erfolgreiches holen eines Namens")
    public void getNameErfolgreich() {
        //When
        name = kinoSaal1.getName();

        //Then
        Assertions.assertTrue("Saal 1".equals(name));
    }

    @Test
    @DisplayName("Fehlerhaftes holen eines Namens")
    public void getNameFehlerhaft() {
        //When
        name = kinoSaal1.getName();

        //Then
        Assertions.assertFalse("Saal 2".equals(name));
    }


    @Test
    @DisplayName("Platz prüfen erfolgreich")
    public void pruefePlatz_korrekteInputParameter_platzErfolgreichGeprueft() {
        //When
        boolean platzKorrekt1 = kinoSaal1.pruefePlatz('A', 15);
        boolean platzKorrekt2 = kinoSaal1.pruefePlatz('B', 10);
        boolean platzKorrekt3 = kinoSaal1.pruefePlatz('C', 1);

        //Then
        Assertions.assertTrue(platzKorrekt1);
        Assertions.assertTrue(platzKorrekt2);
        Assertions.assertTrue(platzKorrekt3);
    }

    @Test
    @DisplayName("Platz prüfen fehlerhaft")
    public void pruefePlatz_falscheInputParameter_platzFehlerhaftGeprueft() {
        //When
        boolean platzKorrekt1 = kinoSaal1.pruefePlatz('A', 20);
        boolean platzKorrekt2 = kinoSaal1.pruefePlatz('B', -5); //--> überarbeiten
        boolean platzKorrekt3 = kinoSaal1.pruefePlatz('C', 0);

        //Then
        Assertions.assertFalse(platzKorrekt1);
        Assertions.assertFalse(platzKorrekt2);
        Assertions.assertFalse(platzKorrekt3);
    }
}
