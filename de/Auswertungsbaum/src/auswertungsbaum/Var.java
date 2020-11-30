package auswertungsbaum;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class Var implements Expression {

    private String name;

    public Var(String pName){
        this.name = pName;
    }

    @Override
    public double eval(Map<String, Double> evMap) {
        for (Map.Entry<String, Double> index : evMap.entrySet()) {
            if (this.name.equals(index.getKey())){
                return index.getValue();
            }
        }
        throw new IllegalArgumentException("Variable " + this.name + " fehlt in der Map");
    }

    @Override
    public Set<String> getVars() {
        Set<String> variablen = new TreeSet<>();
        variablen.add(this.name);
        return variablen;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
