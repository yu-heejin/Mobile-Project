package ddwu.mobile.finalproject.ma01_20200989.model.service.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import ddwu.mobile.finalproject.ma01_20200989.model.domain.dto.AdoptDto;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.dto.HospitalDto;

public class AdoptXmlParser {
    private enum TagType { NONE, KIND, IMAGE, STATE, ADDRESS, MARK, START_DATE, END_DATE };

    private final static String ITEM = "item";
    private final static String KIND_CD = "kindCd";
    private final static String FILE_NAME = "filename";
    private final static String PROCESS_STATE = "processState";
    private final static String CARE_ADDR = "careAddr";
    private final static String SPECIAL_MARK = "specialMark";
    private final static String NOTICE_SDT = "noticeSdt";
    private final static String NOTICE_EDT = "noticeEdt";

    private XmlPullParser parser;

    public AdoptXmlParser() {
        XmlPullParserFactory factory = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
        } catch (XmlPullParserException exception) {
            exception.printStackTrace();
        }
    }

    public List<AdoptDto> parse(String xml) {
        List<AdoptDto> resultList = new ArrayList();
        AdoptDto adoptDto = null;
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

                        if (tag.equals(ITEM)) {
                            adoptDto = new AdoptDto();
                        } else if (adoptDto != null && tag.equals(KIND_CD)) {
                            tagType = TagType.KIND;
                        } else if (adoptDto != null && tag.equals(CARE_ADDR)) {
                            tagType = TagType.ADDRESS;
                        } else if (adoptDto != null && tag.equals(PROCESS_STATE)) {
                            tagType = TagType.STATE;
                        } else if (adoptDto != null && tag.equals(SPECIAL_MARK)) {
                            tagType = TagType.MARK;
                        } else if (adoptDto != null && tag.equals(NOTICE_SDT)) {
                            tagType = TagType.START_DATE;
                        } else if (adoptDto != null && tag.equals(NOTICE_EDT)) {
                            tagType = TagType.END_DATE;
                        } else if (adoptDto != null && tag.equals(FILE_NAME)) {
                            tagType = TagType.IMAGE;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals(ITEM)) {
                            resultList.add(adoptDto);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        switch (tagType) {
                            case KIND:
                                adoptDto.setKind(parser.getText());
                                break;
                            case ADDRESS:
                                adoptDto.setCity(parser.getText());
                                break;
                            case STATE:
                                adoptDto.setProtection(parser.getText());
                                break;
                            case MARK:
                                adoptDto.setDisease(parser.getText());
                                break;
                            case START_DATE:
                                adoptDto.setStartDate(parser.getText());
                                break;
                            case END_DATE:
                                adoptDto.setEndDate(parser.getText());
                                break;
                            case IMAGE:
                                adoptDto.setUrl(parser.getText());
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
