package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import util.Difficulty;

public class GameEditor {
	
	private int[] newCell = new int[81];
	private int[] hideCell = new int[81];
	private List<Integer> zeroPool = new ArrayList<>();
	private List<Integer> indexPool = new ArrayList<>();
	private List<Integer> valuePool = new ArrayList<>();
	private List<Integer> alternatives = new ArrayList<>();
	ListIterator<Integer> indexIter;
	IndexComparator comparator = new IndexComparator();
	private Map<Integer,List<Integer>> records = new HashMap<>(81);
	{
		for (int i = 0; i < 81; i++) {
			records.put(i, new ArrayList<Integer>());
		}
	}
	
	public int[] solve(int[] grid) {
		boolean forward;
		
		zeroPool.clear();
		for (int i = 0; i < 81; i++) {
			records.get(i).clear();
			newCell[i] = grid[i];
			if (grid[i] == 0) zeroPool.add(i);
		}
		if (zeroPool.isEmpty()) return newCell;
		indexIter = zeroPool.listIterator();
		int current = indexIter.next();
		indexIter.previous();
		forward = false;

		while(true) {
			parsePool(current, newCell);
			if (valuePool.isEmpty()) {
				if(indexIter.hasPrevious()) {
					records.get(current).clear();
					newCell[current] = 0;
					current = indexIter.previous();
					if (forward) current = indexIter.previous();forward=false;
					
				} else {
					return null;
				}
			}
			else {
				newCell[current] = valuePool.remove(0);
				if (indexIter.hasNext()) {
					records.get(current).add(newCell[current]);
					current = indexIter.next();
					if (!forward) current = indexIter.next();forward=true;
				} else {
					return Arrays.copyOf(newCell,81);
				}
			}
		}	
	}
	
	public int[] hide(int[] grid, Difficulty diff) {

		indexPool.clear();
		for(int i = 0; i < 81; i++) indexPool.add(i);
		Collections.shuffle(indexPool);
		for (int i = 0; i < 81; i++) hideCell[i] = grid[i];
			
		
		while(!indexPool.isEmpty()) {
			int i = indexPool.remove(0);
			parsePool(i,hideCell);
			if(valuePool.size() == 0) {
				hideCell[i] = 0;
			}
			else {
				int original = hideCell[i];
				alternatives.clear();
				for (int v : valuePool) alternatives.add(v);
				for (int alternative : alternatives){
					hideCell[i] = alternative;
					if (solve(hideCell) != null) {
						hideCell[i] = original;
						break;
					} else {
						hideCell[i] = 0;
					}
				}
			}			
			
		}
		indexPool.clear();
		for (int i = 0; i < 81; i++) {
			if(hideCell[i] == 0)  indexPool.add(i);
		}
		indexPool.sort(comparator);
		for (int i = 0; i < diff.extra(); i++) {
			int index = indexPool.remove(0);
			hideCell[index] = grid[index];
			indexPool.sort(comparator);
		}
		return Arrays.copyOf(hideCell,81);
			
	}
	
	private class IndexComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer i, Integer j) {
			parsePool(i,hideCell);
			int iSize = valuePool.size();
			parsePool(j,hideCell);
			int jSize = valuePool.size();
			if (iSize > jSize) return -1;
			else if (iSize < jSize) return 1;
			
			
			return 0;
		}
		
	}

	private void parsePool(int index, int[] grid) {
		refillPool();
		cond1(index, grid);
		cond2(index, grid);
		cond3(index, grid);
		valuePool.removeAll(records.get(index));
	}
	
	private void refillPool() {
		valuePool.clear();
		valuePool.add(1);valuePool.add(2);valuePool.add(3);valuePool.add(4);valuePool.add(5);
		valuePool.add(6);valuePool.add(7);valuePool.add(8);valuePool.add(9);
		Collections.shuffle(valuePool);
	}
	
	private void cond1(int index, int[] grid) {
		int row = (index / 9) * 9;
		for(int i = row; i < row+9; i++) {
			valuePool.remove((Object)grid[i]);
		}
		
	}
	
	private void cond2(int index, int[] grid) {
		int column = index % 9;
		for(int i = column; i < 81; i += 9) {
			valuePool.remove((Object)grid[i]);
		}
	}
	
	private void cond3(int index, int[] grid) {
		int pointer = areaStart(index);
		int areaEnd = pointer +21;
		while (pointer < areaEnd) {
			valuePool.remove((Object)grid[pointer]);
			pointer = pointer + ((pointer%3 == 2)?7:1);
		}
	}
	
	private int areaStart (int x) {
		return 9*((x/9) -((x/9)%3)) + (x%9) - (x%3);
	}

}
