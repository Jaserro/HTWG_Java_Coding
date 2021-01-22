package GenericFrequencyTables.src.genericFrequencyTables;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Jan-Niclas Serr + Simon Warmers
 * @since 23.4.2020
 */
public class ArrayFrequencyTable<T> extends AbstractFrequencyTable<T> {
	private int size;
	private Word<T> fqTable[];
	private final int DefaultSize = 100;

	public ArrayFrequencyTable() {
        this.clear();
	}

    @Override
    public Word<T>[] getFqTable(){
		return fqTable;
	}

	public int size(){
		return size;
	}


	@Override
	public Iterator<Word<T>> iterator() {
		return new ArrayFrequencyTableIterator();
	}

	private class ArrayFrequencyTableIterator implements Iterator<Word<T>>{
		private int i = 0;
		@Override
		public boolean hasNext() {
			return i <= size-1;
		}

		@Override
		public Word<T> next() {
			return fqTable[i++];
		}
	}


	@Override
	public final void clear() {
		fqTable = new Word[DefaultSize];
		this.size = 0;
	}


	@Override
	public void add(T w, int f) {

		for (int i = 0; i <= fqTable.length; i++) {
			if (fqTable[i] == null){
				resize();
				fqTable[i] = new Word<T>((T) w,f);
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
	public Word<T> get(int pos) {
		if(pos >= size){
			return null;
		}
		return fqTable[pos];
	}

	@Override
	public int get(Object w) {
		for (int i = 0; i < size; i++) {
			if (fqTable[i].getWord().equals(w)){
				return fqTable[i].getFrequency();
			}
		}
		return 0;
	}


	private void resize(){
		if (size==fqTable.length-1) {
			Word<T>[] oldfqTable = fqTable;
			fqTable = new Word[oldfqTable.length * 2];
			System.arraycopy(oldfqTable, 0, fqTable, 0, size);
		}
	}

	private void moveLeft(int i){
		if (i==0){
			return;
		}
		while (fqTable[i-1].getFrequency() < fqTable[i].getFrequency()){
			Word<T> temp = fqTable[i - 1];
			fqTable[i - 1] = fqTable[i];
			fqTable[i] = temp;
			i--;
			if(i==0){
				break;
			}
		}

	}
}
