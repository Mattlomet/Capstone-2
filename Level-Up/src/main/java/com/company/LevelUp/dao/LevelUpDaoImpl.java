package com.company.LevelUp.dao;

import com.company.LevelUp.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LevelUpDaoImpl implements LevelUpDao{
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_LEVEL_SQL =
            "insert into level_up (customer_id, points, member_date) values (?, ?, ?)";

    private static final String SELECT_LEVEL_SQL =
            "select * from level_up where level_up_id = ?";

    private static final String SELECT_LEVEL_BY_CUSTOMER_SQL =
            "select * from level_up where customer_id = ?";

    private static final String SELECT_ALL_LEVEL_SQL =
            "select * from level_up";

    private static final String UPDATE_LEVEL_SQL =
            "update level_up set customer_id = ?, points = ?, member_date = ? where level_up_id = ?";

    private static final String DELETE_LEVEL =
            "delete from level_up where level_up_id = ?";

    @Autowired
    public LevelUpDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public LevelUp saveLevelUp(LevelUp levelUp) {
        jdbcTemplate.update(
                INSERT_LEVEL_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        levelUp.setLevelUpId(id);

        return levelUp;
    }

    @Override
    public LevelUp getLevelUpById(int id) {
        try{
        return jdbcTemplate.queryForObject(SELECT_LEVEL_SQL, this::mapRowToLevelUp, id);
        } catch (EmptyResultDataAccessException e) {
        throw new IllegalArgumentException("Please enter a valid level up id");
        }
    }

    @Override
    public List<LevelUp> getAllLevelUps() {
        return jdbcTemplate.query(SELECT_ALL_LEVEL_SQL, this::mapRowToLevelUp);
    }

    @Override
    public void updateLevelUp(LevelUp levelUp) {
        jdbcTemplate.update(
                UPDATE_LEVEL_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate(),
                levelUp.getLevelUpId());
    }

    @Override
    public void deleteLevelUp(int id) {
        jdbcTemplate.update(DELETE_LEVEL, id);
    }

    @Override
    public LevelUp getLevelUpByCustomerId(int customerId) {
        try{
            return jdbcTemplate.queryForObject(SELECT_LEVEL_BY_CUSTOMER_SQL, this::mapRowToLevelUp, customerId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Please enter a valid customer id");
        }
    }

    private LevelUp mapRowToLevelUp(ResultSet rs, int rowNum) throws SQLException {
        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(rs.getInt("level_up_id"));
        levelUp.setCustomerId(rs.getInt("customer_id"));
        levelUp.setMemberDate(rs.getDate("member_date").toLocalDate());
        levelUp.setPoints(rs.getInt("points"));
        return levelUp;
    }
}
