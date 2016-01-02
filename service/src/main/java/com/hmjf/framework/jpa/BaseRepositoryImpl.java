package com.hmjf.framework.jpa;

import com.hmjf.dao.BaseRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import static org.springframework.data.jpa.repository.query.QueryUtils.getQueryString;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ningcl on 15/8/12.
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T,ID> implements BaseRepository<T,ID> {

    EntityManager entityManager;
    private final JpaEntityInformation<T, ?> entityInformation;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        entityManager = em;
        entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
    }

}
