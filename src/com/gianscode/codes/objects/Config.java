package com.gianscode.codes.objects;

import java.util.List;

public class Config {

	private String alreadyRedeemed, invalidCode, successfullyRedeemed;
	private List<Code> codes;

	public Config(String alreadyRedeemed, String invalidCode, String successfullyRedeemed, List<Code> codes) {
		this.alreadyRedeemed = alreadyRedeemed;
		this.invalidCode = invalidCode;
		this.successfullyRedeemed = successfullyRedeemed;
		this.codes = codes;
	}

	public String getAlreadyRedeemed() {
		return alreadyRedeemed;
	}

	public String getInvalidCode() {
		return invalidCode;
	}

	public String getSuccessfullyRedeemed() {
		return successfullyRedeemed;
	}

	public List<Code> getCodes() {
		return codes;
	}
}