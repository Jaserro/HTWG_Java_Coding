package GenericFrequencyTables.src.genericFrequencyTables;

/**
 * @author Jan-Niclas Serr + Simon Warmers
 * @since 23.04.2020
 */
public abstract class AbstractFrequencyTable<T> implements FrequencyTable<T> {
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    public abstract Word[] getFqTable();

    @Override
    public void add(T w) {
        this.add(w, 1);
    }

    @Override
    public void addAll(FrequencyTable<? extends T> fq) {

        for (Word<? extends T> index : fq) {
            add(index.getWord(), index.getFrequency());
        }

    }

    @Override
    public void collectMostFrequent(FrequencyTable<? super T> fq) {
        fq.clear();
        int greatest = get(0).getFrequency();
		for (Word<T> index : this) {
			if (index.getFrequency() == greatest){
				fq.add(index.getWord(), index.getFrequency());
			}

		}


    }

    @Override
    public void collectLeastFrequent(FrequencyTable<? super T> fq) {
        fq.clear();
		for (Word<T> index : this) {
			if (index.getFrequency() == 1){
				fq.add(index.getWord(), index.getFrequency());
			}
		}
    }

    /**
     * Liefert eine String-Darstellung zur&uuml;ck.
     *
     * @return String-Darstellung.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("{");
		for (Word<T> index : this) {
            s.append(index.toString());
            s.append(", ");
		}

        s.append("} size = ");
        s.append(size());
        return s.toString();
    }
}
