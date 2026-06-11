package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
public class BurgerDaoImpl implements BurgerDao {

    private final EntityManager entityManager;

    @Autowired
    public BurgerDaoImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Override
    public List<Burger> findAll() {
        TypedQuery<Burger> query=entityManager.createQuery("SELECT b FROM Burger b", Burger.class);
        return query.getResultList();
    }

    @Override
    public Burger findById(Long id) {
        Burger burger = entityManager.find(Burger.class, id);
        if(burger == null){
            throw new BurgerException("Burger Not Found: " + id, HttpStatus.NOT_FOUND);
        }
        return burger;
    }

    @Transactional
    @Override
    public Burger save(Burger burger) {
        log.info("Save process has started");
        entityManager.persist(burger);
        log.info("Save process ended");
        return burger;
    }

    @Transactional
    @Override
    public Burger update(Burger burger) {
        return entityManager.merge(burger);
    }

    @Override
    public Burger remove(Long id) {
        Burger findRemove = findById(id);
        entityManager.remove(findRemove);
        return findRemove;
    }

    @Override
    public List<Burger> findByPrice(Integer price) {
        TypedQuery<Burger> priceQuery = entityManager.createQuery("SELECT b FROM Burger b WHERE b.price > :price ORDER BY b.price DESC", Burger.class);
        priceQuery.setParameter("price", price);
        return priceQuery.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        TypedQuery<Burger> breadQuery = entityManager.createQuery("SELECT b FROM Burger b WHERE b.breadType = :breadType ORDER BY b.name DESC", Burger.class);
        breadQuery.setParameter("breadType", breadType);
        return breadQuery.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {
        TypedQuery<Burger> priceQuery = entityManager.createQuery("SELECT b FROM Burger b WHERE b.contents LIKE CONCAT('%', :content,'%') ORDER BY b.name", Burger.class);
        priceQuery.setParameter("content", content);
        return priceQuery.getResultList();
    }
}
