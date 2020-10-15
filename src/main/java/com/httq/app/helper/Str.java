package com.httq.app.helper;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

@Component
public class Str {
	static Map<String, String[]> _charsArray;

	public String random(int length) {
		StringBuilder string = new StringBuilder();
		int len;
		while ((len = string.length()) < length) {
			int size = length - len;
			byte[] bytes = random_bytes(size);

			String encodedString = Base64.getEncoder().encodeToString(bytes);
			encodedString = encodedString.replaceAll("[\\/\\+\\=]", "");
			encodedString = encodedString.substring(0, size);

			string.append(encodedString);
		}

		return string.toString();
	}

	public byte[] random_bytes(int size) {
		byte[] bytes = new byte[size];
		new Random().nextBytes(bytes);
		return bytes;
	}

	/**
	 * Transliterate a UTF-8 value to ASCII.
	 *
	 * @param value [String]
	 * @return String
	 */
	public String ascii(String value) {
		String string = value;
		for (Entry entry: charsArray().entrySet()) {
			string = str_replace(string, (String[]) entry.getValue(), (String)entry.getKey());
		}
		return string;
	}

	private String str_replace(String value, String[] val, String key) {
		String string = value;
		for (String search: val){
			string = string.replaceAll(search, key);
		}
		return string.trim();
	}

	public String slug(String title){
		return slug(title, "-");
	}

	/**
	 * Generate a URL friendly "slug" from a given string.
	 *
	 * @param title
	 * @param separator
	 * @return string
	 */

	public String slug(String title, String separator)
	{
		String result = ascii(title);

		// Convert all dashes/underscores into separator
		String flip = separator.equals("-") ? "_" : "-";

		result = result.replaceAll("[" + flip + "]+", separator);

		// Replace @ with the word 'at'
		result = result.replaceAll("@", separator+"at"+separator);

		result = result.toLowerCase();

		// Remove all characters that are not the separator, letters, numbers, or whitespace.
		result = result.replaceAll("[^" + separator + "\\pL\\pN\\s]+", "");

		// Replace all separator characters and whitespace by a single separator
		result = result.replaceAll("[" + separator + "\\s]+", separator);

		return result;
	}

	public Map<String, String[]> charsArray() {
		if (_charsArray == null) {
			synchronized (Str.class) {
				_charsArray = new HashMap<>();
				_charsArray.put("0", new String[]{"°", "₀", "۰", "０"});
				_charsArray.put("1", new String[]{"¹", "₁", "۱", "１"});
				_charsArray.put("2", new String[]{"²", "₂", "۲", "２"});
				_charsArray.put("3", new String[]{"³", "₃", "۳", "３"});
				_charsArray.put("4", new String[]{"⁴", "₄", "۴", "٤", "４"});
				_charsArray.put("5", new String[]{"⁵", "₅", "۵", "٥", "５"});
				_charsArray.put("6", new String[]{"⁶", "₆", "۶", "٦", "６"});
				_charsArray.put("7", new String[]{"⁷", "₇", "۷", "７"});
				_charsArray.put("8", new String[]{"⁸", "₈", "۸", "８"});
				_charsArray.put("9", new String[]{"⁹", "₉", "۹", "９"});
				_charsArray.put("a", new String[]{"à", "á", "ả", "ã", "ạ", "ă", "ắ", "ằ", "ẳ", "ẵ", "ặ", "â", "ấ", "ầ", "ẩ", "ẫ", "ậ", "ā", "ą", "å", "α", "ά", "ἀ", "ἁ", "ἂ", "ἃ", "ἄ", "ἅ", "ἆ", "ἇ", "ᾀ", "ᾁ", "ᾂ", "ᾃ", "ᾄ", "ᾅ", "ᾆ", "ᾇ", "ὰ", "ά", "ᾰ", "ᾱ", "ᾲ", "ᾳ", "ᾴ", "ᾶ", "ᾷ", "а", "أ", "အ", "ာ", "ါ", "ǻ", "ǎ", "ª", "ა", "अ", "ا", "ａ", "ä"});
				_charsArray.put("b", new String[]{"б", "β", "ب", "ဗ", "ბ", "ｂ"});
				_charsArray.put("c", new String[]{"ç", "ć", "č", "ĉ", "ċ", "ｃ"});
				_charsArray.put("d", new String[]{"ď", "ð", "đ", "ƌ", "ȡ", "ɖ", "ɗ", "ᵭ", "ᶁ", "ᶑ", "д", "δ", "د", "ض", "ဍ", "ဒ", "დ", "ｄ"});
				_charsArray.put("e", new String[]{"é", "è", "ẻ", "ẽ", "ẹ", "ê", "ế", "ề", "ể", "ễ", "ệ", "ë", "ē", "ę", "ě", "ĕ", "ė", "ε", "έ", "ἐ", "ἑ", "ἒ", "ἓ", "ἔ", "ἕ", "ὲ", "έ", "е", "ё", "э", "є", "ə", "ဧ", "ေ", "ဲ", "ე", "ए", "إ", "ئ", "ｅ"});
				_charsArray.put("f", new String[]{"ф", "φ", "ف", "ƒ", "ფ", "ｆ"});
				_charsArray.put("g", new String[]{"ĝ", "ğ", "ġ", "ģ", "г", "ґ", "γ", "ဂ", "გ", "گ", "ｇ"});
				_charsArray.put("h", new String[]{"ĥ", "ħ", "η", "ή", "ح", "ه", "ဟ", "ှ", "ჰ", "ｈ"});
				_charsArray.put("i", new String[]{"í", "ì", "ỉ", "ĩ", "ị", "î", "ï", "ī", "ĭ", "į", "ı", "ι", "ί", "ϊ", "ΐ", "ἰ", "ἱ", "ἲ", "ἳ", "ἴ", "ἵ", "ἶ", "ἷ", "ὶ", "ί", "ῐ", "ῑ", "ῒ", "ΐ", "ῖ", "ῗ", "і", "ї", "и", "ဣ", "ိ", "ီ", "ည်", "ǐ", "ი", "इ", "ی", "ｉ"});
				_charsArray.put("j", new String[]{"ĵ", "ј", "Ј", "ჯ", "ج", "ｊ"});
				_charsArray.put("k", new String[]{"ķ", "ĸ", "к", "κ", "Ķ", "ق", "ك", "က", "კ", "ქ", "ک", "ｋ"});
				_charsArray.put("l", new String[]{"ł", "ľ", "ĺ", "ļ", "ŀ", "л", "λ", "ل", "လ", "ლ", "ｌ"});
				_charsArray.put("m", new String[]{"м", "μ", "م", "မ", "მ", "ｍ"});
				_charsArray.put("n", new String[]{"ñ", "ń", "ň", "ņ", "ŉ", "ŋ", "ν", "н", "ن", "န", "ნ", "ｎ"});
				_charsArray.put("o", new String[]{"ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", "ồ", "ổ", "ỗ", "ộ", "ơ", "ớ", "ờ", "ở", "ỡ", "ợ", "ø", "ō", "ő", "ŏ", "ο", "ὀ", "ὁ", "ὂ", "ὃ", "ὄ", "ὅ", "ὸ", "ό", "о", "و", "ို", "ǒ", "ǿ", "º", "ო", "ओ", "ｏ", "ö"});
				_charsArray.put("p", new String[]{"п", "π", "ပ", "პ", "پ", "ｐ"});
				_charsArray.put("q", new String[]{"ყ", "ｑ"});
				_charsArray.put("r", new String[]{"ŕ", "ř", "ŗ", "р", "ρ", "ر", "რ", "ｒ"});
				_charsArray.put("s", new String[]{"ś", "š", "ş", "с", "σ", "ș", "ς", "س", "ص", "စ", "ſ", "ს", "ｓ"});
				_charsArray.put("t", new String[]{"ť", "ţ", "т", "τ", "ț", "ت", "ط", "ဋ", "တ", "ŧ", "თ", "ტ", "ｔ"});
				_charsArray.put("u", new String[]{"ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", "ừ", "ử", "ữ", "ự", "û", "ū", "ů", "ű", "ŭ", "ų", "µ", "у", "ဉ", "ု", "ူ", "ǔ", "ǖ", "ǘ", "ǚ", "ǜ", "უ", "उ", "ｕ", "ў", "ü"});
				_charsArray.put("v", new String[]{"в", "ვ", "ϐ", "ｖ"});
				_charsArray.put("w", new String[]{"ŵ", "ω", "ώ", "ဝ", "ွ", "ｗ"});
				_charsArray.put("x", new String[]{"χ", "ξ", "ｘ"});
				_charsArray.put("y", new String[]{"ý", "ỳ", "ỷ", "ỹ", "ỵ", "ÿ", "ŷ", "й", "ы", "υ", "ϋ", "ύ", "ΰ", "ي", "ယ", "ｙ"});
				_charsArray.put("z", new String[]{"ź", "ž", "ż", "з", "ζ", "ز", "ဇ", "ზ", "ｚ"});
				_charsArray.put("aa", new String[]{"ع", "आ", "آ"});
				_charsArray.put("ae", new String[]{"æ", "ǽ"});
				_charsArray.put("ai", new String[]{"ऐ"});
				_charsArray.put("ch", new String[]{"ч", "ჩ", "ჭ", "چ"});
				_charsArray.put("dj", new String[]{"ђ", "đ"});
				_charsArray.put("dz", new String[]{"џ", "ძ"});
				_charsArray.put("ei", new String[]{"ऍ"});
				_charsArray.put("gh", new String[]{"غ", "ღ"});
				_charsArray.put("ii", new String[]{"ई"});
				_charsArray.put("ij", new String[]{"ĳ"});
				_charsArray.put("kh", new String[]{"х", "خ", "ხ"});
				_charsArray.put("lj", new String[]{"љ"});
				_charsArray.put("nj", new String[]{"њ"});
				_charsArray.put("oe", new String[]{"ö", "œ", "ؤ"});
				_charsArray.put("oi", new String[]{"ऑ"});
				_charsArray.put("oii", new String[]{"ऒ"});
				_charsArray.put("ps", new String[]{"ψ"});
				_charsArray.put("sh", new String[]{"ш", "შ", "ش"});
				_charsArray.put("shch", new String[]{"щ"});
				_charsArray.put("ss", new String[]{"ß"});
				_charsArray.put("sx", new String[]{"ŝ"});
				_charsArray.put("th", new String[]{"þ", "ϑ", "θ", "ث", "ذ", "ظ"});
				_charsArray.put("ts", new String[]{"ц", "ც", "წ"});
				_charsArray.put("ue", new String[]{"ü"});
				_charsArray.put("uu", new String[]{"ऊ"});
				_charsArray.put("ya", new String[]{"я"});
				_charsArray.put("yu", new String[]{"ю"});
				_charsArray.put("zh", new String[]{"ж", "ჟ", "ژ"});
				_charsArray.put("(c)", new String[]{"©"});
				_charsArray.put("A", new String[]{"Á", "À", "Ả", "Ã", "Ạ", "Ă", "Ắ", "Ằ", "Ẳ", "Ẵ", "Ặ", "Â", "Ấ", "Ầ", "Ẩ", "Ẫ", "Ậ", "Å", "Ā", "Ą", "Α", "Ά", "Ἀ", "Ἁ", "Ἂ", "Ἃ", "Ἄ", "Ἅ", "Ἆ", "Ἇ", "ᾈ", "ᾉ", "ᾊ", "ᾋ", "ᾌ", "ᾍ", "ᾎ", "ᾏ", "Ᾰ", "Ᾱ", "Ὰ", "Ά", "ᾼ", "А", "Ǻ", "Ǎ", "Ａ", "Ä"});
				_charsArray.put("B", new String[]{"Б", "Β", "ब", "Ｂ"});
				_charsArray.put("C", new String[]{"Ç", "Ć", "Č", "Ĉ", "Ċ", "Ｃ"});
				_charsArray.put("D", new String[]{"Ď", "Ð", "Đ", "Ɖ", "Ɗ", "Ƌ", "ᴅ", "ᴆ", "Д", "Δ", "Ｄ"});
				_charsArray.put("E", new String[]{"É", "È", "Ẻ", "Ẽ", "Ẹ", "Ê", "Ế", "Ề", "Ể", "Ễ", "Ệ", "Ë", "Ē", "Ę", "Ě", "Ĕ", "Ė", "Ε", "Έ", "Ἐ", "Ἑ", "Ἒ", "Ἓ", "Ἔ", "Ἕ", "Έ", "Ὲ", "Е", "Ё", "Э", "Є", "Ə", "Ｅ"});
				_charsArray.put("F", new String[]{"Ф", "Φ", "Ｆ"});
				_charsArray.put("G", new String[]{"Ğ", "Ġ", "Ģ", "Г", "Ґ", "Γ", "Ｇ"});
				_charsArray.put("H", new String[]{"Η", "Ή", "Ħ", "Ｈ"});
				_charsArray.put("I", new String[]{"Í", "Ì", "Ỉ", "Ĩ", "Ị", "Î", "Ï", "Ī", "Ĭ", "Į", "İ", "Ι", "Ί", "Ϊ", "Ἰ", "Ἱ", "Ἳ", "Ἴ", "Ἵ", "Ἶ", "Ἷ", "Ῐ", "Ῑ", "Ὶ", "Ί", "И", "І", "Ї", "Ǐ", "ϒ", "Ｉ"});
				_charsArray.put("J", new String[]{"Ｊ"});
				_charsArray.put("K", new String[]{"К", "Κ", "Ｋ"});
				_charsArray.put("L", new String[]{"Ĺ", "Ł", "Л", "Λ", "Ļ", "Ľ", "Ŀ", "ल", "Ｌ"});
				_charsArray.put("M", new String[]{"М", "Μ", "Ｍ"});
				_charsArray.put("N", new String[]{"Ń", "Ñ", "Ň", "Ņ", "Ŋ", "Н", "Ν", "Ｎ"});
				_charsArray.put("O", new String[]{"Ó", "Ò", "Ỏ", "Õ", "Ọ", "Ô", "Ố", "Ồ", "Ổ", "Ỗ", "Ộ", "Ơ", "Ớ", "Ờ", "Ở", "Ỡ", "Ợ", "Ø", "Ō", "Ő", "Ŏ", "Ο", "Ό", "Ὀ", "Ὁ", "Ὂ", "Ὃ", "Ὄ", "Ὅ", "Ὸ", "Ό", "О", "Ө", "Ǒ", "Ǿ", "Ｏ", "Ö"});
				_charsArray.put("P", new String[]{"П", "Π", "Ｐ"});
				_charsArray.put("Q", new String[]{"Ｑ"});
				_charsArray.put("R", new String[]{"Ř", "Ŕ", "Р", "Ρ", "Ŗ", "Ｒ"});
				_charsArray.put("S", new String[]{"Ş", "Ŝ", "Ș", "Š", "Ś", "С", "Σ", "Ｓ"});
				_charsArray.put("T", new String[]{"Ť", "Ţ", "Ŧ", "Ț", "Т", "Τ", "Ｔ"});
				_charsArray.put("U", new String[]{"Ú", "Ù", "Ủ", "Ũ", "Ụ", "Ư", "Ứ", "Ừ", "Ử", "Ữ", "Ự", "Û", "Ū", "Ů", "Ű", "Ŭ", "Ų", "У", "Ǔ", "Ǖ", "Ǘ", "Ǚ", "Ǜ", "Ｕ", "Ў", "Ü"});
				_charsArray.put("V", new String[]{"В", "Ｖ"});
				_charsArray.put("W", new String[]{"Ω", "Ώ", "Ŵ", "Ｗ"});
				_charsArray.put("X", new String[]{"Χ", "Ξ", "Ｘ"});
				_charsArray.put("Y", new String[]{"Ý", "Ỳ", "Ỷ", "Ỹ", "Ỵ", "Ÿ", "Ῠ", "Ῡ", "Ὺ", "Ύ", "Ы", "Й", "Υ", "Ϋ", "Ŷ", "Ｙ"});
				_charsArray.put("Z", new String[]{"Ź", "Ž", "Ż", "З", "Ζ", "Ｚ"});
				_charsArray.put("AE", new String[]{"Æ", "Ǽ"});
				_charsArray.put("Ch", new String[]{"Ч"});
				_charsArray.put("Dj", new String[]{"Ђ"});
				_charsArray.put("Dz", new String[]{"Џ"});
				_charsArray.put("Gx", new String[]{"Ĝ"});
				_charsArray.put("Hx", new String[]{"Ĥ"});
				_charsArray.put("Ij", new String[]{"Ĳ"});
				_charsArray.put("Jx", new String[]{"Ĵ"});
				_charsArray.put("Kh", new String[]{"Х"});
				_charsArray.put("Lj", new String[]{"Љ"});
				_charsArray.put("Nj", new String[]{"Њ"});
				_charsArray.put("Oe", new String[]{"Œ"});
				_charsArray.put("Ps", new String[]{"Ψ"});
				_charsArray.put("Sh", new String[]{"Ш"});
				_charsArray.put("Shch", new String[]{"Щ"});
				_charsArray.put("Ss", new String[]{"ẞ"});
				_charsArray.put("Th", new String[]{"Þ", "Θ"});
				_charsArray.put("Ts", new String[]{"Ц"});
				_charsArray.put("Ya", new String[]{"Я"});
				_charsArray.put("Yu", new String[]{"Ю"});
				_charsArray.put("Zh", new String[]{"Ж"});
				_charsArray.put(" ", new String[]{"\\xC2\\xA0", "\\xE2\\x80\\x80", "\\xE2\\x80\\x81", "\\xE2\\x80\\x82", "\\xE2\\x80\\x83", "\\xE2\\x80\\x84", "\\xE2\\x80\\x85", "\\xE2\\x80\\x86", "\\xE2\\x80\\x87", "\\xE2\\x80\\x88", "\\xE2\\x80\\x89", "\\xE2\\x80\\x8A", "\\xE2\\x80\\xAF", "\\xE2\\x81\\x9F", "\\xE3\\x80\\x80", "\\xEF\\xBE\\xA0"});
			}
		}
		return _charsArray;
	}
}
