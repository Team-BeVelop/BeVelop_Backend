//package com.bevelop.devbevelop.domain.project.domain;
//
//import com.sun.istack.NotNull;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.EnumSet;
//import java.util.List;
//import java.util.Map;
//
//@Embeddable
//@Getter @Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@ToString
//public class ProjectTemplate {
//
//    //by month?
//    @Column(name = "period")
//    @NotNull
//    private int period;
//
//    //기술스택
//    @Convert(converter = SetTechniqueConverter.class)
//    @Column(name = "techniques")
//    @NotNull
//    private EnumSet<Technique> techniques;
//
//    //연관분야
//    @NotNull
//    private String category;
//
//    @NotNull
//    private List<Website> sites;
//}
