package com.httq.app.helper;

public class StaticUtils {
	private static Str str;
	private static Helpers helpers;

	private StaticUtils(){}

	public static Str getStr() {
		return str;
	}

	public static void setStr(Str str) {
		StaticUtils.str = str;
	}

	public static Helpers getHelpers() {
		return helpers;
	}

	public static void setHelpers(Helpers helpers) {
		StaticUtils.helpers = helpers;
	}
}
