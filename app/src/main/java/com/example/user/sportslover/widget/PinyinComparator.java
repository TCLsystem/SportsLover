package com.example.user.sportslover.widget;

import com.example.user.sportslover.bean.Friend;

import java.util.Comparator;


public class PinyinComparator implements Comparator<Friend> {

	@Override
	public int compare(Friend o1, Friend o2) {
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
