public class Kommentar {

    private String text;
    private String autor;
    private int bewertung;
    private int stimmen;

    public Kommentar(String text, String autor, int bewertung) {
        if (text == null || autor == null || bewertung < 1 || bewertung > 5) throw new InvalidConstructionParameterException();
        this.text = text;
        this.autor = autor;
        this.bewertung = bewertung;
    }

    public String getKommentarText() {
        return this.text;
    }

    public int getStimmen() {
        return this.stimmen;
    }

    public String getAutor() {
        return autor;
    }

    public int getBewertung() {
        return bewertung;
    }

    public void zustimmen() {
        this.stimmen++;
    }
}
