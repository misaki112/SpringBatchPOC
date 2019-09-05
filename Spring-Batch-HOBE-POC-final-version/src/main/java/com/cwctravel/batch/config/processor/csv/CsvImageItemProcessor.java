package com.cwctravel.batch.config.processor.csv;

import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.cwctravel.batch.model.csv.Hotel;
import com.cwctravel.batch.model.csv.HotelImage;
import com.cwctravel.batch.model.csv.ImageAndUrlCombination;
import com.cwctravel.batch.model.csv.ImageRowObject;
import com.cwctravel.batch.model.derby.ImageURL;

/**
 * CsvImageItemProcessor process data from ImageRowObject to ImageAndUrlCombination.
 * <p>
 * CsvImageItemProcessor extract fields of ImageRowObject and generate new ImageAndUrlCombination using those fields.
 * @author chris.nie
 *
 */
public class CsvImageItemProcessor implements ItemProcessor<ImageRowObject, ImageAndUrlCombination> {

	private Map<String, Hotel> hotelIdMap;

	/**
	 * Constructor generating a CsvImageItemProcessor with a map 
	 * @param hotelIdMap Map a map mapping a Hotel to its hotelId
	 */
	public CsvImageItemProcessor(Map<String, Hotel> hotelIdMap) {
		super();
		this.hotelIdMap = hotelIdMap;
	}

	/**
	 * Extract data from fields in ImageRowObject and use them to build a new ImageAndUrlCombination
	 * 
	 * @param  row ImageRowObject representing a row of hotel image and url data in csv file to be processed
	 * @return ImageAndUrlCombination Intermediate class with information of HotelImage and ImageURL that helps 
	 * writing to hotel_image table and hotel_image_url table in destination MySQL database.
	 */
	public ImageAndUrlCombination process(ImageRowObject row) throws Exception {

		String hotelId = row.getHotelId();

		String title = row.getTitle();

		long id = row.getId();

		long imageId = row.getImageId();

		String url = row.getUrl();

		Hotel hotel = hotelIdMap.get(hotelId);

		if(hotel == null) {
			hotel = new Hotel(hotelId);
			hotelIdMap.put(hotelId, hotel);
		}

		HotelImage image = new HotelImage(title, id, hotel);

		ImageURL imageUrl = new ImageURL(imageId, url, image);

		image.getUrlSet().add(imageUrl);

		hotel.getImageSet().add(image);

		return new ImageAndUrlCombination(image, imageUrl);
	}

}
