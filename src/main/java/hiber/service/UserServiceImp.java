package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional
    @Override
    public User getUserByModelAndSeriesCar(String model, int series) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        User user = null;

        try {
            transaction = session.beginTransaction();

            String hql = "FROM User u WHERE u.car.model=:carModel " +
                    "AND u.car.series=:carSeries";
            Query query = session.createQuery(hql, User.class);
            query.setParameter("carModel", model);
            query.setParameter("carSeries", series);
            List<User> users = ((org.hibernate.query.Query) query).list();
            user = users.get(0);
            transaction.commit();
        } catch (IllegalStateException | PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        session.close();

        return user;
    }

}
