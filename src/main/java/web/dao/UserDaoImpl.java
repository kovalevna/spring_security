package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public List<User> getAllUser() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User getUser(int id) {
        TypedQuery<User> q = em.createQuery(
                "SELECT u FROM User u WHERE u.id = :id",
                User.class
        );
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public void updateUser(int id, User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(int id) {
        em.remove(getUser(id));
    }

    public User findByUsername(String username){
        return em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name",username).getSingleResult();
    }

    @Override
    public User getUser(String username) {

        User user = em.createQuery("FROM User WHERE username=:username", User.class)
                .setParameter("username", username)
                .getSingleResult();

        return user;
    }
}
