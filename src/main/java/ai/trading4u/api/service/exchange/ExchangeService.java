package ai.trading4u.api.service.exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.trading4u.api.service.domain.AuthKeyService;
import ai.trading4u.api.service.domain.TradeRepository;
import ai.trading4u.api.service.domain.entity.TradeData;
import ai.trading4u.api.service.domain.entity.TradeDataDto;
import ai.trading4u.api.service.exchange.bybit.BybitService;
import ai.trading4u.api.web.entity.AuthKey;
import ai.trading4u.api.web.entity.TradingviewOrderReq;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExchangeService {
	
	@Autowired ObjectMapper objectMapper;

	@Autowired TradeRepository tradeRepository;
	
	@Autowired AuthKeyService authKeyService;
	
	@Autowired BybitService bybitService;
	
	public void saveRequest(TradingviewOrderReq tvOrder) {
		
		String paramJson;
		try {
			paramJson = objectMapper.writeValueAsString(tvOrder);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Json Parsing Error.");
		}
		
		TradeData data = new TradeData(
				tvOrder.getAuthKey(), 
				tvOrder.getAlertName(), 
				tvOrder.getAlertTime(), 
				tvOrder.getOrderExchange().name(), 
				tvOrder.getOrderSymbol(), 
				tvOrder.getOrderMode(),
				tvOrder.getOrderId(),
				tvOrder.getOrderAction().name(),
				tvOrder.getOrderSize(),
				tvOrder.getTpPrice(),
				paramJson);
		
		tradeRepository.save(data);
		
	}
	
//	@Scheduled(fixedDelay = 1000, initialDelay = 3000) // fixedDelay : milliseconds 단위로, 이전 Task의 종료 시점으로부터 정의된 시간만큼 지난 후 Task를 실행한다.
	public void scheduledCallBybitApi() {
		while(true) {
			List<TradeDataDto.AuthKeyAndSymbol> targetSymbolList = tradeRepository.findDistinctByReqExchangeReqTimeIsNull(TradingviewOrderReq.OrderExchange.BYBIT.name());
			if(targetSymbolList == null || targetSymbolList.size() == 0) {
				break;
			}
			callBybitApi(targetSymbolList);
		}
	}
	
	public void callBybitApi(List<TradeDataDto.AuthKeyAndSymbol> targetSymbolList) {
		// 여기서 db 읽어서 계정별 종목별 비동기 api 호출
		
		List<CompletableFuture<String>> futures = new ArrayList<>();
		for(TradeDataDto.AuthKeyAndSymbol targetSymboldata : targetSymbolList) {
			
			// 계정별 종목별 async thread 시작~
			AuthKey authKeyObj = authKeyService.resolveKey(targetSymboldata.getAuthKey());
			CompletableFuture<String> future = bybitService.requestBybit(authKeyObj, targetSymboldata.getOrderSymbol());
			futures.add(future);
		}
		
		List<String> retList = CompletableFuture.allOf(futures.toArray(
				new CompletableFuture[futures.size()])
				).thenApply(
						result -> futures.stream().map(future -> future.join()).collect(Collectors.toList())
						)
		.join();
		
		for(String s : retList) {
			log.debug(s);
		}
	}
	
	
}
