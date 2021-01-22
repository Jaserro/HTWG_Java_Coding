package Minimale_Baeume.src.telefon.TelNetApplication;

public class TelVerbindung{
    public final TelKnoten u;
    public final TelKnoten v;
    public final int c;

    public TelVerbindung(TelKnoten u, TelKnoten v, int c){
        this.u = u;
        this.v = v;
        this.c = c;
    }

    @Override
    public String toString() {
        return "{[" + u + "," + v + "]: " + c + "} ";
    }
}