package lk.ijse.dep.pos.dao;

import javax.persistence.EntityManager;

public interface SuperDAO {

    public abstract void setEntityManager(EntityManager entityManager);
}
