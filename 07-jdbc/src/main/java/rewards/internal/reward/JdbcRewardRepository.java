package rewards.internal.reward;

import common.datetime.SimpleDate;
import org.springframework.jdbc.core.JdbcTemplate;
import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;

import javax.sql.DataSource;
import java.sql.*;

/**
 * JDBC implementation of a reward repository that records the result
 * of a reward transaction by inserting a reward confirmation record.
 */

// TODO-08 (Optional) : Inject JdbcTemplate directly to this repository class
// - Refactor the constructor to get the JdbcTemplate injected directly
//   (instead of DataSource getting injected)
// - Refactor RewardsConfig accordingly
// - Refactor JdbcRewardRepositoryTests accordingly
// - Run JdbcRewardRepositoryTests and verity it passes

// TODO-03: Refactor the cumbersome low-level JDBC code in JdbcRewardRepository with JdbcTemplate.
// - Add a field of type JdbcTemplate.
// - Refactor the code in the constructor to instantiate JdbcTemplate
//   object from the given DataSource object.
// - Refactor the confirmReward(...) and nextConfirmationNumber() methods to use
//   the JdbcTemplate object.
//
//   DO NOT delete the old JDBC code, just comment out the try/catch block.
//   You will need to refer to the old JDBC code to write the new code.
//
// - Run JdbcRewardRepositoryTests and verity it passes
//   (If you are using Gradle, make sure to comment out the exclude statement
//    in the test task in the build.gradle.)

public class JdbcRewardRepository implements RewardRepository {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public JdbcRewardRepository(JdbcTemplate jdbcTemplate) {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}

//	public JdbcRewardRepository(DataSource dataSource) {
//		this.dataSource = dataSource;
//		this.jdbcTemplate = new JdbcTemplate(dataSource);
//	}

	public RewardConfirmation confirmReward(AccountContribution contribution, Dining dining) {
		String sql = "insert into T_REWARD (CONFIRMATION_NUMBER, REWARD_AMOUNT, REWARD_DATE, ACCOUNT_NUMBER, DINING_MERCHANT_NUMBER, DINING_DATE, DINING_AMOUNT) values (?, ?, ?, ?, ?, ?, ?)";
		String confirmationNumber = nextConfirmationNumber();

		jdbcTemplate.update(sql,
				confirmationNumber,
				contribution.getAmount().asBigDecimal(),
				new Date(SimpleDate.today().inMilliseconds()),
				contribution.getAccountNumber(),
				dining.getMerchantNumber(),
				new Date(dining.getDate().inMilliseconds()),
				dining.getAmount().asBigDecimal()
		);

		// Update the T_REWARD table with the new Reward
//		try (Connection conn = dataSource.getConnection();
//			 PreparedStatement ps = conn.prepareStatement(sql)) {
//
//			ps.setString(1, confirmationNumber);
//			ps.setBigDecimal(2, contribution.getAmount().asBigDecimal());
//			ps.setDate(3, new Date(SimpleDate.today().inMilliseconds()));
//			ps.setString(4, contribution.getAccountNumber());
//			ps.setString(5, dining.getMerchantNumber());
//			ps.setDate(6, new Date(dining.getDate().inMilliseconds()));
//			ps.setBigDecimal(7, dining.getAmount().asBigDecimal());
//			ps.execute();
//		} catch (SQLException e) {
//			throw new RuntimeException("SQL exception occurred inserting reward record", e);
//		}
		
		return new RewardConfirmation(confirmationNumber, contribution);
	}

	private String nextConfirmationNumber() {
		String sql = "select next value for S_REWARD_CONFIRMATION_NUMBER from DUAL_REWARD_CONFIRMATION_NUMBER";
//		String nextValue;

		return jdbcTemplate.queryForObject(sql, String.class);
		
//		try (Connection conn = dataSource.getConnection();
//			 PreparedStatement ps = conn.prepareStatement(sql);
//			 ResultSet rs = ps.executeQuery()) {
//			rs.next();
//			nextValue = rs.getString(1);
//		} catch (SQLException e) {
//			throw new RuntimeException("SQL exception getting next confirmation number", e);
//		}
//
//		return nextValue;



	}
}
