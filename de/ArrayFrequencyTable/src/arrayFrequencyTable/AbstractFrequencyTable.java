package arrayFrequencyTable;

import linkedListFrequencyTable.FrequencyTable;
import linkedListFrequencyTable.Word;

/**
 *
 * @author Jan-Niclas Serr + Simon Warmers
 * @since 23.04.2020
 */
public abstract class AbstractFrequencyTable implements FrequencyTable {
	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	public abstract Word[] getFqTable();

	@Override
    public void add(String w) {
        add(w, 1);
    }

	@Override
	public void addAll(FrequencyTable fq) {

		for (int i = 0; i < fq.size(); i++) {
			add( fq.get(i).getWord(), fq.get(i).getFrequency() );
		}

	}

	@Override
	public void collectMostFrequent(FrequencyTable fq) {
		fq.clear();
		int greatest = get(0).getFrequency();
		for (int i = 0; get(i).getFrequency() == greatest ; i++) {
			fq.add(get(i).getWord(),get(i).getFrequency());
		}

	}

	@Override
	public void collectLeastFrequent(FrequencyTable fq) {
		fq.clear();
		for (int i = size()-1; get(i).getFrequency() == 1; i--) {
			fq.add(get(i).getWord(),get(i).getFrequency());
		}
	}

	/**
	 * Liefert eine String-Darstellung zur&uuml;ck.
	 * @return String-Darstellung.
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("{");
		for (int i = 0; i < size() ; i++) {
			s.append( get(i).toString());
			s.append(", ");
		}
		s.append("} size = ");
		s.append(size());
		return s.toString();
	}
}
