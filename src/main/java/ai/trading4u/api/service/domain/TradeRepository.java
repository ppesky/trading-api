package ai.trading4u.api.service.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ai.trading4u.api.service.domain.entity.TradeData;

public interface TradeRepository extends JpaRepository<TradeData, Long> {
	
	@Query(value =
	        """
			SELECT DISTINCT 
				auth_key AS authKey, 
				order_symbol AS orderSymbol
			  FROM trade_data
			 WHERE req_exchange = ?1
			   AND req_time IS NULL
			"""
	        , nativeQuery = true
	)
	List<TradeData> findDistinctByReqExchangeBytradeReqTimeIsNull(String reqExchange);

	List<TradeData> findByReqExchangeAndAuthKeyAndOrderSymbolOrderByTradeNumAsc(String reqExchange, String authKey, String orderSymbol);

//	List<TradeData> findTop100ByAuthKeyOrderByTradeNumDesc(String authKey); // limit 100, 역순

}
