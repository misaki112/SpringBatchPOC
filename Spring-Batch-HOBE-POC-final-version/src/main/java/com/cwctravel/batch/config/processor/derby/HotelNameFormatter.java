package com.cwctravel.batch.config.processor.derby;

/**
 * HotelNameFormatter helps remove special character and capitalize starting letter of each word in name and generate hotelId for Hotel from Derby
 * response.
 * <p>
 * HotelNameFormatter is used inside DerbyHotelItemProcessor.
 * 
 * @author chris.nie
 */
public class HotelNameFormatter {

	/**
	 * Capitalize starting letter of each word for given string
	 * 
	 * @param hotelName
	 *                  String hotelName of a Hotel from Derby response to be processed
	 * @return String name of the Hotel after capitalizing starting letter of each word
	 */
	public String hotelNameFormatter(String hotelName) {

		String[] temp = hotelName.split(" ");

		StringBuffer sb = new StringBuffer();

		for(int i = 0; i < temp.length; i++) {
			sb.append(Character.toUpperCase(temp[i].charAt(0))).append(temp[i].substring(1).toLowerCase()).append(" ");
		}

		String res = sb.toString();
		res = this.removeSpecialChar(res);
		res = res.replaceAll(",", "");
		res = res.trim();

		return res;
	}

	/**
	 * Remove special character for given string
	 * 
	 * @param str
	 *            String hotelName of a Hotel from Derby response to be processed
	 * @return String name of the Hotel after removing special character
	 */
	public String removeSpecialChar(String str) {
		str = str.replace("&amp;", "");
		str = str.replaceAll("[^0-9a-zA-Z ]", "");
		return str;
	}

	/**
	 * Generate unique hotelId for each Hotel by merging hotelName and hotelBrand
	 * 
	 * @param hotelBrand
	 *                   String brand of the hotel such as "Hilton" or "Hyatt"
	 * @param hotelName
	 *                   String well formatted name of the hotel
	 * @return String unique hotelId for each Hotel
	 */
	public String hotelIdGenerator(String hotelBrand, String hotelName) {
		String hotelId = hotelName;
		hotelName = hotelName.replace("&amp;", "");
		hotelName = hotelName.replaceAll("[^0-9a-zA-Z ]", "");

		if(hotelId.contains(hotelBrand)) {
			hotelId = (hotelBrand + hotelId.replaceAll(hotelBrand, ""));
		}
		if(!hotelId.startsWith(hotelBrand)) {
			hotelId = (hotelBrand + hotelId);
		}
		hotelId = hotelId.replaceAll(" ", "");
		return hotelId;
	}
}
