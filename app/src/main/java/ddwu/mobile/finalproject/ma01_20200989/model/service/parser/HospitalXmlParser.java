package ddwu.mobile.finalproject.ma01_20200989.model.service.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import ddwu.mobile.finalproject.ma01_20200989.model.domain.dto.HospitalDto;

public class HospitalXmlParser {
    private enum TagType { NONE, TITLE, ADDRESS, TEL, STATUS, LATITUDE, LONGITUDE };

    private final static String ROW = "row";
    private final static String BIZPLC_NM = "BIZPLC_NM";
    private final static String BSN_STATE_NM = "BSN_STATE_NM";
    private final static String REFINE_LOTNO_ADDR = "REFINE_LOTNO_ADDR";
    private final static String LOCPLC_FACLT_TELNO = "LOCPLC_FACLT_TELNO";
    private final static String REFINE_WGS84_LOGT = "REFINE_WGS84_LOGT";
    private final static String REFINE_WGS84_LAT = "REFINE_WGS84_LAT";

    private XmlPullParser parser;

    public HospitalXmlParser() {
        XmlPullParserFactory factory = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
        } catch (XmlPullParserException exception) {
            exception.printStackTrace();
        }
    }

    public List<HospitalDto> parse(String xml) {
        List<HospitalDto> resultList = new ArrayList();
        HospitalDto hospitalDto = null;
        TagType tagType = TagType.NONE;     //  태그를 구분하기 위한 enum 변수 초기화

        try {
            // 파싱 대상 지정
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType(); // 태그 유형 구분 변수 준비

            // parsing 수행 - for 문 또는 while 문으로 구성
            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();

                        if (tag.equals(ROW)) {
                            hospitalDto = new HospitalDto();
                        } else if (hospitalDto != null && tag.equals(BIZPLC_NM)) {
                            tagType = TagType.TITLE;
                        } else if (hospitalDto != null && tag.equals(REFINE_LOTNO_ADDR)) {
                            tagType = TagType.ADDRESS;
                        } else if (hospitalDto != null && tag.equals(BSN_STATE_NM)) {
                            tagType = TagType.STATUS;
                        } else if (hospitalDto != null && tag.equals(LOCPLC_FACLT_TELNO)) {
                            tagType = TagType.TEL;
                        } else if (hospitalDto != null && tag.equals(REFINE_WGS84_LOGT)) {
                            tagType = TagType.LONGITUDE;
                        } else if (hospitalDto != null && tag.equals(REFINE_WGS84_LAT)) {
                            tagType = TagType.LATITUDE;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals(ROW)) {
                            resultList.add(hospitalDto);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        switch (tagType) {
                            case TITLE:
                                hospitalDto.setTitle(parser.getText());
                                break;
                            case ADDRESS:
                                hospitalDto.setAddress(parser.getText());
                                break;
                            case STATUS:
                                hospitalDto.setStatus(parser.getText());
                                break;
                            case TEL:
                                hospitalDto.setTel(parser.getText());
                                break;
                            case LONGITUDE:
                                hospitalDto.setLongitude(parser.getText());
                                break;
                            case LATITUDE:
                                hospitalDto.setLatitude(parser.getText());
                                break;
                        }
                        tagType = TagType.NONE;
                        break;
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
