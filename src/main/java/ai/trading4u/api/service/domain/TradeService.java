package ai.trading4u.api.service.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.trading4u.api.service.domain.entity.TradeData;

@Service
public class TradeService {
	
	@Autowired TradeRepository tradeRepository;
	
	public List<TradeData> findTop999ByAuthKeyOrderByTradeNumDesc(String authKey) {
		return tradeRepository.findTop999ByAuthKeyOrderByTradeNumDesc(authKey);
	}

}
