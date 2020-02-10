package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.OverDraftLimitExceededException;
import com.luxoft.bankapp.service.feed.Feed;

import java.util.Map;

public class CheckingAccount extends AbstractAccount {
	@Feed
	private float overdraft;

	public CheckingAccount(float overdraft) throws IllegalArgumentException {
		if (overdraft >= 0) {
			this.overdraft = overdraft;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void setOverdraft(float x) throws IllegalArgumentException {
		if (x >= 0) {
			overdraft = x;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void withdraw(float x) throws OverDraftLimitExceededException {
		if (balance + overdraft >= x) {
			balance -= x;
			System.out.println("Successful withdrawal from checking account");
		} else {
			throw new OverDraftLimitExceededException(getAccountName(), balance
					+ overdraft);
		}
	}

	@Override
	public void parseFeed(Map<String, String> map){
		super.parseFeed(map);
		try {
			String overdraft = map.get("overdraft");
			this.overdraft = Float.parseFloat(overdraft != null ? overdraft : "");
		} catch (NumberFormatException ignore) {

		}
	}

	public String getAccountName() {
		return "Checking Account";
	}

}
