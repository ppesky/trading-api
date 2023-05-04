package ai.withtrade.api.service.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.withtrade.api.service.domain.entity.TradeData;

public interface TradeRepository extends JpaRepository<TradeData, Long> {
	
	List<TradeData> findByAuthKey(String authKey);

	List<TradeData> findByAuthKeyBytradeReqTimeIsNull(String authKey);
	
	List<TradeData> findTop100ByAuthKeyOrderByTradeNumDesc(String authKey); // limit 100, 역순

}
