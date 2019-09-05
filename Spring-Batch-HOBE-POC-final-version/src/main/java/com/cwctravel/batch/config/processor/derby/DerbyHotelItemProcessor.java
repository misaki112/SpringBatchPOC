package com.cwctravel.batch.config.processor.derby;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cwctravel.batch.model.derby.Amenity;
import com.cwctravel.batch.model.derby.DescriptiveText;
import com.cwctravel.batch.model.derby.GuestRoom;
import com.cwctravel.batch.model.derby.HotelDescriptiveContent;
import com.cwctravel.batch.model.derby.ImageItem;
import com.cwctravel.batch.model.derby.MultimediaDescription;
import com.cwctravel.batch.model.derby.Recreation;
import com.cwctravel.batch.model.derby.RefPoint;
import com.cwctravel.batch.model.derby.Service;

/**
 * DerbyHotelItemProcessor is a processor formatting, filling up missing fields in HotelDescriptiveContent.
 * 
 * @author chris.nie
 */
public class DerbyHotelItemProcessor implements ItemProcessor<HotelDescriptiveContent, HotelDescriptiveContent> {

	private JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(DerbyHotelItemProcessor.class);
	private DerbyFeatureIdMapper idMapper;

	private static final int HOTEL_FEATURE_CODE = 1;
	private static final int HOTEL_ROOM_FEATURE_CODE = 2;
	private static final int HOTEL_BUSINESS_SERVICE_CODE = 3;
	private static final int POINT_OF_INTEREST_CODE = 4;
	private static final int RECREATION_SERVICE_CODE = 5;

	private Map<Integer, String> locationMap = new HashMap<>();

	/**
	 * Construct a new DerbyHotelItemProcessor
	 * 
	 * @param jdbcTemplate
	 *                          JdbcTemplate template used to generate unique id for id fields in GuestRoom and ImageItem
	 * @param namedJdbcTemplate
	 *                          NamedParameterJdbcTemplate template which helps query for featureType information from derby_feature_id_mapping table
	 *                          using featureId and featureCode of a feature
	 */
	public DerbyHotelItemProcessor(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		idMapper = new DerbyFeatureIdMapper(namedJdbcTemplate);
		locationMap.put(1, "ONSITE");
		locationMap.put(2, "OFFSITE");
		locationMap.put(3, "NEARBY");
		locationMap.put(4, "INFORMATION NOT AVAILABLE");
		locationMap.put(5, "ONSITE AND OFFSITE");
	}

	/**
	 * Formatting, filling up missing fields in HotelDescriptiveContent
	 */
	public HotelDescriptiveContent process(HotelDescriptiveContent item) throws Exception {

		// process hotel information
		HotelNameFormatter formatter = new HotelNameFormatter();
		String hotelName = item.getHotelName();
		hotelName = formatter.hotelNameFormatter(hotelName);
		String hotelBrand = item.getHotelCodeContext();
		if(item.getHotelInfo() != null) {
			String hotelDescription = item.getHotelInfo().getDescriptions().getDescriptiveText().getText();
			item.getHotelInfo().getDescriptions().getDescriptiveText().setText(formatter.removeSpecialChar(hotelDescription));
		}
		hotelBrand = Character.toUpperCase(hotelBrand.charAt(0)) + hotelBrand.substring(1).toLowerCase();
		item.setHotelName(hotelName);
		String hotelId = formatter.hotelIdGenerator(hotelBrand, hotelName);

		item.setHotelCodeContext(hotelBrand);
		item.setHotelId(hotelId);

		// process ImageItem
		if(item.getMultimediaDescriptions() != null) {
			List<MultimediaDescription> multiDescriptionList = item.getMultimediaDescriptions().getMultimediaDescription();
			if(multiDescriptionList.get(multiDescriptionList.size() - 1).getImageItems() != null) {
				List<ImageItem> imageList = multiDescriptionList.get(multiDescriptionList.size() - 1).getImageItems().getImageItem();
				// generate unique id for ImageItems
				for(ImageItem image: imageList) {
					String getIdQuery = "Call pNextVal()";
					long imageId = jdbcTemplate.queryForObject(getIdQuery, Long.class);
					image.setImageId(imageId);
					image.setHotelId(hotelId);
				}
			}

		}

		// process hotel feature of type service
		if(item.getHotelInfo() != null && item.getHotelInfo().getServices() != null) {
			List<Service> serviceList = item.getHotelInfo().getServices().getService();

			// get feauterId using featureCode and proximityCode of Services
			for(Service service: serviceList) {
				if(service.getCode() != null || service.getBusinessServiceCode() != null) {
					int derbyFeatureCode = 0;
					if(service.getCode() != null) {
						derbyFeatureCode = Integer.parseInt(service.getCode());
					}
					else if(service.getBusinessServiceCode() != null) {
						derbyFeatureCode = Integer.parseInt(service.getBusinessServiceCode());
					}
					service.setHotelId(hotelId);
					long featureId = 0L;
					if(service.getCode() != null) {
						idMapper.derbyCodeToFeatureId(derbyFeatureCode, HOTEL_FEATURE_CODE);
					}
					else if(service.getBusinessServiceCode() != null) {
						idMapper.derbyCodeToFeatureId(derbyFeatureCode, HOTEL_BUSINESS_SERVICE_CODE);
					}
					int proximityCode = 4;
					if(service.getProximityCode() != null) {
						proximityCode = Integer.parseInt(service.getProximityCode());
					}
					service.setLocation(locationMap.get(proximityCode));
					service.setFeatureId(featureId);
				}
			}
		}

		// process hotel feature of type recreation
		if(item.getAreaInfo() != null && item.getAreaInfo().getRecreations() != null) {
			List<Recreation> recreationList = item.getAreaInfo().getRecreations().getRecreation();

			// get feauterId using featureCode and proximityCode of Recreations
			for(Recreation recreation: recreationList) {
				if(recreation.getCode() != null) {
					recreation.setHotelId(hotelId);
					int derbyFeatureCode = Integer.parseInt(recreation.getCode());
					long featureId = idMapper.derbyCodeToFeatureId(derbyFeatureCode, RECREATION_SERVICE_CODE);
					recreation.setFeatureId(featureId);

					int proximityCode = 4;
					if(recreation.getProximityCode() != null) {
						proximityCode = Integer.parseInt(recreation.getProximityCode());
					}
					recreation.setLocation(locationMap.get(proximityCode));
				}
			}
		}

		// process point of interest
		if(item.getAreaInfo() != null && item.getAreaInfo().getRefPoints() != null) {
			List<RefPoint> refPointList = item.getAreaInfo().getRefPoints().getRefPoint();

			// get featureId for RefPoints
			for(RefPoint refPoint: refPointList) {
				refPoint.setHotelId(hotelId);
				int derbyFeatureCode = refPoint.getIndexPointCode();
				long featureId = idMapper.derbyCodeToFeatureId(derbyFeatureCode, POINT_OF_INTEREST_CODE);
				refPoint.setFeatureId(featureId);
			}
		}

		// process GuestRoom and room features
		if(item.getFacilityInfo() != null) {
			List<GuestRoom> roomList = item.getFacilityInfo().getGuestRooms().getGuestRoom();
			if(item.getHotelCodeContext().equals("Hilton")) {
				roomList.remove(0);
			}

			// generate unique id for GuestRooms
			for(GuestRoom room: roomList) {
				String getIdQuery = "Call pNextVal()";
				long roomId = jdbcTemplate.queryForObject(getIdQuery, Long.class);
				room.setHotelId(hotelId);
				room.setRoomId(roomId);
				if(room.getTypeRoom().getName() == null && room.getDescriptiveText() == null) {
					room.getTypeRoom().setName(room.getRoomTypeName());
					DescriptiveText description = new DescriptiveText();
					description.setText("");
					room.setDescriptiveText(description);
				}
				else if(room.getTypeRoom().getName() == null) {
					room.getTypeRoom().setName(room.getRoomTypeName());
				}

				if(room.getAmenities() != null) {
					List<Amenity> features = room.getAmenities().getAmenity();
					// get featureId of using FeatureCode of Amenities in the GuestRoom
					for(Amenity feature: features) {
						feature.setRoomId(roomId);
						int derbyFeatureCode = feature.getRoomAmenityCode();
						long featureId = idMapper.derbyCodeToFeatureId(derbyFeatureCode, HOTEL_ROOM_FEATURE_CODE);
						feature.setFeatureId(featureId);
					}
				}
			}
		}

		// process Rate
		String supplierId = null;
		String hotelContext = item.getHotelCodeContext().toUpperCase();
		String hotelCode = item.getHotelCode().toUpperCase();

		if(hotelContext.equalsIgnoreCase("HILTON")) {
			supplierId = "HO" + "HHW" + hotelCode;
		}
		else if(hotelContext.equalsIgnoreCase("HYATT")) {
			supplierId = "HO" + "HHR" + hotelCode;
		}
		else if(hotelContext.equalsIgnoreCase("FAIRMONT")) {
			supplierId = "HO" + "FRH" + hotelCode;
		}
		String combinedId = (hotelId + "_" + supplierId);
		item.setCombinedId(combinedId);

		log.info("Derby loading hotel: " + hotelName);
		return item;
	}

}
