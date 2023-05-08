package ai.trading4u.api.service.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ai.trading4u.api.service.domain.entity.TradeData;
import ai.trading4u.api.web.entity.TradingviewOrderReq;

public interface TradeRepository extends JpaRepository<TradeData, Long> {
	
	@Query(value =
	        """
			SELECT DISTINCT 
				auth_key AS authKey, 
				order_symbol AS orderSymbol
			  FROM trade_data
			 WHERE exchange_name = ?1
			   AND req_time IS NULL
			"""
	        , nativeQuery = true
	)
	List<TradeData> findDistinctByExchangeNameBytradeReqTimeIsNull(TradingviewOrderReq.OrderExchange exchangeName);

	List<TradeData> findByExchangeNameAndAuthKeyAndOrderSymbolOrderByTradeNumAsc(TradingviewOrderReq.OrderExchange exchangeName, String authKey, String orderSymbol);

//	List<TradeData> findTop100ByAuthKeyOrderByTradeNumDesc(String authKey); // limit 100, 역순

}
