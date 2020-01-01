package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.custom.QueryDAO;
import lk.ijse.dep.pos.entity.CustomEntity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    private EntityManager entityManager;


    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public CustomEntity getOrderInfo(int orderId) throws Exception {

        return (CustomEntity) entityManager.createNativeQuery("SELECT C.customerId, C.name, O.date  FROM Customer C INNER JOIN `Order` O ON C.customerId=O.customerId WHERE O.id=?")
                .setParameter(1, orderId).getSingleResult();

    }

    @Override
    public CustomEntity getOrderInfo2(int orderId) throws Exception {
       /* ResultSet rst = CrudUtil.execute("SELECT O.id, C.customerId, C.name, O.date, SUM(OD.qty * OD.unitPrice) AS Total  FROM Customer C INNER JOIN `Order` O ON C.customerId=O.customerId " +
                "INNER JOIN OrderDetail OD on O.id = OD.orderId WHERE O.id=? GROUP BY orderId", orderId);
        if (rst.next()){
            return new CustomEntity(rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getDouble(5));
        }else{
            return null;
        }*/

        return null;
    }


    @Override
    public List<CustomEntity> getOrdersInfo(String query) throws Exception {


        List<Object[]> resultList = entityManager.createNativeQuery("SELECT O.id, C.customer_id, C.name, O.date, SUM(OD.qty * OD.unit_price) AS Total  FROM Customer C INNER JOIN `Order` O ON C.customer_id= O.customer_id " +
                "INNER JOIN OrderDetail OD on O.id = OD.order_id WHERE O.id LIKE ?1 OR C.customer_id LIKE ?2 OR C.name LIKE ?3 OR O.date LIKE ?4 GROUP BY O.id")
                .setParameter(1, query)
                .setParameter(2, query)
                .setParameter(3, query)
                .setParameter(4, query)
                .getResultList();


        List<CustomEntity> al = new ArrayList<>();
        for (Object[] cols : resultList) {
            al.add(new CustomEntity((int) cols[0], (String) cols[1], (String) cols[2],
                    (java.sql.Date) cols[3], (Double) cols[4]));
        }
        return al;

    }


}
