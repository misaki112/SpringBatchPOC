package com.cwctravel.batch.config.reader;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestTemplate;

import com.cwctravel.batch.model.derby.Application;
import com.cwctravel.batch.model.derby.HotelDescriptiveContent;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * DerbyHotelReader is a ItemReader mapping Json file response when requesting Derby for hotel information to list of HotelDescriptiveContent.
 * <p>
 * DerbyHotelReader tolerate bad connection to Derby web service with 5 tries.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.HotelDescriptiveContent} for class HotelDescriptiveContent.
 * 
 * @author chris.nie
 */
public class DerbyHotelReader implements ItemReader<HotelDescriptiveContent> {

	private static final Logger log = LoggerFactory.getLogger(DerbyHotelReader.class);

	private final String apiUrl;
	private final RestTemplate restTemplate;

	private int nextHotelIndex;
	private List<HotelDescriptiveContent> hotelData;

	/**
	 * Construct a new DerbyHotelReader
	 * 
	 * @param apiUrl
	 *                     String the Url the where the response is in
	 * @param restTemplate
	 *                     RestTemplate helps transferring Json file to Java object
	 */
	public DerbyHotelReader(String apiUrl, RestTemplate restTemplate) {
		this.apiUrl = apiUrl;
		this.restTemplate = restTemplate;
		nextHotelIndex = 0;
	}

	/**
	 * Generate a list of HotelDescriptiveContent from the Json response given by calling Derby web service, tolerate 5 time of retry if encounter bad
	 * web service connection during reading.
	 */
	@Retryable(include = {Exception.class}, maxAttempts = 5, backoff = @Backoff(delay = 100, maxDelay = 500))
	public HotelDescriptiveContent read() throws Exception {

		if(hotelDataIsNotInitialized()) {
			hotelData = fetchHotelDataFromAPI();
		}

		HotelDescriptiveContent nextHotel = null;

		if(nextHotelIndex < hotelData.size()) {
			nextHotel = hotelData.get(nextHotelIndex);
			nextHotelIndex++;
		}
		return nextHotel;
	}

	/**
	 * @return boolean check if the HotelDescriptiveContent list has been created or not
	 */
	private boolean hotelDataIsNotInitialized() {
		return this.hotelData == null;
	}

	/**
	 * @return List<HotelDescriptiveContent> a list of HotelDescriptiveContent from the Json response
	 * @throws JsonParseException
	 *                              throws exception if Json format not correct
	 * @throws JsonMappingException
	 *                              throws exception if the structure of the Java Object is not the same as the structure of the Json file
	 * @throws IOException
	 *                              throws exception if error occurs when reading Json file
	 */
	private List<HotelDescriptiveContent> fetchHotelDataFromAPI() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

		String data = restTemplate.getForObject(apiUrl, String.class);

		Application hotelDataWrapper = objectMapper.readValue(data, Application.class);

		return hotelDataWrapper.getEnvelope().getBody().getGetHotelContentResponse().getReturn().getHotelDescriptiveContents().getHotelDescriptiveContent();
	}

}
