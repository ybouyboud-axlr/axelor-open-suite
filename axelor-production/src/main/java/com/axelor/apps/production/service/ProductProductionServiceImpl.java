package com.axelor.apps.production.service;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.ProductTradingNameLine;
import com.axelor.apps.base.db.repo.ProductRepository;
import com.axelor.apps.stock.service.WeightedAveragePriceService;

public class ProductProductionServiceImpl {
	 protected WeightedAveragePriceService avgPriceService;
	  
	  public ProductProductionServiceImpl(WeightedAveragePriceService avgPriceService) {
		  this.avgPriceService = avgPriceService;
	  }

	public void computeTradingNamesCostPrice(Product product, Company company) {
		  for(ProductTradingNameLine productTradingNameLine : product.getProductTradingNameLineList()) {
			  if(product.getCostTypeSelect() == ProductRepository.COST_TYPE_LAST_PURCHASE_PRICE) {
				  productTradingNameLine.setCostPrice(product.getLastPurchasePrice());
			  }
			  else if(product.getCostTypeSelect() == ProductRepository.COST_TYPE_AVERAGE_PRICE) {
				  productTradingNameLine.setCostPrice(avgPriceService.computeAvgPriceForCompany(product, company) );
			  }
			  else if(product.getCostTypeSelect() == ProductRepository.COST_TYPE_LAST_PRODUCTION_PRICE) {
				  productTradingNameLine.setCostPrice(product.getLastProductionPrice());
			  }
		  }
	  }
}
