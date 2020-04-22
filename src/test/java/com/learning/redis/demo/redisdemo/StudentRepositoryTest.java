package com.learning.redis.demo.redisdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository repository;

    @Test
    public void should_save_student_success() {
        Student student = new Student("Eng2015001", "Wangwu", Student.Gender.MALE, 12);
        repository.save(student);

        Student cachedValue = repository.findById("Eng2015001").get();

        Assertions.assertEquals("Eng2015001", cachedValue.getId());
    }

    @Test
    public void should_update_student_success() {
        Student student = new Student("Eng2015001", "Wangwu", Student.Gender.MALE, 12);
        repository.save(student);

        Student cachedValue = repository.findById("Eng2015001").get();
        cachedValue.setGrade(13);
        repository.save(cachedValue);
        cachedValue = repository.findById("Eng2015001").get();

        Assertions.assertEquals(13, cachedValue.getGrade());
    }
}
