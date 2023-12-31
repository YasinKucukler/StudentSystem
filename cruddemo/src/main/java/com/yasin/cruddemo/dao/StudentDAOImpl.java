package com.yasin.cruddemo.dao;

import com.yasin.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO{

    //define field for entity manager
    private EntityManager entityManager;

    //inject entity manager using constructor injection
    @Autowired
    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //implement save method
    @Override
    @Transactional
    public void save(Student theStudent) {
        //saves the student
        entityManager.persist(theStudent);

    }

    @Override
    public Student findById(Integer id) {
        //entity class, primary key
        return entityManager.find(Student.class , id);
    }

    @Override
    public List<Student> findAll() {
        //create JPA query
        //FROM JPAEntityClassName
        TypedQuery<Student> theQuery = entityManager.createQuery("FROM Student", Student.class);

        //return query results
        return theQuery.getResultList();
    }

    @Override
    public List<Student> findByLastName(String theLastName) {

        // create query
        TypedQuery<Student> theQuery = entityManager.createQuery(
                "FROM Student WHERE lastName =: theData", Student.class);

        // set query parameters
        theQuery.setParameter("theData", theLastName);

        // return query results
        return  theQuery.getResultList();

    }

    @Override
    @Transactional
    public void update(Student theStudent) {
        entityManager.merge(theStudent);
    }

    //@Transactional --> modifying db
    @Override
    @Transactional
    public void delete(Integer id) {
        //retrieve student
        Student theStudent = entityManager.find(Student.class, id);

        //delete student
        entityManager.remove(theStudent);
    }

    //.executeUpdate() --> modifies the db
    @Override
    @Transactional
    public int deleteAll() {
        int numRowsDeleted = entityManager.createQuery("DELETE FROM Student").executeUpdate();
        return numRowsDeleted;
    }
}
