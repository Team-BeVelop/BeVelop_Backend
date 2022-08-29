//package com.bevelop.devbevelop.domain.stack;
//
//import com.bevelop.devbevelop.domain.user.domain.User;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.List;
//import java.util.Objects;
//
//@Getter
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@ToString
//public class Stack {
//
//    @Id
//    @GeneratedValue
//    @Column(name="stack_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="user_id")
//    private User user;
//
//    @Enumerated(EnumType.STRING)
//    private StackName stackName;
//
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Stack stack = (Stack) o;
//        return Objects.equals(id, stack.id) && Objects.equals(user, stack.user) && stackName == stack.stackName;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, user, stackName);
//    }
//}
