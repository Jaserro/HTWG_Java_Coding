package Auswertungsbaum.src.auswertungsbaum;

import java.util.Set;
import java.util.TreeSet;

public abstract class Compound implements Expression {
    protected String zeichen;
    protected Expression wert1;
    protected Expression wert2;

    @Override
    public Set<String> getVars(){
        Set<String> variablen = new TreeSet<>();
        variablen.addAll(wert1.getVars());
        variablen.addAll(wert2.getVars());
        return variablen;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(this.wert1);
        sb.append(" ");
        sb.append(this.zeichen);
        sb.append(" ");
        sb.append(this.wert2);
        sb.append(")");
        return sb.toString();
    }
}
