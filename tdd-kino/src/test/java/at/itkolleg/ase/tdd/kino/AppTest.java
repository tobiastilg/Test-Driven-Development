package at.itkolleg.ase.tdd.kino;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
@ExtendWith(MockitoExtension.class)
public class AppTest {

    @Mock
    private KinoSaal kinosaalMock; //Mocking Stub zum Testen

    private KinoSaal kinosaalOriginal;

    @BeforeEach
    void setup() {
        //Saal anlegen
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 10);
        map.put('B', 10);
        map.put('C', 15);
        kinosaalOriginal = new KinoSaal("KS2", map);
    }

    @Test
    void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    void testKinosaalMockName() {
        //wenn vom Stub die getName aufgerufen wird, soll KS1 zurückkommen
        Mockito.when(kinosaalMock.getName()).thenReturn("KS1");
        //Schauen ob der Wert korrekt gemockt wurde
        assertEquals("KS1", kinosaalMock.getName());


        /*
            Verifikationen sind Beheviour Testing. Sie testen nicht das Ergebnis eines
            Aufrufs, sondern sie testen, ob z.B. oder wie oft ein Aufruf stattgefunden hat.

            Verifizieren, dass getName() aufgerufen wurde:
         */
        Mockito.verify(kinosaalMock).getName();

        /*
            Verifizieren, dass pruefePlatz mit bestimmten Params aufgerufen wurde.
            (zum Testen auskommentieren)
         */
        //Mockito.verify(kinosaalMock).pruefePlatz('A',12);

        /*
            weitere Verifikationsmöglichkeiten (Behaviour Testing):
            https://www.baeldung.com/mockito-verify
         */
    }

    @Test
    void testKinosaalPlätze() {
        assertFalse(kinosaalOriginal.pruefePlatz('A', 11));
        assertTrue(kinosaalOriginal.pruefePlatz('A', 10));
        assertTrue(kinosaalOriginal.pruefePlatz('B', 10));
        assertTrue(kinosaalOriginal.pruefePlatz('C', 14));
    }

    @Test
    void testKinosaalName() {
        assertEquals("KS2", kinosaalOriginal.getName());
    }
}
