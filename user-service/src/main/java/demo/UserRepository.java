package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public User findById(Long id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT id,firstname,lastname FROM USERS WHERE id=?", new Object[]{id}, new UserRowMapper());
        }catch (Exception exception) {
            throw new UserNotFoundException(id);
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAll(int page, int itemPerPage) {
        int countRow = this.jdbcTemplate.queryForObject("select count(*) from users", int.class);
        int maxPage = (int) Math.ceil(countRow/(double) itemPerPage);
        if (page < 1 || itemPerPage < 1 || page > maxPage) {
            throw new InvalidArgumentsException();
        }
        System.out.println("Max page = "+maxPage);
        return this.jdbcTemplate.query("select id,firstname,lastname from users limit ?, ?", new Object[]{(page-1)*itemPerPage, itemPerPage}, new UserRowMapper());
    }

    @Transactional
    public void save(User user) {
        String sql = "INSERT INTO USERS(id, firstname, lastname) VALUES (?,?,?)";
        this.jdbcTemplate.update(sql, user.getId(), user.getFirstname(), user.getLastname());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM USERS WHERE id=?";
        this.jdbcTemplate.update(sql, id);
    }
}
