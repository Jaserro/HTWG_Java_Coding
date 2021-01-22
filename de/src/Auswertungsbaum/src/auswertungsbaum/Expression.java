package Auswertungsbaum.src.auswertungsbaum;

import java.util.Map;
import java.util.Set;

public interface Expression {

    double eval(Map<String, Double> evMap);

    Set<String> getVars();

    @Override
    String toString();
}
