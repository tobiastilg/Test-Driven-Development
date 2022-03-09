import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class KommentarTest {

    //Datenfelder für setUp() Methode
    String kommentartext;
    String autor;
    int bewertung;
    Kommentar k1;

    /**
     * Stellt Baseline zur Verfügung (Lifecycle-Method)
     */
    @BeforeEach
    public void setUp(){
        kommentartext = "Das ist mein Kommentartext";
        autor = "Tobias Tilg";
        bewertung = 3;
        k1 = new Kommentar(kommentartext, autor, bewertung);
    }

    @Test //damit er ausführbar ist - IntelliJ ist JUnit optimiert
    public void testAnlegen_korrekteInputParameter_kommentarAngelegt() {
        //Given - nicht setUp() verwendet, da es im Test selbst ja ums Anlegen der Klasse (Datenfelder und Konstruktor geht)
        String kommentartext = "Das ist mein Kommentartext";
        String autor = "Tobias Tilg";
        int bewertung = 3;

        //When
        Kommentar k1 = new Kommentar(kommentartext, autor, bewertung);

        //Then
        Assertions.assertEquals("Das ist mein Kommentartext", k1.getKommentarText());
        Assertions.assertEquals(0, k1.getStimmen());
        Assertions.assertEquals("Tobias Tilg", k1.getAutor());
        Assertions.assertEquals(3, k1.getBewertung());
    }

    @Test
    public void testAnlegen_falscheInputParameter_kommentarAngelegt() {
        //Given
        String kommentartext = "Das ist mein Kommentartext";
        String autor = "Tobias Tilg";
        int bewertung = 3;


        //Then
        /* Eigene Exception bieten sich hier natürlich an
          Lambda Ausdruck definiert die Methode, durch welche die Exception geschmissen werden sollte
         */
        Assertions.assertThrows(InvalidConstructionParameterException.class, ()->new Kommentar(null, autor, bewertung));
        Assertions.assertThrows(InvalidConstructionParameterException.class, ()->new Kommentar(kommentartext, null, bewertung));
        Assertions.assertThrows(InvalidConstructionParameterException.class, ()->new Kommentar(kommentartext, autor, 0));
        Assertions.assertThrows(InvalidConstructionParameterException.class, ()->new Kommentar(kommentartext, autor, 6));
    }

    @Test
    public void testAnlegen_falscheInputParameter_kommentarAngelegt_v2() {
        //Given
        String kommentartext = "Das ist mein Kommentartext";
        String autor = "Tobias Tilg";
        int bewertung = 3;


        //Sammeln der AssertionErrors - besser lesbarer Testcode
        ArrayList<Throwable> al = new ArrayList<>();

        try {
            Assertions.assertThrows(InvalidConstructionParameterException.class, ()->new Kommentar(null, autor, bewertung));
        } catch (AssertionError e1) {
            al.add(e1);
        }
        try {
            Assertions.assertThrows(InvalidConstructionParameterException.class, ()->new Kommentar(kommentartext, null, bewertung));
        } catch (AssertionError e1) {
            al.add(e1);
        }
        Assertions.assertThrows(InvalidConstructionParameterException.class, ()->new Kommentar(kommentartext, autor, 0));
        Assertions.assertThrows(InvalidConstructionParameterException.class, ()->new Kommentar(kommentartext, autor, 6));

        //Prüfen mehrerer AssertionErrors zur gleichen Zeit - somit sieht mah mehrere Fehler in den Tests bei einem Durchlauf
        if (al.size() > 0) {
            System.out.println(al);
            Assertions.fail();
        }
    }

    @Test
    public void testKommentarZustimmen() {
        //Given
        //siehe setUp()

        //When
        Kommentar k1 = new Kommentar(kommentartext, autor, bewertung);

        //Then
        Assertions.assertEquals(0, k1.getStimmen());

        //When
        k1.zustimmen();

        //Then
        Assertions.assertEquals(1, k1.getStimmen());
    }

    @AfterEach
    public void afterEachTest(){
        System.out.println("Hallo After Each!");
    }
}
