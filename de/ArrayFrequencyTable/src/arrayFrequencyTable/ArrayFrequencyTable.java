package arrayFrequencyTable;

import linkedListFrequencyTable.AbstractFrequencyTable;
import linkedListFrequencyTable.Word;

/**
 *
 * @author Jan-Niclas Serr + Simon Warmers
 * @since 23.4.2020
 */
public class ArrayFrequencyTable extends AbstractFrequencyTable {
	private int size;
	private Word fqTable[];
	private final int DefaultSize = 100;

	public ArrayFrequencyTable() {
        clear();
    }

    @Override
    public Word[] getFqTable(){
		return fqTable;
	}
	public int size(){
		return size;
	}


	@Override
	public final void clear() {
		fqTable = new Word[DefaultSize];
		size = 0;
	}

	@Override
	public void add(String w, int f) {
		for (int i = 0; i < fqTable.length; i++) {
			if (fqTable[i] == null){
				resize();
				fqTable[i] = new Word(w,f);
				moveLeft(i);
				size++;
				return;
			}
			if(fqTable[i].getWord().equals(w)){
				fqTable[i].addFrequency(f);
				moveLeft(i);
				return;
			}
		}
	}

	@Override
	public Word get(int pos) {
		if(pos >= size){
			return null;
		}
		return fqTable[pos];
	}

	@Override
	public int get(String w) {
		for (int i = 0; i < size; i++) {
			if (fqTable[i].getWord().equals(w)){
				return fqTable[i].getFrequency();
			}
		}
		return 0;
	}

	private void resize(){
		if (size==fqTable.length) {
			Word[] oldfqTable = fqTable;
			fqTable = new Word[oldfqTable.length * 2];
			System.arraycopy(oldfqTable, 0, fqTable, 0, size);
		}
	}

	private void moveLeft(int i){
		if (i==0){
			return;
		}
		while (fqTable[i-1].getFrequency() < fqTable[i].getFrequency()){
			Word temp = fqTable[i - 1];
			fqTable[i - 1] = fqTable[i];
			fqTable[i] = temp;
			i--;
			if(i==0){
				break;
			}
		}
		if (i==0){
			return;
		}
		while (fqTable[i-1].getFrequency() == fqTable[i].getFrequency() &&
				fqTable[i-1].getWord().compareToIgnoreCase(fqTable[i].getWord()) > 0){
			Word temp = fqTable[i-1];
			fqTable[i-1] = fqTable[i];
			fqTable[i] = temp;
			i--;
			if(i==0){
				break;
			}
		}
	}
}
