package business.custom.impl;

import business.custom.ItemBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.ItemDAO;
import db.JPAUtil;
import entity.Item;
import util.ItemTM;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    private static ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    public List<ItemTM> getAllItems(){
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);

        List<Item> allItems = null;
        List<ItemTM> items = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            allItems = itemDAO.findAll();
            entityManager.getTransaction().commit();
        } catch (SQLException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        for (Item item : allItems) {
            items.add(new ItemTM(item.getCode(),item.getDescription(),item.getUnitPrice().doubleValue(),item.getQtyOnHand()));
        }
        return items;
    }

    public void saveItem(String code, String description, double unitPrice, int qtyOnHand){
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);
        try {
            entityManager.getTransaction().begin();
            itemDAO.save(new Item(code,description,BigDecimal.valueOf(unitPrice),qtyOnHand));
            entityManager.getTransaction().commit();
        } catch (SQLException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void updateItem(String code, String description, double unitPrice, int qtyOnHand){
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);
        try {
            entityManager.getTransaction().begin();
            itemDAO.update(new Item(code,description,BigDecimal.valueOf(unitPrice),qtyOnHand));
            entityManager.getTransaction().commit();
        } catch (SQLException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void deleteItem(String code){
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);
        try {
            entityManager.getTransaction().begin();
            itemDAO.delete(code);
            entityManager.getTransaction().commit();
        } catch (SQLException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public String generateNewItemId(){
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(entityManager);
        try {
            entityManager.getTransaction().begin();
            String lastItemId= itemDAO.getLastItemId();
            entityManager.getTransaction().commit();
            int lastNumber = Integer.parseInt(lastItemId.substring(1, 4));
            if (lastNumber==0) {
                lastNumber++;
                return "I001";
            } else if (lastNumber<9) {
                lastNumber++;
                return "I00" +lastNumber;
            } else if (lastNumber<99) {
                lastNumber++;
                return "I0" +lastNumber;
            }
            else{
                lastNumber++;
                return "I" +lastNumber;
            }
        } catch (SQLException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
}
