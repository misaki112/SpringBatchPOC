package com.cwctravel.batch.test;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;

import com.cwctravel.batch.config.BatchConfiguration;
import com.cwctravel.batch.config.TableCleanTasklet;
import com.cwctravel.batch.config.util.CsvPartitionUtil;
import com.cwctravel.batch.config.util.CsvWriterUtil;
import com.cwctravel.batch.config.util.DerbyWriterUtil;
import com.cwctravel.batch.model.derby.HotelDescriptiveContent;

import junit.framework.TestCase;

/**
 * This class tests the reader reading from Hyatt web service response.
 * <p>
 * No need to hit the actual web service api when running.
 * 
 * @author chris.nie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration(classes = {BatchConfiguration.class, TableCleanTasklet.class, //
		CsvPartitionUtil.class, CsvWriterUtil.class, DerbyWriterUtil.class})
@TestPropertySource(locations = "classpath:application.properties")
public class DerbyHyattReaderTest extends TestCase {

	@Autowired
	private ItemReader<HotelDescriptiveContent> restHotelReaderHyatt;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${hyatt.url}")
	private String hyattURL;

	// The Json File we use as test response
	private String response = "{\r\n" + "	\"Envelope\": {\r\n" + "		\"Body\": {\r\n" + "			\"getHotelContentResponse\": {\r\n"
			+ "				\"return\": {\r\n" + "					\"Success\": {\r\n" + "						\"prefix\": \"ns2\"\r\n"
			+ "					},\r\n" + "					\"HotelDescriptiveContents\": {\r\n"
			+ "						\"HotelDescriptiveContent\": {\r\n" + "							\"HotelInfo\": {\r\n"
			+ "								\"Descriptions\": {\r\n" + "									\"DescriptiveText\": {\r\n"
			+ "										\"prefix\": \"ns2\",\r\n"
			+ "										\"text\": \"Daily Resort Charge\"\r\n" + "									},\r\n"
			+ "									\"prefix\": \"ns2\"\r\n" + "								},\r\n"
			+ "								\"Position\": {\r\n" + "									\"Latitude\": \"19.928662\",\r\n"
			+ "									\"Longitude\": \"-155.887081\",\r\n" + "									\"prefix\": \"ns2\"\r\n"
			+ "								},\r\n" + "								\"Services\": {\r\n"
			+ "									\"Service\": {\r\n" + "										\"Code\": \"228\",\r\n"
			+ "										\"CodeDetail\": \"Meeting Rooms\",\r\n"
			+ "										\"ProximityCode\": \"1\",\r\n" + "										\"prefix\": \"ns2\"\r\n"
			+ "									},\r\n" + "									\"prefix\": \"ns2\"\r\n"
			+ "								},\r\n" + "								\"HotelStatusCode\": \"1\",\r\n"
			+ "								\"prefix\": \"ns2\"\r\n" + "							},\r\n"
			+ "							\"FacilityInfo\": {\r\n" + "								\"GuestRooms\": {\r\n"
			+ "									\"GuestRoom\": [\r\n" + "										{\r\n"
			+ "											\"ID\": \"ALL\",\r\n"
			+ "											\"RoomTypeName\": \"ALL\",\r\n"
			+ "											\"prefix\": \"ns2\"\r\n" + "										},\r\n"
			+ "										{\r\n" + "											\"TypeRoom\": {\r\n"
			+ "												\"Name\": \"MAKAI-MTN VW-LAGN TWR-2QNS\",\r\n"
			+ "												\"RoomTypeCode\": \"D2DM\",\r\n"
			+ "												\"prefix\": \"ns2\"\r\n" + "											},\r\n"
			+ "											\"DescriptiveText\": {\r\n"
			+ "												\"prefix\": \"ns2\",\r\n"
			+ "												\"text\": \"MAKAI-MOUNTAIN SIDE-LAGOON TOWER-2 QUEEN BEDS; 45USD RSRT CHG-MOUNTAIN VIEW-PREMIUM ROOM; LANAI-TUB/SHWR COMBO-MINI REFRIG-TWIN VANITY\"\r\n"
			+ "											},\r\n" + "											\"ID\": \"D2DM\",\r\n"
			+ "											\"MaxOccupancy\": \"4\",\r\n"
			+ "											\"RoomTypeName\": \"MAKAI-MTN VW-LAGN TWR-2QNS\",\r\n"
			+ "											\"prefix\": \"ns2\"\r\n" + "										}\r\n"
			+ "									],\r\n" + "									\"prefix\": \"ns2\"\r\n"
			+ "								},\r\n" + "								\"prefix\": \"ns2\"\r\n" + "							},\r\n"
			+ "							\"Policies\": {\r\n" + "								\"Policy\": {\r\n"
			+ "									\"PolicyInfo\": {\r\n" + "										\"CheckInTime\": \"16:00\",\r\n"
			+ "										\"CheckOutTime\": \"12:00\",\r\n"
			+ "										\"prefix\": \"ns2\"\r\n" + "									},\r\n"
			+ "									\"prefix\": \"ns2\"\r\n" + "								},\r\n"
			+ "								\"prefix\": \"ns2\"\r\n" + "							},\r\n"
			+ "							\"AreaInfo\": {\r\n" + "								\"RefPoints\": {\r\n"
			+ "									\"RefPoint\": {\r\n" + "										\"IndexPointCode\": \"17\",\r\n"
			+ "										\"RefPointName\": \"Blue Hawaiian Helicoptor Company\",\r\n"
			+ "										\"prefix\": \"ns2\"\r\n" + "									},\r\n"
			+ "									\"prefix\": \"ns2\"\r\n" + "								},\r\n"
			+ "								\"Recreations\": {\r\n" + "									\"Recreation\": {\r\n"
			+ "										\"Code\": \"23\",\r\n"
			+ "										\"CodeDetail\": \"Fitness Center\",\r\n"
			+ "										\"Included\": \"false\",\r\n"
			+ "										\"ProximityCode\": \"1\",\r\n" + "										\"prefix\": \"ns2\"\r\n"
			+ "									},\r\n" + "									\"prefix\": \"ns2\"\r\n"
			+ "								},\r\n" + "								\"prefix\": \"ns2\"\r\n" + "							},\r\n"
			+ "							\"AffiliationInfo\": {\r\n" + "								\"Awards\": {\r\n"
			+ "									\"Award\": [\r\n" + "										{\r\n"
			+ "											\"Rating\": \"3.5\",\r\n"
			+ "											\"prefix\": \"ns2\"\r\n" + "										},\r\n"
			+ "										{\r\n" + "											\"Rating\": \"3\",\r\n"
			+ "											\"prefix\": \"ns2\"\r\n" + "										},\r\n"
			+ "										{\r\n" + "											\"Rating\": \"4\",\r\n"
			+ "											\"prefix\": \"ns2\"\r\n" + "										}\r\n"
			+ "									],\r\n" + "									\"prefix\": \"ns2\"\r\n"
			+ "								},\r\n" + "								\"prefix\": \"ns2\"\r\n" + "							},\r\n"
			+ "							\"MultimediaDescriptions\": {\r\n" + "								\"MultimediaDescription\": [\r\n"
			+ "									{\r\n" + "										\"AdditionalDetailCode\": \"4\",\r\n"
			+ "										\"prefix\": \"ns2\"\r\n" + "									},\r\n"
			+ "									{\r\n" + "										\"ImageItems\": {\r\n"
			+ "											\"ImageItem\": {\r\n"
			+ "												\"ImageFormat\": {\r\n"
			+ "													\"URL\": {\r\n"
			+ "														\"prefix\": \"ns2\",\r\n"
			+ "														\"text\": \"https://media.iceportal.com/59830/photos/64731762S.jpg\"\r\n"
			+ "													},\r\n" + "													\"Height\": \"50\",\r\n"
			+ "													\"Width\": \"75\",\r\n"
			+ "													\"prefix\": \"ns2\"\r\n" + "												},\r\n"
			+ "												\"Description\": {\r\n"
			+ "													\"Caption\": \"Guest room\",\r\n"
			+ "													\"prefix\": \"ns2\"\r\n" + "												},\r\n"
			+ "												\"Category\": \"6\",\r\n"
			+ "												\"prefix\": \"ns2\"\r\n" + "											},\r\n"
			+ "											\"prefix\": \"ns2\"\r\n" + "										},\r\n"
			+ "										\"prefix\": \"ns2\"\r\n" + "									}\r\n"
			+ "								],\r\n" + "								\"prefix\": \"ns2\"\r\n" + "							},\r\n"
			+ "							\"ContactInfos\": {\r\n" + "								\"ContactInfo\": {\r\n"
			+ "									\"Addresses\": {\r\n" + "										\"Address\": {\r\n"
			+ "											\"AddressLine\": {\r\n"
			+ "												\"prefix\": \"ns2\",\r\n"
			+ "												\"text\": \"69-425 Waikoloa Beach Drive\"\r\n"
			+ "											},\r\n" + "											\"CityName\": {\r\n"
			+ "												\"prefix\": \"ns2\",\r\n"
			+ "												\"text\": \"Waikoloa\"\r\n" + "											},\r\n"
			+ "											\"PostalCode\": {\r\n"
			+ "												\"prefix\": \"ns2\",\r\n"
			+ "												\"text\": \"96738\"\r\n" + "											},\r\n"
			+ "											\"StateProv\": {\r\n"
			+ "												\"StateCode\": \"HI\",\r\n"
			+ "												\"prefix\": \"ns2\",\r\n"
			+ "												\"text\": \"Hawaii\"\r\n" + "											},\r\n"
			+ "											\"CountryName\": {\r\n" + "												\"Code\": \"US\",\r\n"
			+ "												\"prefix\": \"ns2\",\r\n"
			+ "												\"text\": \"United States\"\r\n" + "											},\r\n"
			+ "											\"prefix\": \"ns2\"\r\n" + "										},\r\n"
			+ "										\"prefix\": \"ns2\"\r\n" + "									},\r\n"
			+ "									\"Phones\": {\r\n" + "										\"Phone\": {\r\n"
			+ "											\"PhoneNumber\": \"+1-808-886-1234\",\r\n"
			+ "											\"PhoneTechType\": \"1\",\r\n"
			+ "											\"prefix\": \"ns2\"\r\n" + "										},\r\n"
			+ "										\"prefix\": \"ns2\"\r\n" + "									},\r\n"
			+ "									\"URLs\": {\r\n" + "										\"URL\": {\r\n"
			+ "											\"prefix\": \"ns2\",\r\n"
			+ "											\"text\": \"http://www3.hilton.com/en/hotels/hawaii/hilton-waikoloa-village-KOAHWHH/index.html\"\r\n"
			+ "										},\r\n" + "										\"prefix\": \"ns2\"\r\n"
			+ "									},\r\n" + "									\"prefix\": \"ns2\"\r\n"
			+ "								},\r\n" + "								\"prefix\": \"ns2\"\r\n" + "							},\r\n"
			+ "							\"BrandName\": \"HH\",\r\n" + "							\"ChainCode\": \"HH\",\r\n"
			+ "							\"CurrencyCode\": \"USD\",\r\n" + "							\"HotelCityCode\": \"KOAHWHH\",\r\n"
			+ "							\"HotelCode\": \"code\",\r\n" + "							\"HotelCodeContext\": \"TEST\",\r\n"
			+ "							\"HotelName\": \"Fancy Hotel\",\r\n" + "							\"prefix\": \"ns2\"\r\n"
			+ "						},\r\n" + "						\"prefix\": \"ns2\"\r\n" + "					},\r\n"
			+ "					\"PrimaryLangID\": \"EN-US\",\r\n" + "					\"Target\": \"Production\",\r\n"
			+ "					\"TimeStamp\": \"2019-08-21T19:12:52.052-07:00\",\r\n" + "					\"Version\": \"4.0\"\r\n"
			+ "				},\r\n" + "				\"xmlnsns2\": \"http://www.opentravel.org/OTA/2003/05\",\r\n"
			+ "				\"xmlnsns3\": \"http://hotel.cwctravel.com\",\r\n" + "				\"prefix\": \"ns3\"\r\n" + "			},\r\n"
			+ "			\"prefix\": \"S\"\r\n" + "		},\r\n" + "		\"xmlnsS\": \"http://schemas.xmlsoap.org/soap/envelope/\",\r\n"
			+ "		\"xmlnsSOAPENV\": \"http://schemas.xmlsoap.org/soap/envelope/\",\r\n" + "		\"prefix\": \"S\"\r\n" + "	}\r\n" + "}";

	private MockRestServiceServer mockServer;

	@Override
	@Before
	public void setUp() {
		RestGatewaySupport gateway = new RestGatewaySupport();
		gateway.setRestTemplate(restTemplate);
		mockServer = MockRestServiceServer.createServer(gateway);

		this.mockServer.expect(requestTo(hyattURL))//
				.andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

	}

	@Test
	public void hyattReaderTest() throws Exception {

		HotelDescriptiveContent hotel = this.restHotelReaderHyatt.read();

		assertEquals(hotel.getHotelName(), "Fancy Hotel");
		assertEquals(hotel.getHotelCode(), "code");
		assertEquals(hotel.getHotelCodeContext(), "TEST");
	}

}
