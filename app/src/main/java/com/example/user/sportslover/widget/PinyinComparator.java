package com.example.user.sportslover.widget;

import com.example.user.sportslover.bean.PersonItem;

import java.util.Comparator;


public class PinyinComparator implements Comparator<PersonItem> {

	@Override
	public int compare(PersonItem o1, PersonItem o2) {
		if (o1.getSortLetters().equals("☆")) {
			return -1;
		} else if (o2.getSortLetters().equals("☆")) {
			return 1;
		} else if (o1.getSortLetters().equals("#")) {
			return -1;
		} else if (o2.getSortLetters().equals("#")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
