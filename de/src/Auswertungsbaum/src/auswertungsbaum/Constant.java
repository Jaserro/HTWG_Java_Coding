package Auswertungsbaum.src.auswertungsbaum;

import java.util.Map;
import java.util.Set;


public class Constant implements Expression {

    private double wert;

    public Constant(double pWert){
        this.wert = pWert;
    }

    @Override
    public double eval(Map<String, Double> evMap) {
        return this.wert;
    }

    @Override
    public Set<String> getVars() {
        return Set.of();
    }

    @Override
    public String toString() {
        return String.valueOf(this.wert);
    }
}
