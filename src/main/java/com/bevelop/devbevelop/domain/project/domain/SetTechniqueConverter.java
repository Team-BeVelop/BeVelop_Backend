//package com.bevelop.devbevelop.domain.project.domain;
//
//import org.springframework.util.StringUtils;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//import java.util.Arrays;
//import java.util.EnumSet;
//
//@Converter
//// Entity에서 사용할 X Type으로 EnumSet<Category>
//// DB에서 읽어오거나 저장할 때 사용할 Y Type으로 String 선언
//public class SetTechniqueConverter implements AttributeConverter<EnumSet<Technique>, String> {
//
//    // DB에서 사용할 Type으로 Entity Type을 변환하는 로직 구현
//    @Override
//    public String convertToDatabaseColumn(EnumSet<Technique> attribute) {
//        // DB에서 사용할 String Type 생성을 위해 StringBuilder 선언
//        StringBuilder sb = new StringBuilder();
//        // ["DESIGNER", "FRONTEND"] 과 같은 EnumSet Collection Data를
//        // 각각 foreach를 돌며 "DESIGNER," 형식으로 StringBuilder에 append
//        attribute.stream().forEach(e -> sb.append(e.name()+","));
//        // 최종 결과 String으로 변환
//        String result = sb.toString();
//        // "DESIGNER,FRONTEND," 형식 일 경우 마지막 ',' 제거
//        if(result.length()>0 && result.charAt(result.length() - 1) == ',') result = result.substring(0, result.length() - 1);
//        return result;
//    }
//
//    // Entity에서 사용할 Type으로 DB Type을 변환하는 로직 구현
//    @Override
//    public EnumSet<Technique> convertToEntityAttribute(String dbData) {
//        // DB에서 읽어온 값이 null이거나 공백이거나 Task.DESIGNER 형태로 읽어올 경우 제외
//        if(dbData == null || dbData == "" || dbData.contains(".")) return EnumSet.noneOf(Technique.class);
//        // 최초 빈 Collection 생성
//        EnumSet<Technique> attribute = EnumSet.noneOf(Technique.class);
//        // DB에서 읽어온 "DESIGNER,FRONTEND" 형태의 데이터 ','로 split
//        String[] dbDataArray = StringUtils.trimAllWhitespace(dbData).toUpperCase().split(",");
//        // 빈 Collection으로 생성한 EnumSet에 split한 data를 Task(Enum) .valueOf로 생성
//        // 해당 구문에서 Enum에 선언되지 않은 값 존재 시 Exception 발생 가능
//        Arrays.stream(dbDataArray).forEach(e -> attribute.add(Technique.valueOf(e)));
//        return attribute;
//    }
//}