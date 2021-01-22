package Auswertungsbaum.src.auswertungsbaum;

import java.util.Map;

public class Difference extends Compound {

    public Difference(Expression pWert1, Expression pWert2){
        this.zeichen = "-";
        this.wert1 = pWert1;
        this.wert2 = pWert2;
    }

    @Override
    public double eval(Map<String, Double> evMap) {
        return wert1.eval(evMap) - wert2.eval(evMap);
    }
}
