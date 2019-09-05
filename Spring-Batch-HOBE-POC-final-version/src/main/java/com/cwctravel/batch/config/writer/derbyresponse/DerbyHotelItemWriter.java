package com.cwctravel.batch.config.writer.derbyresponse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import com.cwctravel.batch.model.derby.GuestRoom;
import com.cwctravel.batch.model.derby.HotelDescriptiveContent;
import com.cwctravel.batch.model.derby.ImageItem;
import com.cwctravel.batch.model.derby.MultimediaDescription;
import com.cwctravel.batch.model.derby.Recreation;
import com.cwctravel.batch.model.derby.RefPoint;
import com.cwctravel.batch.model.derby.Service;

/**
 * DerbyHotelItemWriter is a JdbcBatchItemWriter writing to ipm_hotel table and trigger lower level writers for other tables taking
 * HotelDescriptiveContent as input.
 * 
 * @author chris.nie
 */
public class DerbyHotelItemWriter extends JdbcBatchItemWriter<HotelDescriptiveContent> {

	private DerbyImageItemWriter imageWriter;
	private ItemWriter<HotelDescriptiveContent> derbyIdMappingWriter;
	private DerbyRoomWriter derbyRoomWriter;
	private ItemWriter<HotelDescriptiveContent> derbyRateWriter;
	private ItemWriter<Recreation> recreationWriter;
	private ItemWriter<Service> serviceWriter;
	private ItemWriter<RefPoint> refPointWriter;

	/**
	 * Construct a new DerbyHotelItemWriter
	 * 
	 * @param imageWriter
	 *                              DerbyImageItemWriter writer writing to hotel_image table and trigger writer for hotel_image_url table
	 * @param derbyIdMappingWriter
	 *                              ItemWriter<HotelDescriptiveContent> writer writing to dc_id_mapping table
	 * @param derbyRoomWriter
	 *                              DerbyRoomWriter writer writing to hotel_room table and trigger writer for hotel_room_feature_type table.
	 * @param derbyRateWriter
	 *                              ItemWriter<HotelDescriptiveContent> writer writing to ipm_hotel_rate table
	 * @param derbyRecreationWriter
	 *                              ItemWriter<Recreation> writer writing to hotel_feature_type table
	 * @param derbyServiceWriter
	 *                              ItemWriter<Service> writer writing to hotel_feature_type table
	 * @param derbyRefPointWriter
	 *                              ItemWriter<RefPoint> writer writing to hotel_point_of_interset_feature_type table
	 */
	public DerbyHotelItemWriter(DerbyImageItemWriter imageWriter, ItemWriter<HotelDescriptiveContent> derbyIdMappingWriter, DerbyRoomWriter derbyRoomWriter, ItemWriter<HotelDescriptiveContent> derbyRateWriter, ItemWriter<Recreation> recreationWriter, ItemWriter<Service> serviceWriter, ItemWriter<RefPoint> refPointWriter) {
		super();
		this.imageWriter = imageWriter;
		this.derbyIdMappingWriter = derbyIdMappingWriter;
		this.derbyRoomWriter = derbyRoomWriter;
		this.derbyRateWriter = derbyRateWriter;
		this.recreationWriter = recreationWriter;
		this.serviceWriter = serviceWriter;
		this.refPointWriter = refPointWriter;
	}

	/**
	 * Writing to items list ipm_hotel table and trigger lower level writers for other tables
	 */
	@Override
	public void write(List<? extends HotelDescriptiveContent> items) throws Exception {
		for(HotelDescriptiveContent item: items) {
			List<HotelDescriptiveContent> list = new ArrayList<>();
			list.add(item);

			super.write(list);

			derbyIdMappingWriter.write(list);
			derbyRateWriter.write(list);

			// write hotel feature of type service
			if(item.getHotelInfo() != null && item.getHotelInfo().getServices() != null) {
				List<Service> serviceList = item.getHotelInfo().getServices().getService();
				List<Service> serviceWriteList = new ArrayList<>();
				for(Service service: serviceList) {
					if(service.getFeatureId() != 0L) {
						if(service.getCode() != null) {
							serviceWriteList.add(service);
						}
						else if(service.getBusinessServiceCode() != null) {
							serviceWriteList.add(service);
						}
					}
				}
				serviceWriter.write(serviceWriteList);
			}

			// write hotel feature of type recreation
			if(item.getAreaInfo() != null && item.getAreaInfo().getRecreations() != null) {
				List<Recreation> recreationList = item.getAreaInfo().getRecreations().getRecreation();
				List<Recreation> recreationWriteList = new ArrayList<>();
				for(Recreation recreation: recreationList) {
					if(recreation.getFeatureId() != 0L && recreation.isIncluded()) {
						recreationWriteList.add(recreation);
					}
				}
				recreationWriter.write(recreationWriteList);
			}

			// write point of interest
			if(item.getAreaInfo() != null && item.getAreaInfo().getRefPoints() != null) {
				List<RefPoint> refPointList = item.getAreaInfo().getRefPoints().getRefPoint();
				refPointWriter.write(refPointList);
			}

			// write image and Url
			if(item.getMultimediaDescriptions() != null) {
				List<MultimediaDescription> multiDescriptionList = item.getMultimediaDescriptions().getMultimediaDescription();
				if(multiDescriptionList.get(multiDescriptionList.size() - 1).getImageItems() != null) {
					List<ImageItem> imageList = multiDescriptionList.get(multiDescriptionList.size() - 1).getImageItems().getImageItem();
					imageWriter.write(imageList);
				}
			}

			// write room and room category
			if(item.getFacilityInfo() != null) {
				List<GuestRoom> roomList = item.getFacilityInfo().getGuestRooms().getGuestRoom();
				derbyRoomWriter.write(roomList);
			}
		}

	}
}
