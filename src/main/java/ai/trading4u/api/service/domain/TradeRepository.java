package ai.trading4u.api.service.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ai.trading4u.api.service.domain.entity.TradeData;
import ai.trading4u.api.service.domain.entity.TradeDataDto;

public interface TradeRepository extends JpaRepository<TradeData, Long> {
	
	@Query(value =
	        """
			SELECT DISTINCT 
				auth_key AS authKey, 
				order_symbol AS orderSymbol
			  FROM trade_data
			 WHERE req_exchange = :reqExchange
			   AND req_time IS NULL
			"""
	        , nativeQuery = true
	)
	List<TradeDataDto.AuthKeyAndSymbol> findDistinctByReqExchangeReqTimeIsNull(@Param("reqExchange") String reqExchange);

	List<TradeData> findByReqExchangeAndAuthKeyAndOrderSymbolAndReqTimeIsNullOrderByTradeNumAsc(String reqExchange, String authKey, String orderSymbol);

	List<TradeData> findTop999ByAuthKeyOrderByTradeNumDesc(String authKey); // limit 999, 역순

}
